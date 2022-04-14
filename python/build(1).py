#! /bin/python

import os
import json

import eventlet
eventlet.monkey_patch()  # noqa

from git import Repo
from git import InvalidGitRepositoryError

from distutils import dir_util

import subprocess

import time
import docker
import yaml

import argparse


def str2bool(v):
    if v.lower() in ('yes', 'true', 't', 'y', '1'):
        return True
    elif v.lower() in ('no', 'false', 'f', 'n', '0'):
        return False
    else:
        raise argparse.ArgumentTypeError('Boolean value expected.')


pwd = os.path.dirname(os.path.abspath(__file__))
project = os.path.basename(pwd)


verbose = False
arch = 'amd64'
device = 'nv'


class VendorItem(object):
    def __init__(self, name, path, url, branch, commit, subdir):
        self.name = name
        self.path = path
        self.url = url
        self.branch = branch
        self.commit = commit
        self.subdir = subdir


class VendorItems(object):
    def __init__(self, items):
        self._dep_items = items

    @classmethod
    def load(cls, deps_file=None):
        if deps_file is None:
            deps_file = os.path.join(pwd, 'vendor-' + args.arch + '-' + args.device + '.yml')

        items = []
        with open(deps_file, 'r') as f:
            # docs = yaml.load_all(f, Loader=yaml.FullLoader)
            docs = yaml.load_all(f, Loader=yaml.BaseLoader)
            for deps in docs:
                for dep in deps:
                    # print(dep)
                    d = VendorItem(dep['name'], dep['path'], dep['url'], dep.get('branch', None), dep.get('commit', None), dep.get('subdir', None))
                    items.append(d)

        return VendorItems(items)

    @property
    def items(self):
        return self._dep_items


class RPMBuilder(object):
    def __init__(self, name, opts):
        self.name = name
        self.opts = opts

    def setup(self):
        pass

    def run_commands(self, cmds, is_output=True):
        pass

    def run_command(self, cmd, is_output=True):
        pass

    def teardown(self):
        pass

    def _drain(self, logs):
        for line in logs:
            if type(line) == bytes:
                line = line.decode('utf-8')
            print(line.strip())


class DockerBuilder(RPMBuilder):
    def __init__(self, *args, **kwargs):
        super(DockerBuilder, self).__init__(*args, **kwargs)
        client = docker.from_env()

        self.client = client
        self.container = None


    def setup(self):
        self.client.pull(self.opts['image'])
        self.container = self.client.create_container(**self.opts)
        self.client.start(self.container)

        return self.container

    def run_commands(self, cmds, is_output=True):
        ret = []
        for cmd in cmds:
            if type(cmd) is list:
                cwd = cmd[0]
                cmd = cmd[1]

                cmd = "cd " + cwd + " && " + cmd

            ret.append(cmd)

        command = "sh -c '%s'" % (";".join(ret))
        if is_output:
            print(command)
        ex = self.client.exec_create(self.container, command)
        logs = self.client.exec_start(ex, tty=True, stream=True)
        if is_output:
            self._drain(logs)
        else:
            for line in logs:
                pass

    def run_command(self, cmd, is_output=True):
        command = "cd " + cmd[0] + " && " + cmd[1]
        command = "sh -c '%s'" % command
        if is_output:
            print(command)
        ex = self.client.exec_create(self.container, command)
        logs = self.client.exec_start(ex, tty=True, stream=True)
        if is_output:
            self._drain(logs)
        else:
            lines = []
            for line in logs:
                if type(line) == bytes:
                    line = line.decode('utf-8')
                lines.append(line.strip())
            return lines

    def teardown(self):
        # remove docker container
        self.client.stop(self.container)
        self.client.remove_container(self.container, v=True, force=True)


class GitlabBuilder(RPMBuilder):
    def setup(self):
        pass

    def _call(self, command, is_output):
        cwd = None
        cmd = command
        if type(command) is list:
            cwd = command[0]
            cmd = command[1]

        sp = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, shell=True, cwd=cwd)
        stdout, stderr = sp.communicate()
        stdout_lines = []
        if stdout and is_output:
            print("stdout for ", command)
            self._drain(stdout.decode('utf-8').split('\n'))
        else:

            for line in stdout.decode('utf-8').split('\n'):
                if type(line) == bytes:
                    line = line.decode('utf-8')
                stdout_lines.append(line.strip())
        if stderr:
            print("stderr for ", command)
            self._drain(stderr.decode('utf-8').split('\n'))

        if not is_output:
            return stdout_lines

    def run_commands(self, cmds, is_output=True):
        for cmd in cmds:
            self._call(cmd, is_output)

    def run_command(self, cmd, is_output=True):
        stdout = self._call(cmd, is_output)
        return stdout


def _git_checkout(git_remote, path, branch, commit):
    if not os.path.isdir(path):
        os.makedirs(path)

    try:
        repo = Repo(path)
        # print(path, ' repo already exists.')
    except InvalidGitRepositoryError:
        print(path, ' repo not exists, cloning...')
        # new in GitPython
        # repo = Repo.clone_from(git_remote, path, multi_options=['--recurse-submodules'])
        # old in GitPython 1.0.1
        repo = Repo.clone_from(git_remote, path, recurse_submodules=True)
    except Exception:
        raise

    # force reset git remote
    repo.git.remote('set-url', 'origin', git_remote)

    # reset this repo
    repo.git.reset('--hard')
    repo.git.fetch('-av')
    # checkout the needed branch
    if commit is not None:
        repo.git.checkout(commit)
    else:
        repo.git.checkout('origin/' + branch)

    # reset any submodules
    repo.git.submodule('foreach', 'git', 'reset', '--hard')
    repo.git.submodule('update', '--init', '--recursive', '--remote')


def _do_vendor(root, dep):
    if not os.path.isdir(dep.path):
        os.makedirs(dep.path)

    if dep.url.startswith("../"):
        head, tail = root.split("sensetime.com")
        git_remote = head + "sensetime.com" + os.path.normpath(os.path.join(tail, dep.url))
    else:
        git_remote = dep.url
    print(git_remote)

    try:
        # if subdir is defined clone to some temp dir, and copy subdir to dep.path
        if dep.subdir is not None:
            tmp_path = os.path.join("/tmp", dep.path)
            _git_checkout(git_remote, tmp_path, dep.branch, dep.commit)
            dir_util.copy_tree(os.path.join(tmp_path, dep.subdir), dep.path)
        else:
            _git_checkout(git_remote, dep.path, dep.branch, dep.commit)
    except Exception:
        import traceback
        traceback.print_exc()  # noqa
        print('==> :( vendor failure: ', dep.path)
    else:
        print('==> :) vendor success: ', dep.path)


def _vendor(root, vendor_items):
    threads = eventlet.GreenPool(size=20)
    queue = eventlet.queue.Queue(maxsize=10)

    def _consume():
        while True:
            try:
                vendor_item = queue.get(timeout=1)
                _do_vendor(root, vendor_item)
            except Exception:
                return

    def _produce():
        for vendor_item in vendor_items:
            queue.put(vendor_item)

    # 10 consumers, 1 producer
    for i in range(10):
        threads.spawn_n(_consume)
    threads.spawn_n(_produce)

    threads.waitall()


def _prune(vendor_items):
    for dep in vendor_items:
        try:
            print(dep.path)
            dir_util.remove_tree(os.path.join(dep.path, '.git'))
        except Exception:
            pass

# return first url of origin remote
def _get_root_origin_url():
    root = Repo(pwd)
    try:
        url = root.git.config('--get', 'remote.origin.url')
        return url
    except Exception:
        return None


def deps(args):
    vendor_items = VendorItems.load().items

    root = _get_root_origin_url()
    if not root:
        raise 'product root dir is not a git repo, aborting.'
    print("product root git repo is: %s" % root)

    _vendor(root, vendor_items)


def prune(args):
    vendor_items = VendorItems.load().items
    _prune(vendor_items)


def printv(args):
    vendor_items = VendorItems.load().items

    if args.images is True:
        working_dir = "/" + project

        if args.gitlab:
            working_dir = pwd

        with open("ansible/group_vars/all.yml", "a") as f:
            f.write("\nhost_architecture: %s" % args.arch)

        name = "printv"
        opts = {
            "name": name,
            "image": "registry.sensetime.com/viper/buildpy:4.0-a01c2f2",
            "command": "sleep 1000000000",
            "host_config": {"binds": ["%s:%s" % (pwd, working_dir)]},
            "working_dir": working_dir,
            "environment": {"TZ": "Asia/Shanghai"},
            "detach": True,
        }

        if args.gitlab:
            builder = GitlabBuilder(name, opts)
        else:
            builder = DockerBuilder(name, opts)

        builder.setup()

        # prepare container, install packages
        prepare_cmds = [
            [working_dir, "make deps " + "arch=" + args.arch + " device=" + args.device],
            [working_dir, "export ANSIBLE_CONFIG=./ansible.cfg"],
            [working_dir, "export PASSWORD_MANAGER_VAULT_PASSWORD='c2Vuc2V0aW1l'"],
            [working_dir, "export HTTPS_SIGNGEN_PASSWORD='eUcySDc4QXQ2UklHMStOYXhJZnBxSzVXTW1UQUVkUTAkYnNDdEFEdmJNYTg9Cg=='"],
        ]
        builder.run_commands(prepare_cmds, is_output=False)

        print_cmds = [working_dir + "/ansible", "molecule test -s info"]

        stdout = builder.run_command(print_cmds, is_output=False)
        builder.teardown()

        # load iamges from ansible-product-sensefoundry/ansible/ansible-infra/ansible/roles/infra-defaults/defaults/image-versions-k8s-amd64.yml
        infra_images_path = "./ansible/ansible-infra/ansible/roles/infra-defaults/defaults/image-versions-k8s-%s.yml" % args.arch
        infra_images = yaml.load(open(infra_images_path), Loader=yaml.BaseLoader)
        images = {}
        if stdout:
            product_images = None
            found = False
            for line in stdout:
                if 'image_versions =>' in line.strip():
                    product_images = line.strip().split("=>")[-1]
                    product_images = product_images.split('"')[0]
                    product_images = product_images.replace(" u'", " '")
                    product_images = product_images.replace("{u'", "{'")
                    found = True
                    continue
                if found and "'}" not in product_images:
                    product_images = product_images + line.strip().split('"')[0]
                    product_images = product_images.replace("}}", "}")
                    break

            # merge infra_images with image_versions
            try:
                images.update(eval(product_images))
            except Exception:
                import traceback
                traceback.print_exc()  # noqa
                print('==> :( builder command: ', print_cmds)
                print('==> :( builder result: ', stdout)
                print('==> :( eval product images failure: ', product_images)
            
            images.update(infra_images)
            if args.output == "yaml" and product_images:
                print(yaml.safe_dump(images, encoding='utf-8', default_flow_style=False))
            elif product_images:
                print(json.dumps(images))
            else:
                print("print image failed")
                exit(1)

    if args.roles is True:
        for item in vendor_items:
            if 'ansible-role' in item.url:
                print("%s: %s" % (item.name, item.branch))


def buildrpm(args):
    package = args.package
    version = args.version
    release = args.release
    upload = args.upload
    device = args.device

    working_dir = "/" + project
    building_dir = "/tmp/" + project

    if args.gitlab:
        working_dir = pwd

    name = "build-rpm-" + str(int(time.time()))

    if args.gitlab:
        opts = {
            "name": name,
            "image": "registry.sensetime.com/viper/buildpy:4.0-a01c2f2",
            "command": "sleep 1000000000",
            "host_config": {"binds": ["%s:%s" % (pwd, working_dir)]},
            "working_dir": working_dir,
            "environment": {"TZ": "Asia/Shanghai"},
            "detach": True,
        }
        builder = GitlabBuilder(name, opts)
    else:
        home_dir = os.path.expanduser('~')
        ssh_key_file = os.path.join(home_dir, '.ssh', 'id_rsa')

        opts = {
            "name": name,
            "image": "registry.sensetime.com/viper/buildpy:4.0-a01c2f2",
            "command": "sleep infinity",
            "host_config": {"binds": ["%s:%s" % (pwd, working_dir),
                                      "%s:%s:ro" % (ssh_key_file, '/root/.ssh/id_rsa_host')]},
            "working_dir": working_dir,
            "environment": {"TZ": "Asia/Shanghai"},
            "detach": True,
        }
        builder = DockerBuilder(name, opts)

    builder.setup()

    if not args.gitlab:
        ssh_cfg_cmds = [
            'cat /root/.ssh/id_rsa_host > /root/.ssh/id_rsa',
            'chmod 400 /root/.ssh/id_rsa',
            'echo "StrictHostKeyChecking no" > /root/.ssh/config',
            'echo "UserKnownHostsFile /dev/null" >> /root/.ssh/config',
        ]
        builder.run_commands(ssh_cfg_cmds)

    package_cmds = [
        "rsync -a --progress %s/ %s --exclude dist --exclude venv > /dev/null" % (working_dir, building_dir),
        [building_dir, "python build.py --arch " + args.arch + " --device " + args.device + " deps"],
        [building_dir, "python build.py --arch " + args.arch + " --device " + args.device + " prune"],
        [building_dir + "/buildrpm", "flake8 --ignore=E501 infra_cli"],
    ]
    builder.run_commands(package_cmds)

    build_cmd = "./build.sh"
    if package:
        build_cmd = build_cmd + " --package " + package
    if version:
        build_cmd = build_cmd + " --version " + version
    if release:
        build_cmd = build_cmd + " --release " + release
    if upload:
        build_cmd = build_cmd + " --upload true "
    build_cmd = [building_dir + "/buildrpm/contrib/rpm", build_cmd]

    dist_cmd = "cp -r %s/dist %s/ && chmod -R 777 %s/dist " % (building_dir, working_dir, working_dir)

    building_cmds = [build_cmd, dist_cmd]
    builder.run_commands(building_cmds)

    builder.teardown()


parser = argparse.ArgumentParser()
parser.add_argument('--verbose', dest='verbose', type=bool)
parser.add_argument('--arch', dest='arch', type=str)
parser.add_argument('--device', dest='device', type=str)

subparsers = parser.add_subparsers(title='subcommands')

deps_parser = subparsers.add_parser('deps', help="download dependencies from git repository to local in vendor.json")
deps_parser.set_defaults(func=deps)

prune_parser = subparsers.add_parser('prune', help="remove .git folder in every vendor path")
prune_parser.set_defaults(func=prune)

print_parser = subparsers.add_parser('print', help="print some version infos, such as image versions, role versions")
print_parser.add_argument('--images', dest='images', type=str2bool)
print_parser.add_argument('--roles', dest='roles', type=str2bool)
print_parser.add_argument('--gitlab', dest='gitlab', type=str2bool)
print_parser.add_argument('--output', dest='output', type=str)
print_parser.set_defaults(func=printv)

rpm_parser = subparsers.add_parser('rpm')
rpm_parser.add_argument('--package', dest='package', type=str)
rpm_parser.add_argument('--version', dest='version', type=str)
rpm_parser.add_argument('--release', dest='release', type=str)
rpm_parser.add_argument('--upload', dest='upload', type=str2bool)
rpm_parser.add_argument('--gitlab', dest='gitlab', type=str2bool)
rpm_parser.set_defaults(func=buildrpm)



if __name__ == "__main__":
    args = parser.parse_args()
    args.func(args)
    if args.arch is None:
        args.arch = "amd64"
    if args.device is None:
        args.device = "nv"

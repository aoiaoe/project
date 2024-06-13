import tkinter as tk
from tkinter import filedialog
import requests
from urllib.parse import urlparse
from tkinter import messagebox
import spider

global new_file_name
global selected_directory

def download_file(url, save_path):
    response = requests.get(url)
    file_name = urlparse(url).path.split("/")[-1]
    full_path = save_path + '/' + file_name

    with open(full_path, 'wb') as file:
        file.write(response.content)

    msg = f"File downloaded successfully to: {full_path}"
    show_popup(msg)
    # print()

# 弹窗
def show_popup(msg):
    messagebox.showinfo("警告", msg)

def browse_url():
    check_res = check()
    if not check_res:
        return
    url = entry_url.get()
    download_file(url, selected_directory)

def browse_directory():

    selected_directory = filedialog.askdirectory()
    label_directory.config(text=selected_directory)

def check():
    # print("selected_directory:{}", selected_directory)
    # if not selected_directory:
    #     show_popup("请先选择保存的文件夹!")
    #     return False

    new_file_name = entry_file_name.get()
    if not new_file_name:
        show_popup("请先输入文件名,并确保文件名不存在，否则会覆盖已有文件")
        return False

# 创建主窗口
root = tk.Tk()
root.title("URL Downloader")

# 添加URL输入框和下载按钮
label_url = tk.Label(root, text="输入网站:")
label_url.pack(pady=10)

entry_url = tk.Entry(root, width=50)
entry_url.pack(pady=10)

button_download = tk.Button(root, text="Download", command=browse_url)
button_download.pack(pady=10)

# 添加选择目录按钮和标签
button_select_directory = tk.Button(root, text="Select Directory", command=browse_directory)
button_select_directory.pack(pady=10)

label_directory = tk.Label(root, text="Selected Directory: ")
label_directory.pack(pady=10)

# 添加URL输入框和下载按钮
label_file = tk.Label(root, text="输入文件名(文件名必须不存在,否则会覆盖已有文件):")
label_file.pack(pady=10)

entry_file_name = tk.Entry(root, width=50)
entry_file_name.pack(pady=10)

# 启动主循环
root.mainloop()

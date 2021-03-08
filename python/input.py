#encoding:utf-8

import xlrd
import xlwt
import argparse
import requests
import time



def read_excel(token, path):
    # 打开文件
    workBook = xlrd.open_workbook(path);

    sheet1_content1 = workBook.sheet_by_index(0);

    # 3. sheet的名称，行数，列数
    print("工作簿名称:", sheet1_content1.name, " 总行数(含表头)" ,sheet1_content1.nrows);
    print("___________________________________________________________________________________")

    for i in range(sheet1_content1.nrows):
        if i > 0:
            x = i
            print("第", x+1, "行")
            userName = sheet1_content1.row(i)[0].value
            phone = sheet1_content1.row(i)[1].value
            phone = str(int(phone))
            idcardNo = sheet1_content1.row(i)[2].value
            send_post(token, userName, phone, idcardNo)
            time.sleep(1)


def deal():
    parser = argparse.ArgumentParser(description='manual to this script')
    parser.add_argument('--token', type=str, default = None)
    parser.add_argument('--path', type=str, default= None)
    args = parser.parse_args()
    token = args.token
    path = args.path

    print("token:", token)
    print("文件路径:", path)
    read_excel(token, path)


def send_post(token='', name='', phone='', idcard=''):
    print("名字: ", name, " 手机号:", phone, " 身份证:", idcard)
    if(len(token) == 0 or len(name) == 0 or len(str(phone)) == 0 or len(str(idcard)) == 0):
        print("注意有值为空了, 操作失败。。请查明")
        print("___________________________________________________________________________________")
        return

    data = {
        'token':token,
        'name': name,
        'nation':'汉族',
        'phone':phone,
        'idCard':idcard,
        'specialty':'声乐',
        'region':'511321',
        'serviceRegion':'511321',
        'attribution':'1517',
        'serviceIntention':'不限'
    }

    print("发送请求")
    # res = requests.post(url, data)
    # print("服务器响应:",res.text)
    print("___________________________________________________________________________________")

deal()

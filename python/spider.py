import urllib.request as req
import xlwt
import json

def req_url(url):
    headers = {
        'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36'
    }
    request = req.Request(url=url, headers=headers)
    response = req.urlopen(request)
    res = response.read().decode("utf-8")
    # print(res)
    jsonStr = json.loads(res)
    for jsonObj in jsonStr:
        print(jsonObj['id'])
    # print(jsonStr)
    return jsonStr

def save_file(data_list, file_path):
    book = xlwt.Workbook(encoding="utf-8",style_compression=0)
    sheet = book.add_sheet("数据", cell_overwrite_ok=True)
    col = {'ID', '姓名', '性别'}
    index = 0;
    for item in col:
        sheet.write(0, index, item)
        index+=1
    row = 1;
    for jonsObj in data_list:
        sheet.write(row, 0, jonsObj["id"])
        sheet.write(row, 1, jonsObj["name"])
        sheet.write(row, 2, jonsObj["sex"])
        row+=1
    book.save(file_path)


# # 从网站上获取数据
# jsonList = req_url("http://localhost:18104/demoBeans")
# # 将数据保存成excel
# save_file(jsonList, "D:\\demoList.xls")



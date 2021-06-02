import json


def getData():
  file = open(r"D:\Users\Desktop\response6.json", "rb")
  fileJson = json.load(file)
  field = fileJson["data"]
  list = field["list"]
  total = 0
  for i in range(len(list)):
    # if(1687839597094013 != list[i]['creative_id']):
    print("cId:", list[i]['creative_id'], "   cost: ", list[i]['cost'])
    total = total + list[i]['cost']
  print(total)


def getCreativeStatus():
  file = open(r"D:\Users\Desktop\delete_creatives.json", "rb")
  fileJson = json.load(file)
  field = fileJson["data"]
  list = field["list"]
  total = 0
  for i in range(len(list)):
    # if(1687839597094013 != list[i]['creative_id']):
    print("cId:", list[i]['creative_id'], "   status: ", list[i]['status'])
  print(total)



def demo():

  jsonstr = '''[
          {
            "key_as_string" : "2021-04-26 02",
            "key" : 1619402400000,
            "doc_count" : 14
          },
          {
            "key_as_string" : "2021-04-26 03",
            "key" : 1619406000000,
            "doc_count" : 58
          },
          {
            "key_as_string" : "2021-04-26 04",
            "key" : 1619409600000,
            "doc_count" : 59
          },
          {
            "key_as_string" : "2021-04-26 05",
            "key" : 1619413200000,
            "doc_count" : 60
          },
          {
            "key_as_string" : "2021-04-26 06",
            "key" : 1619416800000,
            "doc_count" : 59
          },
          {
            "key_as_string" : "2021-04-26 07",
            "key" : 1619420400000,
            "doc_count" : 54
          },
          {
            "key_as_string" : "2021-04-26 08",
            "key" : 1619424000000,
            "doc_count" : 58
          },
          {
            "key_as_string" : "2021-04-26 09",
            "key" : 1619427600000,
            "doc_count" : 58
          },
          {
            "key_as_string" : "2021-04-26 10",
            "key" : 1619431200000,
            "doc_count" : 57
          },
          {
            "key_as_string" : "2021-04-26 11",
            "key" : 1619434800000,
            "doc_count" : 58
          },
          {
            "key_as_string" : "2021-04-26 12",
            "key" : 1619438400000,
            "doc_count" : 56
          },
          {
            "key_as_string" : "2021-04-26 13",
            "key" : 1619442000000,
            "doc_count" : 53
          },
          { 
            "key_as_string" : "2021-04-26 14",
            "key" : 1619445600000,
            "doc_count" : 56
          },
          {
            "key_as_string" : "2021-04-26 15",
            "key" : 1619449200000,
            "doc_count" : 58
          }
        ]'''

  total = 0
  jsonObj = json.loads(jsonstr) 
  for i in range(len(jsonObj)):
    total = total + int(jsonObj[i]['doc_count'])

  print(total)

# getData()

getCreativeStatus()



0
# 828.8699999999988
# 2090.3699999999985
# 
# 
# 2665.3199999999993
# 
# 
# 179.83
# 74.09000000000005
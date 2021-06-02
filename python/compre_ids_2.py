import json
import xlrd
import xlwt
import argparse
import requests
import time
from random import randint
import requests
import compare_ids

SECOND_AGENT_ACCOUNT_PATH = "/open_api/2/agent/advertiser/select/"
second_agetn_ids = [81657402978,84913375290,91754195316,92843752171,104779097942,56803574940552,1024374517540180,1629600995158019,1630575599558659,2713222254435796,1632849229251598,1639363376522244,1639455884540941,1640644282792974,1641173193989128,1642369985188868,1643169399374852,1644344471629832,1644814495101964,1647173901330436,1648238939859976,1649329730762764,1649533903228941,1650054885019652,1653420518876173,1653966803159044,1664105580141576,1664814783378440,1665006305469454,1680417403438093,1682571492126727]
page_size = 1000
AGENT_IDS = r"D:\Users\Desktop\agent_account_ids.json"
SECOND_AGENT_IDS = r"D:\Users\Desktop\second_agent_account_ids.json"
DB_IDS = r"D:\Users\Desktop\ids_in_db.txt"

def write_to_file(ids):
	file = open(r"D:\Users\Desktop\agent_account_ids.json", "a")
	for i in range(len(ids)):
		file.write(str(ids[i]) + ",\r")


def get_second_agent_account_ids(page, page_size, advertiser_id, path):
    # Args in JSON format
    my_args = "{\"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\"}" % (page_size, advertiser_id, page)
    jsonString = compare_ids.get(path, my_args)
    # print(jsonString)
    # creative_json = json.loads(jsonString)
    creative_json_array = None
    if('data' in jsonString):
        creative_json_array = jsonString['data']
    if('list' in creative_json_array):
        creative_json_array = creative_json_array['list']
    list = []
    for i in range(len(creative_json_array)):
        list.append(creative_json_array[i])
    return list 


# for i in range(len(second_agetn_ids)):
# 	for j in range(2):
# 		page = j + 1
# 		time.sleep(1)
# 		id_list = get_second_agent_account_ids(page, 1000, second_agetn_ids[i], SECOND_AGENT_ACCOUNT_PATH)
# 		write_to_file(id_list)


# for i in range(21):
# 	page = i + 1
# 	time.sleep(1)
# 	id_list = get_second_agent_account_ids(page, 1000, 6880408002, SECOND_AGENT_ACCOUNT_PATH)
# 	print(len(id_list))
# 	write_to_file(id_list)



file = open(AGENT_IDS, "rb")
agent_id_list = json.load(file)

file = open(SECOND_AGENT_IDS, "rb")
send_agent_id_list = json.load(file)

file = open(DB_IDS, "rb")
db_ids = json.load(file)

includ_is = []
for i in range(len(db_ids)):
	if(db_ids[i] not in agent_id_list):
		includ_is.append(db_ids[i])

print(len(includ_is))
print(includ_is)
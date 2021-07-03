import json
import xlrd
import xlwt
import argparse
import requests
import time
from random import randint
import requests
import do_pgsql

from six import string_types
from six.moves.urllib.parse import urlencode, urlunparse 

def read_excel(path):
    # 打开文件
    workBook = xlrd.open_workbook(path);

    sheet1_content1 = workBook.sheet_by_index(0); 

    # 3. sheet的名称，行数，列数
    print("工作簿名称:", sheet1_content1.name, " 总行数(含表头)" ,sheet1_content1.nrows);
    print("___________________________________________________________________________________")

    list = []
    for i in range(sheet1_content1.nrows):
        list.append(sheet1_content1.row(i)[0].value)
    return list

# list = read_excel(r"D:\Users\Desktop\creative_ids.xls")
# print(len(list))
# print(list[2])
# i = '1698185480081501'

ACCESS_TOKEN = "0523190182bc8a9f5c44fb1c0a8d812f63a02b53"
GET_CREATIVE_PATH = "/open_api/2/creative/get/"
GET_CREATIVE_REPORT_PATH = "/open_api/2/report/creative/get"
GET_AD_REPORT_PATH = "/open_api/2/report/ad/get"
GET_AGENT_CHILD_ACCOUNT = "/open_api/2/agent/advertiser/select/"
  
def build_url(path, query=""):
    # type: (str, str) -> str
    """
    Build request URL
    :param path: Request path
    :param query: Querystring
    :return: Request URL
    """
    scheme, netloc = "https", "ad.oceanengine.com"
    return urlunparse((scheme, netloc, path, "", query, ""))

def get(path, json_str):
    # type: (str) -> dict
    """
    Send GET request
    :param json_str: Args in JSON format
    :return: Response in JSON format
    """
    args = json.loads(json_str)
    print(args)
    query_string = urlencode({k: v if isinstance(v, string_types) else json.dumps(v) for k, v in args.items()})
    url = build_url(path, query_string)
    headers = {
        "Access-Token": ACCESS_TOKEN,
    }
    rsp = requests.get(url, headers=headers)
    return rsp.json()

def get_creative_ids(page):
    fields_list = ["creative_id"]
    fields = json.dumps(fields_list)
    page_size = 1000
    advertiser_id = 1694283573329934

    # Args in JSON format
    my_args = "{\"fields\": %s, \"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\"}" % ( fields, page_size, advertiser_id, page)
    jsonString = get(GET_CREATIVE_PATH, my_args)
    # print(jsonString)
    # creative_json = json.loads(jsonString)
    creative_json_array = None
    if('data' in jsonString):
        creative_json_array = jsonString['data']
    if('list' in creative_json_array):
        creative_json_array = creative_json_array['list']
    list = []
    for i in range(len(creative_json_array)):
        list.append(creative_json_array[i]['creative_id'])
    return list

def get_total_cost_by_day(path, page, ids):
    fields_list = ["creative_id"]
    fields = json.dumps(fields_list)
    page_size = 1000
    end_date = '2021-05-05'
    start_date = '2021-05-05'
    time_granularity = 'STAT_TIME_GRANULARITY_DAILY'
    advertiser_id = 1685115520377864
    group_by = '["STAT_GROUP_BY_FIELD_ID","STAT_GROUP_BY_FIELD_STAT_TIME"]'
    filtering = '{"creative_ids":[' + ids +']}'

    # Args in JSON format
    my_args = "{\"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\", \"end_date\": \"%s\", \"start_date\": \"%s\", \"time_granularity\": \"%s\", \"group_by\": %s, \"filtering\": %s}" % (page_size, advertiser_id, page, end_date, start_date, time_granularity, group_by, filtering)  
    jsonString = get(path, my_args)
    # print(jsonString)
    # creative_json = json.loads(jsonString)
    creative_json_array = None
    if('data' in jsonString):
        creative_json_array = jsonString['data']
    if('list' in creative_json_array):
        creative_json_array = creative_json_array['list']
    list = []
    for i in range(len(creative_json_array)):
        list.append(creative_json_array[i]['cost'])
    return list

def get_total_cost_by_hour_without_ids(path, page):
    fields_list = ["creative_id"]
    fields = json.dumps(fields_list)
    page_size = 1000
    end_date = '2021-05-24'
    start_date = '2021-05-24'
    time_granularity = 'STAT_TIME_GRANULARITY_HOURLY'
    advertiser_id = 1694283573329934
    group_by = '["STAT_GROUP_BY_FIELD_ID","STAT_GROUP_BY_FIELD_STAT_TIME"]'

    # filtering = '{"status":"CREATIVE_STATUS_DATA_ERROR"}'
    # my_args = "{\"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\", \"end_date\": \"%s\", \"start_date\": \"%s\", \"time_granularity\": \"%s\", \"group_by\": %s, \"filtering\": %s}" % (page_size, advertiser_id, page, end_date, start_date, time_granularity, group_by, filtering)  
    # Args in JSON format
    # 
    my_args = "{\"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\", \"end_date\": \"%s\", \"start_date\": \"%s\", \"time_granularity\": \"%s\", \"group_by\": %s}" % (page_size, advertiser_id, page, end_date, start_date, time_granularity, group_by)  
    jsonString = get(path, my_args)
    # print(jsonString)
    # creative_json = json.loads(jsonString)
    creative_json_array = None
    if('data' in jsonString):
        creative_json_array = jsonString['data']
    if('list' in creative_json_array):
        creative_json_array = creative_json_array['list']
    list = []
    for i in range(len(creative_json_array)):
        list.append(creative_json_array[i]['show'])
    return list

def get_total_cost_by_day_without_ids(path, page):
    fields_list = ["creative_id"]
    fields = json.dumps(fields_list)
    page_size = 1000
    end_date = '2021-05-24'
    start_date = '2021-05-24'
    time_granularity = 'STAT_TIME_GRANULARITY_DAILY'
    advertiser_id = 1694283573329934
    group_by = '["STAT_GROUP_BY_FIELD_ID","STAT_GROUP_BY_FIELD_STAT_TIME"]'

    # Args in JSON format
    my_args = "{\"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\", \"end_date\": \"%s\", \"start_date\": \"%s\", \"time_granularity\": \"%s\", \"group_by\": %s}" % (page_size, advertiser_id, page, end_date, start_date, time_granularity, group_by)  
    jsonString = get(path, my_args)
    # print(jsonString)
    # creative_json = json.loads(jsonString)
    creative_json_array = None
    if('data' in jsonString):
        creative_json_array = jsonString['data']
    if('list' in creative_json_array):
        creative_json_array = creative_json_array['list']
    list = []
    for i in range(len(creative_json_array)):
        list.append(creative_json_array[i]['cost'])
    return list

def compare_id_not_in():
    list = []
    id_in_db = read_excel(r"D:\Users\Desktop\creative_ids.xls")
    for i in range(10):
        time.sleep(5)
        num = i + 1
        print("get ids page:", num)
        id_remote = get_creative_ids(num)
        for i in range(len(id_remote)):
            if(str(id_remote[i]) not in id_in_db):
                list.append(id_remote[i])
    for i in range(len(id_remote)):
        if(str(id_remote[i]) not in id_in_db):
            list.append(id_remote[i])
    return list

def get_account_ids(page):
    page_size = 2000
    advertiser_id = 6880408002

    # Args in JSON format
    my_args = "{\"page_size\": \"%s\", \"advertiser_id\": \"%s\", \"page\": \"%s\"}" % (page_size, advertiser_id, page)
    jsonString = get(GET_AGENT_CHILD_ACCOUNT, my_args)
    list = []
    for i in range(len(creative_json_array)):
        list.append(creative_json_array[i]['creative_id'])
    return list


# print(compare_id_not_in())
# 
# id_in_db = read_excel(r"D:\Users\Desktop\creative_ids.xls") 
# do_pgsql.insert_data(id_in_db)

total = 0
ids = [
'1687138011815979,1692106118535176,1685764624888941,1692106118724669,1698185475426344,1687378399334420,1685762477471816,1687378398605356,1687138080811027,1685765702897668,1688780926456876,1698175398257688,1685762477470824,1686397991472174,1685580687930413,1687954388755516,1685580687932429,1688598475467832,1688781059181629,1687571768433676,1688781054758941,1685571795640349,1685580686847015,1688780926456892,1686397994258440,1687839597094013,1686316749409303,1686952829398030,1686415880340535,1688751863881752,1687787743554606,1690754727968807,1686831632243725,1685762476571655,1687954390001724,1686952829398046,1693098070239246,1686427168145459,1688781059181581,1685762967975940,1687787743260727,1689570861245501,1685580687608888,1688757525978173,1698185478743054,1690754588188695,1688757525976125,1688217889458221,1687035766504471,1689582430170232,1685767232181288,1690754588531741,1685880807888925,1686412609786968,1690758738423859,1685764626551864,1686412610445367,1686415881357325,1686312279734301,1690766814693416,1686316950103044,1686315966019592,1687571767366700,1692102536066084,1685580688681991,1690758740398139,1686424766469284,1688937287141404,1686868322206724,1687469258874936,1686862094477336,1688937287771139,1689572005656622,1688575771853838,1686308180013085,1686316949486635,1688575771066375,1692181173220376,1686952830446647,1688065702797336,1686424360897547,1688214277902349,1686424360896555,1686315966018600,1692102582113320,1686953891493931,1687138835995688,1686424360827964,1687396140838942,1687839769792535,1685762967713851,1688215105729581,1687921137594414,1688936580295757,1687839598931998,1686868322206756,1686952658528318,1686952830446599,1694667449201719,1688311870689293',
'1690758880517272,1686424359518212,1688311871457294,1687040388793355,1685880491179070,1688937623315496,1686831631858733,1685580687608840,1687839597094989,1689568679225389,1686831632242749,1687468643631144,1686427168145427,1687568269651101,1685674194230317,1686482067418168,1687469259497496,1688936548261927,1686398703331358,1686862095027262,1685579562894411,1686953892013075,1688317894994974,1686309574200360,1687468643631128,1688575771065399,1690846730066045,1688936526365700,1689568740572203,1688572908999687,1686412090194952,1687839636431917,1692103738285076,1689568740571147,1688310382708780,1686831991815191,1686766900260984,1687469122675715,1685580687932477,1685881540895752,1685881950678126,1686862632674360,1688578621675575,1686832410405928,1686766899818590,1686308410571795,1688578512308349,1686424360896523,1686398000742509,1687568332485694,1690758879228958,1692101167973422,1692106445386766,1689570930476087,1688317889317928,1692090986204232,1686482067419160,1687035767198776,1685880807458856,1688320325322797,1688317889315960,1685580687607848,1686411812916248,1690844372329518,1685580488842302,1686312279321725,1687839755725934,1688215038156862,1692090980650008,1685881950679118,1687839598022680,1688936548261943,1689568738481213,1685674188959784,1686426189109283,1688581610144779,1686411812609070,1692103872816189,1690854060767255,1685765702834188,1688937623315464,1687138849315901,1686952659510350,1686862634758157,1688214277901341,1688217889457213,1685762476571703,1689106114331662,1688936592701447,1688217889457165,1685580488842270,1689106114331710,1686312170979358,1686862634758189,1693385956946967,1689568738645031,1686862633201672,1689572077819912,1688310384088099,1688581610690572',
'1693385956945975,1687839598022696,1692092470594606,1689106114329630,1688937770956899,1690767240852520,1687138835996696,1687138849316909,1690766552492072,1690760218829880,1685580133683239,1692103187891246,1685674194230301,1692103739747332,1685582132330509,1687574551502910,1693397307191422,1687040388793403,1688936593992829,1690755632889864,1685580687932461,1687871365895230,1686832409438237,1686410290949159,1693396466477069,1686410290951255,1687138849315853,1687574551502862,1689570930477079,1688064510626872,1692103872817197,1692094397937694,1685580529086523,1688215038156814,1689108041843726,1686309575335966,1693396466550791,1688580310279278,1685571795407959,1686424360826892,1688215415117950,1693387395626040,1685881540895768,1693098070307895,1685882489088052,1688936530741304,1686315543555096,1692106444049415,1685882489088004,1687568269651133,1690767240853528,1687839598932030,1687042665999364,1692094394823736,1688217890496541,1694630420446215,1688317096331351,1685880491855960,1688326466755613,1688936580872200,1688214258976813,1689568680406039,1686852822730813,1692106516662461,1687839598857255,1692104939775032,1687469122675731,1688751865772087,1692103873692679,1688215679251495,1698185475426360,1689570860158007,1688215094871059,1689570924946436,1688215659155469,1689108025174061,1689572038710280,1686831991815207,1688064510626824,1694667449613415,1694020534582333,1686424768240716,1693387395469373,1688064510625816,1689106110884904,1698186250791950,1688572892131364,1688937771051011,1685674188959800,1685882626504766,1697002293868606,1692109658943550,1685674193634333,1688325997056028,1694630419519543,1686126539797531,1688326474699790,1686852822264877,1692102539978772,1686308410063900',
'1692101169509384,1689570924069923,1688216095507463,1685875020299300,1685582133278728,1688215114978311,1692104940255277,1687839527205980,1689572038709272,1688215415116830,1688325997056044,1692181172556862,1685580528798780,1692109658943502,1688216095506455,1685875019780148,1690854061097021,1689108025175069,1694020533496903,1693397061165117,1685882626503742,1688936530331672,1693398130430024,1689572078259262,1688221165479992,1686308180733982,1697002403248205,1692094542722167,1688215094960140,1693387356987448,1685880128957496,1688065695001645,1692092063139895,1688572910108717,1695195262561315,1694631680650263,1692103596597275,1694629804556328,1686126540185644,1685880951012414,1688064503766125,1685579563814987,1692092059985975,1692106516663405,1692094544596024,1685767180638237,1685880951012366,1686412089912488,1693398129920030,1687568332079165,1692094495295512,1685767180637245,1692103596598315,1692094407417876,1685580133418030,1688215114875927,1695195262742644,1698191628581927,1688580310207550,1695195297242142,1685762476572695,1688214290311179,1695195297582125,1688215679252503,1692103892384796,1685764399230999,1685762476571671,1693397307405335,1692106251487271,1688215385678894,1692118423953416,1694631611440152,1692106251486247,1688221167154199,1692118423953464,1688214290244652,1692103892384812,1698175540138110,1693387356986472,1693385875817543,1694629804127293,1686315544554552,1688215659155517,1688221165481000,1692118423954472,1692104940255245,1692092468265992,1692103863871495,1693397061164045,1690766814310408,1698185480081533,1697010790163485,1692103873929262,1693385873993784,1694641825073207,1688221165479944,1686426188861444,1685764399230007,1688310635608087,1692094495127614',
'1692094405545083,1697010790163501,1688215114873895,1688214290245660,1685767231982637,1686424766469252,1685874823805997,1688214258974845,1688326466754605'
]

# for i in range(len(ids)):
#     for i in range(3):
#         page = i + 1
#         list = get_total_cost_by_day(GET_CREATIVE_REPORT_PATH, page, ids[i])
#         for i in range(len(list)):
#             total = total + list[i]
#         time.sleep(2)
#         print("done:", page)
#         print("page total: ", total)
# print(total)


# creative hourly 32.68000000000003
# ad       hourly 3289.8000000000097
# 
# 
# new_ids = read_excel(r"D:\Users\Desktop\creative_ids_new.xls")
# print(len(new_ids))

# old_ids = read_excel(r"D:\Users\Desktop\creative_ids.xls")
# print(len(old_ids))
# for i in range(len(old_ids)):
#     if(old_ids[i] not in new_ids):
#         print(old_ids[i])

# print(type(new_ids[1]))
# print(type(old_ids[1]))
# print(old_ids[1] in new_ids)
#
# list1 = [1693387356601400,1693387396779038,1693387396778014,1686309615981587,1686481363981351,1690755632525352,1685762969191460,1685762969191444,1685762969191428,1685762969190436,1685762969190420,1685762969190404,1694020087392318,1688936579402771,1687839527348243,1688572892284980,1694175653889080,1694175653890056,1688320325322797,1693398130184221,1688937288757300,1693398130184253,1685880491855960,1685872271800446,1692092468266040,1692094087768077,1692094087768093,1692094164981812,1692094164981796,1692094164981780,1692094164980788,1692094164980772,1692094164980756,1692094159221820,1692094159221804,1692094087769197,1692094087769181,1692094087769165,1692094087768125,1692094087768109,1692094074369079,1692103892384796,1694629805232151,1692092471761933,1692092470284302,1694631612320878,1694630419898391,1694629804127293,1692106118724637,1694630419519543,1694630419518519,1694629804127245,1694631614912520,1694631611265038,1692090983151639,1692090981846088,1692090980651048,1692106119633950,1692105328039998,1692103913644088,1692103912006664,1686424769674316,1694631610475565,1690854061324312,1694667449614439,1692109662024861,1686852880575517,1686316951097372,1685880128958520,1685762967975988,1692106446566423,1695195262742612,1695198445860875,1692092066360366,1692106547128343,1692106517218311,1692106446566439,1692103867206701,1692103595226163,1695197012513799,1695198101153911,1695200419917847,1695198444483588,1695198444482612,1695195262562307,1692106445307959,1692106118723613,1692105211597883,1692103913645080,1692103912006696,1686411813846045,1686397999750151,1686315966897208,1686312280356910,1692106516530183,1694020534581293,1692092470284334,1689572078259246,1688317096994871,1688578514311197,]
# list2 = [1690747973682222,1690748630318109,1690747972554781,1690748631203880,1690748631203864,1690748631203848,1690748631202872,1690748631202856,1690748631202840,1690748631202824,1690748631044157,1690748631044141,1690748631044125,1690748631044109,1690748631043133,1690748631043117,1690748631043101,1690748631043085,1690748630319165,1690748630319149,1690748630319133,1690748630319117,1690748630318141,1690748630318125,1690748630318093,1690748630004798,1690748630004782,1690748213290030,1690748213289022,1690748213288974,1690747973829694,1690747973829678,1690747973829662,1690747973829646,1690747973828670,1690747973828654,1690747973828638,1690747973828622,1690747973756983,1690747973756967,1690747973756951,1690747973756935,1690747973755959,1690747973755943,1690747973755927,1690747973755911,1690747973682238,1690747973682206,1690747973682190,1690747973681230,1690747973681214,1690747973681198,1690747973681182,1690747973577784,1690747973577768,1690747973577752,1690747973577736,1690747973576760,1690747973576744,1690747973576728,1690747973576712,1690747972555853,1690747972555837,1690747972555821,1690747972555805,1690747972554813,1690747972554797,1690747972554765,1685762969191460,1685762969191444,1685762969191428,1685762969190436,1685762969190420,1685762969190404,1692094087768077,1692094087768093,1692094164981812,1692094164981796,1692094164981780,1692094164980788,1692094164980772,1692094164980756,1692094159221820,1692094159221804,1692094087769197,1692094087769181,1692094087769165,1692094087768125,1692094087768109,1692094074369079,1685762967975988,1685762969190452]

# for i in range(len(list1)):
#     if list1[i] not in list2:
#         print(list1[i])
#         
# new_ids = read_excel(r"D:\Users\Desktop\creative_ids.xls")
# do_pgsql.insert_data(new_ids)


# total = 0
# for i in range(9):
#     page = i + 1;
#     cost_list = get_total_cost_by_hour_without_ids(GET_CREATIVE_REPORT_PATH, page)
#     for i in range(len(cost_list)):
#         total = total + cost_list[i]
#     print("----")
#     print(page)
#     print(total)
#     time.sleep(1)
# print(total)

# CREATIVE_STATUS_ALL 日报 小时报 : 15791.600000000435
#                     展示量  :  2585077 
#                     
#                     展示量  :  2405440

# cost = get_total_cost_by_day_without_ids(GET_CREATIVE_REPORT_PATH, 1)
# total = 0
# for i in range(len(cost)):
#     total = total + cost[i]
# print(total)
# 

# total = 0
# file = open(r"D:\Users\Desktop\response1.json", "rb")
# response = json.load(file)
# report_list = response["data"]["list"]
# for i in range(len(report_list)):
#     total = total + report_list[i]["show"]

# print(total)



file = open(r"D:\Users\Desktop\creative_ids.txt", "rb")
creative_ids_in_db = json.load(file)

id_exclude = []
id_interface = []
for i in range(9):
    page = i + 1
    id_list = get_creative_ids(page)
    for i in range(len(id_list)):
        id_interface.append(id_list[i])
    time.sleep(1)

for i in range(len(creative_ids_in_db)):
    if(creative_ids_in_db[i] not in id_interface):
        id_exclude.append(creative_ids_in_db[i])
print(id_exclude)

import datetime
import time
def test(str):
    dateObj = datetime.datetime.strptime(str, "%Y-%m-%d")
    localTime = time.localtime(time.time())
    localYear = localTime[0]
    return localYear - dateObj.year


age = test("1995-07-24")
print(age)

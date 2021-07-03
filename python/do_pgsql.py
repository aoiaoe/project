import psycopg2

conn = psycopg2.connect(database="yiye", user="postgres", password="root", host="127.0.0.1", port="5432")

def insert_data(list):
	cur = conn.cursor()

	#让它ID自动生成
	for i in range(len(list)):
		cur.execute("INSERT INTO marketing_data_creative (account_id,platform_id,campaign_id,creative_id) VALUES (1685115520377864, 1, 1, " + list[i] + ")");
	#注意这里还可以返回插入数据的ID
	# results = cur.fetchone()
	# ID = results[0]  #返回插入的记录的id 便于后续操作

	conn.commit()
	conn.close()



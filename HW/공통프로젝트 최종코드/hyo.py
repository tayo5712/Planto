#!/usr/bin/env python
import cv2
import paho.mqtt.client as mqtt
import threading
from datetime import datetime
from PIL import ImageFont, ImageDraw, Image
import numpy as np
import pratice as pr
import json
from parse import *

def init() :
	#global get_msg
	global test
	global category
	lines = open('/home/pi/init.txt','r').readlines()
	category = int(lines[0].rstrip('\n'))
	get_msg = lines[1]
	test = 1

def what_day_is_today():
	now = datetime.now()
	t = ['월', '화', '수', '목', '금', '토', '일']
	r = datetime.today().weekday()
	day =  str(now.month) + '월 ' + str(now.day) + '일 ' + t[r] + '요일'
	return day

def replace_text_line(line_num,content) :
	lines = open('/home/pi/init.txt','r').readlines()
	#print("-----------")
	#print(content)
	#print(len(lines))
	#print("-----------")
	lines[line_num] = content
	out = open('/home/pi/init.txt','w')
	out.writelines(lines)
	out.close()

def PIL2CV(pil_image):
    numpy_image= np.array(pil_image)
    opencv_image = cv2.cvtColor(numpy_image, cv2.COLOR_RGB2BGR)
    return opencv_image

def CV2PIL(opencv_image):
    color_coverted = cv2.cvtColor(opencv_image, cv2.COLOR_BGR2RGB)
    pil_image = Image.fromarray(color_coverted)
    return pil_image

def on_message(client, userdata, message):
	global get_msg
	global test
	get_msg = str(message.payload.decode("utf-8"))
	replace_text_line(1,get_msg)

def ttq():
	broker_address = "i8c202.p.ssafy.io"
	#client1 = mqtt.Client("client1")
	client1 = mqtt.Client()
	client1.connect(broker_address)
	client1.subscribe("STM")
	client1.on_message = on_message
	client1.loop_forever()

	
def on_message2(client, userdata, message):
	global get_msg2
	global category
	global prev_category
	get_msg2 = str(message.payload.decode("utf-8"))
	
	msg = str(message.payload.decode("utf-8"))
	print(msg)
	temp = parse("{\"serialNo\":{},\"liking\":{}}",msg)
	if category == 3 :
		prev_category = int(temp[1])
	else :
		category = int(temp[1])
	
	replace_text_line(0,temp[1]+'\n')

def ttq2():
	broker_address = "i8c202.p.ssafy.io"
	client2 = mqtt.Client()
	client2.connect(broker_address)
	client2.subscribe("STM/liking")
	client2.on_message = on_message2
	client2.loop_forever()

def touchScreen(event, x, y, flag, param):
	global idx
	global prev_idx
	global category
	global prev_category
	global wflag
	
	if info_cloud == "1":
		if info_rain == "강수없음" and info_snow == "적설없음":
			wflag = 0
		elif info_rain == "강수없음" and info_snow != "적설없음":
			wflag = 2
		elif info_rain != "강수없음" and info_snow == "적설없음":
			wflag = 1
		elif info_rain != "강수없음" and info_snow != "적설없음":
			wflag = 2
	elif info_cloud == "3":
		if info_rain == "강수없음" and info_snow == "적설없음":
			wflag = 3
		elif info_rain == "강수없음" and info_snow != "적설없음":
			wflag = 2
		elif info_rain != "강수없음" and info_snow == "적설없음":
			wflag = 1
		elif info_rain != "강수없음" and info_snow != "적설없음":
			wflag = 2
	elif info_cloud == "4":
		if info_rain == "강수없음" and info_snow == "적설없음":
			wflag = 3
		elif info_rain == "강수없음" and info_snow != "적설없음":
			wflag = 2
		elif info_rain != "강수없음" and info_snow == "적설없음":
			wflag = 1
		elif info_rain != "강수없음" and info_snow != "적설없음":
			wflag = 2

	if event == cv2.EVENT_LBUTTONDOWN:
		if category == 3: # cur : weather screen
			category = prev_category
			idx = prev_idx
		else: # cur : emotion screen
			prev_category = category
			category = 3
			prev_idx = idx
			idx = wflag

def setVariable(inputString):
	global data
	data = inputString

def printWeather(rain,snow,temp,cloud):
	print()
	print(rain)
	print(snow)
	print(temp)
	print(cloud)

# 센서값에 따른 화면 전환
def sensorScreen(data) :
	global category
	global wflag
	if category == 3 :
		return wflag

	idx = 0
	info_temperature = data[1]
	info_humidity= data[2]
	info_light = data[3]
	info_soil_moisture = data[4]
	info_touch= data[5]
	info_attack = data[6]

	# 1. 화분이 맞았을 때( 0 or 1 )	
	if info_attack == 1:
		idx = 9

	# 2. 화분을 잡았을 때( 0 or 1 or 2 )
	elif info_touch == 1:
		idx = 7

	elif info_touch == 2:
		idx = 8

	# 3. 화분의 토양( 변곡점 20 )
	elif info_soil_moisture < 50:
		idx = 6
	
	elif info_soil_moisture > 400:
		idx = 5

	# 4.화분의 광조도( 변곡점 120 어두울수록 값커짐)
	elif info_light > 200: 
		idx = 10

	# 5.화분의 온도( 변곡점 26 )
	elif info_temperature > 26:
		idx = 3

	elif info_temperature < 16:
		idx = 4

	# 6.화분의 습도( 변곡점 50 ) 
#	elif info_humidity > 50:
#		idx = 5
	
	else:
		idx = 0 
    	
	return idx


def addStream():
	now = datetime.now()
	global data
	
	global idx
	global prev_idx
	global category
	global prev_category
	global font
	global get_msg
	global test
	font = cv2.FONT_HERSHEY_SIMPLEX
	#fontpath ="gulim.ttc"
	fontpath = "TmoneyRoundWindRegular.ttf"
	font_kr = ImageFont.truetype(fontpath, 24)
	global white
	color = (255,255,255)
	# append Streaim

	capture = [[] for _ in range(4)]
	emotion_gif = ["./emotion/1_2.gif","./emotion/1_2.gif","./emotion/1_2.gif", "./emotion/1_4.gif", "./emotion/1_5.gif", "./emotion/1_6.gif", "./emotion/1_7.gif", "./emotion/1_8.gif", "./emotion/1_9.gif", "./emotion/1_10.gif", "./emotion/1_11.gif"]
	liking_gif = ["./liking/2_2.gif","./liking/2_2.gif","./liking/2_2.gif","./liking/2_4.gif","./liking/2_5.gif","./liking/2_6.gif","./liking/2_7.gif","./liking/2_8.gif","./liking/2_9.gif","./liking/2_10.gif","./liking/2_11.gif","./liking/2_13.gif","./liking/2_13.gif","./liking/2_13.gif","./liking/2_15.gif","./liking/2_16.gif","./liking/2_17.gif","./liking/2_18.gif","./liking/2_19.gif","./liking/2_20.gif","./liking/2_21.gif","./liking/2_22.gif",]
	weather_gif = ["./weather/1_1.gif", "./weather/1_2.gif", "./weather/1_3.gif", "./weather/1_4.gif"]
	
	for i in emotion_gif:
		capture[0].append(cv2.VideoCapture(i))
	for i in range(0,11) :
		capture[1].append(cv2.VideoCapture(liking_gif[i]))
	for i in range(11,22) :
		capture[2].append(cv2.VideoCapture(liking_gif[i]))
	for i in weather_gif:
		capture[3].append(cv2.VideoCapture(i))
	
	pr.makeQR()
	# run code
	while True:
		#now.hour, now.minute
		# (me data  <-> get data,,,, compare)
		
		#print(get_msg) # weather API
		#print(get_msg2) # liking data
		while test == 0 :
			continue
		#print(get_msg)
		idx = sensorScreen(data)
		now_date = ""

		if now.day < 10:
			now_date = str(now.year) + "0" + str(now.month) + "0" + str(now.day)
		else:
			now_date = str(now.year) + "0" + str(now.month) + str(now.day)
		now_time = ""
		if now.hour < 10:
			now_time = "0" + str(now.hour) + "00"
		else:
			now_time = str(now.hour) + "00" 
		
		global info_temp
		info_temp = ""
		global info_snow
		info_snow = ""
		global info_rain
		info_rain = ""
		global info_cloud
		info_cloud = ""
		#global get_msg
		#print("#############3")
		#print(get_msg)
		#print("#############")
		
		#print("###")
		#print(get_msg)
		#print("###")


		parsed_data = get_msg.split("}")
		for i in parsed_data:
			tar = i
			if tar.find(now_date) != -1 and tar.find(now_time) != -1:
				parsed_data_small = tar.split(",")
				for j in parsed_data_small:
					if j.find("pcp") != -1:	#rain 0, X
						info_rain = j.split(":")[1].replace('"', '')
					if j.find("sno") != -1:	#snow 0, X
						info_snow = j.split(":")[1].replace('"', '')
					if j.find("tmp") != -1:	#temp val
						info_temp = j.split(":")[1].replace('"', '')
						info_temp = info_temp + "°C"
					if j.find("sky") != -1: #cloud 1, 3, 4
						info_cloud = j.split(":")[1].replace('"', '')
				break
		
		#info_rain = "1.0mm"
		#info_snow = "적설없음"
		#info_temp = "4℃"
		#info_cloud = "4"
		#printWeather(info_rain, info_snow, info_temp, info_cloud)

		keyCode = cv2.waitKey(77)
		if keyCode < 0: # input None
		#if data2 > 32.0:
		#	idx = 0
		#else:
		#	idx = 1
			#print(category)
			#print(idx)
			#print()
			if capture[category][idx].get(cv2.CAP_PROP_POS_FRAMES) == capture[category][idx].get(cv2.CAP_PROP_FRAME_COUNT):
				capture[category][idx].set(cv2.CAP_PROP_POS_FRAMES, 0)

			global ret
			global frame
			ret, frame = capture[category][idx].read()
			
			cv2.namedWindow("Image", cv2.WND_PROP_FULLSCREEN)
			cv2.setWindowProperty("Image", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)
			if category ==  3:
				info_temp = "온도: " + info_temp
				if info_rain == "강수없음":
					info_rain = "강수: 없음"
				else:
					info_rain = "강수: " + info_rain
				if info_snow == "적설없음":
					info_snow = "적설: 없음"
				else:
					info_snow = "적설: " + info_snow
				if info_cloud == "1":
					info_cloud = "날씨: 맑음"
				elif info_cloud == "3":
					info_cloud = "날씨: 구름많음"
				elif info_cloud == "4":
					info_cloud = "날씨: 흐림"
			
				now = datetime.now()
				pil_image = CV2PIL(frame)
				draw = ImageDraw.Draw(pil_image, 'RGBA')

				font_kr = ImageFont.truetype(fontpath, 50)
				draw.text((130,370),now.strftime('%H:%M:%S'),font=font_kr,fill=color)
				font_kr = ImageFont.truetype(fontpath, 20)
				draw.text((175,430),what_day_is_today(),font=font_kr,fill=color)

				font_kr = ImageFont.truetype(fontpath, 24)
				draw.text((570,355),info_rain,font=font_kr,fill=color)
				draw.text((570,385),info_snow,font=font_kr,fill=color)
				draw.text((570,415),info_temp,font=font_kr,fill=color)
				draw.text((570,445),info_cloud,font=font_kr,fill=color)

				cv2_image = PIL2CV(pil_image)
				frame = cv2_image

			cv2.imshow("Image", frame)
			cv2.setMouseCallback("Image", touchScreen)
			
		if keyCode == ord('q') : # input 'q'
			if category <= 2 and idx  == 1 :
				idx = 0
				continue
			elif category <= 2 and idx == 0:
				idx = 1
				continue
		
		if keyCode == 27: # input 'esc' -> exit
			break

	# release Stream
	for i in range (0, len(emotion_gif)):
		capture[0][i].release()
	for i in range (0, 11):
		capture[1][i].release()
	for i in range(0,11) :
		capture[2][i].release()

	for i in range (0, len(weather_gif)):
		capture[3][i].release()
	cv2.destroyAllWindows()

#init
test=  0
idx = 0
wflag = 0

get_msg = ""
get_msg2 = "===="
data = ["", 1.0, 2.0, 3.0, 4.0, 5, 6]
category = 0
info_temp = ""
info_snow = ""
info_rain = ""
info_cloud = ""
# main
t = threading.Thread(target=ttq)
t.start()
t.demon = True

t2 = threading.Thread(target=ttq2)
t2.start()
t2.demon = True
#addStream()

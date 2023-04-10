import serial, time
import paho.mqtt.client as mqtt
import threading
from pratice import getMyNumber
import hyo

def handle_exit() :
	ser.close()
	
def serial_process(ser) :
	mqttc = mqtt.Client("client2")
	mqttc.connect("i8c202.p.ssafy.io", 1883)
	datas = ["",0.1,0.2,0.3,0.4,5,6]
	touch_cnt = 0
	attack_cnt = 0
	while True :
		try :
			if ser.readable() :
				data = "{\"" + "device_number\":\""  
				data += getMyNumber()	
				data += "\","
				arduino_data = ser.readline().decode()
				data += arduino_data
			
				parsed_data = data.split(",")
				datas[0] = parsed_data[0].split(":")[1].replace("\"","")	
				datas[1] = float(parsed_data[1].split(":")[1].replace("\"",""))	
				datas[2] = float(parsed_data[2].split(":")[1].replace("\"",""))	
				datas[3] = float(parsed_data[3].split(":")[1].replace("\"",""))	
				datas[4] = float(parsed_data[4].split(":")[1].replace("\"",""))	
				datas[5] = int(parsed_data[5].split(":")[1].replace("\"",""))	
				datas[6] = int(parsed_data[6].split(":")[1].replace("}\r\n", "").replace("\"",""))
				
				hyo.setVariable(datas)

				if touch_cnt != 0 :
					idx = data.find("touch")
					data = list(data)
					data[idx+8] = '0'
					data = ''.join(data)
					touch_cnt -= 1
				

				if attack_cnt != 0 :
					idx = data.find("attack")
					data = list(data)
					data[idx+9] ='0'
					data = ''.join(data)
					attack_cnt -= 1

				if datas[5] > 0 and touch_cnt == 0 :
					touch_cnt = 2
				
				if datas[6] == 1 and attack_cnt == 0 :
					attack_cnt = 2
				
				#print(data)
				#Arduino data -(Serial)> Raspberry Pi -(MQTT pub)> Server
				mqttc.publish("MTS", data)


		except KeyboardInterrupt :
			print("KeyBoardInterrupt occur")
			exit()
		except Exception as e:
			print("data process error occured,this : \n",e)

if __name__=='__main__':
	ser = serial
	print(ser)
	try :
		ser = serial.Serial('/dev/ttyACM0',115200)
		print(ser)

		t2 = threading.Thread(target=serial_process, args=(ser, ))
		t2.start()
		t2.demon = True
	except Exception as  e :
		print(e)
		print("bb")
		exit()
	hyo.init()	
	hyo.addStream()

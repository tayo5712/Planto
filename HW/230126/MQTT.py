import serial, time
import paho.mqtt.client as mqtt
import threading


def handle_exit() :
	ser.close()
	

def on_message(client, userdata,messsage) :
	print("action")

def ttq() :
	broker_address = "i8c202.p.ssafy.io"
	client1 = mqtt.Client("client1")
	client1.connect(broker_address)
	client1.subscribe("STM")
	client1.on_message = on_message
	client1.loop_forever()

def serial_process(ser) :
	mqttc = mqtt.Client("client2")
	mqttc.connect("i8c202.p.ssafy.io", 1883)
	while True :
		try :
			if ser.readable() :
				data = ser.readline().decode()
				print(data)
				# Arduino data -(Serial)> Raspberry Pi -(MQTT pub)> Server
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
		t = threading.Thread(target=ttq)
		t.start()
		t.demon = True
		ser = serial.Serial('/dev/ttyACM0',115200)
		print(ser)
	except Exception as  e :
		print(e)
		print("bb")
		exit()

	serial_process(ser)

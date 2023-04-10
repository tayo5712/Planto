import serial, time

def handle_exit() :
	ser.close()

def serial_process(ser) :
	while True :
		try :
			if ser.readable() :
				data = ser.readline().decode()
				print(data)
		except KeyBoardInterrupt :
			print("ctrl + c process exit")
			exit()
		except Exception as e:
			print("data process error occured,this : \n",e)

if __name__=='__main__':
	ser = serial
	print(ser)
	try :
		ser = serial.Serial('/dev/ttyACM0',115200)
		print(ser)
	except Exception as  e :
		print(e)
		print("bb")
		exit()

	serial_process(ser)

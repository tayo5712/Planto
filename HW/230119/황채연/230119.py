import serial, time


with serial.Serial("/dev/ttyACM0", 115200, timeout=1) as arduino :
	time.sleep(0.1)
	if arduino.isOpen() :
		print("{} connected ". format(arduino.port))
		try :
			while True :
				cmd ="come on"
				arduino.write(cmd.encode())

				while arduino.inWaiting() == 0 : 
					pass
				if arduino.inWaiting() > 0 :
					answer = arduino.readline()
					print(answer.decode())
					arduino.flushInput()

		except KeyboardInterrupt :
			print("KeyboardInterrupt has been caught.")

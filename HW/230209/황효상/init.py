lines = open('/home/pi/hh.txt','r').readlines()
lines[2] = "test input"
out = open('/home/pi/hh.txt','w')
out.writelines(lines)
out.close()

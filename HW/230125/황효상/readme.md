### Today What I DO

1. mosquitto 설치
    
    sudo add-apt-repository ppa:mosquitto-dev/mosquitto-ppa
    sudo apt-get update
    sudo apt install mosquitto
    

2. mosquitto.conf 파일 수정

![image.png](./image.png)

3. 1883 port 열기
    - sudo ufw allow 1883
    - sudo ufw enable

4. Mosquitto pub sub 연결 후 통신
- pub

![image-1.png](./image-1.png)


## Trouble Shooting

1.FIFO /tmp/dlt cannot be opened. Retrying later… ERROR

![image-3.png](./image-3.png)

2. Could not

![image-4.png](./image-4.png)


- sudo rm /var/lib/apt/lists/lock
- sudo rm /var/cache/apt/archives/lock
- sudo rm /var/lib/dpkg/lcok*
- sudo dpkg --configure -a
- sudo apt update

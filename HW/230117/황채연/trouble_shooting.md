# Trouble Shooting

# [H/W] Raspberry Pi 4 와 Arduino Uno 1:1 시리얼 통신

1. WiringPi 버전 문제
    - RPI4 와 Arduino 시리얼 통신에 사용되는 WiringPi 설치 시 “collect2 : ld returned 1 exit status” , “skipping incompatible error” 에러 발생
        - 원인 : WiringPi 설치 시 sudo apt-get install wiringpi 설치시 운영체제의 버전과 관계없이 32bit wiringPi 가 설치되어 발생되는 문제
        - 해결
            
            [https://hoho325.tistory.com/212](https://hoho325.tistory.com/212) 블로그 참조
            
            → wiringpi 완전 제거 후 해당 블로그 참조하여 git clone하여 직접설치
            
2. 시리얼 통신 간 원하지 문자열 밀림 발생
    - C언어 시리얼 통신은 이상없음
    - Python 시리얼 통신 중 밀림 현상 발생 
        - 파이썬에서 제공하는 라이브러리가 많고 아두이노 입출력이 용이하게에 테스트
        - read, write, print의 각 기능에 대한 정확한 이해가 필요
        ![image.png](./image.png)

- 구현 동작
    - RPI4 : Arduino에게 TEST CODE 송신, Arduino에게 받은 데이터 출력
    - Arduino : RPI4에게 Hyomusae 송신, RPI4에게 받은 데이터 출력

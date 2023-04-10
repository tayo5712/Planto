### today what I do

[Arduino] - C/C++ programming

수신 데이터 형식
- 아두이노에서 데이터를 보낼 때 수신측에서 받는 데이터 타입은 'string'
- 5~6 종류의 데이터를 한번에 string으로 보낼 예정
- ex) "temperature:11,humidity:40, ..." 와 같은 형식으로 보냄
- ',' 기준으로 센서 종류 구분, 그 안의 ':' 기준으로 좌측 센서명, 우측 센서값 수신

Test
- 초음파 센서 테스트
https://juahnpop.tistory.com/68

진행상황
- 아두이노, 라즈베리파이 USB연결 serial 통신
- 값을 송수신 즉, 양방향 통신일 경우 이상없음
- 단방향 통신일 때(아두이노 to 라즈베리파이)는 문제 발생
- USB 연결일 때 생기는 문제점일 수도 있어 1. 통신 방식을 바꾸기, 2. trick 사용(쓰레기값 전송)

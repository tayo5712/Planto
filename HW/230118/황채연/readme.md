# [Arduino] - C/C++ programming

아두이노 시리얼 통신 참조
- https://blog.naver.com/yuyyulee/220301796341
- https://steemit.com/kr-arduino/@codingman/serial-string


        void setup() {
            // 시리얼 통신 기본 baud rate: 9600
            Serial.begin(9600);
        }

        void loop() {
            if (Serial.available() != 0) { // 수신된 값이 있을 경우(return 0 -> 수신된 값 없음)
                // 하나의 글자
                char ch = Serial.read();
                Serial.println(ch);

                // 문자열
                String str = Serial.readStringUntil('\n');
                Serial.println(str);
            }
        }

Trouble Shooting
- 아두이노 Serial.print() 함수는 시리얼 모니터(아두이노), python CLI에 동시 동작
- 데이터 값을 송수신하는 과정에서 두 화면에 print되지 않는다(독립적으로 활용해야 함)

![image.png](./image.png)
![image-1.png](./image-1.png)

- 아두이노 코드를 지속적으로 컴파일할 경우 기존에 사용하던 포트가 종료되지 않아 error 발생
- 자연적으로 포트가 종료되길 기다리거나, reload하는 과정이 필요
- 실제로 프로젝트 완성할 시 프로그램을 단 1회 실행하여 종료전까지 프로그램을 reload할 일이 없으므로 error 발생하지 않을 것으로 기대

# Today What I Do

## 화분 표정 구현


### 살살 화분의 압력센서 터치시

![기분좋음 (2)](https://user-images.githubusercontent.com/57944215/216549624-19a0d38f-9275-4be0-bfc2-6031120e0428.gif)


### 강하게 화분의 압력센서 터치시 

![슬픔 (2) (2)](https://user-images.githubusercontent.com/57944215/216549605-6e090762-2256-4ae7-b31f-c701a76a9ff5.gif)

## Gif이미지 내 글씨 삽입



### 삽입 전 jpg 그림

![example](https://user-images.githubusercontent.com/57944215/216550141-fff0d05d-eca4-46af-a2bf-996313d68f61.jpg)

###  삽입 후 jpg 그림

![image.png](./image.png)

### 날씨화면 내 글씨 삽입

![image-1.png](./image-1.png)

#### 1. 데이터 파싱

![image-2.png](./image-2.png)

- Server로 부터 받은 데이터 파싱

- 현재 날짜 값과 파싱한 데이터가 일치할 경우를 판별

![image-3.png](./image-3.png)

![image-4.png](./image-4.png)

- 파싱한 데이터의 시간값과 현재 시간값이 일치하는 것을 확인할 수 있음

- topic에 맞게 데이터를 추출하는 과정, 구름 데이터를 받아오는 과정 추가 예정

#### 2. 날씨 화면

##### 2-1) 설계

![image-5.png](./image-5.png)

- 받아온 데이터 값에 따라 출력할 화면을 경우의 수에 따라 구분

##### 2-2) 구현

![image-6.png](./image-6.png)

- Test 화면으로 .gif파일에 파싱한 데이터를 text로 추가하는 방식으로 화면 구현

- 구현 단에서 openCV로 한글 지원이 안됨을 파악

- openCV->PIL->openCV 변환을 통해 문제를 해결

- 타입 변환하는 테스트 코드 "ptest2.py" 파일 참조

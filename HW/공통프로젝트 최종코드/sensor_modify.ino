

/*
void setup(){  
  // baud rate
  Serial.begin(115200);
} 

void loop(){
    
    String t;
    if (Serial.available())
    {
      String temp = Serial.readStringUntil('\n');
      temp += " clear";
      Serial.println(temp);
    }
    delay(100);
}
*/

// triggerPin, EchoPin 지정
 
#include "DHT.h"
#define DHTPIN 12 
#define DHTTYPE DHT11
//온,습도 센서 
#define LIGHT A0
// 광,조도 센서 
#define SOUND A1
// 소리 센서 
#define MOISTURE A2
// 토양습도 센서  
#define TOUCH1 A4
// 터치 센서 1
#define TOUCH2 A5
// 터치 센서 2 
#define SHOCK 13
// 충격 센서 


DHT dht(DHTPIN, DHTTYPE); 
void setup() {

    pinMode(SHOCK,INPUT);
    Serial.begin(115200); 
    Serial.println("Comport Connected..!");
    
  dht.begin();
}

void loop() {
  float plant_temp = dht.readTemperature()-5;
  float plant_hum = dht.readHumidity();
  float plant_light = analogRead(LIGHT);
  float plant_moi = analogRead(MOISTURE);
  
  int SensorReading1 = analogRead(TOUCH1);
  int plant_touch1 = map(SensorReading1, 0, 1024, 0, 255);
  int SensorReading2 = analogRead(TOUCH2);
  int plant_touch2 = map(SensorReading2, 0, 1024, 0, 255);
  int plant_shock = digitalRead(SHOCK);
  int plant_sound = analogRead(SOUND);

  int touch_output = 0;
  int attack_output = 0;


// 1) 볼을 눌렀을 때 조건문 
  
  if(plant_touch1 < 10 && plant_touch2 < 10)
  //touch1,2 잡지 않았을 때  
  {
    touch_output = 0;
  } 
  
  else if(plant_touch1 >= 100 || plant_touch2 >= 100)
  // touch1,2 중 1개라도 꽉 잡았을 때 tocuh  
  {
    touch_output = 2;
  }

  else 
  // touch1,2 중 1개라도 살짝 잡았을 때
  {
    touch_output = 1;
  }

// 2) 딱콩 때렸을 때 조건문 

  if(plant_shock == 0 && plant_sound < 8)
  //화분을 안때렸을 때 attack = 0
  {
    attack_output = 0;
  } 
  
  else if(plant_shock == 1 && plant_sound >= 8)
  //화분을 때렸을 때 attack = 1
  {
    attack_output = 1;
  }
  
  Serial.println("\"temperature\":\""+String(plant_temp)+"\",\"humidity\":\""+String(plant_hum)+"\",\"light\":\""+String(plant_light)+"\",\"soil_moisture\":\""+String(plant_moi)+"\",\"touch\":\""+String(touch_output)+"\",\"attack\":\""+String(attack_output)+"\"}");
  
  delay(1000);
}

void serial_read(){
  if(Serial.available() > 0) {  
    String msg = Serial.readStringUntil('\n');

    Serial.println("# be get message");
  }
}  

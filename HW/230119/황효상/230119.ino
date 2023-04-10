#define triggerPin 8
#define echoPin 9
String nom = "Arduino";
String msg = "";
double Duration;
void setup() {
  pinMode(triggerPin, OUTPUT); 
  pinMode(echoPin, INPUT); 
  Serial.begin(115200); 
  delay(2000);
  Serial.println("Comport Connected..!");
  delay(500);
}

void loop() {
  // put your main code here, to run repeatedly:
  
  digitalWrite(triggerPin, LOW);
  delayMicroseconds(2);
  digitalWrite(triggerPin, HIGH);
  delayMicroseconds(10); 
  digitalWrite(triggerPin, LOW); 

  Duration = pulseIn(echoPin, HIGH); 
  Duration = Duration* 17 / 1000;
  readSerialPort();
  
  if(msg != ""){
    sendData();
  }
  delay(500);
}

void readSerialPort() {
  msg = "";
  if (Serial.available()) {
    delay(10);
    while (Serial.available() > 0){
      msg += (char)Serial.read();
    }
    Serial.flush();
  }
}
void sendData(){
  Serial.print(nom);
  Serial.print(" received : ");
  Serial.print(String(Duration));
    Serial.flush();
}

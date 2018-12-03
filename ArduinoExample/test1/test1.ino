#include <SoftwareSerial.h> //시리얼 통신 라이브러리 호출
#include "Servo.h" //서보 라이브러리
int servoPin = 9;
Servo myservo; //서보객체
int blueTx=2;   //Tx (블투 보내는핀 설정)
int blueRx=3;   //Rx (블투 받는핀 설정)
SoftwareSerial mySerial(blueTx, blueRx);  //시리얼 통신을 위한 객체선언
String myString=""; //받는 문자열
int angle = 0;
 
void setup() {
  myservo.attach(servoPin);   //서보 시그널 핀설정
  //myservo.write(0);     //서보 초기각도 0도 설정
  mySerial.begin(9600); //블루투스 시리얼 개방
}
 
void loop() {
  while(mySerial.available())  //mySerial 값이 있으면
  {
    char myChar = (char)mySerial.read();  //mySerial int형식의 값을 char형식으로 변환
    myString+=myChar;   //수신되는 문자열을 myString에 모두 붙임 (1바이트씩 전송되는 것을 모두 붙임)
    delay(5);           //수신 문자열 끊김 방지
  }
  
  if(!myString.equals(""))  //myString 값이 있다면
  {
    Serial.println("input value: "+myString); //시리얼모니터에 myString값 출력
 
      if(myString=="on")  //myString 값이 'on' 이라면
      {
        
              myservo.write(0); 
              delay(15); 
           
       // myservo.write(90);   
      } else {
       
              myservo.write(180); 
              delay(15); 
            
       // myservo.write(0);  
      }
    myString="";  //myString 변수값 초기화
  }
}

#include <SoftwareSerial.h>
#include <Servo.h>

int Tx = 0;   //블루투스 보내는 핀 설정
int Rx = 1;   //블루투스 받는 핀 설정

int servoPin1 = 13;    
Servo servo1;
String myString=""; //받는 문자열

SoftwareSerial bluetooth(Tx, Rx);   //시리얼 통신을 위한 객체 선언

void setup(){
  Serial.begin(9600);
  delay(100);
  bluetooth.begin(9600);    //블루투스 시리얼 개방
  servo1.attach(servoPin1); //서보 시그널 핀 설정
   servo1.write(90);
}

void loop(){

  if(bluetooth.available()){
    char myChar = (char)servo1.read();  //mySerial int형식의 값을 char형식으로 변환
    myString+=myChar;   //수신되는 문자열을 myString에 모두 붙임 (1바이트씩 전송되는 것을 모두 붙임)
    delay(5);           //수신 문자열 끊김 방지
  }
  
 if(!myString.equals(""))  {    //myString 값이 있다면 실행한다.
    Serial.println("input value: "+myString); //시리얼모니터에 myString값 출력
 
      if(myString=="급식주기")  //myString 값이 '급식주기'와 일치하면
      {                    
        servo1.write(90);     //각도 90도로 움직임
      } else {
        servo1.write(0);   //각도 0도로 움직임
      }
    myString="";  //myString 변수값 초기화
  }
}

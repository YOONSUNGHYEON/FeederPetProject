#include <HX711.h>

#include "HX711.h"  //You must have this library in your arduino library folder
#include<Servo.h>
#include <SoftwareSerial.h>
 

#define DOUT  3

#define CLK  2

 

HX711 scale(DOUT, CLK);

int bluetoothTx = 6;
int bluetoothRx = 7;
 
SoftwareSerial bluetooth(bluetoothTx, bluetoothRx); 
byte buffer[256];
int bufferPosition; // 버퍼에 기록할 위치


//Change this calibration factor as per your load cell once it is found you many need to vary it in thousands

float calibration_factor = -400000; //-106600 worked for my 40Kg max scale setup
Servo myservo;
int pos = 90;  
float num = 0;
Servo microServo;    //서보모터 객체 선언
const int servoPin = 5;    //서보모터 제어핀 할당

void setup() {

  Serial.begin(9600);  
  myservo.attach(9);
  Serial.println("Press T to tare");
  bluetooth.begin(9600);
  scale.set_scale(calibration_factor);  //Calibration Factor obtained from first sketch
  bufferPosition = 0;
  microServo.attach(servoPin);    //서보모터 초기화
  scale.tare();             //Reset the scale to 0  

}


void loop() {
  int angle;    //각도 변수 선언
  
  
  if(bluetooth.available())
  {
    byte data = bluetooth.read();
    buffer[bufferPosition++] = data; 
    num =  (buffer[0]-48)*100 + (buffer[1]-48)*10 + (buffer[2]-48);
    float lastNum = num*0.001;
    Serial.println(lastNum);

    while(true)
    {
        Serial.print("Weight: ");
        Serial.print(scale.get_units(), 3);  //Up to 3 decimal points
        Serial.println(" kg"); //Change this to kg and re-adjust the calibration factor if you follow lbs
        float z = scale.get_units(); 
        Serial.println(z);
     
       if(z > lastNum)
       {
          pos = 90;
          myservo.write (pos);
          //bluetooth.println("1");
          break;
        } 
       else
       {
           for (angle = 0; angle < 170; angle++)
           {
              microServo.write(angle);    //angle(각도)값으로 서보모터 제어
              delay(10);                    //delay로 각도의 변화 속도를 조절
           }
           pos = 180;
       }
        myservo.write (pos);
    }

    //char toSend = 'yyyy';
    //bluetooth.print(toSend);
    
  }
  
  //Read from usb serial to bluetooth
  if(Serial.available())
  {

    //char toSend = (char)Serial.read();
    //char toSend = "";
    //bluetooth.print(toSend);
    myservo.write (90);

      //scale.tare();  //Reset the scale to zero      

  }
 }

  

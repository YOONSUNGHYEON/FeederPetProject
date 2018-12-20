//로드셀+모터2개+블루투스를 연결하여 앱에서 급식주기를 누르고 블루투스 연결성공하면 무게 입력 후 확인 누르면 보조모터 1번 왔다갔다 하고나서 주모터 돌아가는데 둘다 계속 같이돌다가 해당무게가되면 멈춘다, 입구를 작게해서 조금만 사료가 떨어지게 한다.
#include <HX711.h>

#include "HX711.h"  //You must have this library in your arduino library folder
#include<Servo.h>
#include <SoftwareSerial.h>
 

//#define DOUT 3
//#define CLK  2

 

HX711 scale(A2, A3);

int bluetoothTx = 7;
int bluetoothRx = 8;
 
SoftwareSerial bluetooth(bluetoothTx, bluetoothRx); 
byte buffer[256];
int bufferPosition; // 버퍼에 기록할 위치


//Change this calibration factor as per your load cell once it is found you many need to vary it in thousands

float calibration_factor = -400000; //-106600 worked for my 40Kg max scale setup
Servo myservo;
int pos = 90;  
float num = 0;
int pos2 = 180; // servo position in degrees  // 보조서보 초기 180에서 시작
int num2=1; // 보조서보 while문에 이용
Servo microServo;    //서보모터 객체 선언
const int servoPin = 12;    //서보모터 제어핀 할당

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
 // int angle;    //각도 변수 선언
  
  
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
          //myservo.write (pos);
          bluetooth.println("1");
          break;
        } 
       else
       {
              num2=1;
             while(num2==1){
            for(pos2 = 90; pos2 < 130; pos2 += 1)  // 90도에서 130도로 이동합니다.
            {                                  // 이동할때 각도는 1도씩 이동합니다. 
                microServo.write(pos2);              // 'pos'변수의 위치로 서보를 이동시킵니다.
                delay(40);                       // 서보 명령 간에 40ms를 기다립니다.
            } 
    
            for(pos2 = 130; pos2 >90 ; pos2 -= 1)  // 130도에서 90도로 이동합니다.
            {                                  // 이동할때 각도는 1도씩 이동합니다. 
              microServo.write(pos2);              // 'pos'변수의 위치로 서보를 이동시킵니다.
              delay(40);                       // 서보 명령 간에 40ms를 기다립니다.
            } 
          num2--;
          }
           pos = 180;
       }
       // myservo.write (pos);
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

  

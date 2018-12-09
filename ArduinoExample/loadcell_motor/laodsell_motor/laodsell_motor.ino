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


//Change this calibration factor as per your load cell once it is found you many need to vary it in thousands

float calibration_factor = -400000; //-106600 worked for my 40Kg max scale setup
Servo myservo;
int pos = 90;  

void setup() {

  Serial.begin(9600);  
  myservo.attach(9);
  Serial.println("Press T to tare");
  bluetooth.begin(9600);
  scale.set_scale(calibration_factor);  //Calibration Factor obtained from first sketch
  
  scale.tare();             //Reset the scale to 0  

}


void loop() {

  
  
  if(bluetooth.available())
  {
    //float num = (float)bluetooth.read()*0.001;
    char num = (float)bluetooth.read();
    //float num2 = num;
    Serial.println(num);

    while(true)
    {
        Serial.print("Weight: ");

        Serial.print(scale.get_units(), 3);  //Up to 3 decimal points

        Serial.println(" kg"); //Change this to kg and re-adjust the calibration factor if you follow lbs
        float z = scale.get_units(); 
        Serial.println(z);
     
       if(z > num)
       {
          pos = 90;
          myservo.write (pos);
          char toSend = (char)'y';
          Serial.print(toSend);
          bluetooth.print(toSend);
          break;
        } 
       else
       {
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

  

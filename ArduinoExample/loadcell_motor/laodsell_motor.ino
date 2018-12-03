#include "HX711.h"  //You must have this library in your arduino library folder
#include<Servo.h>
 

#define DOUT  3

#define CLK  2

 

HX711 scale(DOUT, CLK);

 

//Change this calibration factor as per your load cell once it is found you many need to vary it in thousands

float calibration_factor = -400000; //-106600 worked for my 40Kg max scale setup
Servo myservo;
int pos = 90;  

void setup() {

  Serial.begin(9600);  
  myservo.attach(9);
  Serial.println("Press T to tare");

  scale.set_scale(calibration_factor);  //Calibration Factor obtained from first sketch

  scale.tare();             //Reset the scale to 0  

}


void loop() {

  Serial.print("Weight: ");

  Serial.print(scale.get_units(), 3);  //Up to 3 decimal points

  Serial.println(" kg"); //Change this to kg and re-adjust the calibration factor if you follow lbs
  float z = scale.get_units(); 
  Serial.println(z);



 if(z > 0.1){
    pos = 180;
  } else {
    pos = 90;
  }
   Serial.println(pos);
  myservo.write (pos);
  

  
  if(Serial.available())

  {

    char temp = Serial.read();

    if(temp == 't' || temp == 'T')

      scale.tare();  //Reset the scale to zero      

  }

}

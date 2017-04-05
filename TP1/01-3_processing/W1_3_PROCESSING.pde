/** W1_3_PROCESSING.pde  **/

import processing.serial.*;
import cc.arduino.*;

Arduino arduino;
int ledPin = 13;

void setup() 
{
  arduino = new Arduino(this, Arduino.list()[1], 57600);
  arduino.pinMode(13, Arduino.OUTPUT);
  
  // Display the list of Arduino cards to find the good one.
  println(Arduino.list());
}


void draw()
{
  arduino.digitalWrite(ledPin, Arduino.HIGH);
  delay(1000);
  arduino.digitalWrite(ledPin, Arduino.LOW);
  delay(1000);
}
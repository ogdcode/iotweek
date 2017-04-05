/** W1_3_PROCESSING.pde  **/

import processing.serial.*;
import cc.arduino.*;

Arduino arduino;
int ledPin = 13;

void setup() 
{
  // Display the list of opened ports to find the one where the card is.
  println((Object[]) Arduino.list());
  
  arduino = new Arduino(this, Arduino.list()[1], 57600);
  arduino.pinMode(13, Arduino.OUTPUT);
}


void draw()
{
  arduino.digitalWrite(ledPin, Arduino.HIGH);
  delay(1000);
  arduino.digitalWrite(ledPin, Arduino.LOW);
  delay(1000);
}
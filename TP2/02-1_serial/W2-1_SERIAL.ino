unsigned long t;

void setup() {
  // Sets up the serial monitor
  Serial.begin(9600);
  while (!Serial) {
  }

  Serial.println("Init OK");
}

void loop() {
  //simpleReadSerial();
  timeMillis();
  delay(2000);
}

// Test serial communication
void simpleReadSerial() {
  // Check if something can be read
  // If not, print "Nothing..."
  if (Serial.available()) {
    int lu = Serial.read();
    Serial.println(lu);
  } else {
    Serial.println("Nothing...");
  }
}

void timeMillis() {
  // If serial connection can be made, then print time since program started
  // Otherwise, print "Nothing..."
  if (Serial.available()) {
    t = millis();
    // Prints time since program started
    Serial.print("$$> ");
    Serial.println(t);
    delay(500);  
  } else {
    Serial.println("Nothing...");
  }
}


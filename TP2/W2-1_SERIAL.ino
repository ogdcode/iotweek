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
}

// Test serial communication
void simpleReadSerial() {
  if (Serial.available()) {
    int lu = Serial.read();
    Serial.println(lu);
  } else {
    Serial.println("Nothing...");
  }
  delay(2000);
}

void timeMillis() {
  t = millis();
  // Prints time since program started
  Serial.print("$$> ");
  Serial.println(t);
  delay(500);  
}


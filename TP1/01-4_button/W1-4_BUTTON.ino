const int BP = 3; // The pin where the button is connected to.

// Initializaton function.
void setup() {
  Serial.begin(9600);
  while (!Serial) {}
  pinMode(BP, INPUT); // BP is an input (the button)
}

// Repeating main function.
void loop() {
  readBtn();
  delay(1000);
}

void readBtn() {
  int reading = digitalRead(BP);
  Serial.print("reading = ");
  Serial.println(reading);
}


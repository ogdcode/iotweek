const byte BTN = 2; // The pin where the button is connected to.
volatile byte state = LOW;

// Initializaton function.
void setup() {
  Serial.begin(9600);
  while (!Serial) {}
  pinMode(BTN, INPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  // Attach inpterrupt #0 (on pin 2) to the blink function, on state change
  attachInterrupt(BTN, makeBlink, CHANGE);
}

// Repeating main function.
void loop() {
  displaySerial("state = ", state);
  digitalWrite(LED_BUILTIN, state);
}

void readBtn() {
  int reading = digitalRead(BTN);
  displaySerial("reading = ", reading);
}

void displaySerial(char* sentence, long value) {
  Serial.print(sentence);
  Serial.println(value);
}

void makeBlink() {
  state = !state; // Switch variable state
}

void delaySeconds(int s) {
  delay(1000 * s);
}




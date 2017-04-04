//#define digitalPinToInterrupt(p)  ( (p) >= 2 && (p) <= 5 ? 0 : -1 ) 
#define INTER 2
#define PIN_1 3
#define PIN_2 18
#define PIN_3 19

volatile unsigned long buttonTime = 0;
volatile int trigger = 0;
int debounce = 50; // Debounce latency in ms
int total = 0;
int inta = 0, intb = 0, intc = 0;

// Initializaton function.
void setup() {
  initPins();
  attachInterrupts();
}

// Repeating main function.
void loop() {
  updateTrigger();
}

// Initialize all pins and assign default values.
void initPins() {
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(INTER, INPUT);
  pinMode(PIN_1, INPUT);
  pinMode(PIN_2, INPUT);
  pinMode(PIN_3, INPUT);

  digitalWrite(LED_BUILTIN, LOW);
  digitalWrite(INTER, HIGH);
  digitalWrite(PIN_1, HIGH);
  digitalWrite(PIN_2, HIGH);
  digitalWrite(PIN_3, HIGH);
}

// Setup interrupts with actions for some (or all) pins.
void attachInterrupts() {
//  attachInterrupt(INTER, detect, FALLING);
  attachInterrupt(PIN_1, detect, FALLING);
//  attachInterrupt(PIN_2, detect, FALLING);
//  attachInterrupt(PIN_3, detect, FALLING);
  attachInterrupt(digitalPinToInterrupt(INTER), detect, FALLING);
  //attachInterrupt(digitalPinToInterrupt(PIN_1), detect, FALLING);
  attachInterrupt(digitalPinToInterrupt(PIN_2), detect, FALLING);
  attachInterrupt(digitalPinToInterrupt(PIN_3), detect, FALLING);
}

// Update the value of trigger depending on which button was pressed
// (used for working with three buttons).
void updateTrigger() {
  if (trigger != 0) {
    total++;
    if (trigger == 1) inta++;
    else if (trigger == 2) intb++;
    else if (trigger == 3) intc++;

    displaySerial("trigger = ", trigger);
    displaySerial("total = ", total);
    trigger = 0;
  }
}

// Detect which button was pressed
// (used for working with three buttons).
void detect() {
  if (digitalRead(PIN_1) == LOW) trigger = 1;
  else if (digitalRead(PIN_2) == LOW) trigger = 2;
  else if (digitalRead(PIN_3) == LOW) trigger = 3;
}

void displaySerial(char* sentence, long value) {
  Serial.print(sentence);
  Serial.println(value);
}

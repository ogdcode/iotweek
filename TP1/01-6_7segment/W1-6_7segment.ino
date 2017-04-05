#define PIN_MATRIX_D 11
#define PIN_MATRIX_E 10
#define PIN_MATRIX_G 9
#define PIN_MATRIX_F 8
#define PIN_MATRIX_A 7
#define PIN_MATRIX_B 6
#define PIN_MATRIX_C 5
#define PIN_MATRIX_DP 4
#define PIN_INTERRUPT_BTN 2

int counter = 9;

byte digits[10][7] = { { 1,1,0,1,1,1,1 },  // 0
                       { 0,0,0,0,0,1,1 },  // 1
                       { 1,1,1,0,1,1,0 },  // 2
                       { 1,0,1,0,1,1,1 },  // 3
                       { 0,0,1,1,0,1,1 },  // 4
                       { 1,0,1,1,1,0,1 },  // 5
                       { 1,1,1,1,1,0,1 },  // 6
                       { 0,0,0,0,1,1,1 },  // 7
                       { 1,1,1,1,1,1,1 },  // 8
                       { 1,0,1,1,1,1,1 }   // 9
                      };


void setup() {
  // pin setup
  pinMode(PIN_MATRIX_D, OUTPUT);
  pinMode(PIN_MATRIX_E, OUTPUT);
  pinMode(PIN_MATRIX_G, OUTPUT);
  pinMode(PIN_MATRIX_F, OUTPUT);
  pinMode(PIN_MATRIX_A, OUTPUT);
  pinMode(PIN_MATRIX_B, OUTPUT);
  pinMode(PIN_MATRIX_C, OUTPUT);
  pinMode(PIN_MATRIX_DP, OUTPUT);
  pinMode(PIN_INTERRUPT_BTN, INPUT);

  // set all outputs to LOW
  digitalWrite(PIN_MATRIX_D, 0);
  digitalWrite(PIN_MATRIX_E, 0);
  digitalWrite(PIN_MATRIX_G, 0);
  digitalWrite(PIN_MATRIX_F, 0);
  digitalWrite(PIN_MATRIX_A, 0);
  digitalWrite(PIN_MATRIX_B, 0);
  digitalWrite(PIN_MATRIX_C, 0);
  digitalWrite(PIN_MATRIX_DP, 0);
  
  // interrupts
  attachInterrupt(digitalPinToInterrupt(PIN_INTERRUPT_BTN), buttonPressed, FALLING);
}

void loop() {
  displayDigit(counter);
}

void displayDigit(int digit) {
  if (counter > 9 || counter < 0) {
    return;
  }
  digitalWrite(PIN_MATRIX_D, digits[digit][0]);
  digitalWrite(PIN_MATRIX_E, digits[digit][1]);
  digitalWrite(PIN_MATRIX_G, digits[digit][2]);
  digitalWrite(PIN_MATRIX_F, digits[digit][3]);
  digitalWrite(PIN_MATRIX_A, digits[digit][4]);
  digitalWrite(PIN_MATRIX_B, digits[digit][5]);
  digitalWrite(PIN_MATRIX_C, digits[digit][6]);
}

void buttonPressed() {
  counter--;
  if (counter < 0) {
    counter = 9;
  }
}


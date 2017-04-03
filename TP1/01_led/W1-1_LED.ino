// the pins where the LEDs are positioned
const int LEDS[] = {2, 3, 4, 5, 6, 7};

void setup() {
  for (int i = 0; i < 6; ++i) {
    pinMode(LEDS[i], OUTPUT);
  }
}

void loop() {
  // the 6 LEDs work by 2 groups of 3
  // they simulate two semaphors
  // they function as follows:
  // RED1 -- ORG1 -- GRN1 -- RED2 -- ORG2 -- GRN2
  //  ON  -- OFF  --  ON  -- OFF  -- OFF  --  ON
  // (3 seconds)
  // OFF  --  ON  -- OFF  -- OFF  --  ON  -- OFF
  // (1 second)
  // OFF  -- OFF  --  ON  --  ON  -- OFF  -- OFF
  // (3 seconds)
    //test();
    doStuff();
}

void test() {
  digitalWrite(LEDS[1], HIGH);
  delay(1000);
  digitalWrite(LEDS[0], LOW);
  delay(1000);
}

void doStuff() {
    digitalWrite(LEDS[1], HIGH);
    digitalWrite(LEDS[4], HIGH);
    digitalWrite(LEDS[0], LOW);
    digitalWrite(LEDS[3], LOW);
    delay(4000);
    digitalWrite(LEDS[0], HIGH);
    digitalWrite(LEDS[3], HIGH);
    digitalWrite(LEDS[2], LOW);
    digitalWrite(LEDS[5], LOW);
    delay(4000);
    digitalWrite(LEDS[2], HIGH);
    digitalWrite(LEDS[5], HIGH);
    digitalWrite(LEDS[1], LOW);
    digitalWrite(LEDS[4], LOW);
    delay(2000);
}


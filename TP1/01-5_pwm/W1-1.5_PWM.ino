const int ANALOG_IN = 0;
const int PWM_OUT = 3;
int val = 0;

void setup() {
  Serial.begin(9600);
  pinMode(ANALOG_IN, INPUT);
  pinMode(PWM_OUT, OUTPUT);
}

void loop() {
  readPotar();
}

void readPotar() {
  val = analogRead(ANALOG_IN);
  Serial.print("ANALOG val = ");
  Serial.println(val);
  delay(3000);
  val = (val * 255) / 1024;
  Serial.print("PWM val = ");
  Serial.println(val);
  analogWrite(PWM_OUT, val);
  delay(3000);
}


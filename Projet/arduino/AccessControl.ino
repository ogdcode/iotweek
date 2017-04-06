/*
 * --------------------------------------------------------------------------------------------------------------------
 * RFID Access Control
 * --------------------------------------------------------------------------------------------------------------------
 * Using MFRC522 library  https://github.com/miguelbalboa/rfid
 * Pins : 
 * RST/Reset   RST          9
 * SPI SS      SDA(SS)      10
 * SPI MOSI    MOSI         11 / ICSP-4
 * SPI MISO    MISO         12 / ICSP-1
 * SPI SCK     SCK          13 / ICSP-3
 */

#include <SPI.h>
#include <MFRC522.h>

#define SS_PIN 10
#define RST_PIN 9
#define LED_GREEN_PIN 5
#define LED_RED_PIN 3
#define LED_BLUE_PIN 6
 
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
String uid = ""; // Card UID
String response = ""; // Serial response buffer

void setup() {
  
  // init
  Serial.setTimeout(5000); // mongolab is SLOW
  Serial.begin(9600);
  SPI.begin(); // Init SPI bus
  rfid.PCD_Init(); // Init MFRC522

  // conf led pins
  pinMode(LED_GREEN_PIN, OUTPUT);
  pinMode(LED_BLUE_PIN, OUTPUT);
  pinMode(LED_RED_PIN, OUTPUT);
  digitalWrite(LED_GREEN_PIN, LOW);
  digitalWrite(LED_BLUE_PIN, LOW);
  digitalWrite(LED_RED_PIN, LOW);
}
 
void loop() {
  
  // Was the card just scanned ? (avoid spam)
  if ( ! rfid.PICC_IsNewCardPresent())
    return;

  // Is the card UID available ?
  if ( ! rfid.PICC_ReadCardSerial())
    return;

  MFRC522::PICC_Type piccType = rfid.PICC_GetType(rfid.uid.sak);
  // Is the card type supported ?
  if (piccType != MFRC522::PICC_TYPE_MIFARE_MINI &&  
    piccType != MFRC522::PICC_TYPE_MIFARE_1K &&
    piccType != MFRC522::PICC_TYPE_MIFARE_4K) {
    return;
  }

  // Light up the blue led to indicate that the card is being processed
  enableLed(0, 0, 255);

  // Convert card UID to string for convenience
  uidToString();
  
  // Send the UID to the serial port
  Serial.println(uid);

  // Wait and read response from serial port
  response = Serial.readStringUntil('\n');
  if (response == "AUTHORIZED") {
    blinkLed(0, 255, 0);
  } else {
    blinkLed(255, 0, 0);
  }

  // Cleanup
  Serial.flush();
  response = "";
  
  // Stop reading card
  rfid.PICC_HaltA();
}

// LED HELPERS

void blinkLed(int red, int green, int blue) { // Quickly flash the rgb led
  for(int i=0; i<5; i++) {
    enableLed(red, green, blue);
    delay(100);
    disableLed();
  }
}

void enableLed(int red, int green, int blue) { // Turn on the rgb led
  analogWrite(LED_RED_PIN, red);
  analogWrite(LED_GREEN_PIN, green);
  analogWrite(LED_BLUE_PIN, blue);
}

void disableLed() { // Turn off the rgb led
  analogWrite(LED_GREEN_PIN, 0);
  analogWrite(LED_RED_PIN, 0);
  analogWrite(LED_BLUE_PIN, 0);
  delay(100);
}

// RFID HELPERS

void uidToString() { // Converts the card UID into a string and store it
  uid = "";
  for (byte i = 0; i < rfid.uid.size; i++) 
  {
     uid.concat(String(rfid.uid.uidByte[i] < 0x10 ? "0" : ""));
     uid.concat(String(rfid.uid.uidByte[i], HEX));
     if (i != rfid.uid.size-1) {
      uid.concat(":");
     }
  }
  uid.toUpperCase();
}

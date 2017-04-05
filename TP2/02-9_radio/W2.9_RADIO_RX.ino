/** 433MHz receiver  **/
#include <VirtualWire.h>

uint8_t buf[VW_MAX_MESSAGE_LEN]; // Array containing the received message (maximum length is VW_MAX_MESSAGE_LEN).
uint8_t buflen = VW_MAX_MESSAGE_LEN; // Maximum length of the array.

void setup() {
  Serial.begin(9600);
  Serial.println("Wait for reception...");
  vw_setup(2000); // Initialize communication with 2000 bps.
  vw_set_rx_pin(11); // Set RX pin to 11 (default setting).
  vw_rx_start(); // Activate reception.
}

void loop() {
  if (vw_have_message) // If a message has been received
  {
    if (vw_get_message(buf, &buflen))
    {
      int i;
      for (i = 0; i < buflen; ++i)
      {
        Serial.print((char) buf[i]);
      }
      Serial.println();
    }
  }
}

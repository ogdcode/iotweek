#include <VirtualWire.h>
#include <VirtualWire_Config.h>

#define DATA_PIN 2

const char *msg = "Bonjour"; // Tableau qui contient notre message

void setup() {
  Serial.begin(9600);
  vw_set_tx_pin(DATA_PIN);
  vw_setup(2000);  
  // initialisation de la communication à 2000 b/s
}

void loop() {
  Serial.println("Sending packet...");
  vw_send((uint8_t *)msg, strlen(msg)); // On envoie le message
  vw_wait_tx(); // On attend la fin de l’envoi
  delay(1000); // On attend 1s
}


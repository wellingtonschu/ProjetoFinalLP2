#include <dht.h>
dht DHT;
#define DHT22_PIN 4

int ledPin = 10

void setup(){
    Serial.begin(9600);
    Serial.println("Bem vindo ao sistema de irrigação Panaca");
    Serial.println();
}

void loop(){
    int chk;
    Serial.print("Status dos Sensores: ");
    chk = DHT.read(DHT22_PIN); //Leitura de dados
    switch (chk){
        case DHTLIB_OK:
            Serial.println("Todos os sistemas funcionando \t");
            break;
        case DHTLIB_ERROR_CHECKSUM:
            Serial.println("Erro de Checksum \t");
            break;
        case DHTLIB_ERROR_TIMEOUT:
            Serial.println("Erro de Tempo \t");
            break;
        default:
            Serial.println("Erro desconhecido \t");
            break;
    }

    Serial.print("Umidade do ar: ");
    Serial.println(DHT.humidity,1);
    Serial.print("Temperatura do ambiente: ");
    Serial.println(DHT.temperature,1);
    Serial.print("Umidade do solo: ");
    Serial.println(analogRead(0));
    delay(60000);

    if((DHT.humidity >= 50) && (DHT.temperature >= 24)  && (analogRead(0) > 20)){
    pinMode(ledPin,OUTPUT);
    digitalWrite(ledPin, HIGH);
    delay(5000);
    digitalWrite(ledPin, LOW);
    }else
    digitalWrite(ledPin, LOW);
}

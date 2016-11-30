const int LEDVERMELHO = 12;
const int LEDAMARELO = 11;
const int LEDVERDE = 10;

int byteEntrada = 0;

void setup(){
  Serial.begin(9600); // Abre a porta serial
  pinMode(LEDVERMELHO,OUTPUT);
  pinMode(LEDAMARELO,OUTPUT);
  pinMode(LEDVERDE,OUTPUT);
  
  digitalWrite(LEDVERMELHO,LOW);
  digitalWrite(LEDAMARELO,LOW);
  digitalWrite(LEDVERDE,LOW);
}

void loop(){
  if(Serial.available()>0){
    byteEntrada = Serial.read();
      if(byteEntrada == '0'){
        digitalWrite(LEDVERDE,LOW);
      }else if(byteEntrada == '1'){
        digitalWrite(LEDVERDE,HIGH);
      }
      if(byteEntrada == '2'){
        digitalWrite(LEDAMARELO,LOW);
      }else if(byteEntrada == '3'){
        digitalWrite(LEDAMARELO,HIGH);
      }
      if(byteEntrada == '4'){
        digitalWrite(LEDVERMELHO,LOW);
      }else if(byteEntrada == '5'){
        digitalWrite(LEDVERMELHO,HIGH);
      }
  }
}
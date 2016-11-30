
const int LEDVERDE = 10;

int byteEntrada = 0;

void setup(){
  Serial.begin(9600); // Abre a porta serial
  pinMode(LEDVERDE,OUTPUT);
  
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
  }
}

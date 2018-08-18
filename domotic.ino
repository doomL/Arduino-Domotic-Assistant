int LED_CUCINA = 2; // Initializing pin number 
int LED_SALOTTO = 3;
int LED_BAGNO= 4;
int LED_INGRESSO = 5;
int LED_RIPOSTIGLIO = 6;
int LED_CAMERA = 7;
int LED_STUFA= 8;
int FAN_VENTILATORE = 9;
int MQ3 = 12;
int LM35 = A0;
int tMax = 28 ;
int tMin = 20 ;
void setup() //The setup function will only run once, after each powerup.It initializes and sets the initial values
{
  Serial.begin(9600);   //Sets the baud for serial data transmission (Bits Per Second)
  pinMode(LED_CUCINA,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(LED_SALOTTO,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(LED_BAGNO,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(LED_INGRESSO,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(LED_RIPOSTIGLIO,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(LED_CAMERA,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(LED_STUFA,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  pinMode(FAN_VENTILATORE,OUTPUT); //Specify behaviour of pin to work either as an input or an output
  Serial.println(" ");
}

void loop() 
{
  String data;  //Recived data will get stored in this variable
  if(Serial.available()) //Here We're checking whether data is available or not
  {
    data=Serial.readString(); //Data received
     Serial.println(data);
    if(data=="setMin"){
      Serial.write('\r');
      while(!Serial.available()){
      
        }
        tMin=Serial.read();
  }
    if(data=="setMax"){
      Serial.println('\r');
      while(!Serial.available()){
      
        }
      tMax=Serial.parseInt();
    }
    if(data=="cucinaOn")//LED ON
    {
      digitalWrite(LED_CUCINA,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="cucinaOff")//LED OFF
    {
      digitalWrite(LED_CUCINA,LOW);
    }
     if(data=="salottoOn")//LED ON
    {
      digitalWrite(LED_SALOTTO,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="salottoOff")//LED OFF
    {
      digitalWrite(LED_SALOTTO,LOW);
    }
     if(data=="bagnoOn")//LED ON
    {
      digitalWrite(LED_BAGNO,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="bagnoOff")//LED OFF
    {
      digitalWrite(LED_BAGNO,LOW);
    }
     if(data=="ingressoOn")//LED ON
    {
      digitalWrite(LED_INGRESSO,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="ingressoOff")//LED OFF
    {
      digitalWrite(LED_INGRESSO,LOW);
    }
     if(data=="ripostiglioOn")//LED ON
    {
      digitalWrite(LED_RIPOSTIGLIO,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="ripostiglioOff")//LED OFF
    {
      digitalWrite(LED_RIPOSTIGLIO,LOW);
    }
     if(data=="cameraOn")//LED ON
    {
      digitalWrite(LED_CAMERA,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="cameraOff")//LED OFF
    {
      digitalWrite(LED_CAMERA,LOW);
    }
     if(data=="stufaOn")//LED ON
    {
      digitalWrite(LED_STUFA,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="stufaOff")//LED OFF
    {
      digitalWrite(LED_STUFA,LOW);
    }
     if(data=="ventilatoreOn")//LED ON
    {
      digitalWrite(FAN_VENTILATORE,HIGH);  // Write a HIGH or a LOW value to a digital pin
    }
    if(data=="ventilatoreOff")//LED OFF
    {
      digitalWrite(FAN_VENTILATORE,LOW);
    }
    if(data=="temp")
    {
      Serial.println(analogRead(LM35)* 0.48828125);
    }
    if(data=="tMax")
      Serial.println(tMax);
      
  }
      if(digitalRead(MQ3)==LOW){
        Serial.println("Attenzione Fuga di Gas");
          delay(3000);
      }
    

      if(analogRead(LM35)* 0.48828125 <=tMin){
        digitalWrite(LED_STUFA,HIGH);
        digitalWrite(FAN_VENTILATORE,LOW);
      }
     else if(analogRead(LM35)* 0.48828125 >=tMax){
        digitalWrite(LED_STUFA,LOW);
        digitalWrite(FAN_VENTILATORE,HIGH);
      }
}



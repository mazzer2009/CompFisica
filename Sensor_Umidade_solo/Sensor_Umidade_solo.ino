int umidade;
int temperatura;
int umidadeSolo;
String aux;

void setup()
{
  Serial.begin(9600);
}
int i=0;
void loop()
{
  // print a random number from 10 to 19
  umidade = random(0, 100);
  temperatura = random(0, 43);
  umidadeSolo = random(0, 100);
  aux = "1;"+(String)umidade+"|"+"2;"+(String)temperatura+"|"+"3;"+(String)umidadeSolo;
  Serial.println(aux);
  delay(1000);
  i++;
  if (i==100)
  { 
    i=0;
  }
} 

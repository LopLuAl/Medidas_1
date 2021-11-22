#include <Adafruit_ADS1X15.h>

Adafruit_ADS1115 ads;  /* Use this for the 16-bit version */

float tension_adc_2_tension_red(int16_t counts);
float tension_adc_2_corriente_red(int16_t counts);

void setup(void)
{
  Serial.begin(9600);
  Serial.println("Hello!");
  Serial.println("Getting single-ended readings from AIN0..3");
  Serial.println("ADC Range: +/- 6.144V (1 bit = 3mV/ADS1015, 0.1875mV/ADS1115)");

  if (!ads.begin()) {
    Serial.println("Failed to initialize ADS.");
    while (1);
  }
}

void loop(void)
{
  /**
     CANAL 0 -----> SESNOR I
     CANAL 1 -----> SESNOR V
  **/
  /*SENSADO TENSION*/
  //Serial.print("Tension convertida a red");Serial.print("  "); Serial.print("  "); 
  Serial.print(tension_adc_2_tension_red(ads.readADC_SingleEnded(1))); Serial.println("V");
  //Serial.print("Corriente convertida a red");Serial.print("  "); Serial.print("  "); 
  Serial.print(tension_adc_2_corriente_red(ads.readADC_SingleEnded(0))); Serial.println("A");
  delay(1);
}
float tension_adc_2_corriente_red(int16_t counts){
  float mean = 2.515f;
  float fsRange = 6.144f;
  float sqrt_2 = 1.41;
  float cte_sensor = 15.15; // VER DATASHEET P.7 YO TENGO SENSOR 20A 
  
  return (  ( ((counts * (fsRange / 32768))- mean)* cte_sensor*sqrt_2 )*1000/1000.0f );
}
float tension_adc_2_tension_red(int16_t counts){
  float mean = 2.528f;
  float fsRange = 6.144f;
  float cte_sensor = 439.01; // VER FOTO HOJA MIA
  float sqrt_2 = 1.41;
  return (  ( ((counts * (fsRange / 32768))-mean)*cte_sensor*sqrt_2 )*1000/1000.0f );
}
//COSAS FALOPAS EN EL CODIGO
//1. RENDONDEO
//2. DE DONDE SAQUE LA CTE DEL SENSOR V  e I
//https://arduino.stackexchange.com/questions/53604/print-with-round-towards-zero-instead-of-round-to-nearest
//  float cte_sensor = 439.01; // VER FOTO HOJA MIA SENSOR V
//Datasheet del sensor de I
//https://pdf1.alldatasheet.com/datasheet-pdf/view/168326/ALLEGRO/ACS712.html

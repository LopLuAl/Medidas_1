package com.example.app_cliente;

import android.util.Log;

public class Operations {

    private double currentMeanValue = 0.0;
    private double voltageMeanValue = 0.0;
    private double currentRmsValue = 0.0;
    private double voltageRmsValue = 0.0;
    private double activePowerValue = 0.0;


    public double getCurrentMeanValue(){return currentMeanValue;}
    public double getVoltageMeanValue(){return voltageMeanValue;}
    public double getCurrentRmsValue(){return currentRmsValue;}
    public double getVoltageRmsValue(){return voltageRmsValue;}

    public void setCurrentMeanValue(double value){this.currentMeanValue = value;}
    public void setVoltageMeanValue(double value){this.voltageMeanValue = value;}
    public void setCurrentRmsValue(double value){this.currentRmsValue = value;}
    public void setVoltageRmsValue(double value){this.voltageRmsValue = value;}


    /* Returns mean value of a given array */
    public double meanValue(int[] value){

        double meanValue=0;
        int arrayLength = value.length - 1;

        for(int i = 1; i <= arrayLength; i++){
            meanValue+=value[i];
        }

        meanValue = meanValue/arrayLength;
        return meanValue;
    }

    public double rmsValue(int[] value){

        double rmsValue = 0;
        int arrayLength = value.length -1;

        for(int i = 1; i <= arrayLength; i++){
            rmsValue+=(value[i])^2;
        }
        rmsValue = Math.sqrt(rmsValue/arrayLength);

        return rmsValue;
    }

    public double activePowerValue(){

        this.activePowerValue = currentRmsValue * voltageRmsValue;

        return this.activePowerValue;
    }

    public double apparentPower(int[] voltage, int[] current){

        double apparentPower = 0;
        int voltageLength = voltage.length -1;
        int currentLength = current.length -1;

        if(voltageLength == currentLength) {
            for (int i = 1; i <= voltageLength; i++) {

                apparentPower += voltage[i] * current[i];
            }
            apparentPower = apparentPower/voltageLength;

        }else{
            Log.d("Operation apparentPower", "Vector lengths don't match");
            return -1;
        }

        return apparentPower;
    }
}
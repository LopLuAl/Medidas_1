package com.example.app_cliente;

import static java.lang.Math.sqrt;

public class Fft {

    // Defino los buffers, potencia de 2 para mas placer y por la FFT
    private int POW_FREC_SHOW = 10;
    private int POW_TIME_SHOW = 8;
    private int POW_FFT_BUFFER = 16;

    private int BUFFER_SIZE_SHOW_FREQ = (int) Math.pow(2,POW_FREC_SHOW);
    private int BUFFER_SIZE_SHOW_TIME = (int) Math.pow(2,POW_TIME_SHOW);
    private int BUFFER_SIZE = (int) Math.pow(2,POW_FFT_BUFFER);

    //---------------------------------------------------------------------------------------
    //-------------------------- NATIVE Cpp -------------------------------------------------
    //---------------------------------------------------------------------------------------
    // Cargo la libreria en C nativo "signal_proces-lib.so" (la que hicimos acá)
    static
    {
        System.loadLibrary("signal_proces_lib");
    }

    // Delcaro las funciones que voy a utilizar de la libreria
    private native String getNativeString(); // Esta funcion esta contenida en la libreria
    private native double[] calcularFFT(short[] input, int elementos);

    // Contador de tiempo de ejecucion
    private double time_exe = 0.0;

    private double[] fft( short[] bufferShort ) {   //Nuevo metodo con logica para obtener json

        long startTime = System.currentTimeMillis();
        double[] bufferDouble;

        // Calculo FFT en la libreria de C
        bufferDouble = calcularFFT(bufferShort, BUFFER_SIZE);

        time_exe = System.currentTimeMillis() - startTime;
        // Actualizo el tiempo de calculo

        // obtenemos el modulo y mostramos en el grafico de FFT
        int buffer_mod_count = 0;
        for (int i = 0; i < BUFFER_SIZE_SHOW_FREQ; i++) {
            // calculamos el modulo
            double aux_mod = sqrt(bufferDouble[buffer_mod_count] * bufferDouble[buffer_mod_count] + bufferDouble[buffer_mod_count + 1] * bufferDouble[buffer_mod_count + 1]);

            // Adelantamos el index del buffer con un paso grande, submuestreando la salida real
            // asi no colgamos el grafico con muchos puntos.
            buffer_mod_count += 2 ^ (POW_FFT_BUFFER - POW_FREC_SHOW);
        }

        return bufferDouble;
    }
}

package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{

    static byte[] data;
    static int[] byte_occurrences = new int[256];
    static int[][] depending_byte_occurrences = new int[256][256];

    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }

    public static String entropia(){

        double result = 0;
        double omega = data.length;

        for(int i=0; i<256; i++){

            double probability = byte_occurrences[i]*1.0 / omega;

            if(probability != 0)
                result += probability * customLog(2,probability);

        }

        double result2 = 0;

        for(int x=0; x<256; x++){

            for(int y=0; y<256; y++){

                    double omega_that_x_is_before_some_y = byte_occurrences[x]*1.0;
                    double probability_that_x_is_before_some_y = byte_occurrences[x]*1.0 / omega;
                    double var_probability = depending_byte_occurrences[x][y] / omega_that_x_is_before_some_y;

                    if(var_probability != 0 && probability_that_x_is_before_some_y !=0)
                        result2 += probability_that_x_is_before_some_y * var_probability * customLog(2,var_probability);
            }

        }

        return ("Entropia : " + -result + "  Entropia Warunkowa : " + -result2);

    }



    public static void main(String[] args) throws IOException
    {

        data = Files.readAllBytes(Paths.get("test3.bin"));

        for(int i=0; i<256; i++){
            byte_occurrences[i] = 0;
        }

        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                depending_byte_occurrences[i][j] = 0;
            }
        }

        for(int i=0; i<data.length; i++){
            if(i==0)
                depending_byte_occurrences[0][data[i]+128]++;
            else
                depending_byte_occurrences[data[i-1]+128][data[i]+128]++;
            byte_occurrences[data[i]+128]++;
        }

        System.out.println(entropia());
    }
}
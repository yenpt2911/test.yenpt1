package com.nopcommerce;



public class Test {
    public static void main(String[] args) {


        int height =5, width = 5;
        int i;

        for(i = 1; i <= height; i++){
            // Genarate row
            for(int j = 1; j <= width; j++) {
                // Generate element by index
                if (j == 1 || j == width || (i>1 && i < height && j==i)){
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }


            System.out.println("");
        }
}}
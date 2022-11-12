package com.nopcommerce.register;

public class TestJava2 {
    public static void main(String[] args) {
        int height =5, width = 5;

        for(int row = 1; row <= height; row ++){
            for(int col = 1; col <= width; col++) {
                if (col == 1 || col == width || (row>1 && row < height && col==row)){
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }

            System.out.println("");
        }

    }
}

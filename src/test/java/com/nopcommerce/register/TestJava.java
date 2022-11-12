package com.nopcommerce.register;

public class TestJava {
    public static void main(String[] args) {


        int height = 5, width = 5;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == col || row + col == width - 1) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println("");
        }
    }
}

package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.pluralsight.console.scanner;

public class Main {

    public static void main(String[] args){

        // Home Screen

        boolean running = true;

        while(running) {
            System.out.println("\n=======Welcome=======\n");
            System.out.println("What would you like to do?\n");
            System.out.println("1. Add a deposit");
            System.out.println("2. Make a payment (Debit)");
            System.out.println("3. Display Ledger");
            System.out.println("4. Exit");
            System.out.println("\nPlease select an option");

            int choice = scanner.nextInt();
            scanner.nextLine();


            // Switch Cases for main menu

        }






    }
}

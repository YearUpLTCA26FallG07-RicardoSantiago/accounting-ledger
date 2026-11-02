package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
            switch (choice) {
                case 1:
                    addDeposit();
                    break;

                //case 2:
                    //break;

                //case 3:
                    //break;

                case 4:
                    running = false;
                    System.out.println("\nHave a nice Day!");
                    break;

            }

        }






    }

    // Add Deposit method

    public static void addDeposit() {

        System.out.println("\n=======ADD DEPOSIT=======");

        System.out.println("Item: ");
        String item = scanner.nextLine();

        System.out.println("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        transaction deposit = new transaction();

        deposit.setDate(LocalDate.now());
        deposit.setTime(LocalTime.now());
        deposit.setItem(item);
        deposit.setVendor(vendor);
        deposit.setPrice(price);

        System.out.println("\nDeposit Added:");
        System.out.println(deposit);

    }
}

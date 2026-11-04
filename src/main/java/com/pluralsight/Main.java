package com.pluralsight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
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

                case 2:
                    makePayment();
                    break;

                case 3:
                    displayLedger();
                    break;

                case 4:
                    running = false;
                    System.out.println("\nHave a nice Day!");
                    break;

            }

        }






    }

    // Add Deposit method

    public static void addDeposit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


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

        try  (FileWriter writer = new FileWriter("Transactions.csv",true)) {

            String newitem = deposit.getDate()+","+deposit.getTime().format(formatter)+","+deposit.getItem()+","+deposit.getVendor()+","+deposit.getPrice();

            writer.write(newitem + "\n");
        }
        catch (IOException e) {
            System.out.println("Error writing in the file");
        }

        System.out.println("\nDeposit Added:");
        System.out.println(deposit);



    }

    //Payment Method

    public static void makePayment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("\n=======Who would you like to pay: =======");
        String vender = scanner.nextLine();

        System.out.println("Enter how much you would like to pay: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("What is the payment for: ");
        String itemp = scanner.nextLine();

        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(formatter);

        try {
            FileWriter writer = new FileWriter("Transactions.csv", true);

            writer.write(date +","+ time + "," + itemp + "," + vender + "," + -(amount) + "\n");
            writer.close();

            System.out.println("\nPayment success");
        } catch (IOException e) {
            System.out.println("Error with payment.\nPayment unsuccessful");
            }
        }

        //Ledger Method

    public static void displayLedger() {
        boolean inLedger = true;

                while (inLedger) {
                    System.out.println("\n=======Ledger=======");
                    System.out.println("\n What would you like to do?");
                    System.out.println("1. Display all entries");
                    System.out.println("2. Show all Deposits");
                    System.out.println("3. Show all Payments");
                    System.out.println("4. Go to Reports");
                    System.out.println("5. Go back to home screen");

                    //Swicth for Ledger

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            showAllEntries();
                            break;
//
                        case 2:
                            showAllDeposits();
                            break;

                        case 3:
                            showAllPayments();
                            break;

                        case 4:
                            displayReports();
                            break;

                        case 5:
                            inLedger = false;
                            break;

                    }
                }
    }

    //                          Reports screen method

    public static void displayReports(){

        boolean inReports = true;

        while(inReports) {

            System.out.println("\n=======Reports=======");
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Search by Vendor");
            System.out.println("2. Custom Search");
            System.out.println("3. Go back to Ledger");

            //              Switch for the Reports

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

               case 1:
                   searchByVendor();
                    break;

                //case 2:
                    //break;

                case 3:
                    inReports = false;
                    break;
            }


        }


    }
                    // Ledger Show all reports method

    public static void showAllEntries() {

        try {
            Scanner fileScanner = new Scanner(new File("Transactions.csv"));

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error, File not found");
        }
    }

                         //Show all Payments Method
    public static void showAllPayments() {

        try { Scanner fileScanner = new Scanner(new File("Transactions.csv"));

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] parts = line.split(",");

                if (parts[4].equalsIgnoreCase("price")){
                    continue;
                }

                double amount = Double.parseDouble(parts[4]);

                if (amount < 0) {
                    System.out.println(line);
                }
            }

    } catch (FileNotFoundException e) {
            System.out.println("Error. File not found");
            }
        }

                                  // Show all deposits method

    public static void showAllDeposits() {

        try { Scanner fileScanner = new Scanner(new File("Transactions.csv"));

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] parts = line.split("\\|");

                if (parts[4].equalsIgnoreCase("price")){
                    continue;
                }

                double amount = Double.parseDouble(parts[4]);

                if (amount > 0) {
                    System.out.println(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found");
        }
    }

                          // Reports Search by vendor Method

    public static void searchByVendor() {

        try {
            Scanner fileScanner = new Scanner(new File("Transactions.csv"));
                System.out.println("Enter the name of the vendor");
                String vendorSearch = scanner.nextLine();

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] parts = line.split("\\|");

                if (parts[3].equalsIgnoreCase("vendor")) {
                    continue;
                }
                String vendor = parts[3];


                if (vendor.equalsIgnoreCase(vendorSearch)) {
                    System.out.println(line);
            }
                }

        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found");
        }

    }

    }








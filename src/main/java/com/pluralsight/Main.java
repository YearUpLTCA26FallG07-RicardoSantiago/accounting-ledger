package com.pluralsight;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.pluralsight.console.scanner;

public class Main {
    static ArrayList<transaction> transactions = new ArrayList<>();

    public static void main(String[] args){
        loadTransactions();
        homeScreen();

    }






    // Main Menu Method
    public  static  void homeScreen() {

        boolean running = true;

        while(running) {
            System.out.print(BLACK_BG);
            System.out.println(CYAN + "================================================");
            System.out.println(WHITE + "                    WELCOME                     ");
            System.out.println(CYAN + "================================================");
            System.out.println(WHITE + "\nWhat would you like to do?\n");
            System.out.println(GREEN + "1. Record a Sale");
            System.out.println(RED + "2. Record a purchase (Debit)");
            System.out.println(PURPLE + "3. Display Ledger");
            System.out.println(YELLOW + "4. Exit");
            System.out.println(CYAN + "\n================================================");
            System.out.print(WHITE + "Select an option: " + RESET);

            int choice;

            while (!scanner.hasNextInt()) {
                System.out.println(BRIGHT_RED + "Invalid input. Please enter a number 1-4" + RESET);
                scanner.nextLine();
            }

            choice = scanner.nextInt();
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


        System.out.println(BLACK_BG);
        System.out.println( GREEN + "\n==============================");
        System.out.println( "      What was the sale?");
        System.out.println("==============================");
        System.out.println("Item: ");
        String item = scanner.nextLine();

        System.out.println("Vendor: ");
        String vendor = scanner.nextLine();

        double price = 0;

        while (true) {

        System.out.println("Price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                scanner.nextLine();
                if (price > 0) {
                    break;
                }
                System.out.println("Error, price must be higher than 0");
            }else {
                System.out.println("Invalid input. Please enetr a number");
                scanner.nextLine();
            }
            }
        transaction deposit = new transaction();

        deposit.setDate(LocalDate.now());
        deposit.setTime(LocalTime.now());
        deposit.setItem(item);
        deposit.setVendor(vendor);
        deposit.setPrice(price);

        try (FileWriter writer = new FileWriter("Transactions.csv", true)) {

            writer.write(deposit.toString() + "\n");
            transactions.add(deposit);

        } catch (IOException e) {
            System.out.println("Error writing in the file");
        }

        System.out.println("\nSale Recorded:\n");
        System.out.println(deposit + RESET);



    }

    //Payment Method

    public static void makePayment() {

        System.out.println( BLACK_BG + RED);
        System.out.println("==============================");
        System.out.println("   Who was it bought from?");
        System.out.println("==============================");
        String vendor = scanner.nextLine();

        System.out.println("==============================");
        System.out.println("    How much did it cost?");
        System.out.println("==============================");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("==============================");
        System.out.println("     What was purchased?");
        System.out.println("==============================");
        String itemp = scanner.nextLine();

        transaction payment = new transaction();
        payment.setDate(LocalDate.now());
        payment.setTime(LocalTime.now());
        payment.setItem(itemp);
        payment.setVendor(vendor);
        payment.setPrice(amount);



        try {
            FileWriter writer = new FileWriter("Transactions.csv", true);

            writer.write(payment.toString()+ "\n");
            writer.close();

            transactions.add(payment);

            System.out.println("\nPayment to "+ vendor+ " Has been recorded");
            System.out.println(payment + RESET);
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

                    int choice;

                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number 1-5");
                        scanner.nextLine();
                    }

                    //Swicth for Ledger

                     choice = scanner.nextInt();
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

            int choice;

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number 1-5");
                scanner.nextLine();
            }

            //              Switch for the Reports

             choice = scanner.nextInt();
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

                String[] parts = line.split("\\|");

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

                          // Method to keep csv stored

    public static void loadTransactions() {

        try (Scanner fileScanner = new Scanner(new File("Transactiopns.csv"))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");

                transaction t = new transaction();

                t.setDate(LocalDate.parse(parts[0]));
                t.setTime(LocalTime.parse(parts[1]));
                t.setItem(parts[2]);
                t.setVendor(parts[3]);
                t.setPrice(Double.parseDouble(parts[4]));

                transactions.add(t);
            }
        }catch (FileNotFoundException e){
            System.out.println("Error. File was not found");
        }
    }
    // Reset
    public static final String RESET = "\u001B[0m";

    // Regular Text Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Bright Text Colors
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    // Background Colors
    public static final String BLACK_BG = "\u001B[40m";
    public static final String RED_BG = "\u001B[41m";
    public static final String GREEN_BG = "\u001B[42m";
    public static final String YELLOW_BG = "\u001B[43m";
    public static final String BLUE_BG = "\u001B[44m";
    public static final String PURPLE_BG = "\u001B[45m";
    public static final String CYAN_BG = "\u001B[46m";
    public static final String WHITE_BG = "\u001B[47m";

    // Bright Background Colors
    public static final String BRIGHT_BLACK_BG = "\u001B[100m";
    public static final String BRIGHT_RED_BG = "\u001B[101m";
    public static final String BRIGHT_GREEN_BG = "\u001B[102m";
    public static final String BRIGHT_YELLOW_BG = "\u001B[103m";
    public static final String BRIGHT_BLUE_BG = "\u001B[104m";
    public static final String BRIGHT_PURPLE_BG = "\u001B[105m";
    public static final String BRIGHT_CYAN_BG = "\u001B[106m";
    public static final String BRIGHT_WHITE_BG = "\u001B[107m";
}











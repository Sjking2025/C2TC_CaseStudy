package com.tnsif.bankingservice;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class BankingSystemApp {

    private static final Bankingserviceimpl service = new Bankingserviceimpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            System.out.print("Enter your choice : ");
            choice = Integer.parseInt(scanner.nextLine().trim());
            System.out.println();
            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> addAccount();
                case 3 -> addBeneficiary();
                case 4 -> addTransaction();
                case 5 -> findCustomerById();
                case 6 -> listAccountsByCustomer();
                case 7 -> listTransactionsByAccount();
                case 8 -> listBeneficiariesByCustomer();
                case 9 -> System.out.println("Thank you!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Banking System");
        System.out.println("1. Add Customers");
        System.out.println("2. Add Accounts");
        System.out.println("3. Add Beneficiary");
        System.out.println("4. Add Transaction");
        System.out.println("5. Find Customer by Id");
        System.out.println("6. List all Accounts of specific Customer");
        System.out.println("7. List all transactions of specific Account");
        System.out.println("8. List all beneficiaries of specific customer");
        System.out.println("9. Exit");
    }

    // ── Case 1: Add Customer ──────────────────────────────────────────────────
    private static void addCustomer() {
        System.out.println("Enter Customer Details");
        System.out.print("Customer Id : ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Name : ");
        String name = scanner.nextLine().trim();
        System.out.print("Address : ");
        String address = scanner.nextLine().trim();
        System.out.print("Contact No. : ");
        String contact = scanner.nextLine().trim();

        Customer customer = new Customer(id, name, address, contact);
        service.addCustomer(customer);
        System.out.println("Customer added successfully.");
    }

    // ── Case 2: Add Account ───────────────────────────────────────────────────
    private static void addAccount() {
        System.out.println("Enter Account Details");
        System.out.print("Account Id : ");
        int accountId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Customer Id : ");
        int customerId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Account Type Saving/ Current : ");
        String type = scanner.nextLine().trim();
        System.out.print("Balance : ");
        double balance = Double.parseDouble(scanner.nextLine().trim());

        Account account = new Account(accountId, customerId, type, balance);
        service.addAccount(account);
        System.out.println("Account added successfully.");
    }

    // ── Case 3: Add Beneficiary ───────────────────────────────────────────────
    private static void addBeneficiary() {
        System.out.println("Enter Beneficiary Details");
        System.out.print("Customer Id : ");
        int customerId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Beneficiary Id : ");
        int beneficiaryId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Beneficiary Name : ");
        String name = scanner.nextLine().trim();
        System.out.print("Beneficiary Account No. : ");
        String accountNumber = scanner.nextLine().trim();
        System.out.print("Beneficiary Bank details : ");
        String bankDetails = scanner.nextLine().trim();

        Beneficiary beneficiary = new Beneficiary(beneficiaryId, customerId, name, accountNumber, bankDetails);
        service.addBeneficiary(beneficiary);
        System.out.println("Beneficiary added successfully.");
    }

    // ── Case 4: Add Transaction ───────────────────────────────────────────────
    private static void addTransaction() {
        System.out.println("Enter Transaction Details");
        System.out.print("Account Id : ");
        int accountId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Type (Deposit/Withdrawal) : ");
        String type = scanner.nextLine().trim();
        System.out.print("Account Amount : ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        Transaction transaction = new Transaction(accountId, type, amount);
        service.addTransaction(transaction);
        System.out.println("Transaction processed successfully.");
    }

    // ── Case 5: Find Customer by ID ───────────────────────────────────────────
    private static void findCustomerById() {
        // Display all customers first
        Collection<Customer> allCustomers = service.getAllCustomers();
        for (Customer c : allCustomers) {
            System.out.println("Customer ID: " + c.getCustomerID() + ", Name: " + c.getName());
        }
        System.out.print("Customer Id : ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Customer customer = service.findCustomerById(id);
        if (customer != null) {
            System.out.println("Customer: " + customer.getName());
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
    }

    // ── Case 6: Accounts by Customer ─────────────────────────────────────────
    private static void listAccountsByCustomer() {
        // Display all accounts first
        Collection<Account> allAccounts = service.getAllAccounts();
        for (Account a : allAccounts) {
            System.out.println("Account ID: " + a.getAccountID()
                    + ", Customer ID : " + a.getCustomerID()
                    + ", Balance: " + a.getBalance());
        }
        System.out.print("Customer Id : ");
        int customerId = Integer.parseInt(scanner.nextLine().trim());
        List<Account> accounts = service.getAccountsByCustomerId(customerId);
        System.out.println("Accounts for Customer ID :" + customerId);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (Account a : accounts) {
                System.out.println("Account ID: " + a.getAccountID() + ", Balance: " + a.getBalance());
            }
        }
    }

    // ── Case 7: Transactions by Account ──────────────────────────────────────
    private static void listTransactionsByAccount() {
        System.out.print("Account Id : ");
        int accountId = Integer.parseInt(scanner.nextLine().trim());
        List<Transaction> transactions = service.getTransactionsByAccountId(accountId);
        System.out.println("Transactions for Account ID :" + accountId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println("Transaction ID: " + t.getTransactionID()
                        + ", Type: " + t.getType()
                        + ", Amount: " + t.getAmount()
                        + ", Timestamp: " + t.getTimestamp());
            }
        }
    }

    // ── Case 8: Beneficiaries by Customer ────────────────────────────────────
    private static void listBeneficiariesByCustomer() {
        // Display all beneficiaries first
        Collection<Beneficiary> allBeneficiaries = service.getAllBeneficiaries();
        for (Beneficiary b : allBeneficiaries) {
            System.out.println("Beneficiary ID: " + b.getBeneficiaryID() + ", Name: " + b.getName());
        }
        System.out.println("-------------------------------------");
        System.out.print("Customer Id : ");
        int customerId = Integer.parseInt(scanner.nextLine().trim());
        List<Beneficiary> beneficiaries = service.getBeneficiariesByCustomerId(customerId);
        System.out.println("Beneficiaries for Customer ID :" + customerId);
        if (beneficiaries.isEmpty()) {
            System.out.println("No beneficiaries found.");
        } else {
            for (Beneficiary b : beneficiaries) {
                System.out.println("Beneficiary ID: " + b.getBeneficiaryID() + ", Name: " + b.getName());
            }
        }
    }
}
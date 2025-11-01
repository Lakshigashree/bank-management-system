import java.util.*;
import java.util.Random;

class BankAccount {
    private String accountNumber;
    private String name;
    private double balance;
    private String pin;

    public BankAccount(String accountNumber, String name, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit Successful!");
        } else {
            System.out.println("Invalid amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal Successfull!");
        } else {
            System.out.println("Insufficient Balance or Invalid Amount");
        }
    }

    public void displayAccount() {
        System.out.println("Account Number: " + " " + accountNumber);
        System.out.println("Name: " + " " + name);
        System.out.println("Balance: " + " " + balance);
    }
}

public class BankManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static Random random = new Random();
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static BankAccount currentAccount = null;

    public static void main(String args[]) {
        int choice;
        boolean running = true;
        do {
            System.out.println("========== BANK MANAGEMENT SYSTEM ==========");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Display Account");
            System.out.println("6. Logout");
            System.out.println("Enter your Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    login();
                    ;
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    displayAccount();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice<6);
    }

    private static void createAccount() {
        System.out.println("Enter Account Number: ");
        String accNum = sc.nextLine();
        System.out.println("Enter Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Initial Balance: ");
        double balance = sc.nextDouble();
        sc.nextLine();
        System.out.println("Set 4-digit PIN: ");
        String pin = sc.nextLine();

        accounts.add(new BankAccount(accNum, name, balance, pin));
        System.out.println("Account Created Successfully!");
    }

    private static BankAccount findAccount() {
        if (currentAccount != null)
            return currentAccount;

        System.out.println("Enter Account Number: ");
        String accNum = sc.nextLine();
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNum))
                return acc;
        }
        System.out.println("Account Not Found!");
        return null;
    }

    private static void deposit() {
        if (currentAccount == null) {
            System.out.println("You Must Login First");
            login();
            if (currentAccount == null)
                return;
        }
        BankAccount acc = findAccount();
        if (acc != null) {
            System.out.println("Enter amount to Deposit: ");
            double amt = sc.nextDouble();
            sc.nextLine();
            acc.deposit(amt);
        }
    }

    private static void withdraw() {
        if (currentAccount == null) {
            System.out.println("You Must Login First");
            login();
            if (currentAccount == null)
                return;
        }
        BankAccount acc = findAccount();
        if (acc != null) {
            System.out.println("Enter amount to Withdraw: ");
            double amt = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter PIN: ");
            String enteredPin = sc.nextLine();
            if (!enteredPin.equals(acc.getPin())) {
                System.out.println("Incorrect PIN!");
                return;
            }

            if (amt > 25000) {
                int otp = 1000 + random.nextInt(9000);
                System.out.println("OTP sent to your registered Mobile Number: " + otp);
                System.out.println("Enter OTP: ");
                int enteredOtp = sc.nextInt();
                sc.nextLine();

                if (enteredOtp != otp) {
                    System.out.println("Invalid OTP!");
                    return;
                }
            }
            acc.withdraw(amt);
        }
    }

    private static void displayAccount() {
        if (currentAccount == null) {
            System.out.println("You Must Login First");
            login();
            if (currentAccount == null)
                return;
        }
        BankAccount acc = findAccount();
        if (acc != null)
            acc.displayAccount();
    }

    private static void login() {
        System.out.println("========== LOGIN ==========");
        System.out.println("Enter Account Number: ");
        String accNum = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(accNum) && acc.getPin().equals(pin)) {
                currentAccount = acc;
                System.out.println("Login Successfull! Welcome " + " " + acc.getName());
                return;
            }
        }
        System.out.println("Invalid Account Number or PIN! Create New Account If Not Exists");
    }

    private static void logout() {
        System.out.println("Logged Out Successfully! Thank You, " + " " + currentAccount.getName());
        currentAccount = null;
    }
}

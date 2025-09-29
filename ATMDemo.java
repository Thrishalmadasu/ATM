import java.util.Scanner;

public class ATMDemo {
    public static void main(String[] args) {
        ATMOrchestrator atm = new ATMOrchestrator("ATM001");
        Scanner scanner = new Scanner(System.in);
        
        Card testCard1 = new Card("4532123456789012", "1234", "BANK001", "1234567890");
        Card testCard2 = new Card("5555666677778888", "5678", "BANK002", "0987654321");
        Card testCard3 = new Card("4444333322221111", "9999", "BANK003", "1111222233");
        
        System.out.println("=== ATM SYSTEM DEMO ===");
        System.out.println("Welcome to the ATM!");
        System.out.println();
        
        System.out.println("Available test cards:");
        System.out.println("1. Card: 4532123456789012, PIN: 1234, Balance: $5,000");
        System.out.println("2. Card: 5555666677778888, PIN: 5678, Balance: $10,000");
        System.out.println("3. Card: 4444333322221111, PIN: 9999, Balance: $25,000");
        System.out.println();
        
        while (true) {
            System.out.print("Select card (1-3) or 0 to exit: ");
            
            int cardChoice;
            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Thank you for using ATM Demo!");
                    break;
                }
                cardChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Thank you for using ATM Demo!");
                break;
            }
            
            if (cardChoice == 0) {
                System.out.println("Thank you for using ATM Demo!");
                break;
            }
            
            Card selectedCard = null;
            switch (cardChoice) {
                case 1:
                    selectedCard = testCard1;
                    break;
                case 2:
                    selectedCard = testCard2;
                    break;
                case 3:
                    selectedCard = testCard3;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }
            
            System.out.println("\nInserting card...");
            atm.insertCard(selectedCard);
            
            System.out.print("Enter PIN: ");
            String pin = "";
            try {
                if (scanner.hasNextLine()) {
                    pin = scanner.nextLine();
                }
            } catch (Exception e) {
                continue;
            }
            atm.enterPin(pin);
            
            if (atm.getCurrentState() instanceof TransactionMenuState) {
                boolean continueTransactions = true;
                
                while (continueTransactions) {
                    System.out.print("\nSelect transaction (1-6): ");
                    
                    int transactionChoice;
                    try {
                        if (!scanner.hasNextInt()) {
                            continueTransactions = false;
                            break;
                        }
                        transactionChoice = scanner.nextInt();
                        scanner.nextLine();
                    } catch (Exception e) {
                        continueTransactions = false;
                        break;
                    }
                    
                    switch (transactionChoice) {
                        case 1:
                            atm.selectTransaction(TransactionType.WITHDRAWAL);
                            if (atm.getCurrentState() instanceof WithdrawalState) {
                                System.out.print("Enter amount to withdraw: $");
                                try {
                                    if (!scanner.hasNextDouble()) {
                                        continueTransactions = false;
                                        break;
                                    }
                                    double withdrawAmount = scanner.nextDouble();
                                    scanner.nextLine();
                                    atm.enterAmount(withdrawAmount);
                                } catch (Exception e) {
                                    continueTransactions = false;
                                    break;
                                }
                            }
                            break;
                        case 2:
                            atm.selectTransaction(TransactionType.DEPOSIT);
                            if (atm.getCurrentState() instanceof DepositState) {
                                System.out.print("Enter amount to deposit: $");
                                try {
                                    if (!scanner.hasNextDouble()) {
                                        continueTransactions = false;
                                        break;
                                    }
                                    double depositAmount = scanner.nextDouble();
                                    scanner.nextLine();
                                    atm.enterAmount(depositAmount);
                                } catch (Exception e) {
                                    continueTransactions = false;
                                    break;
                                }
                            }
                            break;
                        case 3:
                            atm.selectTransaction(TransactionType.BALANCE_INQUIRY);
                            System.out.print("Print receipt? (y/n): ");
                            String printReceipt = "n";
                            try {
                                if (scanner.hasNextLine()) {
                                    printReceipt = scanner.nextLine();
                                }
                            } catch (Exception e) {
                                continueTransactions = false;
                                break;
                            }
                            if (printReceipt.toLowerCase().startsWith("y")) {
                                atm.printReceipt();
                            } else {
                                atm.ejectCard();
                                continueTransactions = false;
                            }
                            break;
                        case 4:
                            atm.selectTransaction(TransactionType.PIN_CHANGE);
                            if (atm.getCurrentState() instanceof PinChangeState) {
                                System.out.print("Enter new PIN (4 digits): ");
                                String newPin = "";
                                try {
                                    if (scanner.hasNextLine()) {
                                        newPin = scanner.nextLine();
                                    }
                                } catch (Exception e) {
                                    continueTransactions = false;
                                    break;
                                }
                                atm.enterPin(newPin);
                            }
                            break;
                        case 5:
                            atm.selectTransaction(TransactionType.MINI_STATEMENT);
                            continueTransactions = false;
                            break;
                        case 6:
                            atm.cancel();
                            continueTransactions = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    
                    if (atm.getCurrentState() instanceof TransactionCompleteState) {
                        System.out.print("Another transaction? (y/n): ");
                        try {
                            if (!scanner.hasNextLine()) {
                                atm.ejectCard();
                                continueTransactions = false;
                            } else {
                                String another = scanner.nextLine();
                                if (!another.toLowerCase().startsWith("y")) {
                                    atm.ejectCard();
                                    continueTransactions = false;
                                }
                            }
                        } catch (Exception e) {
                            atm.ejectCard();
                            continueTransactions = false;
                        }
                    }
                }
            }
            
            System.out.println("\n" + "=".repeat(50) + "\n");
        }
        
        scanner.close();
    }
}

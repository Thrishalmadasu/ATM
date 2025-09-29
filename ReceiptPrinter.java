import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReceiptPrinter {
    private boolean paperAvailable;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public ReceiptPrinter() {
        this.paperAvailable = true;
    }

    public void printTransactionReceipt(Transaction transaction, String atmId) {
        if (!paperAvailable) {
            System.out.println("Receipt printing unavailable - paper not available");
            return;
        }

        System.out.println("========================");
        System.out.println("    TRANSACTION RECEIPT");
        System.out.println("========================");
        System.out.println("ATM ID: " + atmId);
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Account: ***" + transaction.getAccountNumber().substring(
            Math.max(0, transaction.getAccountNumber().length() - 4)));
        System.out.println("Type: " + transaction.getType());
        System.out.println("Amount: $" + String.format("%.2f", transaction.getAmount()));
        System.out.println("Date/Time: " + transaction.getTimestamp().format(formatter));
        System.out.println("Balance: $" + String.format("%.2f", transaction.getBalanceAfter()));
        System.out.println("========================");
        System.out.println();
    }

    public void printMiniStatement(List<Transaction> transactions, String accountNumber, double currentBalance) {
        if (!paperAvailable) {
            System.out.println("Mini statement printing unavailable - paper not available");
            return;
        }

        System.out.println("========================");
        System.out.println("      MINI STATEMENT");
        System.out.println("========================");
        System.out.println("Account: ***" + accountNumber.substring(Math.max(0, accountNumber.length() - 4)));
        System.out.println("Current Balance: $" + String.format("%.2f", currentBalance));
        System.out.println("------------------------");
        System.out.println("Recent Transactions:");
        
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getTimestamp().format(formatter) + 
                " | " + transaction.getType() + 
                " | $" + String.format("%.2f", transaction.getAmount()));
        }
        System.out.println("========================");
        System.out.println();
    }

    public void printBalanceReceipt(double balance, String accountNumber, String atmId) {
        if (!paperAvailable) {
            System.out.println("Balance receipt printing unavailable - paper not available");
            return;
        }

        System.out.println("========================");
        System.out.println("   BALANCE INQUIRY");
        System.out.println("========================");
        System.out.println("ATM ID: " + atmId);
        System.out.println("Account: ***" + accountNumber.substring(Math.max(0, accountNumber.length() - 4)));
        System.out.println("Available Balance: $" + String.format("%.2f", balance));
        System.out.println("Date/Time: " + java.time.LocalDateTime.now().format(formatter));
        System.out.println("========================");
        System.out.println();
    }

    public boolean isPaperAvailable() {
        return paperAvailable;
    }

    public void setPaperAvailable(boolean paperAvailable) {
        this.paperAvailable = paperAvailable;
    }
}

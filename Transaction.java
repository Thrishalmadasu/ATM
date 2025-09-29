import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
    private double balanceAfter;

    public Transaction(String transactionId, String accountNumber, TransactionType type, 
                      double amount, String description, double balanceAfter) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.balanceAfter = balanceAfter;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }
}

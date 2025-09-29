import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankServiceImpl implements BankService {
    private Map<String, Double> accountBalances;
    private Map<String, List<Transaction>> transactionHistory;
    private int transactionCounter;
    private String bankId;

    public BankServiceImpl(String bankId) {
        this.bankId = bankId;
        this.accountBalances = new HashMap<>();
        this.transactionHistory = new HashMap<>();
        this.transactionCounter = 1000;
    }

    public String getBankId() {
        return bankId;
    }

    @Override
    public boolean validatePin(Card card, String enteredPin) {
        if (card == null || enteredPin == null) {
            return false;
        }
        return card.getPin().equals(enteredPin);
    }

    @Override
    public double getAccountBalance(Card card) {
        if (card == null) {
            return 0.0;
        }
        return accountBalances.getOrDefault(card.getAccountNumber(), 0.0);
    }

    @Override
    public boolean withdraw(Card card, double amount) {
        if (card == null || amount <= 0) {
            return false;
        }

        double currentBalance = getAccountBalance(card);
        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            accountBalances.put(card.getAccountNumber(), newBalance);
            
            Transaction transaction = new Transaction(
                generateTransactionId(),
                card.getAccountNumber(),
                TransactionType.WITHDRAWAL,
                amount,
                "ATM Withdrawal",
                newBalance
            );
            
            addTransactionToHistory(card.getAccountNumber(), transaction);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(Card card, double amount) {
        if (card == null || amount <= 0) {
            return false;
        }

        double currentBalance = getAccountBalance(card);
        double newBalance = currentBalance + amount;
        accountBalances.put(card.getAccountNumber(), newBalance);
        
        Transaction transaction = new Transaction(
            generateTransactionId(),
            card.getAccountNumber(),
            TransactionType.DEPOSIT,
            amount,
            "ATM Deposit",
            newBalance
        );
        
        addTransactionToHistory(card.getAccountNumber(), transaction);
        return true;
    }

    @Override
    public boolean changePin(Card card, String newPin) {
        if (card == null || newPin == null || newPin.length() != 4) {
            return false;
        }
        
        if (!newPin.matches("\\d{4}")) {
            return false;
        }
        
        card.setPin(newPin);
        
        Transaction transaction = new Transaction(
            generateTransactionId(),
            card.getAccountNumber(),
            TransactionType.PIN_CHANGE,
            0.0,
            "PIN Change",
            getAccountBalance(card)
        );
        
        addTransactionToHistory(card.getAccountNumber(), transaction);
        return true;
    }

    @Override
    public List<Transaction> getMiniStatement(Card card, int numberOfTransactions) {
        if (card == null) {
            return new ArrayList<>();
        }
        
        List<Transaction> history = transactionHistory.getOrDefault(card.getAccountNumber(), new ArrayList<>());
        
        if (history.size() <= numberOfTransactions) {
            return new ArrayList<>(history);
        }
        
        return new ArrayList<>(history.subList(Math.max(0, history.size() - numberOfTransactions), history.size()));
    }

    @Override
    public String generateTransactionId() {
        return "TXN" + String.format("%06d", transactionCounter++);
    }

    private void addTransactionToHistory(String accountNumber, Transaction transaction) {
        transactionHistory.computeIfAbsent(accountNumber, _ -> new ArrayList<>()).add(transaction);
        
        List<Transaction> history = transactionHistory.get(accountNumber);
        if (history.size() > 50) {
            history.remove(0);
        }
    }

    public void addAccount(String accountNumber, String cardNumber, String pin, double initialBalance) {
        accountBalances.put(accountNumber, initialBalance);
        transactionHistory.put(accountNumber, new ArrayList<>());
        
        Transaction initialTransaction = new Transaction(
            generateTransactionId(),
            accountNumber,
            TransactionType.DEPOSIT,
            initialBalance,
            "Account Opening",
            initialBalance
        );
        
        addTransactionToHistory(accountNumber, initialTransaction);
    }
}

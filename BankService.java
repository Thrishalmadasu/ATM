import java.util.List;

public interface BankService {
    boolean validatePin(Card card, String enteredPin);
    double getAccountBalance(Card card);
    boolean withdraw(Card card, double amount);
    boolean deposit(Card card, double amount);
    boolean changePin(Card card, String newPin);
    List<Transaction> getMiniStatement(Card card, int numberOfTransactions);
    String generateTransactionId();
}

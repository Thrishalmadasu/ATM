public class Card {
    private String cardNumber;
    private String pin;
    private String bankId;
    private String accountNumber;
    private boolean isBlocked;

    public Card(String cardNumber, String pin, String bankId, String accountNumber) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.bankId = bankId;
        this.accountNumber = accountNumber;
        this.isBlocked = false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBankId() {
        return bankId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}

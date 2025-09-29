public class ATMOrchestrator {
    private ATMState currentState;
    private Card currentCard;
    private String atmId;
    private BankServiceManager bankServiceManager;
    private CashDispenser cashDispenser;
    private ReceiptPrinter receiptPrinter;
    private double transactionAmount;
    private TransactionType selectedTransaction;
    private int pinAttempts;
    private static final int MAX_PIN_ATTEMPTS = 3;

    public ATMOrchestrator(String atmId) {
        this.atmId = atmId;
        this.bankServiceManager = BankServiceManager.getInstance();
        this.cashDispenser = new CashDispenser(new MinimumNotesStrategy());
        this.receiptPrinter = new ReceiptPrinter();
        this.currentState = new IdleState();
        this.pinAttempts = 0;
    }

    public ATMOrchestrator(String atmId, DispensingStrategy dispensingStrategy) {
        this.atmId = atmId;
        this.bankServiceManager = BankServiceManager.getInstance();
        this.cashDispenser = new CashDispenser(dispensingStrategy);
        this.receiptPrinter = new ReceiptPrinter();
        this.currentState = new IdleState();
        this.pinAttempts = 0;
    }

    public void setState(ATMState state) {
        this.currentState = state;
    }

    public ATMState getCurrentState() {
        return currentState;
    }

    public void insertCard(Card card) {
        currentState.insertCard(this, card);
    }

    public void enterPin(String pin) {
        currentState.enterPin(this, pin);
    }

    public void selectTransaction(TransactionType transactionType) {
        currentState.selectTransaction(this, transactionType);
    }

    public void enterAmount(double amount) {
        currentState.enterAmount(this, amount);
    }

    public void dispenseCash() {
        currentState.dispenseCash(this);
    }

    public void ejectCard() {
        currentState.ejectCard(this);
    }

    public void printReceipt() {
        currentState.printReceipt(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public String getAtmId() {
        return atmId;
    }

    public BankServiceManager getBankServiceManager() {
        return bankServiceManager;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public ReceiptPrinter getReceiptPrinter() {
        return receiptPrinter;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionType getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(TransactionType selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }

    public int getPinAttempts() {
        return pinAttempts;
    }

    public void incrementPinAttempts() {
        this.pinAttempts++;
    }

    public void resetPinAttempts() {
        this.pinAttempts = 0;
    }

    public boolean hasExceededPinAttempts() {
        return pinAttempts >= MAX_PIN_ATTEMPTS;
    }

    public void reset() {
        this.currentCard = null;
        this.transactionAmount = 0;
        this.selectedTransaction = null;
        this.pinAttempts = 0;
    }
}

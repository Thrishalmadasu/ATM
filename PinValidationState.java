public class PinValidationState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Card already inserted. Please enter your PIN.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        if (pin == null || pin.trim().isEmpty()) {
            System.out.println("Please enter a valid PIN.");
            return;
        }

        if (context.getBankServiceManager().validatePin(context.getCurrentCard(), pin)) {
            System.out.println("PIN validated successfully.");
            context.resetPinAttempts();
            context.setState(new TransactionMenuState());
            showTransactionMenu();
        } else {
            context.incrementPinAttempts();
            System.out.println("Invalid PIN. Attempts remaining: " + 
                (3 - context.getPinAttempts()));
            
            if (context.hasExceededPinAttempts()) {
                System.out.println("Maximum PIN attempts exceeded. Card will be blocked.");
                context.getCurrentCard().setBlocked(true);
                context.setState(new CardEjectionState());
                context.ejectCard();
            }
        }
    }

    private void showTransactionMenu() {
        System.out.println("\n=== TRANSACTION MENU ===");
        System.out.println("1. WITHDRAWAL");
        System.out.println("2. DEPOSIT");
        System.out.println("3. BALANCE INQUIRY");
        System.out.println("4. PIN CHANGE");
        System.out.println("5. MINI STATEMENT");
        System.out.println("6. CANCEL");
        System.out.println("Please select a transaction:");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please enter your PIN first.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Please enter your PIN first.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("Please enter your PIN first.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        System.out.println("Transaction cancelled. Please take your card.");
        context.reset();
        context.setState(new IdleState());
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Please complete authentication first.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        context.ejectCard();
    }
}

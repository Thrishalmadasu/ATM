public class TransactionCompleteState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Card already inserted. Choose another transaction or exit.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Already authenticated. Choose another transaction or exit.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        if (transactionType != null) {
            System.out.println("Starting new transaction...");
            context.setState(new TransactionMenuState());
            context.selectTransaction(transactionType);
        }
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Please select a transaction first or exit.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("No active withdrawal transaction.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        System.out.println("Thank you for using our ATM. Please take your card.");
        context.reset();
        context.setState(new IdleState());
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Transaction already completed with receipt.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        context.ejectCard();
    }
}

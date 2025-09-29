public class MiniStatementState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Transaction in progress. Please wait for mini statement to complete.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Mini statement in progress.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please wait for mini statement to complete.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Mini statement does not require amount entry.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("Mini statement does not dispense cash.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        System.out.println("Please take your card.");
        context.reset();
        context.setState(new IdleState());
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Mini statement already printed.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        System.out.println("Mini statement processing completed.");
        context.setState(new CardEjectionState());
        context.ejectCard();
    }
}

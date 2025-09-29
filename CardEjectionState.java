public class CardEjectionState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Please take your current card before inserting a new one.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Please take your card. Session ended.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please take your card. Session ended.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Please take your card. Session ended.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("Please take your card. Session ended.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        System.out.println("Please take your card.");
        System.out.println("Thank you for using our ATM service.");
        context.reset();
        context.setState(new IdleState());
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("ATM is ready for the next customer.");
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Session ended. Please take your card.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        context.ejectCard();
    }
}

public class BalanceInquiryState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Transaction in progress. Please complete current transaction.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Balance inquiry in progress.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please complete balance inquiry first.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Balance inquiry does not require amount entry.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("Balance inquiry does not dispense cash.");
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
        double balance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
        context.getReceiptPrinter().printBalanceReceipt(balance, 
            context.getCurrentCard().getAccountNumber(), context.getAtmId());
        
        System.out.println("Would you like another transaction? (Y/N)");
        context.setState(new TransactionCompleteState());
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        context.ejectCard();
    }
}

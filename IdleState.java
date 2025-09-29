public class IdleState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        if (card == null) {
            System.out.println("Invalid card. Please try again.");
            return;
        }
        
        if (card.isBlocked()) {
            System.out.println("Card is blocked. Please contact your bank.");
            context.ejectCard();
            return;
        }

        if (!context.getBankServiceManager().isBankSupported(card.getBankId())) {
            System.out.println("Sorry, your bank is not supported by this ATM.");
            return;
        }
        
        System.out.println("Card accepted. Please enter your PIN.");
        context.setCurrentCard(card);
        context.setState(new PinValidationState());
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Please insert your card first.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please insert your card first.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Please insert your card first.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("Please insert your card first.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        System.out.println("No card to eject.");
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Please insert your card first.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        System.out.println("No operation to cancel.");
    }
}

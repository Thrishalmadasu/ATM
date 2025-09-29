public class PinChangeState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Transaction in progress. Please complete current transaction.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String newPin) {
        if (newPin == null || newPin.length() != 4 || !newPin.matches("\\d{4}")) {
            System.out.println("Invalid PIN format. PIN must be 4 digits.");
            return;
        }

        if (newPin.equals(context.getCurrentCard().getPin())) {
            System.out.println("New PIN cannot be the same as current PIN.");
            return;
        }

        System.out.println("Please confirm your new PIN:");
        confirmPinChange(context, newPin);
    }

    private void confirmPinChange(ATMOrchestrator context, String newPin) {
        System.out.println("PIN change confirmed. Processing...");
        
        boolean success = context.getBankServiceManager().changePin(context.getCurrentCard(), newPin);
        
        if (success) {
            context.getCurrentCard().setPin(newPin);
            System.out.println("PIN changed successfully!");
            
            String transactionId = context.getBankServiceManager().generateTransactionId();
            double balance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
            
            Transaction transaction = new Transaction(
                transactionId,
                context.getCurrentCard().getAccountNumber(),
                TransactionType.PIN_CHANGE,
                0.0,
                "PIN Change",
                balance
            );

            context.getReceiptPrinter().printTransactionReceipt(transaction, context.getAtmId());
            System.out.println("Would you like another transaction? (Y/N)");
            context.setState(new TransactionCompleteState());
        } else {
            System.out.println("PIN change failed. Please try again.");
            context.setState(new TransactionMenuState());
        }
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please complete PIN change first.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("PIN change does not require amount entry.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("PIN change does not dispense cash.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        System.out.println("Transaction in progress. Please complete or cancel first.");
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Please complete PIN change first.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        System.out.println("PIN change cancelled.");
        context.setState(new TransactionMenuState());
    }
}

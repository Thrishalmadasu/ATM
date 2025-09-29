public class DepositState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Transaction in progress. Please complete current transaction.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Transaction in progress. Please enter deposit amount.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please complete current deposit transaction.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        if (amount <= 0) {
            System.out.println("Please enter a valid amount greater than zero.");
            return;
        }

        if (amount > 50000) {
            System.out.println("Maximum deposit limit is $50,000 per transaction.");
            return;
        }

        context.setTransactionAmount(amount);
        System.out.println("Please insert cash into the deposit slot.");
        System.out.println("Deposit amount: $" + String.format("%.2f", amount));
        System.out.println("Press confirm to complete deposit or cancel to abort.");
        
        processDeposit(context);
    }

    private void processDeposit(ATMOrchestrator context) {
        System.out.println("Processing deposit...");
        System.out.println("Counting and validating cash...");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        boolean success = context.getBankServiceManager().deposit(context.getCurrentCard(), 
            context.getTransactionAmount());
        
        if (success) {
            String transactionId = context.getBankServiceManager().generateTransactionId();
            double newBalance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
            
            Transaction transaction = new Transaction(
                transactionId,
                context.getCurrentCard().getAccountNumber(),
                TransactionType.DEPOSIT,
                context.getTransactionAmount(),
                "ATM Deposit",
                newBalance
            );

            System.out.println("Deposit successful!");
            context.getReceiptPrinter().printTransactionReceipt(transaction, context.getAtmId());
            System.out.println("Would you like another transaction? (Y/N)");
            context.setState(new TransactionCompleteState());
        } else {
            System.out.println("Deposit failed. Cash will be returned.");
            context.setState(new TransactionMenuState());
        }
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("This is a deposit transaction. Cash dispensing not available.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        System.out.println("Transaction in progress. Please complete or cancel first.");
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Please complete deposit first.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        System.out.println("Deposit cancelled. Any inserted cash will be returned.");
        context.setState(new TransactionMenuState());
    }
}

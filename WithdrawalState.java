public class WithdrawalState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Transaction in progress. Please complete current transaction.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("Transaction in progress. Please enter withdrawal amount.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        System.out.println("Please complete current withdrawal transaction.");
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        if (amount <= 0) {
            System.out.println("Please enter a valid amount greater than zero.");
            return;
        }

        if (amount > 10000) {
            System.out.println("Maximum withdrawal limit is $10,000 per transaction.");
            return;
        }

        if (!context.getCashDispenser().canDispense(amount)) {
            System.out.println("Unable to dispense requested amount. Please try a different amount.");
            return;
        }

        double balance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
        if (amount > balance) {
            System.out.println("Insufficient funds. Your current balance is: $" + 
                String.format("%.2f", balance));
            return;
        }

        context.setTransactionAmount(amount);
        System.out.println("Processing withdrawal of $" + String.format("%.2f", amount));
        context.dispenseCash();
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        try {
            boolean success = context.getBankServiceManager().withdraw(context.getCurrentCard(), 
                context.getTransactionAmount());
            
            if (success) {
                var dispensedCash = context.getCashDispenser().dispenseCash(context.getTransactionAmount());
                System.out.println("Please take your cash:");
                
                for (var entry : dispensedCash.entrySet()) {
                    System.out.println("$" + entry.getKey() + " x " + entry.getValue());
                }

                String transactionId = context.getBankServiceManager().generateTransactionId();
                double newBalance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
                
                Transaction transaction = new Transaction(
                    transactionId,
                    context.getCurrentCard().getAccountNumber(),
                    TransactionType.WITHDRAWAL,
                    context.getTransactionAmount(),
                    "ATM Withdrawal",
                    newBalance
                );

                context.getReceiptPrinter().printTransactionReceipt(transaction, context.getAtmId());
                System.out.println("Would you like another transaction? (Y/N)");
                context.setState(new TransactionCompleteState());
            } else {
                System.out.println("Transaction failed. Please try again.");
                context.setState(new TransactionMenuState());
            }
        } catch (Exception e) {
            System.out.println("Error processing withdrawal: " + e.getMessage());
            context.setState(new TransactionMenuState());
        }
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        System.out.println("Transaction in progress. Please complete or cancel first.");
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Please complete withdrawal first.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        System.out.println("Withdrawal cancelled.");
        context.setState(new TransactionMenuState());
    }
}

public class TransactionMenuState implements ATMState {
    
    @Override
    public void insertCard(ATMOrchestrator context, Card card) {
        System.out.println("Card already inserted. Please select a transaction.");
    }

    @Override
    public void enterPin(ATMOrchestrator context, String pin) {
        System.out.println("PIN already validated. Please select a transaction.");
    }

    @Override
    public void selectTransaction(ATMOrchestrator context, TransactionType transactionType) {
        if (transactionType == null) {
            System.out.println("Please select a valid transaction.");
            return;
        }

        context.setSelectedTransaction(transactionType);
        
        switch (transactionType) {
            case WITHDRAWAL:
                System.out.println("Enter withdrawal amount:");
                context.setState(new WithdrawalState());
                break;
            case DEPOSIT:
                System.out.println("Enter deposit amount:");
                context.setState(new DepositState());
                break;
            case BALANCE_INQUIRY:
                context.setState(new BalanceInquiryState());
                processBalanceInquiry(context);
                break;
            case PIN_CHANGE:
                System.out.println("Enter new PIN:");
                context.setState(new PinChangeState());
                break;
            case MINI_STATEMENT:
                context.setState(new MiniStatementState());
                processMiniStatement(context);
                break;
        }
    }

    private void processBalanceInquiry(ATMOrchestrator context) {
        double balance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
        System.out.println("Your current balance is: $" + String.format("%.2f", balance));
        
        System.out.println("Would you like a printed receipt? (Y/N)");
    }

    private void processMiniStatement(ATMOrchestrator context) {
        var transactions = context.getBankServiceManager().getMiniStatement(context.getCurrentCard(), 10);
        double balance = context.getBankServiceManager().getAccountBalance(context.getCurrentCard());
        
        context.getReceiptPrinter().printMiniStatement(transactions, 
            context.getCurrentCard().getAccountNumber(), balance);
        
        context.setState(new CardEjectionState());
        context.ejectCard();
    }

    @Override
    public void enterAmount(ATMOrchestrator context, double amount) {
        System.out.println("Please select a transaction first.");
    }

    @Override
    public void dispenseCash(ATMOrchestrator context) {
        System.out.println("Please select a transaction first.");
    }

    @Override
    public void ejectCard(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        context.ejectCard();
    }

    @Override
    public void printReceipt(ATMOrchestrator context) {
        System.out.println("Please complete a transaction first.");
    }

    @Override
    public void cancel(ATMOrchestrator context) {
        context.setState(new CardEjectionState());
        context.ejectCard();
    }
}

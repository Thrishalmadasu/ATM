public interface ATMState {
    void insertCard(ATMOrchestrator orchestrator, Card card);
    void enterPin(ATMOrchestrator orchestrator, String pin);
    void selectTransaction(ATMOrchestrator orchestrator, TransactionType transactionType);
    void enterAmount(ATMOrchestrator orchestrator, double amount);
    void dispenseCash(ATMOrchestrator orchestrator);
    void ejectCard(ATMOrchestrator orchestrator);
    void printReceipt(ATMOrchestrator orchestrator);
    void cancel(ATMOrchestrator orchestrator);
}
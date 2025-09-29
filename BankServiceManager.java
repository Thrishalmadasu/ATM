import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankServiceManager {
    private Map<String, BankService> bankServices;
    private static BankServiceManager instance;

    private BankServiceManager() {
        this.bankServices = new HashMap<>();
        initializeBankServices();
    }

    public static BankServiceManager getInstance() {
        if (instance == null) {
            instance = new BankServiceManager();
        }
        return instance;
    }

    private void initializeBankServices() {
        BankServiceImpl centralBank = new BankServiceImpl("CENTRAL_BANK");
        BankServiceImpl nationalBank = new BankServiceImpl("NATIONAL_BANK");
        BankServiceImpl commercialBank = new BankServiceImpl("COMMERCIAL_BANK");

        centralBank.addAccount("1234567890", "4532123456789012", "1234", 5000.00);
        nationalBank.addAccount("0987654321", "5555666677778888", "5678", 10000.00);
        commercialBank.addAccount("1111222233", "4444333322221111", "9999", 25000.00);

        bankServices.put("BANK001", centralBank);
        bankServices.put("BANK002", nationalBank);
        bankServices.put("BANK003", commercialBank);
    }

    public BankService getBankService(String bankId) {
        BankService service = bankServices.get(bankId);
        if (service == null) {
            throw new IllegalArgumentException("Unsupported bank: " + bankId);
        }
        return service;
    }

    public boolean validatePin(Card card, String enteredPin) {
        BankService bankService = getBankService(card.getBankId());
        return bankService.validatePin(card, enteredPin);
    }

    public double getAccountBalance(Card card) {
        BankService bankService = getBankService(card.getBankId());
        return bankService.getAccountBalance(card);
    }

    public boolean withdraw(Card card, double amount) {
        BankService bankService = getBankService(card.getBankId());
        return bankService.withdraw(card, amount);
    }

    public boolean deposit(Card card, double amount) {
        BankService bankService = getBankService(card.getBankId());
        return bankService.deposit(card, amount);
    }

    public boolean changePin(Card card, String newPin) {
        BankService bankService = getBankService(card.getBankId());
        return bankService.changePin(card, newPin);
    }

    public List<Transaction> getMiniStatement(Card card, int numberOfTransactions) {
        BankService bankService = getBankService(card.getBankId());
        return bankService.getMiniStatement(card, numberOfTransactions);
    }

    public String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }

    public void addBankService(String bankId, BankService bankService) {
        bankServices.put(bankId, bankService);
    }

    public boolean isBankSupported(String bankId) {
        return bankServices.containsKey(bankId);
    }
}

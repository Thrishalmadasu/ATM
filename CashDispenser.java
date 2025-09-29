import java.util.Map;
import java.util.HashMap;

public class CashDispenser {
    private Map<Integer, Integer> cashInventory;
    private DispensingStrategy dispensingStrategy;
    private final int[] denominations = {2000, 500, 200, 100, 50, 20, 10};

    public CashDispenser(DispensingStrategy strategy) {
        this.cashInventory = new HashMap<>();
        this.dispensingStrategy = strategy;
        initializeCash();
    }

    private void initializeCash() {
        for (int denomination : denominations) {
            cashInventory.put(denomination, 100);
        }
    }

    public boolean canDispense(double amount) {
        if (amount <= 0 || amount % 10 != 0) {
            return false;
        }
        return dispensingStrategy.dispenseCash((int) amount, cashInventory) != null;
    }

    public Map<Integer, Integer> dispenseCash(double amount) {
        if (!canDispense(amount)) {
            throw new IllegalArgumentException("Cannot dispense the requested amount");
        }

        Map<Integer, Integer> dispenseMap = dispensingStrategy.dispenseCash((int) amount, cashInventory);
        if (dispenseMap != null) {
            for (Map.Entry<Integer, Integer> entry : dispenseMap.entrySet()) {
                int denomination = entry.getKey();
                int count = entry.getValue();
                cashInventory.put(denomination, cashInventory.get(denomination) - count);
            }
        }
        return dispenseMap;
    }

    public void setDispensingStrategy(DispensingStrategy strategy) {
        this.dispensingStrategy = strategy;
    }

    public DispensingStrategy getDispensingStrategy() {
        return dispensingStrategy;
    }

    public String getCurrentStrategyName() {
        return dispensingStrategy.getStrategyName();
    }

    public void addCash(int denomination, int count) {
        cashInventory.put(denomination, cashInventory.getOrDefault(denomination, 0) + count);
    }

    public Map<Integer, Integer> getCashInventory() {
        return new HashMap<>(cashInventory);
    }
}

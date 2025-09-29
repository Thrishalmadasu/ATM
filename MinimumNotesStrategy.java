import java.util.HashMap;
import java.util.Map;

public class MinimumNotesStrategy implements DispensingStrategy {
    private final int[] denominations = {2000, 500, 200, 100, 50, 20, 10};

    @Override
    public Map<Integer, Integer> dispenseCash(int amount, Map<Integer, Integer> availableInventory) {
        Map<Integer, Integer> result = new HashMap<>();
        Map<Integer, Integer> tempInventory = new HashMap<>(availableInventory);
        int remainingAmount = amount;

        for (int denomination : denominations) {
            if (remainingAmount >= denomination && tempInventory.get(denomination) > 0) {
                int notesNeeded = Math.min(remainingAmount / denomination, tempInventory.get(denomination));
                if (notesNeeded > 0) {
                    result.put(denomination, notesNeeded);
                    remainingAmount -= notesNeeded * denomination;
                    tempInventory.put(denomination, tempInventory.get(denomination) - notesNeeded);
                }
            }
        }

        return remainingAmount == 0 ? result : null;
    }

    @Override
    public String getStrategyName() {
        return "Minimum Notes Strategy";
    }
}

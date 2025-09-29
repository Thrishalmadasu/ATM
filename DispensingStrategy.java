import java.util.Map;

public interface DispensingStrategy {
    Map<Integer, Integer> dispenseCash(int amount, Map<Integer, Integer> availableInventory);
    String getStrategyName();
}

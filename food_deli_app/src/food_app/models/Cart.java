package food_app.models;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Item, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public double getTotalAmount() {
        double total = 0.0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            total += entry.getKey().getCurrentPrice() * entry.getValue();
        }
        return total;
    }
}
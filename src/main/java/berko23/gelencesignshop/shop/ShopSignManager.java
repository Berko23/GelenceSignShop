package berko23.gelencesignshop.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ShopSignManager {
    private final Map<Location, ShopSign> shopSigns;

    public ShopSignManager() {
        this.shopSigns = new HashMap<>();
    }

    public void markAsWaiting(Location loc, @Nullable Double buy, @Nullable Double sell) {
        shopSigns.put(loc, new ShopSign(loc, buy, sell, true, null));
    }

    public void bindItem(Location loc, ItemStack item) {
        ShopSign shop = shopSigns.get(loc);
        if (shop != null && shop.isWaiting()) {
            shop.setItem(item.clone());
            shop.setWaiting(false);
        }
    }

    public ShopSign get(Location loc) {
        return shopSigns.get(loc);
    }

    public boolean isShop(Location loc) {
        return shopSigns.containsKey(loc);
    }

    public void remove(Location loc) {
        shopSigns.remove(loc);
    }
}

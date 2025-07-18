package berko23.gelencesignshop.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * The logic of a single shop instance
 */
public class ShopSign {
    private final Location location;
    private final Double buyPrice;
    private final Double sellPrice;
    private boolean isWaiting;
    private ItemStack item;

    public ShopSign(Location loc, double buyPrice, double sellPrice, boolean isWaiting, ItemStack item) {
        this.location = loc;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.isWaiting = isWaiting;
        this.item = item;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public Location getLocation() {
        return location;
    }
}
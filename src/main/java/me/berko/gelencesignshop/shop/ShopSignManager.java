package me.berko.gelencesignshop.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ShopSignManager {
    private final Map<Location, ShopSign> shopSigns;

    public ShopSignManager() {
        this.shopSigns = new HashMap<>();
    }

    /**
     * Add the given sign data to the shop sign list.
     *
     * @param location  The sign's location.
     * @param value     The amount the player either pays or gets for an item.
     * @param isBuySign Set to true if the sign is a buy sign (takes money and gives item), false if it is a sell sign (takes item and gives money).
     */
    public void markAsWaiting(@Nonnull Location location, double value, boolean isBuySign) {
        shopSigns.put(location, new ShopSign(location, value, isBuySign, true, null));
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

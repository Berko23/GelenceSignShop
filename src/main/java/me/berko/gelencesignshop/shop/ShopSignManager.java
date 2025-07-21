package me.berko.gelencesignshop.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ShopSignManager {
    private final Map<Location, ShopSign> shopSigns;

    public ShopSignManager() {
        this.shopSigns = new HashMap<>();
    }

    /**
     * Add the given sign data to the shop sign list.
     * @param location The sign's location.
     * @param buyPrice The amount the player pays for an item. If it is null, the item will not be buy-able.
     * @param sellPrice The amount the player gets for an item. If it is null, the item will not be sell-able.
     */
    public void markAsWaiting(@Nonnull Location location, @Nullable Double buyPrice, @Nullable Double sellPrice) {
        shopSigns.put(location, new ShopSign(location, buyPrice, sellPrice, true, null));
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

package me.berko.gelencesignshop.shop;

import me.berko.gelencesignshop.shop.persistance.SignStorageFactory;
import me.berko.gelencesignshop.shop.persistance.YamlSignStorage;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ShopSignManager {
    private final Map<Location, ShopSign> shopSigns;
    private final SignStorageFactory storage = new YamlSignStorage(); // uses the yaml saving model

    public ShopSignManager() {
        this.shopSigns = new HashMap<>();
    }

    /**
     *  Load/Reload all shop signs from save file
     */
    public void loadAllSigns() {
        shopSigns.clear();
        Map<String, ShopSign> loaded = storage.loadAll();
        for (ShopSign sign : loaded.values()) {
            shopSigns.put(sign.getLocation(), sign);
        }
    }

    public void markAsWaiting(@Nonnull Location location, double value, boolean isBuySign) {
        ShopSign sign = new ShopSign(location, value, isBuySign, true, null);
        shopSigns.put(location, sign);
        storage.saveSign(sign); // save to file
    }

    public void bindItem(Location loc, ItemStack item) {
        ShopSign shop = shopSigns.get(loc);
        if (shop != null && shop.isWaiting()) {
            shop.setItem(item.clone());
            shop.setWaiting(false);
            storage.saveSign(shop); // resave to file
        }
    }

    public ShopSign get(Location loc) {
        return shopSigns.get(loc);
    }

    public boolean isShop(Location loc) {
        return shopSigns.containsKey(loc);
    }

    public void remove(Location loc) {
        storage.removeSign(loc);
        shopSigns.remove(loc);
    }
}

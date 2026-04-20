package me.berko.gelencesignshop.shop;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.persistance.SignStorageFactory;
import me.berko.gelencesignshop.shop.persistance.YamlSignStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

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
        shopSigns.putAll(storage.loadAll());
    }

    /**
     * Register a new sign and mark as waiting for item binding. This is called when a sign is first created and validated, but before the player has selected an item.
     * @param location The location of the sign
     * @param value The buy/sell price
     * @param isBuySign Whether this is a buy or sell sign
     */
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

    /**
     * Unbind the item from the shop and mark it as waiting again. This can be used to reset a shop sign if the owner wants to change the item being sold/bought.
     * @param shop The shop sign to unbind
     */
    public void unbindItem(ShopSign shop) {
        shop.setItem(null);
        shop.setWaiting(true);
        shopSigns.put(shop.getLocation(), shop); // update in loaded memory
        storage.saveSign(shop); // resave to file
    }

    /**
     * Change the buy/sell price of a shop sign. This can be used for admin adjustments or if you want to implement a feature for shop owners to change their prices.
     * @param shop The shop sign to update
     * @param newValue The new buy/sell price
     */
    public void changeValue(ShopSign shop, double newValue) {
        shop.setValue(newValue);
        shopSigns.put(shop.getLocation(), shop); // update in loaded memory
        storage.saveSign(shop);
    }

    /**
     * Change whether a shop sign is a buy or sell sign. This can be used for admin adjustments or if you want to implement a feature for shop owners to switch between buying and selling.
     * @param shop The shop sign to update
     * @param isBuySign Whether the sign should be a buy sign (true) or sell sign (false)
     */
    public void changeBuySell(ShopSign shop, boolean isBuySign) {
        shop.setBuySign(isBuySign);
        shopSigns.put(shop.getLocation(), shop); // update in loaded memory
        storage.saveSign(shop);
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

    public int removeInvalidShops() {
        loadAllSigns();

        int removed = 0;
        Iterator<Map.Entry<Location, ShopSign>> iterator = shopSigns.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Location, ShopSign> entry = iterator.next();
            Location loc = entry.getKey();

            if (!(loc.getBlock().getState() instanceof Sign)) {
                iterator.remove();
                storage.removeSign(loc);
                Bukkit.getLogger().info("[GelenceSignShop] Removed orphan shop at " +
                        Objects.requireNonNull(loc.getWorld()).getName() + ":" + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
                removed++;
            }
        }

        loadAllSigns();
        return removed;
    }

    public List<Location> getInvalidShopLocations() {
        List<Location> invalid = new ArrayList<>();

        for (Map.Entry<Location, ShopSign> entry : shopSigns.entrySet()) {
            Location loc = entry.getKey();
            if (!(loc.getBlock().getState() instanceof Sign)) {
                invalid.add(loc);
            }
        }

        return invalid;
    }
}

package me.berko.gelencesignshop.shop;

import me.berko.gelencesignshop.shop.persistance.SignStorageFactory;
import me.berko.gelencesignshop.shop.persistance.YamlSignStorage;
import me.berko.gelencesignshop.util.SignDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class ShopSignManager {
    private final Map<Location, ShopSign> shopSigns;
    private final SignStorageFactory storage = new YamlSignStorage(); // uses the Yaml saving model

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
    @Deprecated
    public void markAsWaiting(@Nonnull Location location, double value, boolean isBuySign) {
        ShopSign sign = new ShopSign(location, value, isBuySign, true, null);
        shopSigns.put(location, sign);
        storage.saveSign(sign); // save to file
    }


    public void markAsWaiting(@Nonnull ShopSign shop) {
        shop.setWaiting(true);
        shop.setItem(null);
        shopSigns.put(shop.getLocation(), shop);
        storage.saveSign(shop); // save to file
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
     * Unbind the item from the shop and mark it as waiting again. This updates the sign block's display text too.
     * @param shop The shop sign to unbind
     */
    public void unbindItem(ShopSign shop) {
        shop.setItem(null);
        shop.setWaiting(true);
        shopSigns.put(shop.getLocation(), shop); // update in loaded memory
        storage.saveSign(shop); // resave to file
        SignDisplayManager.updateSign(shop); // update the sign text to reflect the new price
    }

    /**
     * Change the buy/sell price of a shop sign and update the sign block's display text.
     * @param shop The shop sign to update
     * @param newValue The new buy/sell price
     */
    // TODO: changing the value to a price like 999999 produces 1000.0k on sign (rounding to 2 decimal places did not fix this)
    public void changeValue(ShopSign shop, double newValue) {
        shop.setValue(newValue);
        shopSigns.put(shop.getLocation(), shop); // update in loaded memory
        storage.saveSign(shop);
        SignDisplayManager.updateSign(shop); // update the sign text to reflect the updated shop state
    }

    /**
     * Change whether a shop sign is a buy or sell sign and update the sign block's display text.
     * @param shop The shop sign to update
     * @param isBuySign Whether the sign should be a buy sign (true) or sell sign (false)
     */
    public void changeBuySell(ShopSign shop, boolean isBuySign) {
        shop.setBuySign(isBuySign);
        shopSigns.put(shop.getLocation(), shop); // update in loaded memory
        storage.saveSign(shop);
        SignDisplayManager.updateSign(shop); // update the sign text to reflect the new scope
    }

    public ShopSign getShopSign(Location loc) {
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

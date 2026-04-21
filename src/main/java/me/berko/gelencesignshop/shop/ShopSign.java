package me.berko.gelencesignshop.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The logic of a single shop instance
 */
public class ShopSign {
    private final Location location;
    private double value;
    private boolean buySign;
    private boolean isWaiting;
    private ItemStack item;

    /**
     * Constructor for loading a shop sign from file with all properties specified.
     * @param loc The location of the sign
     * @param buyPrice The buy/sell price of the shop
     * @param buySign Whether this is a buy or sell sign
     * @param isWaiting Whether the shop is waiting for item binding
     * @param item The item being bought/sold, or null if waiting for binding
     */
    public ShopSign(Location loc, double buyPrice, boolean buySign, boolean isWaiting, ItemStack item) {
        this.location = loc;
        this.value = buyPrice;
        this.buySign = buySign;
        this.isWaiting = isWaiting;
        this.item = item;
    }

    /**
     * Constructor for creating a new shop sign that is waiting for item binding.
     * @param loc The location of the sign
     * @param buyPrice The buy/sell price of the shop
     * @param buySign Whether this is a buy or sell sign
     */
    public ShopSign(Location loc, double buyPrice, boolean buySign) {
        this.location = loc;
        this.value = buyPrice;
        this.buySign = buySign;
        this.isWaiting = true;
        this.item = null;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isBuySign() {
        return buySign;
    }

    public void setBuySign(boolean buySign) {
        this.buySign = buySign;
    }

    public Location getLocation() {
        return location;
    }
}
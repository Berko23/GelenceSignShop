package me.berko.gelencesignshop.shop;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * The logic of a single shop instance
 */
public class ShopSign {
    private final Location location;
    private double value;
    private boolean buySign;
    private boolean isWaiting;
    private ItemStack item;

    public ShopSign(Location loc, double buyPrice, boolean buySign, boolean isWaiting, ItemStack item) {
        this.location = loc;
        this.value = buyPrice;
        this.buySign = buySign;
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
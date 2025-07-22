package me.berko.gelencesignshop.shop.persistance;

import me.berko.gelencesignshop.shop.ShopSign;
import org.bukkit.Location;

import java.util.Map;

public interface SignStorageFactory {
    void saveSign(ShopSign sign);
    void removeSign(Location loc);
    Map<String, ShopSign> loadAll(); // string key = Location serialization
}
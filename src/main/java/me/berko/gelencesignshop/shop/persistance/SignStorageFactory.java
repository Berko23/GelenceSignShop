package me.berko.gelencesignshop.shop.persistance;

import me.berko.gelencesignshop.shop.ShopSign;

import java.util.Map;

public interface SignStorageFactory {
    void saveSign(ShopSign sign);
    void removeSign(ShopSign sign);
    Map<String, ShopSign> loadAll(); // string key = Location serialization
}
package me.berko.gelencesignshop.shop.persistance;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YamlSignStorage implements SignStorageFactory {

    private final File file;
    private final FileConfiguration config;

    public YamlSignStorage() {
        file = new File(GelenceSignShop.getInstance().getDataFolder(), "signs.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create signs.yml", e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void saveSign(ShopSign sign) {
        String key = serializeLocation(sign.getLocation());
        config.set(key + ".buy", sign.isBuySign());
        config.set(key + ".price", sign.getValue());
        config.set(key + ".waiting", sign.isWaiting());
        config.set(key + ".item", sign.getItem());
        saveToFile();
    }

    @Override
    public void removeSign(ShopSign sign) {
        String key = serializeLocation(sign.getLocation());
        config.set(key, null);
        saveToFile();
    }

    @Override
    public Map<String, ShopSign> loadAll() {
        Map<String, ShopSign> map = new HashMap<>();

        for (String key : config.getKeys(false)) {
            Location loc = deserializeLocation(key);
            boolean buy = config.getBoolean(key + ".buy");
            double price = config.getDouble(key + ".price");
            boolean waiting = config.getBoolean(key + ".waiting");
            var item = config.getItemStack(key + ".item");

            ShopSign sign = new ShopSign(loc, price, buy, waiting, item);
            map.put(key, sign);
        }

        return map;
    }

    private void saveToFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeLocation(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private Location deserializeLocation(String str) {
        String[] parts = str.split(",");
        return new Location(
                Bukkit.getWorld(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        );
    }
}

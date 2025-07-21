package me.berko.gelencesignshop;

import me.berko.gelencesignshop.economy.EconomyHandler;
import me.berko.gelencesignshop.listeners.ListenerManager;
import me.berko.gelencesignshop.shop.ShopSignManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class GelenceSignShop extends JavaPlugin {
    private static GelenceSignShop instance;
    private ShopSignManager shopSignManager;

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            getLogger().severe("Vault not found! Disabling GelenceSignShop...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        shopSignManager = new ShopSignManager();

        ListenerManager.initListeners(this);

        getLogger().info("GelenceSignShop enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling GelenceSignShop");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        EconomyHandler.setEconomy(rsp.getProvider());
        return EconomyHandler.getEconomy() != null;
    }

    public static GelenceSignShop getInstance() {
        return instance;
    }

    public ShopSignManager getShopSignManager() {
        return shopSignManager;
    }
}

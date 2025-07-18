package berko23.gelencesignshop;

import berko23.gelencesignshop.economy.EconomyHandler;
import berko23.gelencesignshop.listeners.ShopProtectionListener;
import berko23.gelencesignshop.listeners.SignCreateListener;
import berko23.gelencesignshop.listeners.SignInteractListener;
import berko23.gelencesignshop.shop.ShopSignManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
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

        Bukkit.getPluginManager().registerEvents(new SignCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShopProtectionListener(), this);

        getLogger().info("GelenceSignShop enabled!");
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

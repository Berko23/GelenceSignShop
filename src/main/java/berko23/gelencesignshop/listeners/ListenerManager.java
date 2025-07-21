package berko23.gelencesignshop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public static void initListeners(Plugin plugin) {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new SignCreateListener(), plugin);
        pm.registerEvents(new SignInteractListener(), plugin);
        pm.registerEvents(new ShopProtectionListener(), plugin);
    }
}

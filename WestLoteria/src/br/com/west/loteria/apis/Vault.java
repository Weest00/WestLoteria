package br.com.west.loteria.apis;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
    private static Permission permission = null;

    private static Economy economy = null;

    private static Chat chat = null;

    public static Permission getPermission() {
        return permission;
    }

    public static Chat getChat() {
        return chat;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static boolean hasVault() {
        return (Bukkit.getPluginManager().getPlugin("Vault") != null);
    }

    public static boolean hasEconomy() {
        return (economy != null);
    }

    public static boolean hasChat() {
        return (chat != null);
    }

    public static boolean hasPermission() {
        return (permission != null);
    }

    private static boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer()
                .getServicesManager()
                .getRegistration(Chat.class);
        if (chatProvider != null)
            chat = (Chat) chatProvider.getProvider();
        return (chat != null);
    }

    private static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer()
                .getServicesManager()
                .getRegistration(Economy.class);
        if (economyProvider != null)
            economy = (Economy) economyProvider.getProvider();
        return (economy != null);
    }

    private static boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider =
                Bukkit.getServer().getServicesManager().getRegistration(
                        Permission.class);
        if (permissionProvider != null)
            permission = (Permission) permissionProvider.getProvider();
        return (permission != null);
    }

    public static void setupVault() {
        setupEconomy();
        setupChat();
        setupPermissions();
    }

    static {
        if (hasVault())
            setupVault();
    }
}

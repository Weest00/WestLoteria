package br.com.west.loteria;

import br.com.west.loteria.apis.ActionBar;
import br.com.west.loteria.apis.Vault;
import br.com.west.loteria.commands.LoteriaCommand;
import br.com.west.loteria.database.SQLite;
import br.com.west.loteria.database.SQLiteMethods;
import br.com.west.loteria.inventorys.LoteriaInventory;
import br.com.west.loteria.listeners.AsyncPlayerChatListener;
import br.com.west.loteria.listeners.InventoryClickListener;
import br.com.west.loteria.listeners.PlayerJoinListener;
import br.com.west.loteria.listeners.PlayerQuitListener;
import br.com.west.loteria.managers.Cache;
import br.com.west.loteria.managers.Loteria;
import br.com.west.loteria.runnable.SavePlayersRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {


    public static BukkitTask currentTask;
    private static Main instance;

    private SQLite SQLite;
    private SQLiteMethods SQLiteMethods;
    private Cache cache;
    private Loteria loteria;

    private ActionBar actionBar;

    private LoteriaInventory loteriaInventory;

    public void onEnable() {
        instance = this;
        SQLite = new SQLite();
        SQLiteMethods = new SQLiteMethods();
        cache = new Cache();
        loteria = new Loteria();
        actionBar = new ActionBar();
        loteriaInventory = new LoteriaInventory();
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        loadDatabase();
        updateTasks();

        if (Vault.hasVault()){
            Bukkit.getConsoleSender().sendMessage("§a[INFO] §fHook com o vault realizado com sucesso.");

        } else {
            Bukkit.getConsoleSender().sendMessage("§a[INFO]§fNão foi possivel fazer o hook com o vault, desligando o plugin.");
            Bukkit.getPluginManager().disablePlugin(this);

        }

        if (Vault.hasEconomy()){
            Bukkit.getConsoleSender().sendMessage("§a[INFO] §fHook com o plugin de economia com sucesso.");

        } else {
            Bukkit.getConsoleSender().sendMessage("§a[INFO]§fNão foi possivel fazer o hook com o plugin de economia, desligando o plugin.");
            Bukkit.getPluginManager().disablePlugin(this);

        }

    }

    public void onDisable() {
        getSqLiteMethods().savePlayers();

    }

    private void registerCommands() {
        getCommand("loteria").setExecutor(new LoteriaCommand());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);

    }

    private void loadDatabase(){
        getSqLiteMethods().createTable();
        getSqLiteMethods().loadPlayers();
        getSqLiteMethods().updateTopWinners();
        getSqLiteMethods().updateTopCoins();

    }

    private void updateTasks() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new SavePlayersRunnable(), 20 * 60 * 15, 20 * 60 * 15);

    }

    public SQLite getSQLite() {
        return SQLite;
    }

    public SQLiteMethods getSqLiteMethods() {
        return SQLiteMethods;
    }

    public Loteria getLoteria() {
        return loteria;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public Cache getCache() {
        return cache;
    }

    public LoteriaInventory getLoteriaInventory() {
        return loteriaInventory;
    }

    public static Main getInstance() {
        return instance;
    }


}



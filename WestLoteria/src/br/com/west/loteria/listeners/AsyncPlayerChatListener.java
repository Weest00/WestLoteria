package br.com.west.loteria.listeners;

import br.com.west.loteria.Main;
import br.com.west.loteria.apis.NumberFormat;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String message = event.getMessage();


        if (Main.getInstance().getCache().getChatLock().contains(playerName)) {
            event.setCancelled(true);

            if (message.equalsIgnoreCase("cancelar")) {
                Main.getInstance().getCache().getChatLock().remove(playerName);
                Main.getInstance().getActionBar().sendActionMessage(player, "§cO processo foi cancelado, chat disponível novamente.");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                return;
            }

            if (!Main.getInstance().getLoteria().getState()) {
                Main.getInstance().getCache().getChatLock().remove(playerName);
                Main.getInstance().getActionBar().sendActionMessage(player, "§cO evento loteria está offline no momento.");
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                return;
            }

            if (!isNumber(message)) {
                Main.getInstance().getActionBar().sendActionMessage(player, "§cVocê pode usar apenas números na loteria.");
                player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                return;

            }

            double number = Double.parseDouble(message);

            if (number > 100 || number < 1) {
                Main.getInstance().getActionBar().sendActionMessage(player, "§cPor favor, digite apenas números entre 1 a 100.");
                player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                return;

            }

            if (number != Main.getInstance().getLoteria().getNumber()) {
                Main.getInstance().getActionBar().sendActionMessage(player, "§cQue azar! infelizmente não é esse número, tente novamente!");
                player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);

            } else {
                Main.getInstance().getLoteria().closeLoteria();
                Main.getInstance().getCache().getCachePlayer().get(playerName).setWinners(Main.getInstance().getCache().getCachePlayer().get(playerName).getWinners() + 1);
                Main.getInstance().getCache().getCachePlayer().get(playerName).setCoins(Main.getInstance().getCache().getCachePlayer().get(playerName).getCoins() + Main.getInstance().getLoteria().getReward());
                Main.getInstance().getCache().getChatLock().remove(playerName);
                Main.getInstance().getActionBar().sendActionMessage(player, "§aQue sorte! Você acabou de acertar o número premiado!");
                Main.getInstance().getConfig().set("last-winner", playerName);
                Main.getInstance().saveConfig();
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F);
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§6<> §f Evento loteria finalizado");
                Bukkit.broadcastMessage("§6<> §a" + playerName + "§f ganhou o evento loteria.");
                Bukkit.broadcastMessage("§6<> §fNúmero premiado: §a" + Main.getInstance().getLoteria().getNumber());
                Bukkit.broadcastMessage("§6<> §fRecompensa: §2$§a" + NumberFormat.format(Main.getInstance().getLoteria().getReward()));
                Bukkit.getOnlinePlayers().forEach(allPlayers -> allPlayers.playSound(allPlayers.getLocation(), Sound.ENDERDRAGON_DEATH, 1F, 1F));

            }


        }
    }

    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}

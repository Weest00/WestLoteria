package br.com.west.loteria.listeners;

import br.com.west.loteria.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {


    @EventHandler
    void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (Main.getInstance().getCache().getChatLock().contains(playerName)){
            Main.getInstance().getCache().getChatLock().remove(playerName);

        }

    }
}

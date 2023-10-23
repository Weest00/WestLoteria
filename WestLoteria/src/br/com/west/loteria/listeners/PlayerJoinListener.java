package br.com.west.loteria.listeners;

import br.com.west.loteria.Main;
import br.com.west.loteria.objects.PlayerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();

        if (! Main.getInstance().getCache().getCachePlayer().containsKey(playerName)) {
            PlayerUtil playerUtil = new PlayerUtil(playerName, 0, 0);
            Main.getInstance().getCache().getCachePlayer().put(playerName, playerUtil);

        }
    }

}

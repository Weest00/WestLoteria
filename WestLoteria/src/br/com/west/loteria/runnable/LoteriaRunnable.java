package br.com.west.loteria.runnable;

import br.com.west.loteria.Main;
import br.com.west.loteria.apis.NumberFormat;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class LoteriaRunnable implements Runnable {

    int count = Main.getInstance().getConfig().getInt("loteria-time");

    @Override
    public void run() {
        if (!Main.getInstance().getLoteria().getState()) {
            return;

        }

        if (count >= 0) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§6<> §fEvento loteria iniciado");
            Bukkit.broadcastMessage("§6<> §fAcerte o número da loteria");
            Bukkit.broadcastMessage("§6<> §fPara participar digite '§e/loteria§f'.");
            Bukkit.broadcastMessage("§6<> §fChamadas restantes: §e" + count);
            Bukkit.broadcastMessage("§6<> §fTempo p/cada chamada: §e1 Minuto");
            Bukkit.broadcastMessage("§6<> §fPrêmio: §2$§a" + NumberFormat.format(Main.getInstance().getConfig().getDouble("price-reward")));
            Bukkit.broadcastMessage("");
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.LEVEL_UP, 1F, 1F));
        }

        if (count == -1) {
            Main.getInstance().getLoteria().closeLoteria();
            Main.getInstance().getCache().getChatLock().clear();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§6<> §fEvento loteria encerrado");
            Bukkit.broadcastMessage("§6<> §fInfelizmente nínguem acertou o número");
            Bukkit.broadcastMessage("§6<> §fDo evento loteria.");
            Bukkit.broadcastMessage("§6<> §fPrêmio: §2$§a" + NumberFormat.format(Main.getInstance().getConfig().getDouble("price-reward")));
            Bukkit.broadcastMessage("");
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1F, 1F));
            Main.currentTask.cancel();
        }
        count--;
    }
}

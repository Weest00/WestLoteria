package br.com.west.loteria.listeners;

import br.com.west.loteria.Main;
import br.com.west.loteria.apis.NumberFormat;
import br.com.west.loteria.apis.Vault;
import br.com.west.loteria.runnable.LoteriaRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitTask;

public class InventoryClickListener implements Listener {


    @EventHandler
    void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        String playerName = player.getName();
        double playerCoins = Main.getInstance().getCache().getCachePlayer().get(playerName).getCoins();

       if (event.getClickedInventory() == null){
            return;
        }

       if (event.getCurrentItem() == null){
           return;
       }

        if (event.getClickedInventory().getTitle().equals("§7Top Coins")) {
            event.setCancelled(true);

            if (event.getSlot() == 40){
                player.openInventory(Main.getInstance().getLoteriaInventory().openInv(player));
                player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);

            }


            if (event.getSlot() == 41) {
                player.openInventory(Main.getInstance().getLoteriaInventory().openWinnersTopInv());
                player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);
            }
        }



       if (event.getClickedInventory().getTitle().equals("§7Top Vitórias")){
           event.setCancelled(true);

           if (event.getSlot() == 40){
               player.openInventory(Main.getInstance().getLoteriaInventory().openInv(player));
               player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);

           }


           if (event.getSlot() == 41){
               player.openInventory(Main.getInstance().getLoteriaInventory().openCoinsTopInv());
               player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);
           }
       }



       if(event.getClickedInventory().getTitle().equals("§7Loteria")){
           event.setCancelled(true);


           if (event.getSlot() == 11){
               player.openInventory(Main.getInstance().getLoteriaInventory().openWinnersTopInv());
           }

           if (event.getSlot() == 12){
               if (playerCoins > 0){
                   Main.getInstance().getCache().getCachePlayer().get(playerName).setCoins(0);
                   Vault.getEconomy().depositPlayer(player, playerCoins);
                   Main.getInstance().getActionBar().sendActionMessage(player,"§aSucesso! Você acabou de resgatar §2$§a" + NumberFormat.format(playerCoins) + "§a de coins no armazém.");
                   player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);
                   player.closeInventory();


               } else {
                   Main.getInstance().getActionBar().sendActionMessage(player, "§cVocê não possui coins para resgatar no evento loteria.");
                   player.playSound(player.getLocation(), Sound.CAT_MEOW,1F,1F);
                   player.closeInventory();

               }
           }

           if (event.getSlot() == 14){

               if(player.hasPermission("west.loteria.admin")){

                   if (Main.getInstance().getLoteria().getState()){
                       Main.getInstance().getActionBar().sendActionMessage(player,"§cO evento já foi iniciado, aguarde acabar.");
                       player.playSound(player.getLocation(), Sound.CAT_MEOW,1F,1F);
                       player.closeInventory();

                   } else {
                       Main.getInstance().getLoteria().openLoteria();
                       BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new LoteriaRunnable(), 20, 20 * 60 * 1);
                       Main.currentTask = task;
                       Main.getInstance().getActionBar().sendActionMessage(player,"§aEvento loteria iniciado com sucesso.");
                       player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);
                       player.closeInventory();

                   }


               } else {
                   Main.getInstance().getActionBar().sendActionMessage(player,"§cVocê não possui permissão para iniciar o evento.");
                   player.playSound(player.getLocation(), Sound.CAT_MEOW,1F,1F);
                   player.closeInventory();



               }


           }

           if (event.getSlot() == 15){
               if (!Main.getInstance().getLoteria().getState()) {
                   Main.getInstance().getActionBar().sendActionMessage(player, "§cO evento loteria está offline no momento.");
                   player.closeInventory();
                   player.playSound(player.getLocation(), Sound.CAT_MEOW, 1F, 1F);
                   return;
               }

               if (Main.getInstance().getCache().getChatLock().contains(playerName)){
                   Main.getInstance().getActionBar().sendActionMessage(player,"§cVocê já está em um processo ativo.");
                   player.closeInventory();
                   player.playSound(player.getLocation(), Sound.CAT_MEOW,1F,1F);

               } else {
                   Main.getInstance().getCache().getChatLock().add(playerName);
                   player.sendMessage("§aDigite o número premiado na loteria. \npara cancelar a ação digite §7§ncancelar§a.");
                   player.closeInventory();
                   player.playSound(player.getLocation(), Sound.LEVEL_UP,1F,1F);

               }


           }

       }


    }
}

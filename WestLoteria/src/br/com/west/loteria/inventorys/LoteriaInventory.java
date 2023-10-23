package br.com.west.loteria.inventorys;

import br.com.west.loteria.Main;
import br.com.west.loteria.apis.ItemBuilder;
import br.com.west.loteria.apis.NumberFormat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
public class LoteriaInventory {
    

    public Inventory openInv(Player player) {
        String playerName = player.getName();
        int playerWinners = Main.getInstance().getCache().getCachePlayer().get(playerName).getWinners();
        double playerCoins =  Main.getInstance().getCache().getCachePlayer().get(playerName).getCoins();
        String getState = Main.getInstance().getLoteria().getState() ? "§aSim" : "§cNão";
        String hasPermission = player.hasPermission("west.loteria.admin") ? "§aSim" : "§cNão";
        Inventory inventory = Bukkit.createInventory(null, 27, "§7Loteria");
        inventory.setItem(10, ItemBuilder.add(playerName, "§aInformações de " + playerName, "§7Veja abaixo algumas de suas informações.", "", "§fVitórias na loteria: §6✪" + playerWinners, "§fCoins acumulados no armazém: §2$§a" + NumberFormat.format(playerCoins)));

        inventory.setItem(11, ItemBuilder.add(Material.GOLD_INGOT, "§aTop Jogadores", "§7Clique para ver o top jogadores."));


        if (playerCoins <= 0){
            inventory.setItem(12, ItemBuilder.add(Material.WEB, "§aArmázem de coins", "§7Você não possui coins para resgatar."));

        } else {
            inventory.setItem(12, ItemBuilder.add(Material.STORAGE_MINECART, "§aArmázem de coins", "§7Clique para resgatar §2$§a" + NumberFormat.format(playerCoins) + "§7 coins."));

        }

        inventory.setItem(14, ItemBuilder.add(Material.BOOK, "§aIniciar Loteria", "", "§fPossui permissão para iniciar: " + hasPermission, "", "§7Clique para iniciar o evento."));
        inventory.setItem(15, ItemBuilder.add(Material.ENDER_CHEST, "§aParticipar da Loteria","","§fEstá acontecendo: " + getState,"§fJogadores tentando acertar: §e" + Main.getInstance().getCache().getChatLock().size(), "", "§7Clique para adivinhar o número da loteria."));

        return inventory;
    }

    public Inventory openWinnersTopInv() {
        Inventory inventory = Bukkit.createInventory(null, 45, "§7Top Vitórias");
        String lastWinner = Main.getInstance().getConfig().getString("last-winner");
        int[] slots = {10, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24};
        int count = 1;


        if (Main.getInstance().getCache().getTopWinners().isEmpty()) {
            inventory.setItem(22, ItemBuilder.add(Material.WEB, "§cNenhum jogador disponível..."));

        } else {

            for (String top :  Main.getInstance().getCache().getTopWinners()) {
                inventory.setItem(slots[count], ItemBuilder.add(top, "§7#§f" + count + "§7 - §e" + top, "", "§fVitórias: §6✪" +  Main.getInstance().getCache().getCachePlayer().get(top).getWinners(), "§fPosição: §6" + count));
                count++;

            }

        }

        if (lastWinner == null){
            inventory.setItem(39, ItemBuilder.add(Material.WEB , "§cNenhum jogador foi o vencedor..."));
        } else {
            inventory.setItem(39, ItemBuilder.add(lastWinner,  "§aCabeça de " + lastWinner,"§7O ultimo vencedor da loteria foi o jogador §a" + lastWinner + "§7."));
        }

        inventory.setItem(40, ItemBuilder.add(Material.ARROW, "§aVoltar", "§7Clique para voltar a página."));

        inventory.setItem(41, ItemBuilder.add(Material.HOPPER, "§bFiltro", "§b▶ TOP Vitórias", "§7▶ TOP Coins acumulados", "", "§bClique para mudar de filtro."));

        return inventory;

    }

    public Inventory openCoinsTopInv() {
        Inventory inventory = Bukkit.createInventory(null, 45, "§7Top Coins");
        int[] slots = {10, 11, 12, 13, 14, 15, 20, 21, 22, 23, 24};
        int count = 1;


        if ( Main.getInstance().getCache().getTopCoins().isEmpty()) {
            inventory.setItem(22, ItemBuilder.add(Material.WEB, "§cNenhum jogador disponível..."));

        } else {

            for (String top :  Main.getInstance().getCache().getTopCoins()) {
                inventory.setItem(slots[count], ItemBuilder.add(top, "§7#§f" + count + "§7 - §a" + top, "", "§fCoins acumulados: §2$§a" + NumberFormat.format( Main.getInstance().getCache().getCachePlayer().get(top).getCoins()), "§fPosição: §a" + count));
                count++;

            }
        }

        inventory.setItem(40, ItemBuilder.add(Material.ARROW, "§aVoltar", "§7Clique para voltar a página."));
        inventory.setItem(41, ItemBuilder.add(Material.HOPPER, "§bFiltro", "§7▶ TOP Vitórias", "§b▶ TOP Coins acumulados", "", "§bClique para mudar de filtro."));

        return inventory;


    }

}

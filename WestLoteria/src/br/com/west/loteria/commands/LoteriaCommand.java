package br.com.west.loteria.commands;

import br.com.west.loteria.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoteriaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {

        if (sender instanceof org.bukkit.entity.Player) {

            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("loteria")) {
                player.openInventory(Main.getInstance().getLoteriaInventory().openInv(player));

            }


        } else {
            Bukkit.getLogger().warning("Apenas jogadores podem usar este comando.");
        }
        return false;
    }
}

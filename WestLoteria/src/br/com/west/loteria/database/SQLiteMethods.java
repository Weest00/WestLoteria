package br.com.west.loteria.database;

import br.com.west.loteria.Main;
import br.com.west.loteria.objects.PlayerUtil;
import org.bukkit.Bukkit;
import java.sql.*;
import java.util.Map;
public class SQLiteMethods {


    public void createTable() {
        try {
            PreparedStatement stm = connection().prepareStatement("CREATE TABLE IF NOT EXISTS `west_loteria` (`player` TEXT,`winners` INTEGER, `coins` DOUBLE)");
            stm.executeUpdate();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fTabela carregada/criada com sucesso.");
            Main.getInstance().getSQLite().disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao criar tabela na database.");

        }
    }

    public boolean getPlayer(String player) {
        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria` WHERE `player` = ?");
            stm.setString(1, player);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;

            } else {

                return false;
            }

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao buscar o jogador §a" + player + " §fna database.");
            return false;

        }
    }

    public void loadPlayers() {
        int count = 0;

        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria`");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String player = rs.getString("player");
                int winners = rs.getInt("winners");
                double coins = rs.getDouble("coins");


                PlayerUtil playerUtil = new PlayerUtil(player, winners, coins);
                Main.getInstance().getCache().getCachePlayer().put(player, playerUtil);
                count++;
            }

            Main.getInstance().getSQLite().disconnect();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fForam carregados §a" + count + "§f jogadores da database.");

        } catch (SQLException e) {
            Main.getInstance().getSQLite().disconnect();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao carregar jogadores da database.");

        }
    }

    public void insertPlayer(String player, int winners, double coins) {
        try {
            PreparedStatement stm = connection().prepareStatement("INSERT INTO `west_loteria` (`player`,`winners`,`coins`) VAlUES (?,?,?)");
            stm.setString(1, player);
            stm.setInt(2, winners);
            stm.setDouble(3, coins);
            stm.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao inserir o jogador §a" + player + " §fna database.");

        }

    }

    public void updatePlayer(String player, int winners, double coins) {
        try {
            PreparedStatement stm = connection().prepareStatement("UPDATE `west_loteria` SET `winners` =? ,`coins` =? WHERE `player` =?");
            stm.setInt(1, winners);
            stm.setDouble(2, coins);
            stm.setString(3, player);
            stm.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao atualizar o jogador §a" + player + " §fna database.");
        }

    }

    public void savePlayers() {
        int count = 0;
        for (Map.Entry<String, PlayerUtil> player :  Main.getInstance().getCache().getCachePlayer().entrySet()) {
            if (getPlayer(player.getKey())) {
                updatePlayer(player.getKey(), player.getValue().getWinners(), player.getValue().getCoins());
                count++;


            } else {
                insertPlayer(player.getKey(), player.getValue().getWinners(), player.getValue().getCoins());
                count++;

            }

        }
        Main.getInstance().getSQLite().disconnect();
        Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fForam salvos §a" + count + "§f jogadores na database.");
        }


    public void updateTopWinners() {
        if (!Main.getInstance().getCache().getTopWinners().isEmpty()) {
            Main.getInstance().getCache().getTopWinners().clear();

        }

        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria` ORDER BY `winners` DESC LIMIT 10");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String playerName = rs.getString("player");
                Main.getInstance().getCache().getTopWinners().add(playerName);
            }

        } catch (SQLException e) {
            Main.getInstance().getSQLite().disconnect();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao buscar o top vitórias.");
            return;

        }

        Main.getInstance().getSQLite().disconnect();
        Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fTop vitórias atualizado com sucesso.");

    }

    public void updateTopCoins(){
        if(!Main.getInstance().getCache().getTopCoins().isEmpty()) {
            Main.getInstance().getCache().getTopCoins().clear();

        }
        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria` ORDER BY `coins` DESC LIMIT 10");
            ResultSet rs = stm.executeQuery();

            while (rs.next()){
                String playerName = rs.getString("player");
                Main.getInstance().getCache().getTopCoins().add(playerName);
            }

        } catch (SQLException e) {
            Main.getInstance().getSQLite().disconnect();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao buscar o top coins.");
            return;

        }

        Main.getInstance().getSQLite().disconnect();
        Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fTop coins atualizado com sucesso.");

    }

    private Connection connection() {
        return Main.getInstance().getSQLite().getConnection();

    }
}


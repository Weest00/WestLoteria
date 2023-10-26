package br.com.west.loteria.database;

import br.com.west.loteria.Main;
import br.com.west.loteria.objects.PlayerUtil;
import org.bukkit.Bukkit;
import java.sql.*;

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

    public boolean getPlayer(PlayerUtil playerUtil) {
        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria` WHERE `player` = ?");
            stm.setString(1, playerUtil.getName());
            ResultSet rs = stm.executeQuery();
            return rs.next();


        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao buscar o jogador §a" + playerUtil.getName() + " §fna database.");
            return false;

        }
    }

    public void loadPlayers() {
        int count = 0;
        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria`");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                PlayerUtil playerUtil = new PlayerUtil(rs.getString("player"), rs.getInt("winners"), rs.getDouble("coins"));
                Main.getInstance().getCache().getCachePlayer().put(rs.getString("player"), playerUtil);
                count++;
            }

            Main.getInstance().getSQLite().disconnect();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fForam carregados §a" + count + "§f jogadores da database.");

        } catch (SQLException e) {
            Main.getInstance().getSQLite().disconnect();
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao carregar jogadores da database.");

        }
    }

    public void insertPlayer(PlayerUtil playerUtil) {
        try {
            PreparedStatement stm = connection().prepareStatement("INSERT INTO `west_loteria` (`player`,`winners`,`coins`) VAlUES (?,?,?)");
            stm.setString(1, playerUtil.getName());
            stm.setInt(2, playerUtil.getWinners());
            stm.setDouble(3, playerUtil.getCoins());
            stm.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao inserir o jogador §a" + playerUtil.getName() + " §fna database.");

        }

    }

    public void updatePlayer(PlayerUtil playerUtil) {
        try {
            PreparedStatement stm = connection().prepareStatement("UPDATE `west_loteria` SET `winners` =? ,`coins` =? WHERE `player` =?");
            stm.setInt(1, playerUtil.getWinners());
            stm.setDouble(2, playerUtil.getCoins());
            stm.setString(3, playerUtil.getName());
            stm.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§a[SQLITE] §fErro ao atualizar o jogador §a" + playerUtil.getName() + " §fna database.");
        }

    }

    public void savePlayers() {
        int count = 0;
        for (PlayerUtil players : Main.getInstance().getCache().getCachePlayer().values()) {
            if (getPlayer(players)) {
                updatePlayer(players);
                count++;

            } else {
                insertPlayer(players);
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

    public void updateTopCoins() {
        if (!Main.getInstance().getCache().getTopCoins().isEmpty()) {
            Main.getInstance().getCache().getTopCoins().clear();
        }
        try {
            PreparedStatement stm = connection().prepareStatement("SELECT * FROM `west_loteria` ORDER BY `coins` DESC LIMIT 10");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
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


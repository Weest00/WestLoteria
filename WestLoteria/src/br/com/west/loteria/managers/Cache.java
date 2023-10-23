package br.com.west.loteria.managers;


import br.com.west.loteria.objects.PlayerUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {

    private HashMap<String, PlayerUtil> cachePlayer = new HashMap<>();

    private ArrayList<String> topWinners = new ArrayList<>();

    private ArrayList<String> topLastWinners = new ArrayList<>();

    private ArrayList<String> topCoins = new ArrayList<>();

    private ArrayList<String> chatLock = new ArrayList<>();

    public HashMap<String, PlayerUtil> getCachePlayer() {
        return cachePlayer;
    }

    public ArrayList<String> getTopWinners() {
        return topWinners;
    }

    public ArrayList<String> getTopLastWinners() {
        return topLastWinners;
    }

    public ArrayList<String> getTopCoins() {
        return topCoins;
    }

    public ArrayList<String> getChatLock() {
        return chatLock;
    }
}

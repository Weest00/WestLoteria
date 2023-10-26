package br.com.west.loteria.managers;


import br.com.west.loteria.objects.PlayerUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Cache {

    private HashMap<String, PlayerUtil> cachePlayer = new HashMap<>();

    private Set<String> topWinners = new HashSet<>();


    private Set<String> topCoins = new HashSet<>();

    private Set<String> chatLock = new HashSet<>();

    public HashMap<String, PlayerUtil> getCachePlayer() {
        return cachePlayer;
    }

    public Set<String> getTopWinners() {
        return topWinners;
    }


    public Set<String> getTopCoins() {
        return topCoins;
    }

    public Set<String> getChatLock() {
        return chatLock;
    }
}


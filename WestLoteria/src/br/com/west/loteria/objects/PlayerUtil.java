package br.com.west.loteria.objects;

public class PlayerUtil {

    private String name;
    private int winners;
    private double coins;


    public PlayerUtil(String name, int winners, double coins) {
        this.name = name;
        this.winners = winners;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

}

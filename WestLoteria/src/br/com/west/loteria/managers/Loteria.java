package br.com.west.loteria.managers;


import br.com.west.loteria.Main;

import java.util.Random;

public class Loteria {


    private boolean state;
    private int number;
    private double reward;


    public void openLoteria(){
        Random random = new Random();
        number = random.nextInt(100)+1;
        state = true;
        reward = Main.getInstance().getConfig().getDouble("price-reward");

    }

    public void closeLoteria(){
        state = false;
    }

    public boolean getState() {
        return state;
    }

    public int getNumber() {
        return number;
    }

    public double getReward() {
        return reward;
    }
}

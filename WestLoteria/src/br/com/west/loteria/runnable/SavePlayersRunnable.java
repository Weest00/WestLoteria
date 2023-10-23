package br.com.west.loteria.runnable;

import br.com.west.loteria.Main;
public class SavePlayersRunnable implements Runnable{

    @Override
    public void run() {
        Main.getInstance().getSqLiteMethods().savePlayers();
        Main.getInstance().getSqLiteMethods().updateTopWinners();
        Main.getInstance().getSqLiteMethods().updateTopCoins();

    }
}

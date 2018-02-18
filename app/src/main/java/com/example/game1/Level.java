package com.example.game1;

/**
 * Created by jyothsna on 15/2/18.
 */

public class Level {
    private int []colorCollected;
    private boolean isUnlocked;
    private int numStars;

    public Level() {
        colorCollected = new int[4];
        isUnlocked = false;
        numStars = 0;
    }

}

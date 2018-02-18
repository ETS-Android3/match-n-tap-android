package com.example.game1;

/**
 * Created by jyothsna on 15/2/18.
 */

public class Level {
    private int[] colorCollected;
    private boolean isUnlocked;
    private int numStars;
    private int level_num;

    public Level() {
        colorCollected = new int[4];
        isUnlocked = false;
        numStars = 0;
        level_num = 0;
    }

    public int[] getColorCollected() {
        return colorCollected;
    }

    public void setColorCollected(int[] colorCollected) {
        this.colorCollected = colorCollected;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }
}

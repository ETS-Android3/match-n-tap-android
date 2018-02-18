package com.example.game1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by jyothsna on 17/2/18.
 */

public class SoundManager {
    private SoundPool soundPool;
    //final int[] correctSounds;
    final int correctSound;
    final int errorSound;
    final MediaPlayer bgSound;

    public SoundManager(Context context) {
        int num_colors = Box.num_colors;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        correctSound = soundPool.load(context, R.raw.mi, 1);

/*        correctSounds = new int[num_colors];
        correctSounds[0] = soundPool.load(context, R.raw.dor, 1);
        correctSounds[1] = soundPool.load(context, R.raw.mi, 1);
        correctSounds[2] = soundPool.load(context, R.raw.so, 1);
        correctSounds[3] = soundPool.load(context, R.raw.fa, 1);*/

        errorSound = soundPool.load(context, R.raw.error, 1);
        bgSound = MediaPlayer.create(context, R.raw.bg);
    }

    public void playBackground() {
        bgSound.start();
    }

    public void playCorrect() {
        soundPool.play(correctSound, 1, 1, 1, 0, 1);
    }

    public void playError() {
        soundPool.play(errorSound, 1, 1, 1, 0, 1);
    }

    public void stopBackground() {
        bgSound.stop();
    }
}

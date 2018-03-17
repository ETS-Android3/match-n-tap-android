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
    final int buttonClick, correctSound, errorSound, levelPass, levelFail;
    final MediaPlayer bgSound;

    public SoundManager(Context context) {
        int num_colors = Box.num_colors;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        buttonClick = soundPool.load(context, R.raw.click, 1);

        correctSound = soundPool.load(context, R.raw.click, 1);

        errorSound = soundPool.load(context, R.raw.error, 1);

        bgSound = MediaPlayer.create(context, R.raw.bg_music);

        levelPass = soundPool.load(context, R.raw.level_pass, 1);
        levelFail = soundPool.load(context, R.raw.level_fail, 1);

    }

    public void playBackground() {
        if (bgSound != null) {
            bgSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaplayer) {
                    mediaplayer.stop();
                    mediaplayer.release();
                }
            });
            bgSound.start();
        }
    }

    public void playLevelPass() {
        soundPool.play(levelFail, 1, 1, 1, 0, 1);
    }

    public void playLevelFail() {
        soundPool.play(levelFail, 1, 1, 1, 0, 1);
    }

    public void playButtonClick() {
        soundPool.play(buttonClick, 1, 1, 1, 0, 1);
    }

    public void playCorrect() {
        soundPool.play(correctSound, 1, 1, 1, 0, 1);
    }

    public void playError() {
        soundPool.play(errorSound, 1, 1, 1, 0, 1);
    }

    public void stopBackground() {
        if (bgSound != null) bgSound.stop();
    }

    public void stopLevelPass() {
        soundPool.stop(levelPass);
    }

    public void stopLevelFail() {
        soundPool.stop(levelFail);
    }

    public void pauseBackground() {
        if (bgSound != null) bgSound.pause();
    }
}

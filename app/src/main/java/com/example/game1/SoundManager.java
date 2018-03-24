package com.example.game1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by jyothsna on 17/2/18.
 */

public class SoundManager {
    private SoundPool soundPool;
    //final int[] correctSounds;
    final int buttonClick, correctSound, errorSound, levelPass, levelFail;
    MediaPlayer bgSound;
    private Context context;

    public SoundManager(Context context) {
        this.context = context;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        buttonClick = soundPool.load(context, R.raw.click, 1);

        correctSound = soundPool.load(context, R.raw.click, 1);

        errorSound = soundPool.load(context, R.raw.error, 1);

        bgSound = MediaPlayer.create(context, R.raw.bg_music);
        bgSound.setLooping(true);
        bgSound.setVolume(0.2f,0.2f);

        levelPass = soundPool.load(context, R.raw.level_pass, 1);
        levelFail = soundPool.load(context, R.raw.level_fail, 1);

    }

    public void playBackground() {
        bgSound.start();
    }

    public void playLevelPass() {

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(levelPass, 1, 1, 1, 0, 1);
            }
        });
    }

    public void playLevelFail() {
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(levelFail, 1, 1, 1, 0, 1);
            }
        });
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
        bgSound.stop();
        try {
            bgSound.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopLevelPass() {
        soundPool.stop(levelPass);
    }

    public void stopLevelFail() {
        soundPool.stop(levelFail);
    }

    public void pauseBackground() {
        if(bgSound!=null && bgSound.isPlaying())
        bgSound.pause();
    }
}

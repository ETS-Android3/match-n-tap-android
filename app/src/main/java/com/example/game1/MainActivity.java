package com.example.game1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static LevelDbHandler levelDbHandler;
    public static Level[] levels = new Level[20];
    public static SoundManager soundManager;

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundManager = new SoundManager(this);

        levelDbHandler = new LevelDbHandler(this);
        for(int i=0;i<20;i++){
            levels[i] = new Level();
        }
        //hello world
    }

    public void startGame(View v){
        soundManager.playButtonClick();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void displayHighScores(View v){
        soundManager.playButtonClick();
        Intent intent = new Intent(this, HighScore.class);
        startActivity(intent);
    }

    public void showLevels(View v){
        soundManager.playButtonClick();
        Intent intent = new Intent(this, LevelsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        soundManager.playButtonClick();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}

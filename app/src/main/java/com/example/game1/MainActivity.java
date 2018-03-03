package com.example.game1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static LevelDbHandler levelDbHandler;
    public static Level[] levels = new Level[20];
    private int level_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        levelDbHandler = new LevelDbHandler(this);
        for(int i=0;i<20;i++){
            levels[i] = new Level();
        }
        level_count=levelDbHandler.getLevelsCount();
        //hello world
    }

    public void startGame(View v){
        if(level_count<20){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LevelsActivity.class);
            startActivity(intent);
        }
    }

    public void displayHighScores(View v){
        Intent intent = new Intent(this, HighScore.class);
        startActivity(intent);
    }

    public void showLevels(View v){
        Intent intent = new Intent(this, LevelsActivity.class);
        startActivity(intent);
    }
}

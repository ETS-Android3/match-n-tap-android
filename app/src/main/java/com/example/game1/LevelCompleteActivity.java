package com.example.game1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class LevelCompleteActivity extends AppCompatActivity {

    private static final String TAG = "LevelCompleteActivity";

    private int levelNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);

        levelNum = getIntent().getIntExtra("LevelNum", 1);
        ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        ImageButton replayButton = (ImageButton) findViewById(R.id.replayButton);
        if (MainActivity.levels[levelNum].getIsUnlocked() == true) {
            nextButton.setEnabled(true);
        }else{
            nextButton.setEnabled(false);
            nextButton.setAlpha(0.5f);
        }
    }

    public void playNextLevel(View v) {
        Log.d(TAG, "next level");
        if (MainActivity.levels[levelNum].getIsUnlocked() == true) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("level_num", levelNum + 1);
            startActivity(intent);
        }
    }

    public void playSameLevel(View v) {
        Log.d(TAG, "same level");
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level_num", levelNum);
        startActivity(intent);
    }
}

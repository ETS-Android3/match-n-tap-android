package com.example.game1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.game1.MainActivity.soundManager;

public class LevelCompleteActivity extends AppCompatActivity {

    private static final String TAG = "LevelCompleteActivity";

    private int levelNum, score, numStars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);

        levelNum = getIntent().getIntExtra("LevelNum", 1);
        score = getIntent().getIntExtra("Score", 1);
        numStars = getIntent().getIntExtra("NumStars", 1);

        TextView levelMsg = (TextView) findViewById(R.id.level_msg);
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        ImageView star1 = (ImageView) findViewById(R.id.star1);
        ImageView star2 = (ImageView) findViewById(R.id.star2);
        ImageView star3 = (ImageView) findViewById(R.id.star3);

        ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        ImageButton replayButton = (ImageButton) findViewById(R.id.replayButton);
        if (MainActivity.levels[levelNum].getIsUnlocked() == true) {
            nextButton.setEnabled(true);
            soundManager.playLevelPass();
            levelMsg.setText("Level passed");
        }else{
            soundManager.playLevelFail();
            nextButton.setEnabled(false);
            nextButton.setAlpha(0.5f);
            levelMsg.setText("Level failed"); //TODO : msgs
        }
        scoreText.setText(Integer.toString(score));

        //setting num stars
        if(numStars==0){
            star1.setImageResource(R.drawable.empty_star);
            star2.setImageResource(R.drawable.empty_star);
            star3.setImageResource(R.drawable.empty_star);
        }
        else if(numStars==1){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.empty_star);
            star3.setImageResource(R.drawable.empty_star);
        }
        else if(numStars==2){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.empty_star);
        }
        else if(numStars==3){
            star1.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
        }
    }

    public void playNextLevel(View v) {
        soundManager.playButtonClick();
        Log.d(TAG, "next level");
        if (MainActivity.levels[levelNum].getIsUnlocked() == true) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("level_num", levelNum + 1);
            startActivity(intent);
        }
    }

    public void playSameLevel(View v) {
        soundManager.playButtonClick();
        Log.d(TAG, "same level");
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level_num", levelNum);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        soundManager.stopLevelPass();
        soundManager.stopLevelFail();
        soundManager.playButtonClick();
        //Intent startMain = new Intent(LevelCompleteActivity.this, MainActivity.class);
        //startActivity(startMain);
        finish();
    }
}

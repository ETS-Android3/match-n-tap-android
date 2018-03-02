package com.example.game1;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelCompleteActivity extends AppCompatActivity {

    private static final String TAG = "LevelCompleteActivity";

    private int levelNum;
    private int numStars;
    private int scoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);

        levelNum = getIntent().getIntExtra("LevelNum", 1);
        numStars = getIntent().getIntExtra("NumStars",0);
        scoreValue = getIntent().getIntExtra("Score",0);

        ImageView star1 = (ImageView)findViewById(R.id.star1);
        ImageView star2 = (ImageView)findViewById(R.id.star2);
        ImageView star3 = (ImageView)findViewById(R.id.star3);
        TextView score = (TextView)findViewById(R.id.scoreText);

        //displaying score
        score.setText(scoreValue);

        //displaying stars based on score
        if(numStars<1)
            star1.setVisibility(View.INVISIBLE);
        if(numStars<2)
            star2.setVisibility(View.INVISIBLE);
        if(numStars<3)
            star3.setVisibility(View.INVISIBLE);

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

package com.example.game1;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class LevelsActivity extends AppCompatActivity {

    private static final String TAG = "LevelsActivity";
    private Level[] levels = new Level[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        for(int i=0;i<4;i++){
            levels[i] = new Level();
        }
        levels[1].setNumStars(2);
        levels[1].setUnlocked(true);
        LevelAdapter levelsAdapter = new LevelAdapter(this, levels);
        gridView.setAdapter(levelsAdapter);
    }
}

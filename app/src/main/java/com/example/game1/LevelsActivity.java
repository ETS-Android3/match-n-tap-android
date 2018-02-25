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
    public static Level[] levels = new Level[20];
    private int level_Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        for(int i=0;i<20;i++){
            levels[i] = new Level();
        }
        level_Count = MainActivity.levelDbHandler.getLevelsCount();
        for(int i=0;i<level_Count;i++){
            levels[i] = MainActivity.levelDbHandler.getLevel(i+1);
        }
        levels[level_Count].setUnlocked(true);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        LevelAdapter levelsAdapter = new LevelAdapter(this, levels, size.x, size.y);
        gridView.setAdapter(levelsAdapter);
    }
}

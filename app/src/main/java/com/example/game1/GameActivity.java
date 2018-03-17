package com.example.game1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import static com.example.game1.MainActivity.soundManager;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";
    private GameView gameView;
    private int level_num;
    private Point size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        level_num = getIntent().getIntExtra("level_num",0);
        gameView = new GameView(this, level_num,size.x, size.y);
        setContentView(gameView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        soundManager.playButtonClick();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        soundManager.playButtonClick();
                        GameView.stopMusic();
                        Intent startMain = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        soundManager.playButtonClick();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

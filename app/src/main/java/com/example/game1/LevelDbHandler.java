package com.example.game1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by USER on 2/21/2018.
 */

public class LevelDbHandler extends SQLiteOpenHelper{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;



    // Database Name
    private static final String DATABASE_NAME = "LevelDb";

    // Level table name
    private static final String TABLE_NAME = "LevelTable";

    //columns
    private static final String LEVEL_NUM = "LevelNum";
    private static final String NUM_OF_STARS = "NumOfStars";
    private static final String IS_UNLOCKED = "IsUnlocked";
    private static final String COLOR_1 = "Color1";
    private static final String COLOR_2 = "Color2";
    private static final String COLOR_3 = "Color3";
    private static final String COLOR_4 = "Color4";

    public LevelDbHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LEVELS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + LEVEL_NUM + " INTEGER PRIMARY KEY," + NUM_OF_STARS + " INTEGER,"
                + IS_UNLOCKED + " INTEGER," + COLOR_1 + " INTEGER," + COLOR_2 + " INTEGER,"
                + COLOR_3 + " INTEGER," + COLOR_4 + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_LEVELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new level
    public void addLevel(Level level) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LEVEL_NUM,level.getLevel_num());
        values.put(NUM_OF_STARS,level.getNumStars());
        values.put(IS_UNLOCKED,level.getIsUnlocked());
        values.put(COLOR_1,level.getColorCollected()[0]);
        values.put(COLOR_2,level.getColorCollected()[1]);
        values.put(COLOR_3,level.getColorCollected()[2]);
        values.put(COLOR_4,level.getColorCollected()[3]);

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    // Getting single level
    public Level getLevel(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columnNames = {LEVEL_NUM, NUM_OF_STARS, IS_UNLOCKED, COLOR_1, COLOR_2, COLOR_3, COLOR_4};
        String selection = LEVEL_NUM + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columnNames, selection, selectionArgs, null, null, null);
        if(cursor!=null){
           cursor.moveToFirst();
        }
        int[] colors = {Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(6))};

        boolean isUnlocked = Integer.parseInt(cursor.getString(1))==1?false:true;

        Level level = new Level(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(2)), isUnlocked, colors);
        db.close();
        return level;
    }

    // Getting levels Count
    public int getLevelsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int result = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return result;
    }

    // Updating single level
    public int updateLevel(Level level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LEVEL_NUM,level.getLevel_num());
        values.put(NUM_OF_STARS,level.getNumStars());
        values.put(IS_UNLOCKED,level.getIsUnlocked());
        values.put(COLOR_1,level.getColorCollected()[0]);
        values.put(COLOR_2,level.getColorCollected()[1]);
        values.put(COLOR_3,level.getColorCollected()[2]);
        values.put(COLOR_4,level.getColorCollected()[3]);

        int result = db.update(TABLE_NAME,values,LEVEL_NUM+"=?",new String[]{String.valueOf(level.getLevel_num())});
        db.close();
        return result;
    }

    // Deleting single level
    public void deleteLevel(Level level) {}
}

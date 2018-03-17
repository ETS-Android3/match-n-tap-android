package com.example.game1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.example.game1.MainActivity.soundManager;

/**
 * Created by jyothsna on 15/2/18.
 */

public class LevelAdapter extends BaseAdapter {

    public static final String TAG = "LevelAdapter";
    private final Context mContext;
    private final Level[] levels;
    private Bitmap lockDrawable, starDrawable, emptyStarDrawable;

    private int lockWidth, starWidth;

    public LevelAdapter(Context context, Level[] levels, int x, int y) {
        mContext = context;
        this.levels = levels;

        lockWidth = (x - 300) / 4;
        starWidth = (x - 180) / 8;

        Log.d(TAG, String.valueOf(lockWidth));
        Log.d(TAG, String.valueOf(starWidth));

        lockDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.lock);
        lockDrawable = Bitmap.createScaledBitmap(lockDrawable, lockWidth, lockWidth,false);

        starDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        starDrawable = Bitmap.createScaledBitmap(starDrawable, starWidth, starWidth, false);

        emptyStarDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_star);
        emptyStarDrawable = Bitmap.createScaledBitmap(emptyStarDrawable, starWidth, starWidth, false);

    }

    @Override
    public int getCount() {
        return levels.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Level level = levels[position];
        //if (convertView == null) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.layout_level, null);
        // }

        int colWidth = ((GridView) parent).getColumnWidth();
        final int level_number = position + 1;

        TableLayout theLayout = (TableLayout) convertView.findViewById(R.id.table_layout);

        if (level.getIsUnlocked() == false) {
            //TableRow tableRow = new TableRow(mContext);
            ImageView image = new ImageView(mContext);
            ViewGroup.LayoutParams vp = new ViewGroup.LayoutParams
                    (colWidth/2, colWidth/2);
            image.setLayoutParams(vp);
            image.setImageBitmap(lockDrawable);

            //image.setPadding(40,40,40,40);
            image.setAlpha(0.5f);
            //tableRow.addView(image);
            theLayout.addView(image);
        } else {
            int num_stars = level.getNumStars();

            TableRow tableRow1 = new TableRow(mContext);
            TableRow tableRow2 = new TableRow(mContext);

            TableRow.LayoutParams vp = new TableRow.LayoutParams
                    (colWidth / 2, colWidth / 2);

            ImageView star1 = new ImageView(mContext);
            ImageView star2 = new ImageView(mContext);
            ImageView star3 = new ImageView(mContext);
            star1.setLayoutParams(vp);
            star2.setLayoutParams(vp);
            star3.setLayoutParams(vp);

            if (num_stars == 0) {
                star1.setImageBitmap(emptyStarDrawable);
                star2.setImageBitmap(emptyStarDrawable);
                star3.setImageBitmap(emptyStarDrawable);
            } else if (num_stars == 1) {
                star1.setImageBitmap(starDrawable);
                star2.setImageBitmap(emptyStarDrawable);
                star3.setImageBitmap(emptyStarDrawable);
            } else if (num_stars == 2) {
                star1.setImageBitmap(starDrawable);
                star2.setImageBitmap(starDrawable);
                star3.setImageBitmap(emptyStarDrawable);
            } else if (num_stars == 3) {
                star1.setImageBitmap(starDrawable);
                star2.setImageBitmap(starDrawable);
                star3.setImageBitmap(starDrawable);
            }

            TextView text = new TextView(mContext);
            text.setText(String.valueOf(position + 1));
            text.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.text_size_small));
            text.setWidth(colWidth / 2);
            text.setHeight(colWidth / 2);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tableRow1.addView(star1);
            tableRow1.addView(star2);
            theLayout.addView(tableRow1);

            tableRow2.addView(star3);
            tableRow2.addView(text);
            theLayout.addView(tableRow2);

            theLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    soundManager.playButtonClick();
                    Intent intent = new Intent(mContext, GameActivity.class);
                    intent.putExtra("level_num", level_number);
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }
}
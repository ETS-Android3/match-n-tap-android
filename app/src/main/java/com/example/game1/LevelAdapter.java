package com.example.game1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by jyothsna on 15/2/18.
 */

public class LevelAdapter extends BaseAdapter {


    private final Context mContext;
    private final Level[] levels;
    private Bitmap lockDrawable, starDrawable;

    private int lockWidth, starWidth;

    public LevelAdapter(Context context, Level[] levels, int x, int y){
        mContext = context;
        this.levels = levels;

        lockWidth = (x-300)/4;
        starWidth = (x-180)/8;
        lockDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.lock);
        lockDrawable = Bitmap.createScaledBitmap(lockDrawable, lockWidth, lockWidth,false);

        starDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.star);
        starDrawable = Bitmap.createScaledBitmap(starDrawable,starWidth, starWidth,false);

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

        TableLayout theLayout = (TableLayout) convertView.findViewById(R.id.table_layout);

        if(level.isUnlocked()==false){
            //TableRow tableRow = new TableRow(mContext);
            ImageView image = new ImageView(mContext);
            ViewGroup.LayoutParams vp = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            image.setLayoutParams(vp);
            image.setImageBitmap(lockDrawable);
            image.setPadding(40,30,40,30);
            image.setAlpha(0.5f);
            //tableRow.addView(image);
            theLayout.addView(image);
        }else{
            int num_stars = level.getNumStars();

            TableRow tableRow = new TableRow(mContext);

            TableRow.LayoutParams vp = new TableRow.LayoutParams
                    (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            if(num_stars>=1) {
                ImageView star = new ImageView(mContext);
                star.setLayoutParams(vp);
                star.setImageBitmap(starDrawable);
                tableRow.addView(star);
            }
            if(num_stars>=2){
                ImageView star = new ImageView(mContext);
                star.setLayoutParams(vp);
                star.setImageBitmap(starDrawable);
                tableRow.addView(star);
            }
            theLayout.addView(tableRow);

            TableRow tableRow2 = new TableRow(mContext);

            TextView text = new TextView(mContext);
            text.setText(String.valueOf(position+1));
            text.setTextSize(40);
            text.setWidth(starWidth);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow2.addView(text);

            if(num_stars>=3){
                ImageView star = new ImageView(mContext);
                star.setLayoutParams(vp);
                star.setImageBitmap(starDrawable);
                tableRow2.addView(star);
            }

            theLayout.addView(tableRow2);


        }
        return convertView;
    }
}

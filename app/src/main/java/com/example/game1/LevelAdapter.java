package com.example.game1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by jyothsna on 15/2/18.
 */

public class LevelAdapter extends BaseAdapter {


    private final Context mContext;
    private final Level[] levels;

    public LevelAdapter(Context context,Level[] levels){
        mContext = context;
        this.levels = levels;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Level level = levels[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_level, null);
        }
        RelativeLayout theLayout = (RelativeLayout) convertView.findViewById(R.id.layout_level);
        if(level.isUnlocked()==false){
            ImageView image = new ImageView(mContext);
            RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            vp.width = 100;
            image.setLayoutParams(vp);
            image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.lock));
            theLayout.addView(image);
        }else{
            int num_stars = level.getNumStars();
            int width = ((GridView)parent).getColumnWidth()/2;
            RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(width,width);
            vp.width = 40;
            if(num_stars>=1) {
                ImageView star = new ImageView(mContext);
                vp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                vp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                star.setLayoutParams(vp);
                star.setImageDrawable(mContext.getResources().getDrawable(R.drawable.star));
                theLayout.addView(star);
            }
            if(num_stars>=2){
                ImageView star = new ImageView(mContext);
                vp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                vp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                star.setLayoutParams(vp);
                star.setImageDrawable(mContext.getResources().getDrawable(R.drawable.star));
                theLayout.addView(star);
            }
            if(num_stars>=3){
                ImageView star = new ImageView(mContext);
                vp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                vp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                star.setLayoutParams(vp);
                star.setImageDrawable(mContext.getResources().getDrawable(R.drawable.star));
                theLayout.addView(star);
            }
            TextView text = new TextView(mContext);
            vp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            vp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            text.setLayoutParams(vp);
            text.setText(""+level.getLevel_num());
            theLayout.addView(text);
        }
        /*final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate();*/
        return convertView;
    }
}

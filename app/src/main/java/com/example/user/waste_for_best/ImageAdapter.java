package com.example.user.waste_for_best;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private int[] images = {R.drawable.ic_launcher_background,R.drawable.camera,R.drawable.cat2,
                            R.drawable.tomcat,R.drawable.egg,R.drawable.jerrymouse};

    private String [] names = {"sonu","Raj", "Rakesh", "Raushan", "Swarnim","Samraat"};

    public ImageAdapter(Context mContext){

       this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.grid_item, parent, false);

            holder.grid_image = convertView.findViewById(R.id.grid_image);
            holder.grid_text = convertView.findViewById(R.id.grid_text);

            convertView.setTag(holder);

        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.grid_image.setImageResource(images[position]);

        holder.grid_text.setText(names[position]);

        return convertView;
    }

    static class ViewHolder{

        TextView grid_text;
        ImageView grid_image;

    }
}

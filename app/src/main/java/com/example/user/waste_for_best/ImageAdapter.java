package com.example.user.waste_for_best;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    public List<upload> mUpload;


    public ImageAdapter(Context mContext,List<upload> uploads){

       this.mContext = mContext;
       this.mUpload = uploads;

    }
    @Override
    public int getCount() {
        return mUpload.size();
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

        upload uploadCurrent = mUpload.get(position);

        holder.grid_text.setText("\u20B9 " + uploadCurrent.getName());

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.drawable.place_holder)
                .fit()
                .centerCrop()
                .into(holder.grid_image);

        return convertView;
    }

    static class ViewHolder{

        TextView grid_text;
        ImageView grid_image;

    }
}

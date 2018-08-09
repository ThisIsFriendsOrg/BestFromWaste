package com.example.user.waste_for_best;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter  extends ArrayAdapter{

    ArrayList<String> arrayList = new ArrayList();
    Context context;


    public CustomAdapter(@NonNull Context context, ArrayList arrayList) {
        super(context,R.layout.row_item);

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row_item,parent,false);

            holder.checkBox = convertView.findViewById(R.id.listCheckBox);
            holder.textView = convertView.findViewById(R.id.textList);

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        DataModel item = (DataModel) getItem(position);
        //Log.i("Item",item.toString());

        holder.textView.setText(item.name);
        holder.checkBox.setChecked(item.checked);


        return convertView;
    }

    static class ViewHolder{

        TextView textView;
        CheckBox checkBox;
    }
}

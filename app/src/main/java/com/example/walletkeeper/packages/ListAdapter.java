package com.example.walletkeeper.packages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.walletkeeper.R;

public class ListAdapter extends BaseAdapter {
    Context context;
    private final String [] Time;
    private final String [] Comments;
    private final String [] Money;
    private final int [] images;
    public ListAdapter(Context context, String [] Time, String [] Comments, String [] Money, int []
            images){
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.Time = Time;
        this.Comments = Comments;
        this.Money = Money;
        this.images=images;
    }
    @Override
    public int getCount() {
        return Comments.length;
    }
    @Override
    public Object getItem(int i) {
        return i;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull
            ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview, parent, false);
            viewHolder.txtTime = (TextView)
                    convertView.findViewById(R.id.aTime);
            viewHolder.txtComments = (TextView)
                    convertView.findViewById(R.id.aConments);
            viewHolder.Type = (ImageView) convertView.findViewById(R.id.aType);
            viewHolder.txtMoney = (TextView) convertView.findViewById(R.id.aMoney);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtTime.setText(Time[position]);
        viewHolder.txtComments.setText(Comments[position]);
        viewHolder.txtMoney.setText(Money[position]);
        viewHolder.Type.setImageResource(images[position]);
        return convertView;
    }
    private static class ViewHolder {
        TextView txtTime;
        TextView txtMoney;
        TextView txtComments;
        ImageView Type;
    }
}
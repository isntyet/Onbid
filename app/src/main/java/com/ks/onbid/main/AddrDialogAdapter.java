package com.ks.onbid.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ks.onbid.R;

import java.util.ArrayList;

/**
 * Created by jo on 2016-10-27.
 */

public class AddrDialogAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<String> list;

    public AddrDialogAdapter(Context context, ArrayList<String> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_dialog_list, parent, false);


            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();

        viewHolder.textView.setText(list.get(position));

        return view;
    }

    static class ViewHolder {
        TextView textView;
    }
}
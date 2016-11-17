package com.ks.onbid.community;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.ks.onbid.R;
import com.ks.onbid.login.GlobalApplication;
import com.ks.onbid.vo.CommunityItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jo on 2016-11-17.
 */


public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CommunityItem> items;
    int item_layout;

    public ImageLoader imageLoader = GlobalApplication.getGlobalApplicationContext().getImageLoader();

    public CommunityAdapter(Context context, ArrayList<CommunityItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final CommunityItem item = items.get(position);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

        ((ViewHolder) holder).cardview.setCardBackgroundColor(color);

        ((ViewHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, WebviewActivity.class);
                it.putExtra("url", item.getUrl());
                it.putExtra("name", item.getName());
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        });

        ((ViewHolder) holder).name.setText(item.getName());
        ((ViewHolder) holder).url.setText(item.getUrl());

    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        TextView name;
        TextView url;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            url = (TextView) itemView.findViewById(R.id.tv_url);

        }
    }
}

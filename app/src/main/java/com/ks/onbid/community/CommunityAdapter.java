package com.ks.onbid.community;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.freesoulapps.preview.android.Preview;
import com.ks.onbid.R;
import com.ks.onbid.login.GlobalApplication;
import com.ks.onbid.vo.CommunityItem;

import java.util.ArrayList;

/**
 * Created by jo on 2016-11-17.
 */


public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Preview.PreviewListener{
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

        ((ViewHolder) holder).preview.setData(item.getUrl());
        ((ViewHolder) holder).preview.setListener(this);
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Preview preview;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
            preview = (Preview) itemView.findViewById(R.id.preview);

        }
    }

    @Override
    public void onDataReady(Preview preview) {
        preview.setMessage(preview.getLink());
    }
}

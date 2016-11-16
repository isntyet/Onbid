package com.ks.onbid.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.ks.onbid.R;
import com.ks.onbid.login.GlobalApplication;
import com.ks.onbid.utill.CircularNetworkImageView;
import com.ks.onbid.utill.Preferences;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jo on 2016-11-16.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<ChatMessage> items;
    int item_layout;
    private int SELF = 100;
    private Preferences preferences;

    public ImageLoader imageLoader = GlobalApplication.getGlobalApplicationContext().getImageLoader();

    public ChatAdapter(Context context, ArrayList<ChatMessage> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        preferences = new Preferences(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == SELF) {
            // self message
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_self, parent, false);
        } else {
            // others message
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_other, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ChatMessage item = items.get(position);

        String info = "anony";
        Date date = SysUtill.strToDttm(item.getRegDate());


        if (item.getUserNm() != null) {
            info = item.getUserNm() + ", " + new SimpleDateFormat("MM월 dd일  HH:mm").format(date);
        }

        ((ViewHolder) holder).timestamp.setText(info);
        ((ViewHolder) holder).message.setText(item.getContent());

        ((ViewHolder) holder).image.setErrorImageResId(R.drawable.kakao_default_profile_image);
        if ((!"".equals(item.getUserThumnail())) && (!"default".equals(item.getUserThumnail())) && (item.getUserThumnail() != null)) {
            ((ViewHolder) holder).image.setImageUrl(item.getUserThumnail(), imageLoader);
        }

    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message, timestamp;
        CircularNetworkImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            image = (CircularNetworkImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage item = items.get(position);
        if (item.getUserId().equals(preferences.getKakaoId())) {
            return SELF;
        }

        return position;
    }
}
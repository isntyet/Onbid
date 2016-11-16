package com.ks.onbid.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.ks.onbid.R;
import com.ks.onbid.login.GlobalApplication;
import com.ks.onbid.utill.CircularNetworkImageView;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jo on 2016-11-15.
 */


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<Comment> items;
    int item_layout;

    public ImageLoader imageLoader = GlobalApplication.getGlobalApplicationContext().getImageLoader();

    public CommentAdapter(Context context, List<Comment> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment item = items.get(position);
        holder.userName.setText(item.getUserName());

        Date date = SysUtill.strToDttm(item.getRegDate());

        holder.date.setText(new SimpleDateFormat("yyyy.MM.dd.  HH:mm").format(date));
        holder.content.setText(item.getContent());

        holder.userThumnail.setErrorImageResId(R.drawable.kakao_default_profile_image);
        if ((!"".equals(item.getUserThumnail())) && (!"default".equals(item.getUserThumnail())) && (item.getUserThumnail() != null)) {
            holder.userThumnail.setImageUrl(item.getUserThumnail(), imageLoader);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem; //item 전체
        TextView userName; //유저 닉네임
        TextView date;
        CircularNetworkImageView userThumnail; //물건상태
        TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            userName = (TextView) itemView.findViewById(R.id.tv_user_name);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            userThumnail = (CircularNetworkImageView) itemView.findViewById(R.id.nv_thumnail);
            content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
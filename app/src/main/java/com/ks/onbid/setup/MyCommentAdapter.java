package com.ks.onbid.setup;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ks.onbid.R;
import com.ks.onbid.main.SaleDetailActivity;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jo on 2016-11-20.
 */


public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {
    Context context;
    List<Comment> items;
    int item_layout;

    public MyCommentAdapter(Context context, List<Comment> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mycomment_setup, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment item = items.get(position);

        Date date = SysUtill.strToDttm(item.getRegDate());
        holder.cltrMnmtNo.setText(item.getCltrMnmtNo());
        holder.date.setText(new SimpleDateFormat("yyyy.MM.dd.  HH:mm").format(date));
        holder.content.setText(item.getContent());

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, SaleDetailActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra("cltr_mnmt_no", item.getCltrMnmtNo());
                it.putExtra("category", item.getCategory());

                context.startActivity(it);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem; //item 전체
        TextView date;
        TextView cltrMnmtNo;
        TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.ll_item_mycomment_setup);
            date = (TextView) itemView.findViewById(R.id.tv_date_mycomment);
            cltrMnmtNo = (TextView) itemView.findViewById(R.id.tv_cltrMnmtNo_mycomment);
            content = (TextView) itemView.findViewById(R.id.tv_content_setup);
        }
    }

}
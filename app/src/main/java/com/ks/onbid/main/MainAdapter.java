package com.ks.onbid.main;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ks.onbid.R;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.SaleItem;
import com.lid.lib.LabelTextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jo on 2016-11-25.
 */


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private Context context;

    private ArrayList<SaleItem> itemList;

    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 2;
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public MainAdapter(Context context, OnLoadMoreListener onLoadMoreListener) {
        this.context = context;
        this.onLoadMoreListener = onLoadMoreListener;
        itemList = new ArrayList<>();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView) {
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

//                Log.d("total", totalItemCount + "");
//                Log.d("visible", visibleItemCount + "");
//                Log.d("first", firstVisibleItem + "");
//                Log.d("last", lastVisibleItem + "");

                if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_content, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
        }

    }

    public void addAll(List<SaleItem> lst) {
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<SaleItem> lst) {
        itemList.addAll(lst);
        notifyItemRangeChanged(0, itemList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainViewHolder) {

            final SaleItem item = itemList.get(position);
            ((MainViewHolder) holder).title.setText(item.getCLTR_NM());
            ((MainViewHolder) holder).title.setLabelText(item.getDPSL_MTD_NM());

            Date begn_dtm = SysUtill.strToDttm(item.getPBCT_BEGN_DTM());
            String str_begn_dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(begn_dtm);

            Date cls_dtm = SysUtill.strToDttm(item.getPBCT_CLS_DTM());
            String str_cls_dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cls_dtm);

            ((MainViewHolder) holder).pbctDtm.setText(str_begn_dtm + " ~ " + str_cls_dtm);
            //holder.minBidPrc.setText(item.getMIN_BID_PRC());

            NumberFormat nf = NumberFormat.getNumberInstance();
            ((MainViewHolder) holder).minBidPrc.setText(nf.format(SysUtill.strToInt(item.getMIN_BID_PRC())) + " 원");

            ((MainViewHolder) holder).uscbdCnt.setText(item.getUSCBD_CNT() + " 회");
            ((MainViewHolder) holder).pbctCltrStatNm.setText(item.getPBCT_CLTR_STAT_NM());
            ((MainViewHolder) holder).ctgrFullNm.setText(item.getCTGR_FULL_NM());

            ((MainViewHolder) holder).layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, item.getCLTR_NM(), Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(context, SaleDetailActivity.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    it.putExtra("sale_item", item);

                    context.startActivity(it);

                }
            });
        }
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    itemList.add(null);
                    notifyItemInserted(itemList.size() - 1);
                }
            });
        } else {
            itemList.remove(itemList.size() - 1);
            notifyItemRemoved(itemList.size());
        }
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        CardView layoutItem; //item 전체
        LabelTextView title; //물건이름, 처분방식(라벨)
        TextView pbctDtm; //입찰기간
        TextView minBidPrc; //최저입찰가
        TextView uscbdCnt; // 유찰횟수
        TextView pbctCltrStatNm; //물건상태
        TextView ctgrFullNm; //용도명

        public MainViewHolder(View v) {
            super(v);
            layoutItem = (CardView) itemView.findViewById(R.id.cv_item);
            title = (LabelTextView) itemView.findViewById(R.id.tv_title);
            pbctDtm = (TextView) itemView.findViewById(R.id.tv_pbct_dtm);
            minBidPrc = (TextView) itemView.findViewById(R.id.tv_min_bid_prc);
            uscbdCnt = (TextView) itemView.findViewById(R.id.tv_uscbd_cnt);
            pbctCltrStatNm = (TextView) itemView.findViewById(R.id.tv_dpsl_mtd_nm);
            ctgrFullNm = (TextView) itemView.findViewById(R.id.tv_ctgr_full_nm);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;

        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }
}
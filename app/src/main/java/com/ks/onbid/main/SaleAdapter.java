package com.ks.onbid.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ks.onbid.R;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.SaleItem;
import com.lid.lib.LabelTextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jo on 2016-10-25.
 */

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    Context context;
    List<SaleItem> items;
    int item_layout;

    public SaleAdapter(Context context, List<SaleItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SaleItem item = items.get(position);
        holder.title.setText(item.getCLTR_NM());
        holder.title.setLabelText(item.getDPSL_MTD_NM());

        Date begn_dtm = SysUtill.strToDttm(item.getPBCT_BEGN_DTM());
        String str_begn_dtm =  new SimpleDateFormat("yyyy-MM-dd HH:mm").format(begn_dtm);

        Date cls_dtm = SysUtill.strToDttm(item.getPBCT_CLS_DTM());
        String str_cls_dtm =  new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cls_dtm);

        holder.pbctDtm.setText(str_begn_dtm + " ~ " + str_cls_dtm);
        //holder.minBidPrc.setText(item.getMIN_BID_PRC());

        NumberFormat nf = NumberFormat.getNumberInstance();
        holder.minBidPrc.setText(nf.format(SysUtill.strToInt(item.getMIN_BID_PRC())) + " 원");

        holder.uscbdCnt.setText(item.getUSCBD_CNT() + " 회");
        holder.pbctCltrStatNm.setText(item.getPBCT_CLTR_STAT_NM());
        holder.ctgrFullNm.setText(item.getCTGR_FULL_NM());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LabelTextView title; //물건이름, 처분방식(라벨)
        TextView pbctDtm; //입찰기간
        TextView minBidPrc; //최저입찰가
        TextView uscbdCnt; // 유찰횟수
        TextView pbctCltrStatNm; //물건상태
        TextView ctgrFullNm; //용도명


        public ViewHolder(View itemView) {
            super(itemView);
            title = (LabelTextView) itemView.findViewById(R.id.tv_title);
            pbctDtm = (TextView) itemView.findViewById(R.id.tv_pbct_dtm);
            minBidPrc = (TextView) itemView.findViewById(R.id.tv_min_bid_prc);
            uscbdCnt = (TextView) itemView.findViewById(R.id.tv_uscbd_cnt);
            pbctCltrStatNm = (TextView) itemView.findViewById(R.id.tv_dpsl_mtd_nm);
            ctgrFullNm = (TextView) itemView.findViewById(R.id.tv_ctgr_full_nm);
        }
    }
}
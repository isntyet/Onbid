package com.ks.onbid.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ks.onbid.R;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.SaleItem;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jo on 2016-11-14.
 */
public class SaleDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SaleItem saleItem;

    private TextView tvTitle;

    private TextView tvPbctCltrStatNm;
    private TextView tvDate;
    private TextView tvMinBidPrc;
    private TextView tvCltrMnmtNo;
    private TextView tvCtgrFullNm;
    private TextView tvLdnmAdrs;
    private TextView tvNmrdAdrs;

    private TextView tvGoodsNm;

    private TextView tvPlnmNo;
    private TextView tvPbctNo;
    private TextView tvCltrNo;
    private TextView tvBidMnmtNo;
    private TextView tvDpslMtdNm;
    private TextView tvBidMtdNm;
    private TextView tvApslAsesAvgAmt;
    private TextView tvFeeRate;
    private TextView tvUscbdCnt;
    private TextView tvIqryCnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        saleItem = (SaleItem) getIntent().getParcelableExtra("sale_item");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(saleItem.getCLTR_NM());

        tvPbctCltrStatNm = (TextView) findViewById(R.id.tv_pbct_cltr_stat_nm);
        tvPbctCltrStatNm.setText(saleItem.getPBCT_CLTR_STAT_NM());

        Date begn_dtm = SysUtill.strToDttm(saleItem.getPBCT_BEGN_DTM());
        String str_begn_dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(begn_dtm);

        Date cls_dtm = SysUtill.strToDttm(saleItem.getPBCT_CLS_DTM());
        String str_cls_dtm = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cls_dtm);

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDate.setText(str_begn_dtm + " ~ " + str_cls_dtm);

        NumberFormat nf = NumberFormat.getNumberInstance();

        tvMinBidPrc = (TextView) findViewById(R.id.tv_min_bid_prc);
        tvMinBidPrc.setText(nf.format(SysUtill.strToInt(saleItem.getMIN_BID_PRC())) + " 원");

        tvCltrMnmtNo = (TextView) findViewById(R.id.tv_cltr_mnmt_no);
        tvCltrMnmtNo.setText(saleItem.getCLTR_MNMT_NO());

        tvCtgrFullNm = (TextView) findViewById(R.id.tv_ctgr_full_nm);
        tvCtgrFullNm.setText(saleItem.getCTGR_FULL_NM());

        tvLdnmAdrs = (TextView) findViewById(R.id.tv_ldnm_adrs);
        tvLdnmAdrs.setText(saleItem.getLDNM_ADRS());

        tvNmrdAdrs = (TextView) findViewById(R.id.tv_nmrd_adrs);
        tvNmrdAdrs.setText(saleItem.getNMRD_ADRS());

        tvGoodsNm = (TextView) findViewById(R.id.tv_goods_nm);
        tvGoodsNm.setText(saleItem.getGOODS_NM());

        tvPlnmNo = (TextView) findViewById(R.id.tv_plnm_no);
        tvPlnmNo.setText(saleItem.getPLNM_NO());

        tvPbctNo = (TextView) findViewById(R.id.tv_pbct_no);
        tvPbctNo.setText(saleItem.getPBCT_NO());

        tvCltrNo = (TextView) findViewById(R.id.tv_cltr_no);
        tvCltrNo.setText(saleItem.getCLTR_NO());

        tvBidMnmtNo = (TextView) findViewById(R.id.tv_bid_mnmt_no);
        tvBidMnmtNo.setText(saleItem.getBID_MNMT_NO());

        tvDpslMtdNm = (TextView) findViewById(R.id.tv_dpsl_mtd_nm);
        tvDpslMtdNm.setText(saleItem.getDPSL_MTD_NM());

        tvBidMtdNm = (TextView) findViewById(R.id.tv_bid_mtd_nm);
        tvBidMtdNm.setText(saleItem.getBID_MTD_NM());

        tvApslAsesAvgAmt = (TextView) findViewById(R.id.tv_apsl_ases_avg_amt);
        tvApslAsesAvgAmt.setText(nf.format(SysUtill.strToInt(saleItem.getAPSL_ASES_AVG_AMT())) + " 원");

        tvFeeRate = (TextView) findViewById(R.id.tv_fee_rate);
        tvFeeRate.setText(saleItem.getFEE_RATE());

        tvUscbdCnt = (TextView) findViewById(R.id.tv_uscbd_cnt);
        tvUscbdCnt.setText(saleItem.getUSCBD_CNT());

        tvIqryCnt = (TextView) findViewById(R.id.tv_iqry_cnt);
        tvIqryCnt.setText(saleItem.getIQRY_CNT());


    }
}

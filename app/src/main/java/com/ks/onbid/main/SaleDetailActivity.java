package com.ks.onbid.main;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ks.onbid.R;
import com.ks.onbid.utill.Preferences;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.Comment;
import com.ks.onbid.vo.SaleItem;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.ks.onbid.utill.SysUtill.getCurrentTime;

/**
 * Created by jo on 2016-11-14.
 */
public class SaleDetailActivity extends AppCompatActivity implements View.OnClickListener {

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

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Button btnComment;
    private RecyclerView rvCommentList;
    private LinearLayoutManager layoutManager;

    private ArrayList<Comment> commentList;

    private Preferences preferences;
    private Dialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        preferences = new Preferences(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        commentList = new ArrayList<Comment>();

        rvCommentList = (RecyclerView) findViewById(R.id.rv_comment_list);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvCommentList.setNestedScrollingEnabled(false);
        rvCommentList.setHasFixedSize(true);
        rvCommentList.setLayoutManager(layoutManager);

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

        btnComment = (Button) findViewById(R.id.btn_comment);
        btnComment.setOnClickListener(this);

        onCommentRequest();


    }

    private void loadCommentDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_dialog_comment);

        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);

        dialog.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etContent.length() == 0) {
                    Toast.makeText(SaleDetailActivity.this, "내용을 제대로 작성해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = getCurrentTime();
                    String cDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

                    Comment item = new Comment(saleItem.getPLNM_NO(), saleItem.getCLTR_MNMT_NO(), preferences.getKakaoNickname(), preferences.getKakaoThumbUrl(), preferences.getKakaoId(), etContent.getText().toString(), cDate);
                    databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("sale_comment").push().setValue(item);


                    Toast.makeText(SaleDetailActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                    onResume();

                }
            }
        });
        dialog.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onResume();
            }
        });

        dialog.show();
    }


    private void onCommentRequest() {

        databaseReference = firebaseDatabase.getReference().child("sale_comment");
        Query searchQuery = databaseReference.orderByChild("plnmNo").equalTo(saleItem.getPLNM_NO());

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                rvCommentList.removeAllViewsInLayout();
                layoutManager = new LinearLayoutManager(getApplicationContext());

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    //Log.d("key print", messageSnapshot.getKey());
                    Comment value = messageSnapshot.getValue(Comment.class);
                    value.setKey(messageSnapshot.getKey());
                    commentList.add(value);

                }
                rvCommentList.setAdapter(new CommentAdapter(getApplicationContext(), commentList, R.layout.activity_sale_detail));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        commentList.clear();
        rvCommentList.removeAllViewsInLayout();

        onCommentRequest();

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnComment.getId()) {
            loadCommentDialog();
        }
    }
}

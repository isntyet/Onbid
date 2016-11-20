package com.ks.onbid.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.ks.onbid.R;
import com.ks.onbid.chat.ChatActivity;
import com.ks.onbid.community.CommunityActivity;
import com.ks.onbid.request.AddrCodeFirstRequest;
import com.ks.onbid.request.AddrCodeSecondRequest;
import com.ks.onbid.request.AddrCodeThirdRequest;
import com.ks.onbid.request.SaleListApiRequest;
import com.ks.onbid.request.UseCodeBottomRequest;
import com.ks.onbid.request.UseCodeMiddleRequest;
import com.ks.onbid.request.UseCodeTopRequest;
import com.ks.onbid.setup.SetupActivity;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.SaleItem;
import com.ks.onbid.vo.UseCode;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private RecyclerView rvSaleList;
    private NestedScrollView nsMain;
    private LinearLayoutManager layoutManager;
    private CoordinatorLayout clMain;
    private BackPressCloseHandler backPressCloseHandler;

    //처분방식 버튼
    private Button[] btnDPSL;

    //처분방식 플래그 0 = 전체("")  1 = 매각("0001")  2 = 임대("0002")
    private int DPSL_FLAG = 0;

    //용도 버튼
    private Button[] btnUse;

    //용도 플래그
    private String useValue_1 = "";
    private String useValue_2 = "";
    private String useValue_3 = "";

    //주소 버튼
    private Button[] btnAddr;

    //주소 버튼 플래그
    private String addrValue_1 = "";
    private String addrValue_2 = "";
    private String addrValue_3 = "";

    //입찰기간 버튼
    private Button btnDateFrom;
    private Button btnDateTo;

    //1 = FROM  2 = TO
    private int DATE_FLAG = 1;
    private String dateFromValue = "";
    private String dateToValue = "";

    //감정가 입력
    private EditText etGoodsPriceFrom;
    private EditText etGoodsPriceTo;

    //최저입찰가 입력
    private EditText etOpenPriceFrom;
    private EditText etOpenPriceTo;

    //관리번호, 물건명 입력
    private EditText etGoodNo;
    private EditText etGoodName;

    //검색버튼
    private Button btnSearch;
    private InputMethodManager imm;

    //fab button
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    //fab menu
    private TextView btnSetup;
    private TextView btnResearch;
    private TextView btnCommunity;
    private TextView btnChat;


    private ArrayList<SaleItem> saleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        setInitUI();

        getSaleList();

        setupFab();
    }

    private void setupFab() {

        FabButton fab = (FabButton) findViewById(R.id.btn_menu);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.menu_btn_sheet_bg);
        int fabColor = getResources().getColor(R.color.colorAccent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.buttonColor));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        btnSetup = (TextView) findViewById(R.id.btn_setup);
        btnSetup.setOnClickListener(this);

        btnResearch = (TextView) findViewById(R.id.btn_research);
        btnResearch.setOnClickListener(this);

        btnCommunity = (TextView) findViewById(R.id.btn_community);
        btnCommunity.setOnClickListener(this);

        btnChat = (TextView) findViewById(R.id.btn_chat);
        btnChat.setOnClickListener(this);
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    private void getSaleList() {
        //saleList.clear();
        saleList = onlistRequest();
        rvSaleList.removeAllViewsInLayout();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSaleList.setHasFixedSize(true);
        rvSaleList.setLayoutManager(layoutManager);
        rvSaleList.setAdapter(new SaleAdapter(getApplicationContext(), saleList, R.layout.activity_main));
    }

    private void setInitUI() {
        clMain = (CoordinatorLayout) findViewById(R.id.cl_main);
        nsMain = (NestedScrollView) findViewById(R.id.ns_main);
        rvSaleList = (RecyclerView) findViewById(R.id.rv_sale_list);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSaleList.setNestedScrollingEnabled(false);
        rvSaleList.setHasFixedSize(true);
        rvSaleList.setLayoutManager(layoutManager);

        /////////////////////////////////처분방식 버튼 초기 세팅
        btnDPSL = new Button[3];

        btnDPSL[0] = (Button) findViewById(R.id.btn_dpsl_1);
        btnDPSL[1] = (Button) findViewById(R.id.btn_dpsl_2);
        btnDPSL[2] = (Button) findViewById(R.id.btn_dpsl_3);

        for (int i = 0; i < 3; i++) {
            btnDPSL[i].setOnClickListener(this);
        }
        btnDPSL[0].setBackgroundResource(R.drawable.round_btn_click);
        btnDPSL[1].setBackgroundResource(R.drawable.round_btn_unclick);
        btnDPSL[2].setBackgroundResource(R.drawable.round_btn_unclick);

        DPSL_FLAG = 0;

        ////////////////////////////////용도 버튼 초기 세팅
        btnUse = new Button[3];

        btnUse[0] = (Button) findViewById(R.id.btn_use_1);
        btnUse[1] = (Button) findViewById(R.id.btn_use_2);
        btnUse[2] = (Button) findViewById(R.id.btn_use_3);

        for (int i = 0; i < 3; i++) {
            btnUse[i].setOnClickListener(this);
        }

        btnUse[0].setBackgroundResource(R.drawable.round_press_btn_unclick);
        btnUse[1].setBackgroundResource(R.drawable.round_press_btn_unclick);
        btnUse[2].setBackgroundResource(R.drawable.round_press_btn_unclick);

        ////////////////////////////////주소 버튼 초기 세팅
        btnAddr = new Button[3];

        btnAddr[0] = (Button) findViewById(R.id.btn_addr_1);
        btnAddr[1] = (Button) findViewById(R.id.btn_addr_2);
        btnAddr[2] = (Button) findViewById(R.id.btn_addr_3);

        for (int i = 0; i < 3; i++) {
            btnAddr[i].setOnClickListener(this);
        }

        btnAddr[0].setBackgroundResource(R.drawable.round_press_btn_unclick);
        btnAddr[1].setBackgroundResource(R.drawable.round_press_btn_unclick);
        btnAddr[2].setBackgroundResource(R.drawable.round_press_btn_unclick);

        ////////////////////////////////입찰기간 버튼 초기 세팅

        btnDateFrom = (Button) findViewById(R.id.btn_date_from);
        btnDateFrom.setOnClickListener(this);

        btnDateTo = (Button) findViewById(R.id.btn_date_to);
        btnDateTo.setOnClickListener(this);

        btnDateFrom.setBackgroundResource(R.drawable.round_press_btn_unclick);
        btnDateTo.setBackgroundResource(R.drawable.round_press_btn_click);

        Date date = SysUtill.getCurrentTime();
        dateToValue = new SimpleDateFormat("yyyyMMdd").format(date);
        btnDateTo.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));


        ////////////////////////////////감정가 초기 세팅

        etGoodsPriceFrom = (EditText) findViewById(R.id.et_goods_price_from);
        etGoodsPriceTo = (EditText) findViewById(R.id.et_goods_price_to);

        ////////////////////////////////최저입찰가 초기 세팅

        etOpenPriceFrom = (EditText) findViewById(R.id.et_open_price_from);
        etOpenPriceTo = (EditText) findViewById(R.id.et_open_price_to);

        ////////////////////////////////물건번호, 물건명 세팅

        etGoodNo = (EditText) findViewById(R.id.et_good_no);
        etGoodName = (EditText) findViewById(R.id.et_good_name);

        //////////////////////////////////검색버튼 세팅
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }


    //용도 코드 1뎁스
    private ArrayList<UseCode> onUseCodeTopRequest() {
        UseCodeTopRequest request = new UseCodeTopRequest(this);
        request.setParams();
        return request.startRequest();
    }

    //용도 코드 2뎁스
    private ArrayList<UseCode> onUseCodeMiddleRequest(String topCdoe) {
        UseCodeMiddleRequest request = new UseCodeMiddleRequest(this);
        request.setParams(topCdoe);
        return request.startRequest();
    }

    //용도 코드 3뎁스
    private ArrayList<UseCode> onUseCodeBottomRequest(String middleCode) {
        UseCodeBottomRequest request = new UseCodeBottomRequest(this);
        request.setParams(middleCode);
        return request.startRequest();
    }

    //주소 코드 1뎁스
    private ArrayList<String> onAddrCodeFirstRequest() {
        AddrCodeFirstRequest request = new AddrCodeFirstRequest(this);
        request.setParams();
        return request.startRequest();
    }

    //주소 코드 2뎁스
    private ArrayList<String> onAddrCodeSecondRequest(String addr1) {
        AddrCodeSecondRequest request = new AddrCodeSecondRequest(this);
        request.setParams(addr1);
        return request.startRequest();
    }

    //주소 코드 3뎁스
    private ArrayList<String> onAddrCodeThirdRequest(String addr2) {
        AddrCodeThirdRequest request = new AddrCodeThirdRequest(this);
        request.setParams(addr2);
        return request.startRequest();
    }

    @Override
    public void onClick(View v) {
        if ((v.getId() == btnDPSL[0].getId()) || (v.getId() == btnDPSL[1].getId()) || (v.getId() == btnDPSL[2].getId())) {
            setDpslButton(v);
        } else if (v.getId() == btnUse[0].getId()) {
            ArrayList<UseCode> list = onUseCodeTopRequest();
            loadUseDialog(list, btnUse[0]);
        } else if ((v.getId() == btnUse[1].getId()) && (!"".equals(useValue_1))) {
            ArrayList<UseCode> list = onUseCodeMiddleRequest(useValue_1);
            loadUseDialog(list, btnUse[1]);
        } else if ((v.getId() == btnUse[2].getId()) && (!"".equals(useValue_2))) {
            ArrayList<UseCode> list = onUseCodeBottomRequest(useValue_2);
            loadUseDialog(list, btnUse[2]);
        } else if (v.getId() == btnAddr[0].getId()) {
            ArrayList<String> list = onAddrCodeFirstRequest();
            loadAddrDialog(list, btnAddr[0]);
        } else if ((v.getId() == btnAddr[1].getId()) && (!"".equals(addrValue_1))) {
            ArrayList<String> list = onAddrCodeSecondRequest(addrValue_1);
            loadAddrDialog(list, btnAddr[1]);
        } else if ((v.getId() == btnAddr[2].getId()) && (!"".equals(addrValue_2))) {
            ArrayList<String> list = onAddrCodeThirdRequest(addrValue_2);
            loadAddrDialog(list, btnAddr[2]);
        } else if ((v.getId() == btnDateFrom.getId())) {
            btnDateFrom.setBackgroundResource(R.drawable.round_press_btn_unclick);
            btnDateFrom.setText("-");
            dateFromValue = "";
            DATE_FLAG = 1;
            loadCanlenderDialog();
        } else if ((v.getId() == btnDateTo.getId())) {
            btnDateTo.setBackgroundResource(R.drawable.round_press_btn_unclick);
            btnDateTo.setText("-");
            dateToValue = "";
            DATE_FLAG = 2;
            loadCanlenderDialog();
        } else if (v.getId() == btnSearch.getId()) {
            getSaleList();
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else if (v.getId() == btnSetup.getId()) {
            startActivity(new Intent(this, SetupActivity.class));
        } else if (v.getId() == btnCommunity.getId()) {
            startActivity(new Intent(this, CommunityActivity.class));
        } else if(v.getId() == btnChat.getId()){
            startActivity(new Intent(this, ChatActivity.class));
        } else if (v.getId() == btnResearch.getId()) {

            materialSheetFab.hideSheet();
            //nsMain.fullScroll(View.FOCUS_BACKWARD);
            nsMain.smoothScrollTo(0, 0);
        }
    }

    private void loadCanlenderDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void setDpslButton(View v) {
        for (int i = 0; i < 3; i++) {
            if (btnDPSL[i].getId() == v.getId()) {
                btnDPSL[i].setBackgroundResource(R.drawable.round_btn_click);
                DPSL_FLAG = i;
            } else {
                btnDPSL[i].setBackgroundResource(R.drawable.round_btn_unclick);
            }
        }
    }

    private void loadUseDialog(final ArrayList<UseCode> list, final Button btn) {
        UseDialogAdapter adapter = new UseDialogAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if ("전체".equals(list.get(position).getCTGR_NM())) {
                            btn.setBackgroundResource(R.drawable.round_press_btn_unclick);
                        } else {
                            btn.setBackgroundResource(R.drawable.round_press_btn_click);
                        }

                        btn.setText(list.get(position).getCTGR_NM());

                        if (btn.getId() == btnUse[0].getId()) {
                            btnUse[1].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnUse[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnUse[1].setText("전체");
                            btnUse[2].setText("전체");
                            useValue_1 = list.get(position).getCTGR_ID();
                            useValue_2 = "";
                            useValue_3 = "";
                        } else if (btn.getId() == btnUse[1].getId()) {
                            btnUse[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnUse[2].setText("전체");
                            useValue_2 = list.get(position).getCTGR_ID();
                            useValue_3 = "";
                        } else if (btn.getId() == btnUse[2].getId()) {
                            useValue_3 = list.get(position).getCTGR_ID();
                        }
                        Log.d("use_data_1", useValue_1);
                        Log.d("use_data_2", useValue_2);
                        Log.d("use_data_3", useValue_3);
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setMargin(150, 400, 150, 20)
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }

    private void loadAddrDialog(final ArrayList<String> list, final Button btn) {
        AddrDialogAdapter adapter = new AddrDialogAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if ("전체".equals(list.get(position))) {
                            btn.setBackgroundResource(R.drawable.round_press_btn_unclick);
                        } else {
                            btn.setBackgroundResource(R.drawable.round_press_btn_click);
                        }

                        btn.setText(list.get(position));

                        if (btn.getId() == btnAddr[0].getId()) {
                            btnAddr[1].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnAddr[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnAddr[1].setText("전체");
                            btnAddr[2].setText("전체");
                            addrValue_1 = list.get(position);
                            if ("전체".equals(list.get(position))) {
                                addrValue_1 = "";
                            }
                            addrValue_2 = "";
                            addrValue_3 = "";
                        } else if (btn.getId() == btnAddr[1].getId()) {
                            btnAddr[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnAddr[2].setText("전체");
                            addrValue_2 = list.get(position);
                            if ("전체".equals(list.get(position))) {
                                addrValue_2 = "";
                            }
                            addrValue_3 = "";
                        } else if (btn.getId() == btnAddr[2].getId()) {
                            addrValue_3 = list.get(position);
                            if ("전체".equals(list.get(position))) {
                                addrValue_3 = "";
                            }
                        }
                        Log.d("addr_data_1", addrValue_1);
                        Log.d("addr_data_2", addrValue_2);
                        Log.d("addr_data_3", addrValue_3);
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setMargin(150, 400, 150, 20)
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String strMonth = "";
        String strDay = "";

        monthOfYear++;

        strMonth = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;
        strDay = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;

        String date = "" + year + strMonth + strDay;

        if (DATE_FLAG == 1) {
            btnDateFrom.setText(year + "-" + strMonth + "-" + strDay);
            btnDateFrom.setBackgroundResource(R.drawable.round_press_btn_click);
            dateFromValue = date;
        } else if (DATE_FLAG == 2) {
            btnDateTo.setText(year + "-" + strMonth + "-" + strDay);
            btnDateTo.setBackgroundResource(R.drawable.round_press_btn_click);
            dateToValue = date;
        }

        Log.d("choice_date", date);
        Toast.makeText(this, date, Toast.LENGTH_LONG).show();
    }


    private ArrayList<SaleItem> onlistRequest() {
        SaleListApiRequest request = new SaleListApiRequest(this);

        String DPSLValue = "";
        if (DPSL_FLAG == 1) {
            DPSLValue = "0001";
        }
        if (DPSL_FLAG == 2) {
            DPSLValue = "0002";
        }

        String goodsPriceFrom = "" + etGoodsPriceFrom.getText().toString();
        String goodsPriceTo = "" + etGoodsPriceTo.getText().toString();

        String openPriceFrom = "" + etOpenPriceFrom.getText().toString();
        String openPriceTo = "" + etOpenPriceTo.getText().toString();

        String goodName = "" + etGoodName.getText().toString();
        String goodNo = "" + etGoodNo.getText().toString();


        request.setParams(DPSLValue, useValue_1, useValue_2, addrValue_1, addrValue_2, addrValue_3, goodsPriceFrom, goodsPriceTo, openPriceFrom, openPriceTo, goodName, dateFromValue, dateToValue, goodNo, "1");
        return request.startRequest();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}

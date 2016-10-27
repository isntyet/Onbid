package com.ks.onbid.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.ks.onbid.R;
import com.ks.onbid.request.AddrCodeFirstRequest;
import com.ks.onbid.request.AddrCodeSecondRequest;
import com.ks.onbid.request.AddrCodeThirdRequest;
import com.ks.onbid.request.UseCodeBottomRequest;
import com.ks.onbid.request.UseCodeMiddleRequest;
import com.ks.onbid.request.UseCodeTopRequest;
import com.ks.onbid.utill.SysUtill;
import com.ks.onbid.vo.UseCode;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private RecyclerView rvSaleList;

    //처분방식 버튼
    private Button[] btnDPSL;

    //처분방식 플래그 0 = 전체("")  1 = 매각("0001")  2 = 임대("0002")
    private int DPSL_FLAG = 0;

    //용도 버튼
    private Button[] btnUse;

    //용도 플래그
    private String useValue_1 ="";
    private String useValue_2 ="";
    private String useValue_3 ="";

    //주소 버튼
    private Button[] btnAddr;

    //주소 버튼 플래그
    private String addrValue_1 ="";
    private String addrValue_2 ="";
    private String addrValue_3 ="";

    //입찰기간 버튼
    private Button btnDateFrom;
    private Button btnDateTo;

    private String dateFromValue = "";
    private String dateToValue = "";


    private ArrayList<String> saleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setInitUI();


        rvSaleList = (RecyclerView) findViewById(R.id.rv_sale_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSaleList.setHasFixedSize(true);
        rvSaleList.setLayoutManager(layoutManager);

        saleList = onAddrCodeThirdRequest("부산진구");

        rvSaleList.setAdapter(new SaleAdapter(getApplicationContext(),saleList, R.layout.activity_main));

    }

    private void setInitUI(){
        /////////////////////////////////처분방식 버튼 초기 세팅
        btnDPSL = new Button[3];

        btnDPSL[0] = (Button) findViewById(R.id.btn_dpsl_1);
        btnDPSL[1] = (Button) findViewById(R.id.btn_dpsl_2);
        btnDPSL[2] = (Button) findViewById(R.id.btn_dpsl_3);

        for(int i = 0; i < 3; i++){
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

        for(int i = 0; i < 3; i++){
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

        for(int i = 0; i < 3; i++){
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
        btnDateTo.setBackgroundResource(R.drawable.round_press_btn_unclick);
    }


    //용도 코드 1뎁스
    private ArrayList<UseCode> onUseCodeTopRequest(){
        UseCodeTopRequest request = new UseCodeTopRequest(this);
        request.setParams();
        return request.startRequest();
    }

    //용도 코드 2뎁스
    private ArrayList<UseCode> onUseCodeMiddleRequest(String topCdoe){
        UseCodeMiddleRequest request = new UseCodeMiddleRequest(this);
        request.setParams(topCdoe);
        return request.startRequest();
    }

    //용도 코드 3뎁스
    private ArrayList<UseCode> onUseCodeBottomRequest(String middleCode){
        UseCodeBottomRequest request = new UseCodeBottomRequest(this);
        request.setParams(middleCode);
        return request.startRequest();
    }

    //주소 코드 1뎁스
    private ArrayList<String> onAddrCodeFirstRequest(){
        AddrCodeFirstRequest request = new AddrCodeFirstRequest(this);
        request.setParams();
        return request.startRequest();
    }

    //주소 코드 2뎁스
    private ArrayList<String> onAddrCodeSecondRequest(String addr1){
        AddrCodeSecondRequest request = new AddrCodeSecondRequest(this);
        request.setParams(addr1);
        return request.startRequest();
    }

    //주소 코드 3뎁스
    private ArrayList<String> onAddrCodeThirdRequest(String addr2){
        AddrCodeThirdRequest request = new AddrCodeThirdRequest(this);
        request.setParams(addr2);
        return request.startRequest();
    }

    @Override
    public void onClick(View v) {
        if((v.getId() == btnDPSL[0].getId()) || (v.getId() == btnDPSL[1].getId()) || (v.getId() == btnDPSL[2].getId())){
            setDpslButton(v);
        } else if(v.getId() == btnUse[0].getId()){
            ArrayList<UseCode> list = onUseCodeTopRequest();
            loadUseDialog(list, btnUse[0]);
        } else if((v.getId() == btnUse[1].getId()) && (!"".equals(useValue_1))){
            ArrayList<UseCode> list = onUseCodeMiddleRequest(useValue_1);
            loadUseDialog(list, btnUse[1]);
        } else if((v.getId() == btnUse[2].getId()) && (!"".equals(useValue_2))){
            ArrayList<UseCode> list = onUseCodeBottomRequest(useValue_2);
            loadUseDialog(list, btnUse[2]);
        } else if(v.getId() == btnAddr[0].getId()){
            ArrayList<String> list = onAddrCodeFirstRequest();
            loadAddrDialog(list, btnAddr[0]);
        } else if((v.getId() == btnAddr[1].getId()) && (!"".equals(addrValue_1))){
            ArrayList<String> list = onAddrCodeSecondRequest(addrValue_1);
            loadAddrDialog(list, btnAddr[1]);
        } else if((v.getId() == btnAddr[2].getId()) && (!"".equals(addrValue_2))){
            ArrayList<String> list = onAddrCodeThirdRequest(addrValue_2);
            loadAddrDialog(list, btnAddr[2]);
        } else if(((v.getId() == btnDateFrom.getId()) || (v.getId() == btnDateTo.getId())) && ("".equals(dateFromValue)) && ("".equals(dateToValue))){
            loadCanlenderDialog();
        } else if(((v.getId() == btnDateFrom.getId()) || (v.getId() == btnDateTo.getId())) && (!"".equals(dateFromValue)) && (!"".equals(dateToValue))){
            btnDateFrom.setBackgroundResource(R.drawable.round_press_btn_unclick);
            btnDateTo.setBackgroundResource(R.drawable.round_press_btn_unclick);
            btnDateFrom.setText("전체");
            btnDateTo.setText("전체");
            dateFromValue = "";
            dateToValue = "";

            loadCanlenderDialog();
        }
    }

    private void loadCanlenderDialog(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAutoHighlight(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void setDpslButton(View v){
        for(int i = 0; i < 3; i++){
            if(btnDPSL[i].getId() == v.getId()){
                btnDPSL[i].setBackgroundResource(R.drawable.round_btn_click);
                DPSL_FLAG = i;
            } else {
                btnDPSL[i].setBackgroundResource(R.drawable.round_btn_unclick);
            }
        }
    }

    private void loadUseDialog(final ArrayList<UseCode> list, final Button btn){
        UseDialogAdapter adapter = new UseDialogAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if("전체".equals(list.get(position).getCTGR_NM())){
                            btn.setBackgroundResource(R.drawable.round_press_btn_unclick);
                        } else {
                            btn.setBackgroundResource(R.drawable.round_press_btn_click);
                        }

                        btn.setText(list.get(position).getCTGR_NM());

                        if(btn.getId() == btnUse[0].getId()){
                            btnUse[1].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnUse[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnUse[1].setText("전체");
                            btnUse[2].setText("전체");
                            useValue_1 = list.get(position).getCTGR_ID();
                            useValue_2 = "";
                            useValue_3 = "";
                        } else if(btn.getId() == btnUse[1].getId()){
                            btnUse[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnUse[2].setText("전체");
                            useValue_2 = list.get(position).getCTGR_ID();
                            useValue_3 = "";
                        } else if(btn.getId() == btnUse[2].getId()){
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

    private void loadAddrDialog(final ArrayList<String> list, final Button btn){
        AddrDialogAdapter adapter = new AddrDialogAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if("전체".equals(list.get(position))){
                            btn.setBackgroundResource(R.drawable.round_press_btn_unclick);
                        } else {
                            btn.setBackgroundResource(R.drawable.round_press_btn_click);
                        }

                        btn.setText(list.get(position));

                        if(btn.getId() == btnAddr[0].getId()){
                            btnAddr[1].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnAddr[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnAddr[1].setText("전체");
                            btnAddr[2].setText("전체");
                            addrValue_1 = list.get(position);
                            if("전체".equals(list.get(position))){
                                addrValue_1 = "";
                            }
                            addrValue_2 = "";
                            addrValue_3 = "";
                        } else if(btn.getId() == btnAddr[1].getId()){
                            btnAddr[2].setBackgroundResource(R.drawable.round_press_btn_unclick);
                            btnAddr[2].setText("전체");
                            addrValue_2 = list.get(position);
                            if("전체".equals(list.get(position))){
                                addrValue_2 = "";
                            }
                            addrValue_3 = "";
                        } else if(btn.getId() == btnAddr[2].getId()){
                            addrValue_3 = list.get(position);
                            if("전체".equals(list.get(position))){
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String strMonth = "";
        String strMonthEnd = "";
        String strDay = "";
        String strDayEnd = "";

        monthOfYear++;
        monthOfYearEnd++;

        strMonth = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;
        strMonthEnd = monthOfYearEnd < 10 ? "0" + monthOfYearEnd : "" + monthOfYearEnd;
        strDay = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        strDayEnd = dayOfMonthEnd < 10 ? "0" + dayOfMonthEnd : "" + dayOfMonthEnd;

        String dateFrom = "" + year + strMonth + strDay;
        String dateTo = "" + yearEnd + strMonthEnd + strDayEnd;

        btnDateFrom.setBackgroundResource(R.drawable.round_press_btn_click);
        btnDateFrom.setText(year + "-" + strMonth + "-" + strDay);
        dateFromValue = dateFrom;
        btnDateTo.setBackgroundResource(R.drawable.round_press_btn_click);
        btnDateTo.setText(yearEnd + "-" + strMonthEnd + "-" + strDayEnd);
        dateToValue = dateTo;

        if(SysUtill.strToInt(dateFrom) > SysUtill.strToInt(dateTo)){
            btnDateFrom.setBackgroundResource(R.drawable.round_press_btn_unclick);
            btnDateTo.setBackgroundResource(R.drawable.round_press_btn_unclick);
            btnDateFrom.setText("전체");
            btnDateTo.setText("전체");
            dateFromValue = "";
            dateToValue = "";

            Toast.makeText(this, "시작기간이 끝기간보다 나중이면 안됩니다.", Toast.LENGTH_LONG).show();
        } else {
            Log.d("choice_date", dateFrom + " ~ " + dateTo);
            Toast.makeText(this, dateFrom + " ~ " + dateTo, Toast.LENGTH_LONG).show();
        }
    }
/*
    private String listRequest(){
        SaleListApiRequest request = new SaleListApiRequest(this);
        request.setParams("0001", "10000", "10100", "부산광역시", "부산진구", "연지동", "", "", "", "", "", "", "", "", "1");
        return request.startRequest();
    }*/
}

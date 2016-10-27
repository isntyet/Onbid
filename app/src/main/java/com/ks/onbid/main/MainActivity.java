package com.ks.onbid.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.ks.onbid.R;
import com.ks.onbid.request.AddrCodeFirstRequest;
import com.ks.onbid.request.AddrCodeSecondRequest;
import com.ks.onbid.request.AddrCodeThirdRequest;
import com.ks.onbid.request.UseCodeBottomRequest;
import com.ks.onbid.request.UseCodeMiddleRequest;
import com.ks.onbid.request.UseCodeTopRequest;
import com.ks.onbid.vo.UseCode;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView rvSaleList;

    //123523352
    //13242323423463
    //aaabbbccc
    //처분방식 버튼
    private Button[] btnDPSL;

    //처분방식 플래그 0 = 전체("")  1 = 매각("1000")  2 = 임대("2000")
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
        }
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
/*
    private String listRequest(){
        SaleListApiRequest request = new SaleListApiRequest(this);
        request.setParams("0001", "10000", "10100", "부산광역시", "부산진구", "연지동", "", "", "", "", "", "", "", "", "1");
        return request.startRequest();
    }*/
}

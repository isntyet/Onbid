package com.ks.onbid.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ks.onbid.R;
import com.ks.onbid.request.AddrCodeFirstRequest;
import com.ks.onbid.request.AddrCodeSecondRequest;
import com.ks.onbid.request.AddrCodeThirdRequest;
import com.ks.onbid.request.UseCodeBottomRequest;
import com.ks.onbid.request.UseCodeMiddleRequest;
import com.ks.onbid.request.UseCodeTopRequest;
import com.ks.onbid.vo.UseCodeTop;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvSaleList;
    private ArrayList<String> saleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSaleList = (RecyclerView) findViewById(R.id.rv_sale_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSaleList.setHasFixedSize(true);
        rvSaleList.setLayoutManager(layoutManager);

        saleList = onAddrCodeThirdRequest("부산진구");

        rvSaleList.setAdapter(new SaleAdapter(getApplicationContext(),saleList, R.layout.activity_main));

    }


    //용도 코드 1뎁스
    private ArrayList<UseCodeTop> onUseCodeTopRequest(){
        UseCodeTopRequest request = new UseCodeTopRequest(this);
        request.setParams();
        return request.startRequest();
    }

    //용도 코드 2뎁스
    private ArrayList<UseCodeTop> onUseCodeMiddleRequest(String topCdoe){
        UseCodeMiddleRequest request = new UseCodeMiddleRequest(this);
        request.setParams(topCdoe);
        return request.startRequest();
    }

    //용도 코드 3뎁스
    private ArrayList<UseCodeTop> onUseCodeBottomRequest(String middleCode){
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
/*
    private String listRequest(){
        SaleListApiRequest request = new SaleListApiRequest(this);
        request.setParams("0001", "10000", "10100", "부산광역시", "부산진구", "연지동", "", "", "", "", "", "", "", "", "1");
        return request.startRequest();
    }*/
}

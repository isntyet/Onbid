package com.ks.onbid.community;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ks.onbid.R;
import com.ks.onbid.vo.CommunityItem;

import java.util.ArrayList;

/**
 * Created by jo on 2016-11-05.
 */
public class CommunityActivity extends AppCompatActivity {

    private RecyclerView rvCommunityList;
    private CommunityAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ArrayList<CommunityItem> communityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        rvCommunityList = (RecyclerView) findViewById(R.id.rv_community_list);

        communityList = new ArrayList<CommunityItem>();
        makeData();

        layoutManager = new LinearLayoutManager(this);
        rvCommunityList.setLayoutManager(layoutManager);
        rvCommunityList.setItemAnimator(new DefaultItemAnimator());

        adapter = new CommunityAdapter(getApplicationContext(), communityList, R.layout.activity_community);

        rvCommunityList.setAdapter(adapter);
    }

    private void makeData(){
        communityList.add(new CommunityItem("온비드","http://m.onbid.co.kr/mbw/mobileWebMain.do"));
        communityList.add(new CommunityItem("캠코-한국자산관리공사","http://m.kamco.or.kr/"));
        communityList.add(new CommunityItem("네이버 부동산 경매","http://land.naver.com/auction/"));
        communityList.add(new CommunityItem("경매연구소 사랑","http://cafe.naver.com/dgkmresearch"));
        communityList.add(new CommunityItem("경매/공매를 알면 돈이보인다","http://m.cafe.naver.com/psyrich"));
        communityList.add(new CommunityItem("부자마을 이야기","http://m.cafe.naver.com/richvillagestory"));
        communityList.add(new CommunityItem("행복 재태크","http://m.cafe.daum.net/happy-tech/QdvU/855?q=%B0%F8%B8%C5"));
        communityList.add(new CommunityItem("천안 경/공매 정보 카페","http://m.cafe.naver.com/5692533"));
        communityList.add(new CommunityItem("중앙 옥션","http://m.cafe.naver.com/mbcdramafallinlove2"));
        communityList.add(new CommunityItem("랜드프로 - 경매투자강좌","http://www.landpro.co.kr"));

    }

}

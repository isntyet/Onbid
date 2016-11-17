package com.ks.onbid.vo;

/**
 * Created by jo on 2016-11-17.
 */

public class CommunityItem {

    private String name;
    private String url;

    public CommunityItem(){

    }

    public CommunityItem(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

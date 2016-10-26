package com.ks.onbid.vo;

/**
 * Created by jo on 2016-10-22.
 */
public class UseCode {

    private String RNUM;
    private String CTGR_ID;
    private String CTGR_NM;

    public UseCode(){

    }

    public UseCode(String rnum, String ctgr_id, String ctgr_nm){
        RNUM = rnum;
        CTGR_ID = ctgr_id;
        CTGR_NM = ctgr_nm;
    }

    public String getRNUM() {
        return RNUM;
    }

    public void setRNUM(String RNUM) {
        this.RNUM = RNUM;
    }

    public String getCTGR_ID() {
        return CTGR_ID;
    }

    public void setCTGR_ID(String CTGR_ID) {
        this.CTGR_ID = CTGR_ID;
    }

    public String getCTGR_NM() {
        return CTGR_NM;
    }

    public void setCTGR_NM(String CTGR_NM) {
        this.CTGR_NM = CTGR_NM;
    }




}

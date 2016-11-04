package com.ks.onbid.vo;

/**
 * Created by jo on 2016-10-25.
 */
public class SaleItem {

    //리스트 번호
    private String RNUM;
    //공고번호
    private String PLNM_NO;
    //공매번호
    private String PBCT_NO;
    //물건번호
    private String CLTR_NO;
    //용도명
    private String CTGR_FULL_NM;
    //입찰번호
    private String BID_MNMT_NO;
    //물건명
    private String CLTR_NM;
    //물건관리번호
    private String CLTR_MNMT_NO;
    //물건소재지(지번)
    private String LDNM_ADRS;
    //물건소재지(도로명)
    private String NMRD_ADRS;
    //처분방식코드
    private String DPSL_MTD_CD;
    //처분방식코드명
    private String DPSL_MTD_NM;
    //입찰방식명
    private String BID_MTD_NM;
    //최저입찰가
    private String MIN_BID_PRC;
    //감정가
    private String APSL_ASES_AVG_AMT;
    //최저입찰가율
    private String FEE_RATE;
    //입찰시작일시
    private String PBCT_BEGN_DTM;
    //입찰마감일시
    private String PBCT_CLS_DTM;
    //물건상태
    private String PBCT_CLTR_STAT_NM;
    //유찰횟수
    private String USCBD_CNT;
    //조회수
    private String IQRY_CNT;
    //물건상세정보
    private String GOODS_NM;

    public String getRNUM() {
        return RNUM;
    }

    public void setRNUM(String RNUM) {
        this.RNUM = RNUM;
    }

    public String getPLNM_NO() {
        return PLNM_NO;
    }

    public void setPLNM_NO(String PLNM_NO) {
        this.PLNM_NO = PLNM_NO;
    }

    public String getPBCT_NO() {
        return PBCT_NO;
    }

    public void setPBCT_NO(String PBCT_NO) {
        this.PBCT_NO = PBCT_NO;
    }

    public String getCLTR_NO() {
        return CLTR_NO;
    }

    public void setCLTR_NO(String CLTR_NO) {
        this.CLTR_NO = CLTR_NO;
    }

    public String getCTGR_FULL_NM() {
        return CTGR_FULL_NM;
    }

    public void setCTGR_FULL_NM(String CTGR_FULL_NM) {
        this.CTGR_FULL_NM = CTGR_FULL_NM;
    }

    public String getBID_MNMT_NO() {
        return BID_MNMT_NO;
    }

    public void setBID_MNMT_NO(String BID_MNMT_NO) {
        this.BID_MNMT_NO = BID_MNMT_NO;
    }

    public String getCLTR_NM() {
        return CLTR_NM;
    }

    public void setCLTR_NM(String CLTR_NM) {
        this.CLTR_NM = CLTR_NM;
    }

    public String getCLTR_MNMT_NO() {
        return CLTR_MNMT_NO;
    }

    public void setCLTR_MNMT_NO(String CLTR_MNMT_NO) {
        this.CLTR_MNMT_NO = CLTR_MNMT_NO;
    }

    public String getLDNM_ADRS() {
        return LDNM_ADRS;
    }

    public void setLDNM_ADRS(String LDNM_ADRS) {
        this.LDNM_ADRS = LDNM_ADRS;
    }

    public String getNMRD_ADRS() {
        return NMRD_ADRS;
    }

    public void setNMRD_ADRS(String NMRD_ADRS) {
        this.NMRD_ADRS = NMRD_ADRS;
    }

    public String getDPSL_MTD_CD() {
        return DPSL_MTD_CD;
    }

    public void setDPSL_MTD_CD(String DPSL_MTD_CD) {
        this.DPSL_MTD_CD = DPSL_MTD_CD;
    }

    public String getDPSL_MTD_NM() {
        return DPSL_MTD_NM;
    }

    public void setDPSL_MTD_NM(String DPSL_MTD_NM) {
        this.DPSL_MTD_NM = DPSL_MTD_NM;
    }

    public String getBID_MTD_NM() {
        return BID_MTD_NM;
    }

    public void setBID_MTD_NM(String BID_MTD_NM) {
        this.BID_MTD_NM = BID_MTD_NM;
    }

    public String getMIN_BID_PRC() {
        return MIN_BID_PRC;
    }

    public void setMIN_BID_PRC(String MIN_BID_PRC) {
        this.MIN_BID_PRC = MIN_BID_PRC;
    }

    public String getAPSL_ASES_AVG_AMT() {
        return APSL_ASES_AVG_AMT;
    }

    public void setAPSL_ASES_AVG_AMT(String APSL_ASES_AVG_AMT) {
        this.APSL_ASES_AVG_AMT = APSL_ASES_AVG_AMT;
    }

    public String getFEE_RATE() {
        return FEE_RATE;
    }

    public void setFEE_RATE(String FEE_RATE) {
        this.FEE_RATE = FEE_RATE;
    }

    public String getPBCT_BEGN_DTM() {
        return PBCT_BEGN_DTM;
    }

    public void setPBCT_BEGN_DTM(String PBCT_BEGN_DTM) {
        this.PBCT_BEGN_DTM = PBCT_BEGN_DTM;
    }

    public String getPBCT_CLS_DTM() {
        return PBCT_CLS_DTM;
    }

    public void setPBCT_CLS_DTM(String PBCT_CLS_DTM) {
        this.PBCT_CLS_DTM = PBCT_CLS_DTM;
    }

    public String getPBCT_CLTR_STAT_NM() {
        return PBCT_CLTR_STAT_NM;
    }

    public void setPBCT_CLTR_STAT_NM(String PBCT_CLTR_STAT_NM) {
        this.PBCT_CLTR_STAT_NM = PBCT_CLTR_STAT_NM;
    }

    public String getUSCBD_CNT() {
        return USCBD_CNT;
    }

    public void setUSCBD_CNT(String USCBD_CNT) {
        this.USCBD_CNT = USCBD_CNT;
    }

    public String getIQRY_CNT() {
        return IQRY_CNT;
    }

    public void setIQRY_CNT(String IQRY_CNT) {
        this.IQRY_CNT = IQRY_CNT;
    }

    public String getGOODS_NM() {
        return GOODS_NM;
    }

    public void setGOODS_NM(String GOODS_NM) {
        this.GOODS_NM = GOODS_NM;
    }
}

package com.ks.onbid.request;

import android.content.Context;
import android.util.Log;

import com.ks.onbid.utill.ApiRequest;
import com.ks.onbid.utill.KeyData;
import com.ks.onbid.vo.SaleItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jo on 2016-10-21.
 */

public class SaleListApiRequest extends ApiRequest {
    public SaleListApiRequest(Context context) {
        super(context);
    }

    public void setParams(String dpsl_mtd_cd, String ctgr_hirk_id, String ctgr_hirk_id_mid, String sido, String sgk, String emd, String goods_price_from, String goods_price_to, String open_price_from, String open_price_to, String cltr_nm, String pbct_begn_dtm, String pbct_cls_dtm, String cltr_mnmt_no, String page_no) {
        super.clearParams();
        super.setParam("ServiceKey", KeyData.API_KEY);
        super.setParam("DPSL_MTD_CD", dpsl_mtd_cd);
        super.setParam("CTGR_HIRK_ID", ctgr_hirk_id);
        super.setParam("CTGR_HIRK_ID_MID", ctgr_hirk_id_mid);
        super.setParam("SIDO", sido);
        super.setParam("SGK", sgk);
        super.setParam("EMD", emd);
        super.setParam("GOODS_PRICE_FROM", goods_price_from);
        super.setParam("GOODS_PRICE_TO", goods_price_to);
        super.setParam("OPEN_PRICE_FROM", open_price_from);
        super.setParam("OPEN_PRICE_TO", open_price_to);
        super.setParam("CLTR_NM", cltr_nm);
        super.setParam("PBCT_BEGN_DTM", pbct_begn_dtm);
        super.setParam("PBCT_CLS_DTM", pbct_cls_dtm);
        super.setParam("CLTR_MNMT_NO", cltr_mnmt_no);
        super.setParam("numOfRows", "10");
        super.setParam("pageNo", page_no);
    }

    public ArrayList<SaleItem> startRequest() {
        setUrlName("/KamcoPblsalThingInquireSvc/getKamcoSaleList");
        return xmlData(request());
    }

    public ArrayList<SaleItem> xmlData(XmlPullParser parser){

        ArrayList<SaleItem> list = new ArrayList<>();

        try {
            int eventType = parser.getEventType();

            SaleItem data = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:

                        String startTag = parser.getName();

                        if ("item".equals(startTag)) {
                            data = new SaleItem();
                        } else if ("RNUM".equals(startTag)) {
                            data.setRNUM(parser.nextText());
                        } else if ("PLNM_NO".equals(startTag)) {
                            data.setPLNM_NO(parser.nextText());
                        } else if ("PBCT_NO".equals(startTag)) {
                            data.setPBCT_NO(parser.nextText());
                        } else if ("CLTR_NO".equals(startTag)) {
                            data.setCLTR_NO(parser.nextText());
                        } else if ("CTGR_FULL_NM".equals(startTag)) {
                            data.setCTGR_FULL_NM(parser.nextText());
                        } else if ("BID_MNMT_NO".equals(startTag)) {
                            data.setBID_MNMT_NO(parser.nextText());
                        } else if ("CLTR_NM".equals(startTag)) {
                            data.setCLTR_NM(parser.nextText());
                        } else if ("CLTR_MNMT_NO".equals(startTag)) {
                            data.setCLTR_MNMT_NO(parser.nextText());
                        } else if ("LDNM_ADRS".equals(startTag)) {
                            data.setLDNM_ADRS(parser.nextText());
                        } else if ("NMRD_ADRS".equals(startTag)) {
                            data.setNMRD_ADRS(parser.nextText());
                        } else if ("DPSL_MTD_CD".equals(startTag)) {
                            data.setDPSL_MTD_CD(parser.nextText());
                        } else if ("DPSL_MTD_NM".equals(startTag)) {
                            data.setDPSL_MTD_NM(parser.nextText());
                        } else if ("BID_MTD_NM".equals(startTag)) {
                            data.setBID_MTD_NM(parser.nextText());
                        } else if ("MIN_BID_PRC".equals(startTag)) {
                            data.setMIN_BID_PRC(parser.nextText());
                        } else if ("APSL_ASES_AVG_AMT".equals(startTag)) {
                            data.setAPSL_ASES_AVG_AMT(parser.nextText());
                        } else if ("FEE_RATE".equals(startTag)) {
                            data.setFEE_RATE(parser.nextText());
                        } else if ("PBCT_BEGN_DTM".equals(startTag)) {
                            data.setPBCT_BEGN_DTM(parser.nextText());
                        } else if ("PBCT_CLS_DTM".equals(startTag)) {
                            data.setPBCT_CLS_DTM(parser.nextText());
                        } else if ("PBCT_CLTR_STAT_NM".equals(startTag)) {
                            data.setPBCT_CLTR_STAT_NM(parser.nextText());
                        } else if ("USCBD_CNT".equals(startTag)) {
                            data.setUSCBD_CNT(parser.nextText());
                        } else if ("IQRY_CNT".equals(startTag)) {
                            data.setIQRY_CNT(parser.nextText());
                        } else if ("GOODS_NM".equals(startTag)) {
                            data.setGOODS_NM(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if ("item".equals(endTag)) {
                            list.add(data);
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("SaleList_CNT", list.size() +"");


        return list;

    }
}
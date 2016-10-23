package com.ks.onbid.request;

import android.content.Context;

import com.ks.onbid.utill.ApiRequest;
import com.ks.onbid.utill.KeyData;

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
        super.setParam("numOfRows", "999");
        super.setParam("pageNo", page_no);
    }

    public String startRequest() {
        setUrlName("/getKamcoSaleList");
        return jsondata(request().toString());
    }

    public String jsondata(String data){

        String resData = "";

        try{
            //JSONObject jsonobj = new JSONObject(data);

            //resData = jsonobj.getString("result");

        }catch (Exception e){
            //Log.d("json error ===", e.getMessage());
        }

        return data;
    }
}
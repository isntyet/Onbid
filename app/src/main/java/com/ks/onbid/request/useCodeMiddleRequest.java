package com.ks.onbid.request;

import android.content.Context;
import android.util.Log;

import com.ks.onbid.utill.ApiRequest;
import com.ks.onbid.utill.KeyData;
import com.ks.onbid.vo.UseCode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jo on 2016-10-22.
 */


public class UseCodeMiddleRequest extends ApiRequest {
    public UseCodeMiddleRequest(Context context) {
        super(context);
    }

    public void setParams(String ctgr_id) {
        super.clearParams();
        super.setParam("ServiceKey", KeyData.API_KEY);
        super.setParam("CTGR_ID", ctgr_id);
        super.setParam("numOfRows", "999");
        super.setParam("pageNo", "1");
    }

    public ArrayList<UseCode> startRequest() {
        setUrlName("/OnbidCodeInfoInquireSvc/getOnbidMiddleCodeInfo");
        return xmlData(request());
    }

    public ArrayList<UseCode> xmlData(XmlPullParser parser){

        ArrayList<UseCode> list = new ArrayList<>();

        try {
            int eventType = parser.getEventType();

            UseCode data = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:

                        String startTag = parser.getName();

                        if ("item".equals(startTag)) {
                            data = new UseCode();
                        } else if ("RNUM".equals(startTag)) {
                            data.setRNUM(parser.nextText());
                        } else if ("CTGR_ID".equals(startTag)) {
                            data.setCTGR_ID(parser.nextText());
                        } else if ("CTGR_NM".equals(startTag)) {
                            data.setCTGR_NM(parser.nextText());
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

            UseCode all = new UseCode("0", "", "전체");
            list.add(0, all);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("UseCodeMiddle_CNT", list.size() +"");

        return list;

    }
}
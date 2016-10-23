package com.ks.onbid.request;

import android.content.Context;
import android.util.Log;

import com.ks.onbid.utill.ApiRequest;
import com.ks.onbid.utill.KeyData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jo on 2016-10-23.
 */

public class AddrCodeSecondRequest extends ApiRequest {
    public AddrCodeSecondRequest(Context context) {
        super(context);
    }

    public void setParams(String addr) {
        super.clearParams();
        super.setParam("ServiceKey", KeyData.API_KEY);
        super.setParam("ADDR1", addr);
        super.setParam("numOfRows", "999");
        super.setParam("pageNo", "1");
    }

    public ArrayList<String> startRequest() {
        setUrlName("/OnbidCodeInfoInquireSvc/getOnbidAddr2Info");
        return xmlData(request());
    }

    public ArrayList<String> xmlData(XmlPullParser parser){

        ArrayList<String> list = new ArrayList<>();

        try {
            int eventType = parser.getEventType();

            String data = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:

                        String startTag = parser.getName();

                        if ("item".equals(startTag)) {
                            data = new String();
                        } else if ("ADDR2".equals(startTag)) {
                            data = parser.nextText();
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

        Log.d("AddrCodeSecond_CNT", list.size() +"");

        return list;

    }
}
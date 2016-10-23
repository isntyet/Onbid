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

public class AddrCodeThirdRequest extends ApiRequest {
    public AddrCodeThirdRequest(Context context) {
        super(context);
    }

    public void setParams(String addr) {
        super.clearParams();
        super.setParam("ServiceKey", KeyData.API_KEY);
        super.setParam("ADDR2", addr);
        super.setParam("numOfRows", "999");
        super.setParam("pageNo", "1");
    }

    public ArrayList<String> startRequest() {
        setUrlName("/OnbidCodeInfoInquireSvc/getOnbidAddr3Info");
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
                        } else if ("ADDR3".equals(startTag)) {
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

        Log.d("AddrCodeThird_CNT", list.size() +"");

        return list;

    }
}
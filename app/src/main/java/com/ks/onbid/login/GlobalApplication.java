package com.ks.onbid.login;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.kakao.auth.KakaoSDK;

/**
 * Created by pc on 2016-10-23.
 */
public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());

    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }

    /*
     singleton 애플리케이션 객체를 얻는다.
     @return singleton 애플리케이션 객체
     */

    public static GlobalApplication getGlobalApplicationContext() {
        if (instance == null) {
            throw new IllegalArgumentException("this application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    /*
     어플리케이션 종료시 singleton 어플리케이션 객체 초기화 함.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}

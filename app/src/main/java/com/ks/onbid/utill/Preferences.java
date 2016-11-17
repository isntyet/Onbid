package com.ks.onbid.utill;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pc on 2016-11-04.
 */

public class Preferences {

    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.preferences = context.getSharedPreferences("KsHelperData", context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public boolean getBoolean(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.preferences.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.commit();
    }

    public String getString(String key) {
        return this.preferences.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return this.preferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        this.editor.putString(key, value);
        this.editor.commit();
    }

    public int getInt(String key) {
        return this.preferences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return this.preferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        this.editor.putInt(key, value);
        this.editor.commit();
    }
    /////////////////////////////////////////////////////////

    public boolean isPushIdInsert() {
        return this.getBoolean("PUSH_STATE_INSERT", false);
    }

    public void setPushIdInsert(boolean value) {
        this.putBoolean("PUSH_STATE_INSERT", value);
    }

    public String getMainWebUrl() {
        return this.getString("MAIN_WEB_URL", "https://m.facebook.com/profile.php?id=1415423212062917");
    }

    public void setMainWebUrl(String value) {
        this.putString("MAIN_WEB_URL", value);
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    public String getKakaoId() {
        return this.getString("KAKAO_ID", "");
    }

    public void setKakaoId(String value) {
        this.putString("KAKAO_ID", value);
    }

    public String getKakaoNickname() {
        return this.getString("KAKAO_NICKNAME", "");
    }

    public void setKakaoNickname(String value) {
        this.putString("KAKAO_NICKNAME", value);
    }

    public String getKakaoProfileUrl() {
        return this.getString("KAKAO_PROFILE_URL", "");
    }

    public void setKakaoProfileUrl(String value) {
        this.putString("KAKAO_PROFILE_URL", value);
    }

    public String getKakaoThumbUrl() {
        return this.getString("KAKAO_THUMB_URL", "");
    }

    public void setKakaoThumbUrl(String value) {
        this.putString("KAKAO_THUMB_URL", value);
    }
}

//////////////////////////////////////////////////////////////////////////////////////////
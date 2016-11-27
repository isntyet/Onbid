package com.ks.onbid.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.ks.onbid.R;
import com.ks.onbid.main.MainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pc on 2016-10-23.
 */

public class KakaoLoginActivity extends Activity {
    private SessionCallBack callback;      //콜백 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kakaologin);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    this.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.i("TAG", android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP));
                } catch (NoSuchAlgorithmException e) {
                    Log.w("TAG", "Unable to get MessageDigest. signature=" + signature, e);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        callback = new SessionCallBack();
        Session.getCurrentSession().addCallback(callback);
        // 이 두개의 함수 중요함


        if (!Session.getCurrentSession().isClosed()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.i("TAG", "중간접속");
            finish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.i("TAG", "requesteCode: " + requestCode + "resultCode: " + resultCode + "data: " + data);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallBack implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.i("TAG", "Session opened");
            //if (!true == Session.getCurrentSession().isClosed())

            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_kakaologin); //세션 연결 실패시 로그인화면 다시 불러옴
        }
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}

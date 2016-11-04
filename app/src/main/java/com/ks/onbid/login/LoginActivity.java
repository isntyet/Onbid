package com.ks.onbid.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;
import com.ks.onbid.R;
import com.ks.onbid.utill.Preferences;

/**
 * Created by pc on 2016-10-24.
 */

public class LoginActivity extends AppCompatActivity {
    public Preferences sp;
    ImageView userProfile_iv;
    TextView userId_tv, userNickname_tv, id;
    String userId, userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = new Preferences(this);
        Log.i("TAG", "onCreate");

        userId_tv = (TextView) findViewById(R.id.userId);
        userNickname_tv = (TextView) findViewById(R.id.userNickName);
        id = (TextView) findViewById(R.id.userProfilePath);
        userProfile_iv = (ImageView) findViewById(R.id.userProfile);
        Button logout_btn = (Button) findViewById(R.id.kakaologout_btn);
        Button disconnect_btn = (Button) findViewById(R.id.disconnect_btn);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG", "onStart");

        userId = sp.getKakaoId();
        userId_tv.setText(userId);

        userNickname = sp.getKakaoNickname();
        userNickname_tv.setText(userNickname);

        // 발리 라이브러리 써서 imageview 구현
        //userProfile_iv.set


        Log.i("TAG", "SetImageFromSharedPreferences");

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kakaologout_btn:
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        redirectLoginActivity();
                    }
                });
                break;
            case R.id.disconnect_btn:
                onClickUnlink();
        }
    }

    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                        redirectSignupActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        redirectLoginActivity();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, KakaoLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    protected void redirectSignupActivity() {       //세션 연결 성공 시 SignupActivity로 넘김
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}



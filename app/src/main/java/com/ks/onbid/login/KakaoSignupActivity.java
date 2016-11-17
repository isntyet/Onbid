package com.ks.onbid.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;
import com.ks.onbid.main.MainActivity;
import com.ks.onbid.utill.Preferences;

public class KakaoSignupActivity extends AppCompatActivity {
    public Preferences sp;
    String profileUrl, userId, userNickname, thumbProfile;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = new Preferences(this);
        KakaorequestMe();
    }

    protected void KakaorequestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());

                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();

                } else {
                    Log.d("TAG", "오류로 카카오 로그인 실패");
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG", "Session Closed" + errorResult);
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                Log.i("TAG", "Not Signed up");
                //카카오톡 회원이 아닐시 signup 호출
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.i("TAG", "OnSuccess");
                thumbProfile = userProfile.getThumbnailImagePath();
                profileUrl = userProfile.getProfileImagePath();
                userId = String.valueOf(userProfile.getId());
                userNickname = userProfile.getNickname();
                Logger.d("UserProfile : " + userProfile.toString());
                sp.setKakaoProfileUrl(profileUrl);
                sp.setKakaoId(userId);
                sp.setKakaoNickname(userNickname);
                sp.setKakaoThumbUrl(thumbProfile);

                redirectMainActivity();
            }
        });
    }

    private void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, KakaoLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}

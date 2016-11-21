package com.ks.onbid.setup;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;
import com.ks.onbid.R;
import com.ks.onbid.login.KakaoLoginActivity;
import com.ks.onbid.login.KakaoSignupActivity;
import com.ks.onbid.utill.Preferences;
import com.ks.onbid.vo.Comment;

import java.util.ArrayList;

/**
 * Created by jo on 2016-11-05.
 */
public class SetupActivity extends AppCompatActivity {


    private String foldText = "접기";
    private String spreadText = "내 댓글 보기";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private RecyclerView rvMyCommentsList;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Comment> commentList;

    public Preferences sp;
    TextView mycomments_tv;
    TextView userNickname_tv;
    private NetworkImageView userProfile_iv;

    String userNickname;

    private boolean isViewExpanded = false;
    private int originalHeight_rv = 0;
    private int originalHeight_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        commentList = new ArrayList<Comment>();

        rvMyCommentsList = (RecyclerView) findViewById(R.id.mycomments_list_rv);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvMyCommentsList.setHasFixedSize(true);
        rvMyCommentsList.setLayoutManager(layoutManager);
        rvMyCommentsList.setAdapter(new MyCommentAdapter(getApplicationContext(), commentList, R.layout.activity_setup));

        sp = new Preferences(this);

        userNickname_tv = (TextView) findViewById(R.id.userNickName);
        userProfile_iv = (NetworkImageView) findViewById(R.id.userProfile);

        TextView logout_tv = (TextView) findViewById(R.id.kakaologout_tv);
        mycomments_tv = (TextView) findViewById(R.id.mycomments_tv);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loadMyComments();
            }
        });

        thread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        ImageLoader mImageLoader;
        userNickname = sp.getKakaoNickname();
        userNickname_tv.setText(userNickname);

        mImageLoader = MySingletonForVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        final String UserProfile = sp.getKakaoProfileUrl();
        mImageLoader.get(UserProfile, ImageLoader.getImageListener(userProfile_iv,
                R.drawable.kakao_default_profile_image, android.R.drawable.ic_menu_report_image));
        userProfile_iv.setImageUrl(UserProfile, mImageLoader);

    }

    public void onClick(final View v) {
        ValueAnimator valueAnimator = null;
        if (v.getId() == R.id.kakaologout_tv) {
            onClickUnlink();

        } else if (v.getId() == R.id.mycomments_tv) {
            originalHeight_btn = v.getHeight();
            int oringinHeight_rv = rvMyCommentsList.getMeasuredHeight();
            if (!isViewExpanded) {

                mycomments_tv.setText(foldText);
                rvMyCommentsList.setVisibility(View.VISIBLE);
                rvMyCommentsList.setEnabled(true);
                isViewExpanded = true;

                valueAnimator = ValueAnimator.ofInt(originalHeight_rv + originalHeight_btn + (int) (originalHeight_rv * 2.0),
                        originalHeight_rv + originalHeight_btn);

                Animation popupAnimation = new AlphaAnimation(0.00f, 1.00f);

                popupAnimation.setDuration(300);

                popupAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        rvMyCommentsList.setVisibility(View.VISIBLE);
                        rvMyCommentsList.setEnabled(true);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                rvMyCommentsList.startAnimation(popupAnimation);

                valueAnimator.setDuration(200);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        v.getLayoutParams().height = value.intValue();
                        v.requestLayout();
                    }
                });
                valueAnimator.start();

                Log.i("TAG111111", String.valueOf(oringinHeight_rv) + " + " + String.valueOf(originalHeight_rv));

            } else {
                mycomments_tv.setText(spreadText);

                isViewExpanded = false;
                valueAnimator = ValueAnimator.ofInt(originalHeight_rv + originalHeight_btn + (int) (originalHeight_rv * 2.0),
                        originalHeight_rv + originalHeight_btn);

                Animation fadeoutAnimation = new AlphaAnimation(1.00f, 0.00f);

                fadeoutAnimation.setDuration(300);

                fadeoutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rvMyCommentsList.setVisibility(View.INVISIBLE);
                        rvMyCommentsList.setEnabled(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                rvMyCommentsList.startAnimation(fadeoutAnimation);

            }
            valueAnimator.setDuration(200);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    v.getLayoutParams().height = value.intValue();
                    v.requestLayout();
                }
            });
            valueAnimator.start();
        }
    }


    @Override
    protected void onResume() {
        commentList.clear();
        rvMyCommentsList.removeAllViewsInLayout();

        loadMyComments();

        super.onResume();
    }

    private void loadMyComments() {
        databaseReference = firebaseDatabase.getReference().child("sale_comment");
        Query searchQuery = databaseReference.orderByChild("userId").equalTo(sp.getKakaoId());
        Log.i("userId", sp.getKakaoId());

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                rvMyCommentsList.removeAllViewsInLayout();
                layoutManager = new LinearLayoutManager(getApplicationContext());

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    //Log.d("key print", messageSnapshot.getKey());
                    Comment value = messageSnapshot.getValue(Comment.class);
                    value.setKey(messageSnapshot.getKey());
                    commentList.add(value);
                    //Log.i("List 개수", String.valueOf(commentList.size()));

                }
                rvMyCommentsList.setAdapter(new MyCommentAdapter(getApplicationContext(), commentList, R.layout.activity_setup));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.toString());
            }
        });
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
                                        Log.i("TAG", "SessionClosed for Disconnect");
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                        redirectSignupActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        killprocess();
                                        Log.i("TAG", "Success for Disconnect");
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

    protected void killprocess() {
        ActivityCompat.finishAffinity(this);
        System.runFinalization();
        System.exit(0);
        finish();
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

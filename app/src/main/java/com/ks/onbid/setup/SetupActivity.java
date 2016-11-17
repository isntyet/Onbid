package com.ks.onbid.setup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.ks.onbid.main.CommentAdapter;
import com.ks.onbid.utill.Preferences;
import com.ks.onbid.vo.Comment;
import com.ks.onbid.vo.SaleItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.ks.onbid.utill.SysUtill.getCurrentTime;

/**
 * Created by jo on 2016-11-05.
 */
public class SetupActivity extends AppCompatActivity {

    private SaleItem saleItem;

    private Comment comment;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //private Button btnComment;
    private RecyclerView rvMyCommentsList;
    private RecyclerView.LayoutManager layoutManager;

    //private CommentAdapter commentAdapter;

    private ArrayList<Comment> commentList;

    public Preferences sp;
    TextView userNickname_tv;
    private NetworkImageView userProfile_iv;


    String userNickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Date date = getCurrentTime();
        String cDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        commentList = new ArrayList<Comment>();

        rvMyCommentsList = (RecyclerView) findViewById(R.id.mycomments_list_rv);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvMyCommentsList.setHasFixedSize(true);
        rvMyCommentsList.setLayoutManager(layoutManager);
        rvMyCommentsList.setAdapter(new CommentAdapter(getApplicationContext(), commentList, R.layout.activity_setup));


        sp = new Preferences(this);

        userNickname_tv = (TextView) findViewById(R.id.userNickName);
        userProfile_iv = (NetworkImageView) findViewById(R.id.userProfile);

        TextView logout_tv = (TextView) findViewById(R.id.kakaologout_tv);
        TextView mycomments_tv = (TextView) findViewById(R.id.mycomments_tv);

        loadMyComments();

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
                R.mipmap.ic_launcher, android.R.drawable.ic_menu_report_image));
        userProfile_iv.setImageUrl(UserProfile, mImageLoader);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kakaologout_tv:
                onClickUnlink();
                break;
            case R.id.mycomments_tv:
                loadMyComments();
                break;
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
                rvMyCommentsList.setAdapter(new CommentAdapter(getApplicationContext(), commentList, R.layout.activity_setup));
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

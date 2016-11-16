package com.ks.onbid.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ks.onbid.R;
import com.ks.onbid.utill.Preferences;
import com.ks.onbid.vo.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.ks.onbid.utill.SysUtill.getCurrentTime;

/**
 * Created by jo on 2016-11-16.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvChatList;
    private EditText etMessage;
    private Button btnSend;

    private Preferences preferences;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private LinearLayoutManager layoutManager;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        preferences = new Preferences(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        etMessage = (EditText) findViewById(R.id.et_message);

        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        chatList = new ArrayList<ChatMessage>();

        rvChatList = (RecyclerView) findViewById(R.id.rv_chat_list);
        layoutManager = new LinearLayoutManager(this);
        rvChatList.setLayoutManager(layoutManager);
        rvChatList.setItemAnimator(new DefaultItemAnimator());

        chatAdapter = new ChatAdapter(getApplicationContext(), chatList, R.layout.activity_chat);

        rvChatList.setAdapter(chatAdapter);

        onChatMessageRequest();

        scrollDown();
    }

    private void onChatMessageRequest() {

        databaseReference = firebaseDatabase.getReference().child("chat_message");

        databaseReference.addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage data = dataSnapshot.getValue(ChatMessage.class);  // data 가져오고

                chatList.add(data);
                chatAdapter.notifyDataSetChanged();

                scrollDown();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                scrollDown();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void scrollDown() {
        if (chatAdapter.getItemCount() > 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rvChatList.getLayoutManager().smoothScrollToPosition(rvChatList, null, chatAdapter.getItemCount() - 1);
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnSend.getId()) {
            if (etMessage.length() == 0) {
                Toast.makeText(this, "내용을 입력해 주세요", Toast.LENGTH_LONG).show();
            } else {
                Date date = getCurrentTime();
                String cDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

                ChatMessage item = new ChatMessage(preferences.getKakaoId(), preferences.getKakaoNickname(), preferences.getKakaoThumbUrl(), etMessage.getText().toString(), cDate);
                databaseReference = firebaseDatabase.getReference();
                databaseReference.child("chat_message").push().setValue(item);

                scrollDown();

                etMessage.setText("");
            }
        }
    }
}

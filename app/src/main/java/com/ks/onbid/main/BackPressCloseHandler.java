package com.ks.onbid.main;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by jo on 2016-11-17.
 */


public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();

            Intent t = new Intent(activity, MainActivity.class);
            activity.startActivity(t);

            activity.moveTaskToBack(true);
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}

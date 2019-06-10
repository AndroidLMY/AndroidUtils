package com.example.androidutils.start;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;


/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\8\27 0027 14:34
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public abstract class WelcomeActivity extends Activity {
    private String HOME_PAGE = "Defaulthomepage";
    private String IS_FIRST_RUN = "isFirstRun";
    private final int DELAYED_TIME = 0;//5000  5秒
    private static final int GO_MAIN = 100;
    private static final int GO_GUIDE = 101;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    goMain();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        firstRun();
    }

    private void firstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences(HOME_PAGE, 0);
        boolean isFirstRun = sharedPreferences.getBoolean(IS_FIRST_RUN, true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            editor.putBoolean(IS_FIRST_RUN, false);
            editor.commit();
            Log.i("WelcomeActivity", "首次运行");
            mhandler.sendEmptyMessageDelayed(GO_GUIDE, DELAYED_TIME);
        } else {
            Log.i("WelcomeActivity", "多次运行");
            mhandler.sendEmptyMessageDelayed(GO_MAIN, DELAYED_TIME);
        }
    }

    public abstract void goGuide();

    public abstract void goMain();
}

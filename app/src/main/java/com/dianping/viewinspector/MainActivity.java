package com.dianping.viewinspector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dianping.vi_lib.Hawkeye;
import com.dianping.viewinspector.suspend.DebugSuspendViewService;
import com.dianping.viewinspector.suspend.ServiceForegroundHelper;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceForegroundHelper.startService(this, new Intent(this,
                DebugSuspendViewService.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + Hawkeye.getCurrentActivity().getWindow().getDecorView() + "]");

    }

    static Activity sActivity;

    public static Activity getActivity() {
        return sActivity;
    }
}

package com.dianping.viewinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dianping.vi_lib.suspend.DebugSuspendViewService;
import com.dianping.vi_lib.suspend.ServiceForegroundHelper;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceForegroundHelper.startService(this, new Intent(this,
                DebugSuspendViewService.class));

        findViewById(R.id.btnOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DebugPanelActivity.class));
            }
        });

    }

}

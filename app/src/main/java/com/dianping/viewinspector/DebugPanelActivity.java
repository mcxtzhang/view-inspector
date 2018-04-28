package com.dianping.viewinspector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dianping.viewinspector.suspend.DebugSuspendViewService;
import com.dianping.viewinspector.suspend.ServiceForegroundHelper;

public class DebugPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_panel);
        ServiceForegroundHelper.startService(this, new Intent(this,
                DebugSuspendViewService.class));
    }
}

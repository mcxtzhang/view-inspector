package com.dianping.vi_lib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;

import com.dianping.vi_lib.inspector.AttributeShowModel;
import com.dianping.vi_lib.inspector.EditTextAttributes;
import com.dianping.vi_lib.inspector.IAttributes;
import com.dianping.vi_lib.inspector.TextViewAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxutong on 2018/4/28.
 */

public class Hawkeye {
    static Application sApplication;

    static Activity currentActivity;

    static final Application.ActivityLifecycleCallbacks ACTIVITY_LIFECYCLE_CALLBACKS = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            currentActivity = null;
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static void setApplication(Application application) {
        sApplication = application;
    }

    static List<IAttributes> sIAttributesList = new ArrayList<>();

    static {
        sIAttributesList.add(new EditTextAttributes());
        sIAttributesList.add(new TextViewAttributes());
    }

    public static List<AttributeShowModel> getAttributesByView(View view) {
        for (IAttributes iAttributes : sIAttributesList) {
            if (iAttributes.accept(view)) {
                return iAttributes.attributes();
            }
        }
        return null;
    }
}

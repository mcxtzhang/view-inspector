package com.dianping.vi_lib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

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

    public static Application getApplication() {
        return sApplication;
    }

    public static void setApplication(Application application) {
        sApplication = application;
    }

}

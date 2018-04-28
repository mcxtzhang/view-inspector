package com.dianping.vi_lib.suspend.full;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.dianping.vi_lib.Hawkeye;


public enum AttributeViewerManager {
    INSTANCE;

    private AttributeViewerView smallWindow;

    public void createSmallWindow(final Context context) {
        if (smallWindow != null) return;
        smallWindow = new AttributeViewerView(context);
        View decorView = Hawkeye.getCurrentActivity().getWindow().getDecorView();
        if (decorView instanceof FrameLayout) {
            FrameLayout decor = (FrameLayout) decorView;
            decor.addView(smallWindow);
        }
    }

    public void removeSmallWindow(Context context) {
        if (smallWindow != null && smallWindow.getParent() != null) {
            View decorView = Hawkeye.getCurrentActivity().getWindow().getDecorView();
            if (decorView instanceof FrameLayout) {
                FrameLayout decor = (FrameLayout) decorView;
                decor.removeView(smallWindow);
            }
            smallWindow = null;
        }
    }

    public boolean isWindowShowing() {
        return smallWindow != null;
    }

}
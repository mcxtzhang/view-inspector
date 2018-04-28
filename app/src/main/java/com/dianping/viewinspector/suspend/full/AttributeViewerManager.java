package com.dianping.viewinspector.suspend.full;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

import com.dianping.vi_lib.Hawkeye;


public enum AttributeViewerManager {
    INSTANCE;

    /**
     * 小悬浮窗View的参数
     */
    private static LayoutParams smallWindowParams;
    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;
    /**
     * 小悬浮窗View的实例
     */
    private AttributeViewerView smallWindow;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public void createSmallWindow(final Context context) {
        if (smallWindow != null) return;
        smallWindow = new AttributeViewerView(context);
        View decorView = Hawkeye.getCurrentActivity().getWindow().getDecorView();
        if (decorView instanceof FrameLayout) {
            FrameLayout decor = (FrameLayout) decorView;
            decor.addView(smallWindow);
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    void removeSmallWindow(Context context) {
        if (smallWindow != null && smallWindow.getParent() != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    boolean isWindowShowing() {
        return smallWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


}
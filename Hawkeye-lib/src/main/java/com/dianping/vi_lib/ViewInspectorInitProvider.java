package com.dianping.vi_lib;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhangxutong on 2018/4/28.
 */

public class ViewInspectorInitProvider extends ContentProvider {
    private static final String TAG = ViewInspectorInitProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        try {
            Log.d(TAG, "onCreate() called");
            final Context context = requireContext();
            final Application application = (Application) context.getApplicationContext();
            Hawkeye.setApplication(application);
            application.registerActivityLifecycleCallbacks(Hawkeye.ACTIVITY_LIFECYCLE_CALLBACKS);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Init failed.", e);
            return false;
        }
    }

    @NonNull
    private Context requireContext() {
        final Context context = getContext();
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        return context;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

package tv.huan.common.starcor;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.starcor.aaa.app.service.IAAAService;

import org.json.JSONObject;

import tv.huan.common.util.CommonUtil;

public final class StarcorProvider extends ContentProvider {

    private static IAAAService mIAAAService;

    static boolean mediaAuth(String productId) {
        try {
            JSONObject json = new JSONObject();
            json.put("cp_id", "HSTV");
            json.put("action", "PRODUCT_AUTH");
            json.put("product_id", productId);
            String toString = json.toString();
            String result = mIAAAService.mediaAuth(toString);
            CommonUtil.log("mediaAuth => result = " + result);
            JSONObject object = new JSONObject(result);
            int state = object.optInt("state", -1);
            return state == 0;
        } catch (Exception e) {
            CommonUtil.log("mediaAuth => " + e.getMessage(), e);
            return false;
        }
    }

    static boolean auth(Context context) {
        try {
            JSONObject json = new JSONObject();
            json.put("cp_id", "HSTV");
            String packageName = context.getPackageName();
            json.put("package_name", packageName);
            json.put("show_error_dialog", "1");
            String toString = json.toString();
            String result = mIAAAService.auth(toString);
            CommonUtil.log("auth => result = " + result);
            JSONObject object = new JSONObject(result);
            int state = object.optInt("state", -1);
            return state == 0;
        } catch (Exception e) {
            CommonUtil.log("auth => " + e.getMessage(), e);
            return false;
        }
    }

    /***************/

    @Override
    public boolean onCreate() {
        initApplication();
        initAIDL();
        return false;
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

    /***************/

    private void initAIDL() {
        try {
            Intent intent = new Intent("com.starcor.aaa.app.service.IAAAService");
            intent.setPackage("com.starcor.aaa.app");
            getContext().bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    CommonUtil.log("initAIDL => onServiceConnected => componentName = " + componentName);
                    mIAAAService = IAAAService.Stub.asInterface(iBinder);
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    CommonUtil.log("initAIDL => onServiceDisconnected => componentName = " + componentName);
                    mIAAAService = null;
                }

                @Override
                public void onBindingDied(ComponentName name) {
                    CommonUtil.log("initAIDL => onBindingDied => name = " + name);
                }

                @Override
                public void onNullBinding(ComponentName name) {
                    CommonUtil.log("initAIDL => onNullBinding => name = " + name);
                }
            }, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            CommonUtil.log("initAIDL => " + e.getMessage(), e);
        }
    }

    private void initApplication() {
        if (getContext() instanceof Application) {
            ((Application) getContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

                }

                @Override
                public void onActivityStarted(@NonNull Activity activity) {

                }

                @Override
                public void onActivityResumed(@NonNull Activity activity) {

                }

                @Override
                public void onActivityPaused(@NonNull Activity activity) {

                }

                @Override
                public void onActivityStopped(@NonNull Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(@NonNull Activity activity) {

                }
            });
        }
    }
}

package tv.huan.bilibili.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import tv.huan.bilibili.bean.JumpBean;
import tv.huan.bilibili.ui.center.CenterActivity;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.ui.filter.FilterActivity;
import tv.huan.bilibili.ui.search.SearchActivity;
import tv.huan.bilibili.ui.special1.Special1Activity;
import tv.huan.bilibili.ui.special2.Special2Activity;
import tv.huan.bilibili.ui.special3.Special3Activity;
import tv.huan.bilibili.ui.special4.Special4Activity;
import tv.huan.bilibili.ui.special5.Special5Activity;
import tv.huan.bilibili.ui.webview.WebviewActivity;

public class JumpUtil {

    public static void next(@NonNull Context context, @NonNull JumpBean templateBean) {

        if (null == templateBean)
            return;
        Log.e("JumpUtil", "next => " + new Gson().toJson(templateBean));
        int toType = templateBean.getToType();
//        int toType = 4;

        switch (toType) {
            // 分类
            case 2:
                nextFilter(context, templateBean);
                break;
            // 专辑
            case 1:
                nextDetail(context, templateBean);
                break;
            // 专题
            case 4:
                nextSpecial(context, templateBean);
                break;
        }
    }

    public static void nextDetail(@NonNull Context context, @NonNull JumpBean data) {
        try {
            String cid = data.getCid();
            nextDetail(context, cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void nextDetail(@NonNull Context context, @NonNull String cid) {
//        Log.e("JumpUtil", "nextDetail => cid = " + cid + ", classId = " + classId);
        if (null != cid && cid.length() > 0) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.INTENT_CID, cid);
//            intent.putExtra(DetailActivity.INTENT_CLASSID, classId);
            context.startActivity(intent);
        }
    }

    public static void nextFilter(@NonNull Context context, @NonNull JumpBean data) {
        try {
            String name = data.getFilterSecondTag();
            int classId = data.getFilterId();
            nextFilter(context, name, classId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void nextFilter(@NonNull Context context, @NonNull String secondTag, @NonNull int classId) {
        Log.e("JumpUtil", "nextFilter => secondTag = " + secondTag + ", classId = " + classId);
        if (classId > 0) {
            Intent intent = new Intent(context, FilterActivity.class);
            intent.putExtra(FilterActivity.INTENT_SECOND_TAG, secondTag);
            intent.putExtra(FilterActivity.INTENT_CLASSID, classId);
            context.startActivity(intent);
        }
    }

    public static void nextSpecial(@NonNull Context context, @NonNull JumpBean data) {
        try {
            int specialId = data.getSpecialId();
            int type = data.getSpecialType();
            nextSpecial(context, specialId, 2);
//            nextSpecial(specialId, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void nextSpecial(@NonNull Context context, @NonNull int specialId, @NonNull int type) {
        if (specialId > 0) {

            Intent intent = new Intent();
            switch (type) {
                case 2:
                    intent.setClass(context, Special2Activity.class);
                    break;
                case 3:
                    intent.setClass(context, Special3Activity.class);
                    break;
                case 4:
                    intent.setClass(context, Special4Activity.class);
                    break;
                case 5:
                    intent.setClass(context, Special5Activity.class);
                    break;
                default:
                    intent.setClass(context, Special1Activity.class);
                    break;
            }
            intent.putExtra(Special1Activity.INTENT_SPECIALID, specialId);
            context.startActivity(intent);
        }
    }

    /*****************/

    public static final void nextCenter(@NonNull Context context, boolean isFavor) {
        Intent intent = new Intent(context, CenterActivity.class);
        intent.putExtra(CenterActivity.INTENT_FAVORY, isFavor);
        context.startActivity(intent);
    }

    public static final void nextSearch(@NonNull Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static final void nextWebHelp(@NonNull Context context) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(WebviewActivity.INTENT_HELP, true);
        intent.putExtra(WebviewActivity.INTENT_HELP_TYPE, 1);
        context.startActivity(intent);
    }

    public static final void nextWebAbout(@NonNull Context context) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(WebviewActivity.INTENT_HELP, true);
        intent.putExtra(WebviewActivity.INTENT_HELP_TYPE, 2);
        context.startActivity(intent);
    }
}

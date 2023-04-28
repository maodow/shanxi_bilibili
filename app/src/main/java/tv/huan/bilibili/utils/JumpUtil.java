package tv.huan.bilibili.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import tv.huan.bilibili.bean.base.BaseDataBean;
import tv.huan.bilibili.ui.center.CenterActivity;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.ui.filter.FilterActivity;
import tv.huan.bilibili.ui.search.SearchActivity;
import tv.huan.bilibili.ui.special1.Special1Activity;
import tv.huan.bilibili.ui.webview.WebviewActivity;

public final class JumpUtil {

    public static void next(@NonNull Context context, @NonNull BaseDataBean templateBean) {

        if (null == templateBean)
            return;
        Log.e("JumpUtil", "next => " + new Gson().toJson(templateBean));
        int toType = templateBean.getToType();

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

    private static void nextDetail(@NonNull Context context, @NonNull BaseDataBean data) {
        try {
            String cid = data.getCid();
            if (null == cid || cid.length() <= 0)
                throw new Exception("cid error: " + cid);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.INTENT_CID, cid);
            if (data.isFromSearch()) {
                intent.putExtra(DetailActivity.INTENT_FROM_SEARCH, data.isFromSearch());
                intent.putExtra(DetailActivity.INTENT_FROM_SEARCH_KEY, data.getFromSearchKeys());
            }
            if (data.isFromSpecial()) {
                intent.putExtra(DetailActivity.INTENT_FROM_SPECIAL, data.isFromSpecial());
                intent.putExtra(DetailActivity.INTENT_FROM_SPECIAL_SCENEID, data.getFromSpecialTopId());
                intent.putExtra(DetailActivity.INTENT_FROM_SPECIAL_TOPID, data.getFromSpecialTopId());
                intent.putExtra(DetailActivity.INTENT_FROM_SPECIAL_TOPNAME, data.getFromSpecialTopName());
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("JumpUtil", "nextDetail => " + e.getMessage());
        }
    }

    private static void nextFilter(@NonNull Context context, @NonNull BaseDataBean data) {
        try {
            int classId = data.getClassId();
            if (classId <= 0)
                throw new Exception("classId error: " + classId);
            Intent intent = new Intent(context, FilterActivity.class);
            intent.putExtra(FilterActivity.INTENT_SECOND_TAG, data.getName());
            intent.putExtra(FilterActivity.INTENT_CLASSID, classId);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("JumpUtil", "nextFilter => " + e.getMessage());
        }
    }

    private static void nextSpecial(@NonNull Context context, @NonNull BaseDataBean data) {
        try {
            String cid = data.getCid();
            if (null == cid || cid.length() <= 0)
                throw new Exception("cid error: " + cid);
            int specialId = Integer.parseInt(cid);
            if (specialId <= 0)
                throw new Exception("specialId error: " + specialId);
            Intent intent = new Intent(context, Special1Activity.class);
            intent.putExtra(Special1Activity.INTENT_SPECIALID, specialId);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("JumpUtil", "nextSpecial => " + e.getMessage());
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
        intent.putExtra(WebviewActivity.INTENT_HELP_TYPE, 2);
        context.startActivity(intent);
    }

    public static void nextWebAbout(@NonNull Context context) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(WebviewActivity.INTENT_HELP, true);
        intent.putExtra(WebviewActivity.INTENT_HELP_TYPE, 3);
        context.startActivity(intent);
    }

    public static void nextDetailFromWanliu(@NonNull Context context, @NonNull String cid, @NonNull String name) {
        if (null != cid && cid.length() > 0) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.INTENT_CID, cid);
            intent.putExtra(DetailActivity.INTENT_FROM_WANLIU, true);
            intent.putExtra(DetailActivity.INTENT_FROM_WANLIU_KEY, name);
            context.startActivity(intent);
        }
    }
}

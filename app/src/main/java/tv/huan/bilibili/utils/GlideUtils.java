package tv.huan.bilibili.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import lib.kalu.frame.mvp.context.FrameContext;
import lib.kalu.leanback.tab.ninepatch.NinePatchChunk;
import tv.huan.bilibili.HuanApp;
import tv.huan.bilibili.R;

public class GlideUtils {

    /**************************** 网络图片 ************************************/
    /**************************** ↓↓↓↓ ************************************/

    public static void loadHz(Context context, String url, ImageView imageView) {
        into(context, url, imageView, R.drawable.bg_shape_placeholder_hz);
    }

    public static void loadVt(Context context, String url, ImageView imageView) {
        into(context, url, imageView, R.drawable.bg_shape_placeholder_vt);
    }

    public static void load(Context context, String url, ImageView imageView, @DrawableRes int placeholder) {
        into(context, url, imageView, placeholder);
    }

    public static void load(Context context, String url, ImageView imageView) {
        into(context, url, imageView, 0);
    }

    @SuppressLint("CheckResult")
    private static void into(Context context, String url, final ImageView imageView, @DrawableRes int res) {

        if (null == imageView || null == url || url.length() == 0)
            return;

        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .dontTransform()
                .dontAnimate()
                .dontTransform()
                .encodeQuality(40)
                .format(DecodeFormat.PREFER_RGB_565)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false);

        try {
            Drawable drawable = context.getResources().getDrawable(res);
            if (null != drawable) {
                options.error(drawable).placeholder(drawable);
            }
        } catch (Exception e) {
        }

        into(context, url, imageView, options);
    }

    private static void into(Context context, String url, final ImageView imageView, RequestOptions options) {

        if (null == url || url.length() == 0)
            return;

        try {

            // 圆角
//            float v = context.getResources().getDimension(R.dimen.dp_4);
//            GlideRoundTransform transform = new GlideRoundTransform(context, v);
//            options.transform(transform);

            // 图片
            Glide.with(context).load(url.trim()).apply(options).into(imageView);
//            Glide.with(context).load(url).apply(options).into(new CustomTarget<Drawable>() {
//                @Override
//                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    int visibility = imageView.getVisibility();
//                    if (visibility == View.VISIBLE) {
//                        imageView.setImageDrawable(resource);
//                    }
//                }
//
//                @Override
//                public void onLoadCleared(@Nullable Drawable placeholder) {
//                }
//            });
//            Glide.with(context).asBitmap().load(url).apply(options).into(new CustomTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
//                    try {
//                        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//                        imageView.setImageDrawable(null);
//                        imageView.setImageBitmap(null);
//                        imageView.setImageBitmap(copy);
//                    } catch (Exception e) {
//                    }
//                }
//
//                @Override
//                public void onLoadCleared(@Nullable Drawable drawable) {
//                }
//            });
        } catch (Exception e) {
        }
    }

    /**************************** ↓↓↓↓ ************************************/

    public static void loadBackground(Context context, String url, View view) {
        intoBackground(context, url, view);
    }

    private static void intoBackground(Context context, String url, View view) {

        if (null == view || null == url || url.length() == 0)
            return;

        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .dontTransform()
                .encodeQuality(80)
                .format(DecodeFormat.PREFER_RGB_565)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(false);
        intoBackground(context, url, view, options);
    }

    private static void intoBackground(Context context, String url, final View view, RequestOptions options) {

        if (null == url || url.length() == 0)
            return;

        Glide.with(context).asDrawable().load(url).apply(options).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                try {
                    view.setBackground(drawable);
                } catch (Exception e) {
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable drawable) {

            }
        });
    }

    /**************************** ↓↓↓↓ ************************************/

    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    public static void pauseRequests(Context context) {
        Glide.with(context).pauseAllRequests();
    }

    public static void clear(@NonNull Context context) {
        clear(context, true, true);
    }

    public static void clear(@NonNull Context context, boolean cleanDisk, boolean cleanMemory) {

        if (context == null)
            return;

        if (cleanDisk) {
            clearImageDiskCache(context);
        }

        if (cleanMemory) {
            clearImageMemoryCache(context);
        }
    }

    /**
     * 清除图片磁盘缓存
     */
    private static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    private static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**************************** 网络图片 ************************************/
    /**************************** ↑↑↑↑ ************************************/


    /**************************** 皮肤图片 ************************************/
    /**************************** ↓↓↓↓ ************************************/

    public final static String skin_background = "skin_background.png";
    public final static String skin_secondbar_logo = "skin_secondbar_logo.png";
    public final static String skin_secondbar_vip = "skin_secondbar_vip.png";
    public final static String skin_secondbar_vip_backgroung_focus = "skin_secondbar_vip_backgroung_focus.png";
    public final static String skin_secondbar_search_focus = "skin_secondbar_search_focus.png";
    public final static String skin_nine_tab_focus = "skin_nine_tab_focus.9.png";
    public final static String skin_nine_background_focus = "skin_nine_background_focus.9.png";
    public final static String skin_nine_keyboard_focus = "skin_nine_keyboard_focus.9.png";

    // 皮肤包所有文件
    public final static List<String> SKIN_LIST = Arrays.asList(
            skin_background,
            skin_secondbar_logo,
            skin_secondbar_vip,
            skin_secondbar_vip_backgroung_focus,
            skin_secondbar_search_focus,
            skin_nine_tab_focus,
            skin_nine_background_focus,
            skin_nine_keyboard_focus
    );

    public static List<String> getImgPaths() {
        return SKIN_LIST;
    }

    public static void loadBackground(View view, @NonNull int prodId) {
        String filePath = getSkinPath(prodId, skin_background);
        if (null != filePath && filePath.length() > 0) {
            loadDrawableFile(view, filePath);
        }
    }

    public static void loadSkin(View view, boolean hasFocus, @NonNull int prodId, @DrawableRes int defaultRes, @NonNull String focusName) {

        if (view == null)
            return;

        String filePath = getSkinPath(prodId, focusName);
        LogUtil.log("GG", "loadSkin => filePath = " + filePath);
        if (hasFocus && null != filePath && filePath.length() > 0 && filePath.endsWith(".9.png")) {
            loadDrawableNine(view, filePath);
        } else if (hasFocus && null != filePath && filePath.length() > 0 && filePath.endsWith(".png")) {
            loadDrawableFile(view, filePath);
        } else {
            loadDrawableResources(view, defaultRes);
        }
    }

    public static boolean contains(@NonNull int prodId, String filename) {
        String path = getSkinPath(prodId, filename);
        return null != path && !path.startsWith("file:///android_asset/") && path.length() > 0;
    }

    public static String getSkinPath(@NonNull int prodId, @NonNull String filename) {
        return getSkinPath(prodId, filename, true);
    }

    public static String getSkinPath(@NonNull int prodId, @NonNull String filename, boolean autoAssets) {

//            // 未开皮肤开关
//            boolean aBoolean = SPUtils.getInstance().getBoolean(Constants.Key.ICON_OPEN, false);
//            if (!aBoolean)
//                return "";

        // 欢视TV
        if (2 == prodId) {
            LogUtil.log("SKIN", "getSkinPath" + "[" + prodId + "]" + "=> fileName = " + filename);
            return null;
        } else {

            File filesDir = HuanApp.getContext().getFilesDir();
            String filesDirAbsolutePath = filesDir.getAbsolutePath();

            String imgPath = filesDirAbsolutePath + "/skin/" + prodId + "/" + filename;
            File skinFile = new File(imgPath);

            // 下载皮肤
            if (skinFile.exists()) {
                String absolutePath = skinFile.getAbsolutePath();
                LogUtil.log("SKIN", "getSkinPath" + "[" + prodId + "]" + "=> fileName = " + filename + ", filePath = " + absolutePath);
                return absolutePath;
            }
            // Assets皮肤 => 3\4\5
            else if (autoAssets) {
                String uriPath = "file:///android_asset/" + prodId + "/" + filename;
                LogUtil.log("SKIN", "getSkinPath" + "[" + prodId + "]" + "=> fileName = " + filename + ", filePath = " + uriPath);
                return uriPath;
            }
            // null
            else {
                return null;
            }
        }
    }

    public static void loadDrawableResources(@NonNull View view, @NonNull int resource) {

        try {
            Resources resources = view.getResources();
            Drawable drawable = resources.getDrawable(resource);
            setDrawable(view, drawable);
        } catch (Exception e) {
        }
    }

    public static void loadDrawableFile(@NonNull View view, @NonNull String filePath) {

        if (null == filePath || filePath.length() == 0)
            return;

        try {

            BitmapDrawable drawable;
            Resources resources = view.getResources();

            // assets
            if (filePath.startsWith("file:///android_asset/")) {
                Context context = view.getContext();
                String substring = filePath.substring(22);
                InputStream inputStream = context.getAssets().open(substring);
                drawable = new BitmapDrawable(resources, inputStream);
                if (null != inputStream) {
                    inputStream.close();
                }
            }
            // file
            else {
                drawable = new BitmapDrawable(resources, filePath);
            }
            setDrawable(view, drawable);
            LogUtil.log("GG", "setDrawableFile => filePath = " + filePath + ", drawable = " + drawable);
        } catch (Exception e) {
            LogUtil.log("GG", "setDrawableFile => " + e.getMessage());
        }
    }

    /**
     * .9图
     *
     * @param view
     * @param filePath
     */
    public static void loadDrawableNine(@NonNull View view, @NonNull String filePath) {

        if (null == filePath || filePath.length() == 0)
            return;

        try {
            LogUtil.log("GG", "setDrawable9 => filePath = " + filePath);

            Bitmap bitmap;

            // assets
            if (filePath.startsWith("file:///android_asset/")) {
                Context context = view.getContext();
                String substring = filePath.substring(22);
                InputStream inputStream = context.getAssets().open(substring);
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (null != inputStream) {
                    inputStream.close();
                }
            }
            // file
            else {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(filePath));
            }

            NinePatchDrawable drawable = NinePatchChunk.create9PatchDrawable(view.getContext(), bitmap, null);
            setDrawable(view, drawable);
            LogUtil.log("GG", "setDrawable9 => filePath = " + filePath + ", drawable = " + drawable);
        } catch (Exception e) {
            loadDrawableFile(view, filePath);
            LogUtil.log("GG", "setDrawable9 => " + e.getMessage());
        }
    }

    private static void setDrawable(@NonNull View view, @Nullable Drawable drawable) {

        if (null == view || null == drawable)
            return;

        try {
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(null);
                imageView.setImageDrawable(drawable);
            } else {
                view.setBackground(null);
                view.setBackground(drawable);
            }
        } catch (Exception e) {
        }
    }

    /**************************** 皮肤图片 ************************************/
    /**************************** ↑↑↑↑ ************************************/

    //    private static void setDrawableXml(@NonNull Context context, @NonNull final View view, @NonNull String xmlPath) {
//
//        if (null == xmlPath || xmlPath.length() == 0)
//            return;
//
//        try {
//
//            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
//            XmlPullParser xmlPullParser = parserFactory.newPullParser();
//            xmlPullParser.setInput(new FileInputStream(xmlPath), "utf-8");
//            int eventType = xmlPullParser.getEventType();
////            Log.e("GG", "setDrawableXml => eventType = " + eventType);
//
//            String corners = null;
//            String color = null;
//            String colorStart = null;
//            String colorCenter = null;
//            String colorEnd = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
////                        Log.e("GG", "setDrawableXml => START_DOCUMENT");
//                        break;
//                    case XmlPullParser.END_DOCUMENT:
////                        Log.e("GG", "setDrawableXml => END_DOCUMENT");
//                        break;
//                    case XmlPullParser.START_TAG:
//
//                        if ("solid".equals(xmlPullParser.getName()) && "android:color".equals(xmlPullParser.getAttributeName(0))) {
//                            color = xmlPullParser.getAttributeValue(0);
//                        } else if ("gradient".equals(xmlPullParser.getName())) {
////                            Log.e("GG", "setDrawableXml => START_TAG =>");
//                            for (int i = 0; i < 3; i++) {
//                                if ("android:centerColor".equals(xmlPullParser.getAttributeName(i))) {
//                                    colorCenter = xmlPullParser.getAttributeValue(i);
//                                } else if ("android:endColor".equals(xmlPullParser.getAttributeName(i))) {
//                                    colorEnd = xmlPullParser.getAttributeValue(i);
//                                } else {
//                                    colorStart = xmlPullParser.getAttributeValue(i);
//                                }
//                            }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
////                        Log.e("GG", "setDrawableXml => END_TAG");
//                        break;
//                }
//                eventType = xmlPullParser.next();
//            }
//
//            GradientDrawable drawable = new GradientDrawable();
//
//            Log.e("GG", "setDrawableXml => xmlPath = " + xmlPath);
//            float v = context.getResources().getDimension(R.dimen.dp_4);
//            drawable.setCornerRadius(v);
//
//            if (null != color) {
//                try {
//                    drawable.setColor(Color.parseColor(color));
//                } catch (Exception e) {
//                }
//            }
//
//            if (null != colorStart && null != colorCenter && null != colorEnd) {
//                try {
//                    int[] ints = {Color.parseColor(colorStart), Color.parseColor(colorCenter), Color.parseColor(colorEnd)};
//                    drawable.setColors(ints);
//                    drawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
//                } catch (Exception e) {
//                }
//            }
//            setDrawable(view, drawable);
//        } catch (Exception e) {
//            Log.e("GG", "setDrawableXml => Exception => " + e.getMessage());
//        }
//    }
}
package tv.huan.bilibili.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;

import tv.huan.bilibili.R;

public class InfoDialog extends DialogFragment implements DialogInterface.OnKeyListener {

    public static final String BUNDLE_NAME = "bundle_name";
    public static final String BUNDLE_DATA1 = "bundle_data1";
    public static final String BUNDLE_DATA2 = "bundle_data2";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity()) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
            }
        };

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(isCancelable());
        dialog.setOnKeyListener(this);

        try {
            Context context = getContext();
            int dividerId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerId);
            if (divider != null) {
                divider.setBackgroundColor(Color.TRANSPARENT);
            }
        } catch (Exception e) {
        }

        setText(dialog);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //LogUtil.e("dialog", "onCreateView =>");

        // 窗口边框
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);

        // 窗口位置
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.3f;
        windowParams.gravity = Gravity.CENTER;

        // 窗口动画
//        windowParams.windowAnimations = initAnimation();

        window.setAttributes(windowParams);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 不显示状态栏
        try {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//            window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//                @Override
//                public void onSystemUiVisibilityChange(int visibility) {
//                    int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                            //布局位于状态栏下方
//                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                            //全屏
//                            View.SYSTEM_UI_FLAG_FULLSCREEN |
//                            //隐藏导航栏
//                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//                    uiOptions |= 0x00001000;
//                    window.getDecorView().setSystemUiVisibility(uiOptions);
//                }
//            });
        } catch (Exception e) {
        }

        // 初始化window
//        initWindow(window);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            Class cls = DialogFragment.class;
            Field mDismissed = cls.getDeclaredField("mDismissed");
            mDismissed.setAccessible(true);
            mDismissed.set(this, false);
            Field mShownByMe = cls.getDeclaredField("mShownByMe");
            mShownByMe.setAccessible(true);
            mShownByMe.set(this, true);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(this, tag);
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            super.show(manager, tag);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        try {
            dismissAllowingStateLoss();
        } catch (Exception e) {
            super.onDismiss(dialog);
        }
    }

    @Override
    public void dismiss() {
        try {
            dismissAllowingStateLoss();
        } catch (Exception e) {
            super.dismiss();
        }
    }

    private void setText(@NonNull Dialog dialog) {

        SpannableStringBuilder builder = new SpannableStringBuilder();


        // 1
        String text0 = getArguments().getString(BUNDLE_NAME) + "\n";
        AbsoluteSizeSpan span0 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_20));
        builder.append(text0);
        builder.setSpan(span0, 0, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        // 换行
        AbsoluteSizeSpan span01 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_6));
        builder.append(" \n");
        builder.setSpan(span01, builder.length() - 2, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        try {

            String sequence = getArguments().getString(BUNDLE_DATA1);
            if (null != sequence && sequence.length() > 0) {
                int index = sequence.indexOf("主   演：");
                String substring1 = sequence.substring(index) + "\n";

                AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_14));
                builder.append(substring1);
                builder.setSpan(span1, builder.length() - substring1.length(), builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                // 换行
                AbsoluteSizeSpan span11 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_6));
                builder.append(" \n");
                builder.setSpan(span11, builder.length() - 2, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                String substring2 = sequence.substring(0, index) + "\n";
                AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_14));
                builder.append(substring2);
                builder.setSpan(span2, builder.length() - substring2.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                // 换行
                AbsoluteSizeSpan span12 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_6));
                builder.append(" \n");
                builder.setSpan(span12, builder.length() - 2, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }

        } catch (Exception e) {
            String sequence = getArguments().getString(BUNDLE_DATA1);
            if (null != sequence && sequence.length() > 0) {
                sequence = sequence + "\n";
                AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_14));
                builder.append(sequence);
                builder.setSpan(span1, builder.length() - sequence.length(), builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                // 换行
                AbsoluteSizeSpan span11 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_6));
                builder.append(" \n");
                builder.setSpan(span11, builder.length() - 2, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        String text2 = getArguments().getString(BUNDLE_DATA2);
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(getResources().getDimensionPixelSize(R.dimen.dp_12));
        builder.append(text2);
        builder.setSpan(span2, builder.length() - text2.length(), builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView textView = dialog.findViewById(R.id.dialog_info_text);
        textView.setText(builder);
        // 2
        ScrollView scrollView = dialog.findViewById(R.id.dialog_info_scroll);
        scrollView.scrollTo(0, 0);
    }
}

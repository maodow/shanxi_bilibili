package tv.huan.bilibili.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;

import lib.kalu.frame.mvp.BaseActivity;
import tv.huan.bilibili.R;

public class WarningDialog extends DialogFragment implements DialogInterface.OnKeyListener {

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
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
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

        try {
            View viewById = dialog.findViewById(R.id.dialog_warning_sure);
            viewById.requestFocus();
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) getActivity()).killProcess();
                }
            });
        } catch (Exception e) {
        }

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
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
}

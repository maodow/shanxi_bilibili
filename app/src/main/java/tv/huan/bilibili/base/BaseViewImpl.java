package tv.huan.bilibili.base;

import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.frame.mvp.BaseFragment;
import lib.kalu.frame.mvp.BaseView;
import tv.huan.bilibili.dialog.LoadingDialog;
import tv.huan.bilibili.utils.GlideUtils;

public interface BaseViewImpl extends BaseView {

    default void setImageUrl(@IdRes int id, @NonNull String url) {
        try {
            ImageView imageView = findViewById(id);
            GlideUtils.load(getContext(), url, imageView);
        } catch (Exception e) {
        }
    }

    default int getClassId() {
        try {
            return getIntExtra("classId", -1);
        } catch (Exception e) {
            return -1;
        }
    }

    default String getSecondTag() {
        try {
            return getStringExtra("secondTag");
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    default void showLoading() {
        // activity
        if (this instanceof BaseActivity) {
            LoadingDialog dialog = new LoadingDialog();
            FragmentManager manager = ((BaseActivity) this).getSupportFragmentManager();
            dialog.show(manager, "loadingDialog");
        }
        // fragmnet
        else if (this instanceof BaseFragment) {
            LoadingDialog dialog = new LoadingDialog();
            FragmentManager manager = (((BaseFragment) this).getActivity()).getSupportFragmentManager();
            dialog.show(manager, "loadingDialog");
        }
    }

    @Override
    default void hideLoading() {

        // activity
        if (this instanceof BaseActivity) {
            FragmentManager manager = ((BaseActivity) this).getSupportFragmentManager();
            Fragment byTag = manager.findFragmentByTag("loadingDialog");
            if (null != byTag && byTag instanceof LoadingDialog) {
                ((LoadingDialog) byTag).dismiss();
            }
        }
        // fragmnet
        else if (this instanceof BaseFragment) {
            FragmentManager manager = (((BaseFragment) this).getActivity()).getSupportFragmentManager();
            Fragment byTag = manager.findFragmentByTag("loadingDialog");
            if (null != byTag && byTag instanceof LoadingDialog) {
                ((LoadingDialog) byTag).dismiss();
            }
        }
    }
}

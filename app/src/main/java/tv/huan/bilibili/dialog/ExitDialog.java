package tv.huan.bilibili.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.ExitBean;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

/**
 * @author zhanghang
 * @description: loading
 * @date :2022-01-17
 */
public class ExitDialog extends DialogFragment implements DialogInterface.OnKeyListener {

    public static final String BUNDLE_DATA = "bundle_data";
    public static final String BUNDLE_MAX = "bundle_max";

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
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(isCancelable());
        dialog.setOnKeyListener(this);

        // 背景
        try {
            Context context = getContext();
            int dividerId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerId);
            if (divider != null) {
                divider.setBackgroundColor(Color.TRANSPARENT);
            }
        } catch (Exception e) {
        }

        // 默认焦点
        try {
            View view = dialog.findViewById(R.id.dialog_exit_cancle);
            view.requestFocus();
        } catch (Exception e) {
        }

        // adapter
        setAdapter(dialog);

        // 取消
        dialog.findViewById(R.id.dialog_exit_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 强制退出
        dialog.findViewById(R.id.dialog_exit_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MainActivity) getActivity()).onCall(1100, null);
                } catch (Exception e) {
                }
                dismiss();
            }
        });

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

    private void setAdapter(@NonNull Dialog dialog) {

        try {
            String data = getArguments().getString(BUNDLE_DATA, null);
            if (null == data || data.length() <= 0)
                throw new Exception("data error: " + data);
            int max = getArguments().getInt(BUNDLE_MAX, 2);
            Gson gson = new Gson();
            Type type = new TypeToken<List<ExitBean>>() {
            }.getType();
            List<ExitBean> fromList = gson.fromJson(data, type);
            int size = fromList.size();
            int length = Math.min(size, max);
            LinkedList<ExitBean> list = new LinkedList<>();
            for (int i = 0; i < length; i++) {
                ExitBean exitBean = fromList.get(i);
                if (null == exitBean)
                    continue;
                list.add(exitBean);
            }
            if (null == list || list.size() <= 0)
                throw new Exception("list error: " + list);
            RecyclerView recyclerView = dialog.findViewById(R.id.dialog_exit_list);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildAdapterPosition(view);
                    int offset = view.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_12);
                    outRect.set(position <= 0 ? 0 : offset, 0, 0, 0);
                }
            });
            recyclerView.setAdapter(new RecyclerView.Adapter() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    Context context = parent.getContext();
                    View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_exit_item, parent, false);
                    RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(inflate) {
                    };
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            ExitBean itemBean = list.get(position);
                            JumpUtil.nextDetailFromWanliu(getContext(), itemBean.getCid(), itemBean.getName());
                            dismiss();
                        }
                    });
                    return holder;
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                    try {
                        ImageView imageView = holder.itemView.findViewById(R.id.common_poster_vip);
                        imageView.setVisibility(View.GONE);
                    } catch (Exception e) {
                    }
                    try {
                        ExitBean itemBean = list.get(position);
                        TextView textView = holder.itemView.findViewById(R.id.common_poster_name);
                        textView.setText(itemBean.getName());
                    } catch (Exception e) {
                    }
                    try {
                        ExitBean itemBean = list.get(position);
                        ImageView imageView = holder.itemView.findViewById(R.id.common_poster_img);
                        GlideUtils.loadHz(imageView.getContext(), itemBean.getImgs().getPoster(), imageView);
                    } catch (Exception e) {
                    }
                }

                @Override
                public int getItemCount() {
                    return list.size();
                }
            });
        } catch (Exception e) {
            LogUtil.log("ExitDialog => setAdapter => " + e.getMessage());
        }
    }
}

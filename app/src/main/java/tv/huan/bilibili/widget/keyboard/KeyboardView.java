package tv.huan.bilibili.widget.keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.Html;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.huan.bilibili.R;
import tv.huan.bilibili.utils.DisplayUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;


/**
 * 搜索键盘
 * Author: yhw on 2018-10-15.
 */
public class KeyboardView extends LinearLayout {

    private static final int T9_TYPE = 2;
    private static final int FULL_TYPE = 3;
    private int curType = FULL_TYPE; //默认T9类型
    private OnSearchListener onSearchListener;

    private List<KeyBoard> mData = new ArrayList<>();

    public KeyboardView(Context context) {
        super(context);
        init();
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static int mProdId = -1;

    public static void setProdId(@NonNull int id) {
        mProdId = id;
    }

    private void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.keyboard_layout, this);
        initKeyboard();

        // 搜索
        TextView textView = findViewById(R.id.tv_result);
        textView.setHint(Html.fromHtml("<font color='#696969'>请输入影片首字母</font>"));

        // 退格
        findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        // 清空
        findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

//        // 全键盘
//        findViewById(R.id.btn_full).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (curType != FULL_TYPE) {
//                    TextView textView1 = findViewById(R.id.btn_full);
//                    textView1.setTextColor(Color.parseColor("#ff673c"));
//                    textView1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.keyboard_tab_focus);
//                    TextView textView2 = findViewById(R.id.btn_t9);
//                    textView2.setTextColor(Color.WHITE);
//                    textView2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.keyboard_tab_unfocus);
//                    curType = FULL_TYPE;
//                    initKeyboard();
//                }
//            }
//        });

//        // T9
//        findViewById(R.id.btn_t9).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (curType != T9_TYPE) {
//                    curType = T9_TYPE;
//                    TextView textView1 = findViewById(R.id.btn_full);
//                    textView1.setTextColor(Color.WHITE);
//                    textView1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.keyboard_tab_unfocus);
//                    TextView textView2 = findViewById(R.id.btn_t9);
//                    textView2.setTextColor(Color.parseColor("#ff673c"));
//                    textView2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.keyboard_tab_focus);
//                    initKeyboard();
//                }
//            }
//        });
    }

    private final void initKeyboard() {

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        if (null == recyclerView.getAdapter()) {
            KeyboardAdapter adapter = new KeyboardAdapter(getContext(), mData);
            recyclerView.setAdapter(adapter);
        }

        switch (curType) {
            case T9_TYPE:

                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//                int v = (int) getResources().getDimension(R.dimen.dp_4);
//                recyclerView.setSpacingWithMargins(v, v);

                mData.clear();
                for (int i = 0; i < 9; i++) {
                    KeyBoard keyBoard = new KeyBoard();
                    keyBoard.setType(KeyBoard.T9_TYPE);
                    switch (i) {
                        case 0:
                            keyBoard.setValues(Arrays.asList("0", "1"));
                            break;
                        case 1:
                            keyBoard.setValues(Arrays.asList("2", "A", "B", "C"));
                            break;
                        case 2:
                            keyBoard.setValues(Arrays.asList("3", "D", "E", "F"));
                            break;
                        case 3:
                            keyBoard.setValues(Arrays.asList("4", "G", "H", "I"));
                            break;
                        case 4:
                            keyBoard.setValues(Arrays.asList("5", "J", "K", "L"));
                            break;
                        case 5:
                            keyBoard.setValues(Arrays.asList("6", "M", "N", "O"));
                            break;
                        case 6:
                            keyBoard.setValues(Arrays.asList("7", "P", "Q", "R", "S"));
                            break;
                        case 7:
                            keyBoard.setValues(Arrays.asList("8", "T", "U", "V"));
                            break;
                        case 8:
                            keyBoard.setValues(Arrays.asList("9", "W", "X", "Y", "Z"));
                            break;
                    }
                    mData.add(keyBoard);
                }
                break;
            case FULL_TYPE:

                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));
//                int v2 = (int) getResources().getDimension(R.dimen.dp_4);
//                recyclerView.setSpacingWithMargins(v2, v2);

                mData.clear();

                // a-z
                for (int i = 65; i <= 90; i++) {
                    KeyBoard keyBoard = new KeyBoard();
                    keyBoard.setType(KeyBoard.FULL_TYPE);
                    keyBoard.setValues(Arrays.asList(String.valueOf((char) i)));
                    mData.add(keyBoard);
                }

                // 1-9
                for (int i = 49; i <= 57; i++) {
                    KeyBoard keyBoard = new KeyBoard();
                    keyBoard.setType(KeyBoard.FULL_TYPE);
                    keyBoard.setValues(Arrays.asList(String.valueOf((char) i)));
                    mData.add(keyBoard);
                }

                // 0
                KeyBoard keyBoard = new KeyBoard();
                keyBoard.setType(KeyBoard.FULL_TYPE);
                keyBoard.setValues(Arrays.asList(String.valueOf((char) 48)));
                mData.add(keyBoard);

                break;
        }
    }

    public String getResult() {
        TextView textView = findViewById(R.id.tv_result);
        CharSequence text = textView.getText();
        return String.valueOf(text);
    }

    public void append(@NonNull String str) {
        TextView textView = findViewById(R.id.tv_result);
        CharSequence text = textView.getText();
        String news = text + str;
        textView.setText(news);
        if (onSearchListener != null) {
            onSearchListener.onSearchKeyResult(news);
        }
    }

    public final void clear() {
        TextView textView = findViewById(R.id.tv_result);
        textView.setText("");
        if (onSearchListener != null) {
            onSearchListener.onSearchKeyResult("");
        }
    }

    private void delete() {
        String news = "";
        TextView textView = findViewById(R.id.tv_result);
        CharSequence text = textView.getText();
        if (null != text && text.length() > 0) {
            for (int i = 0; i < text.length() - 1; i++) {
                char c = text.charAt(i);
                news += c;
            }
        }
        textView.setText(news);
        if (onSearchListener != null) {
            onSearchListener.onSearchKeyResult(news);
        }
    }

    /**
     * 默认请求焦点
     */
    public void requestDefaultFocus() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        if (null != recyclerView.getAdapter()) {
            recyclerView.requestFocus();
//            recyclerView.setSelection(12);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        View focus1 = findFocus();
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            try {
                View focus = findFocus();
                ViewGroup parent = null;
                if (null != focus && (focus.getId() == R.id.t9_item_type1_txt1 || focus.getId() == R.id.t9_item_type1_txt2)) {
                    parent = (ViewGroup) focus.getParent().getParent().getParent();
                } else if (null != focus && focus.getId() == R.id.t9_item_type2_txt4) {
                    parent = (ViewGroup) focus.getParent().getParent();
                } else if (null != focus && (focus.getId() == R.id.t9_item_type2_txt1 || focus.getId() == R.id.t9_item_type2_txt2 || focus.getId() == R.id.t9_item_type2_txt3)) {
                    parent = (ViewGroup) focus.getParent().getParent().getParent();
                } else if (null != focus && focus.getId() == R.id.t9_item_type2_txt4) {
                    parent = (ViewGroup) focus.getParent().getParent();
                } else if (null != focus && (focus.getId() == R.id.t9_item_type3_txt2 || focus.getId() == R.id.t9_item_type3_txt4)) {
                    parent = (ViewGroup) focus.getParent().getParent();
                } else if (null != focus && (focus.getId() == R.id.t9_item_type3_txt1 || focus.getId() == R.id.t9_item_type3_txt3 || focus.getId() == R.id.t9_item_type3_txt5)) {
                    parent = (ViewGroup) focus.getParent().getParent().getParent();
                }
                if (null != focus && null != parent) {
                    View view1 = parent.findViewById(R.id.t9_item_type1);
                    view1.setVisibility(View.GONE);
                    View view2 = parent.findViewById(R.id.t9_item_type2);
                    view2.setVisibility(View.GONE);
                    View view3 = parent.findViewById(R.id.t9_item_type3);
                    view3.setVisibility(View.GONE);
                    View view4 = parent.findViewById(R.id.t9_item_txt);
                    view4.setVisibility(View.VISIBLE);
                    GlideUtils.loadSkin(parent, true, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
                    parent.requestFocus();
                    return true;
                }

            } catch (Exception e) {
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            View focus = findFocus();
            if (null != focus && (focus.getId() == R.id.btn_full || focus.getId() == R.id.btn_t9)) {
                LogUtil.log("KeyboardView", "dispatchKeyEvent => up1");
                View view = findViewById(R.id.recycler_view);
                view.requestFocus();
                return true;
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            if (null != focus && focus.getId() == R.id.btn_t9) {
                ((TextView) focus).setTextColor(Color.parseColor("#ffffff"));
                TextView view = findViewById(R.id.btn_full);
                view.setTextColor(Color.parseColor("#ff673c"));
                view.requestFocus();
                curType = FULL_TYPE;
                initKeyboard();
                return true;
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            if (null != focus && focus.getId() == R.id.btn_full) {
                ((TextView) focus).setTextColor(Color.parseColor("#ffffff"));
                TextView view = findViewById(R.id.btn_t9);
                view.setTextColor(Color.parseColor("#ff673c"));
                view.requestFocus();
                curType = T9_TYPE;
                initKeyboard();
                return true;
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            View focus = findFocus();
            if (null != focus && focus.getId() == R.id.full_item) {
                CharSequence text = ((TextView) focus).getText();
                if (null == text) {
                    text = "";
                }
                if ("5".equals(text) || "6".equals(text) || "7".equals(text) || "8".equals(text) || "9".equals(text) || "0".equals(text)) {
                    TextView view = findViewById(R.id.btn_full);
                    view.requestFocus();
                    curType = FULL_TYPE;
                    initKeyboard();
                    return true;
                }
            } else if (null != focus && focus.getId() == R.id.t9_item) {
                TextView textView = focus.findViewById(R.id.t9_item_txt);
                String text = String.valueOf(textView.getText());
                if (null == text) {
                    text = "";
                }
                if (text.startsWith("7\n") || text.startsWith("8\n") || text.startsWith("9\n")) {
                    TextView view = findViewById(R.id.btn_t9);
                    view.requestFocus();
                    curType = T9_TYPE;
                    initKeyboard();
                    return true;
                }
            } else if (null != focus && (focus.getId() == R.id.btn_t9 || focus.getId() == R.id.btn_full)) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public class KeyBoard {
        public static final int T9_TYPE = 2;
        public static final int FULL_TYPE = 3;

        private List<String> values;
        private int type;

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public interface OnSearchListener {
        void onSearchKeyResult(String result);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public static class KeyboardRecyclerView extends RecyclerView {

        public KeyboardRecyclerView(Context context) {
            super(context);
            init();
        }

        public KeyboardRecyclerView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        private void init() {
            int v = (int) getResources().getDimension(R.dimen.dp_4);
            setPadding(0, v, 0, v);
        }

        @Override
        public View focusSearch(View focused, int direction) {
            try {
                View focusedChild = getFocusedChild();
                int position = getChildAdapterPosition(focusedChild);
                if (position == 0) {
                    ViewHolder viewHolder = findViewHolderForAdapterPosition(position);
                    View itemView = viewHolder.itemView;
                    View itemViewFocus = itemView.findFocus();

                    boolean change = false;
                    if (direction == View.FOCUS_LEFT && itemViewFocus.getId() == R.id.t9_item_type1_txt1) {
                        change = true;
                    } else if (direction == View.FOCUS_RIGHT && itemViewFocus.getId() == R.id.t9_item_type1_txt2) {
                        change = true;
                    } else if (direction == View.FOCUS_UP) {
                        change = true;
                    } else if (direction == View.FOCUS_DOWN) {
                        change = true;
                    }

                    if (change) {
                        View view1 = itemView.findViewById(R.id.t9_item_type1);
                        view1.setVisibility(View.GONE);
                        View view2 = itemView.findViewById(R.id.t9_item_type2);
                        view2.setVisibility(View.GONE);
                        View view3 = itemView.findViewById(R.id.t9_item_type3);
                        view3.setVisibility(View.GONE);
                        View view4 = itemView.findViewById(R.id.t9_item_txt);
                        view4.setVisibility(View.VISIBLE);
                        View view5 = itemView.findViewById(R.id.t9_item);
                        GlideUtils.loadSkin(view5, true, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
                    }

                    View viewById;
                    if (itemViewFocus.getId() == R.id.t9_item_type1_txt1 && direction == View.FOCUS_RIGHT) {
                        viewById = viewHolder.itemView.findViewById(R.id.t9_item_type1_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type1_txt2 && direction == View.FOCUS_LEFT) {
                        viewById = viewHolder.itemView.findViewById(R.id.t9_item_type1_txt1);
                        viewById.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_DOWN && (itemViewFocus.getId() == R.id.t9_item_type1_txt1 || itemViewFocus.getId() == R.id.t9_item_type1_txt1)) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_UP && (itemViewFocus.getId() == R.id.t9_item_type1_txt1 || itemViewFocus.getId() == R.id.t9_item_type1_txt1)) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_LEFT && itemViewFocus.getId() == R.id.t9_item_type1_txt1) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_RIGHT && itemViewFocus.getId() == R.id.t9_item_type1_txt2) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else {
                        return super.focusSearch(focused, direction);
                    }
                } else if (position == 6 || position == 8) {

                    ViewHolder viewHolder = findViewHolderForAdapterPosition(position);
                    View itemView = viewHolder.itemView;
                    View itemViewFocus = itemView.findFocus();

                    boolean change = false;
                    if (direction == View.FOCUS_LEFT && itemViewFocus.getId() == R.id.t9_item_type3_txt1) {
                        change = true;
                    } else if (direction == View.FOCUS_DOWN && itemViewFocus.getId() == R.id.t9_item_type3_txt4) {
                        change = true;
                    } else if (direction == View.FOCUS_UP && itemViewFocus.getId() == R.id.t9_item_type3_txt2) {
                        change = true;
                    } else if (direction == View.FOCUS_RIGHT && itemViewFocus.getId() == R.id.t9_item_type3_txt3) {
                        change = true;
                    }

                    if (change) {
                        View view1 = itemView.findViewById(R.id.t9_item_type1);
                        view1.setVisibility(View.GONE);
                        View view2 = itemView.findViewById(R.id.t9_item_type2);
                        view2.setVisibility(View.GONE);
                        View view3 = itemView.findViewById(R.id.t9_item_type3);
                        view3.setVisibility(View.GONE);
                        View view4 = itemView.findViewById(R.id.t9_item_txt);
                        view4.setVisibility(View.VISIBLE);
                        View view5 = itemView.findViewById(R.id.t9_item);
                        GlideUtils.loadSkin(view5, true, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
                    }

                    if (itemViewFocus.getId() == R.id.t9_item_type3_txt1 && direction == View.FOCUS_RIGHT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt5);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt1 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt1 && direction == View.FOCUS_DOWN) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt4);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt2 && direction == View.FOCUS_LEFT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt1);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt2 && direction == View.FOCUS_RIGHT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt3);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt2 && direction == View.FOCUS_DOWN) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt5);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt3 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt3 && direction == View.FOCUS_DOWN) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt4);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt3 && direction == View.FOCUS_LEFT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt5);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt4 && direction == View.FOCUS_LEFT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt1);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt4 && direction == View.FOCUS_RIGHT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt3);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt4 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt5);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt5 && direction == View.FOCUS_LEFT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt1);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt5 && direction == View.FOCUS_RIGHT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt3);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt5 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type3_txt5 && direction == View.FOCUS_DOWN) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type3_txt4);
                        viewById.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_LEFT && itemViewFocus.getId() == R.id.t9_item_type3_txt1) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_DOWN && itemViewFocus.getId() == R.id.t9_item_type3_txt4) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_UP && itemViewFocus.getId() == R.id.t9_item_type3_txt2) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_RIGHT && itemViewFocus.getId() == R.id.t9_item_type3_txt3) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else {
                        return super.focusSearch(focused, direction);
                    }

                } else if (position >= 1 && position <= 7) {
                    ViewHolder viewHolder = findViewHolderForAdapterPosition(position);
                    View itemView = viewHolder.itemView;
                    View itemViewFocus = itemView.findFocus();
//                Toast.makeText(getContext(), ((TextView) itemViewFocus).getText() + " => " + itemViewFocus.getId() + " => " + R.id.t9_item_type2_txt3 + " => " + direction, Toast.LENGTH_SHORT).show();

                    boolean change = false;
                    if (direction == View.FOCUS_RIGHT && (itemViewFocus.getId() == R.id.t9_item_type2_txt3 || itemViewFocus.getId() == R.id.t9_item_type2_txt4)) {
                        change = true;
                    } else if (direction == View.FOCUS_LEFT && (itemViewFocus.getId() == R.id.t9_item_type2_txt1 || itemViewFocus.getId() == R.id.t9_item_type2_txt4)) {
                        change = true;
                    } else if (direction == View.FOCUS_UP && itemViewFocus.getId() == R.id.t9_item_type2_txt4) {
                        change = true;
                    } else if (direction == View.FOCUS_DOWN && (itemViewFocus.getId() == R.id.t9_item_type2_txt1 || itemViewFocus.getId() == R.id.t9_item_type2_txt2 || itemViewFocus.getId() == R.id.t9_item_type2_txt3)) {
                        change = true;
                    }

                    if (change) {
                        View view1 = itemView.findViewById(R.id.t9_item_type1);
                        view1.setVisibility(View.GONE);
                        View view2 = itemView.findViewById(R.id.t9_item_type2);
                        view2.setVisibility(View.GONE);
                        View view3 = itemView.findViewById(R.id.t9_item_type3);
                        view3.setVisibility(View.GONE);
                        View view4 = itemView.findViewById(R.id.t9_item_txt);
                        view4.setVisibility(View.VISIBLE);
                        View view5 = itemView.findViewById(R.id.t9_item);
                        GlideUtils.loadSkin(view5, true, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
                    }

                    if (itemViewFocus.getId() == R.id.t9_item_type2_txt1 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt4);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt1 && direction == View.FOCUS_RIGHT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt2 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt4);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt2 && direction == View.FOCUS_RIGHT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt3);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt2 && direction == View.FOCUS_LEFT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt1);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt3 && direction == View.FOCUS_UP) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt4);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt3 && direction == View.FOCUS_LEFT) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (itemViewFocus.getId() == R.id.t9_item_type2_txt4 && direction == View.FOCUS_DOWN) {
                        View viewById = viewHolder.itemView.findViewById(R.id.t9_item_type2_txt2);
                        viewById.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_RIGHT && (itemViewFocus.getId() == R.id.t9_item_type2_txt3 || itemViewFocus.getId() == R.id.t9_item_type2_txt4)) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_LEFT && (itemViewFocus.getId() == R.id.t9_item_type2_txt1 || itemViewFocus.getId() == R.id.t9_item_type2_txt4)) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_UP && itemViewFocus.getId() == R.id.t9_item_type2_txt4) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    } else if (direction == View.FOCUS_DOWN && (itemViewFocus.getId() == R.id.t9_item_type2_txt1 || itemViewFocus.getId() == R.id.t9_item_type2_txt2 || itemViewFocus.getId() == R.id.t9_item_type2_txt3)) {
                        viewHolder.itemView.requestFocus();
                        return null;
                    }
//                else if ((position == 2 || position == 5) && itemViewFocus.getId() == R.id.t9_item && direction == View.FOCUS_RIGHT) {
//                    ViewHolder viewHolder1 = findViewHolderForAdapterPosition(position + 1);
//                    viewHolder1.itemView.requestFocus();
//                    return null;
//                }
                    else {
                        return super.focusSearch(focused, direction);
                    }
                } else {
                    return super.focusSearch(focused, direction);
                }
            } catch (Exception e) {
                return super.focusSearch(focused, direction);
            }
        }
    }

    public static class KeyboardRelativeLayoutT9 extends RelativeLayout {
        public KeyboardRelativeLayoutT9(Context context) {
            super(context);
            init();
        }

        public KeyboardRelativeLayoutT9(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardRelativeLayoutT9(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardRelativeLayoutT9(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }

        @Override
        public void setBackgroundColor(int color) {
            if (color == Color.TRANSPARENT) {
                setTag(getId(), "1");
            }
            super.setBackgroundColor(color);
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            ViewCompat.animate(this).scaleX(focused ? 1.04f : 1f).scaleY(focused ? 1.04f : 1f).start();
            Object tag = getTag(getId());
            if (null != tag) {
                setTag(getId(), null);
            } else {
                GlideUtils.loadSkin(this, focused, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
            }
        }

        private void init() {
            setBackgroundResource(R.drawable.bg_selector_keyboard1);
        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class KeyboardTextViewT9 extends TextView {
        public KeyboardTextViewT9(Context context) {
            super(context);
            init();
        }

        public KeyboardTextViewT9(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardTextViewT9(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardTextViewT9(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            GlideUtils.loadSkin(this, focused, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
        }

        private void init() {
            setBackgroundResource(R.drawable.bg_selector_keyboard1);
        }
    }

    public static class KeyboardLinearLayout extends LinearLayout {
        public KeyboardLinearLayout(Context context) {
            super(context);
        }

        public KeyboardLinearLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public KeyboardLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class KeyboardTextViewFull extends TextView {
        public KeyboardTextViewFull(Context context) {
            super(context);
            init();
        }

        public KeyboardTextViewFull(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardTextViewFull(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardTextViewFull(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            ViewCompat.animate(this).scaleX(focused ? 1.05f : 1f).scaleY(focused ? 1.05f : 1f).start();
            GlideUtils.loadSkin(this, focused, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
        }

        private void init() {
            setBackgroundResource(R.drawable.bg_selector_keyboard1);
        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class KeyboardTextViewSkin extends TextView {
        public KeyboardTextViewSkin(Context context) {
            super(context);
            init();
        }

        public KeyboardTextViewSkin(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardTextViewSkin(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardTextViewSkin(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            ViewCompat.animate(this).scaleX(focused ? 1.05f : 1f).scaleY(focused ? 1.05f : 1f).start();
            GlideUtils.loadSkin(this, focused, mProdId, R.drawable.bg_selector_keyboard1, GlideUtils.skin_nine_keyboard_focus);
        }

        private void init() {
            setBackgroundResource(R.drawable.bg_selector_keyboard1);
        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class KeyboardMenuTextView extends TextView {
        public KeyboardMenuTextView(Context context) {
            super(context);
            init();
        }

        public KeyboardMenuTextView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardMenuTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardMenuTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);

            TextPaint paint = getPaint();
            float measureText = paint.measureText(String.valueOf(getText()));
            int width = getWidth();
            int height = getHeight();
            float left = width / 2 - measureText / 2;
            float top = height * 0.92f;
            float right = width / 2 + measureText / 2;
            float bottom = height * 0.98f;
            RectF rectF = new RectF(left, top, right, bottom);
            if (!hasFocus() || paint.getColor() != Color.parseColor("#ff673c")) {
                paint.setColor(Color.TRANSPARENT);
            }
            canvas.drawRoundRect(rectF, 1, 1, paint);
        }

        private void init() {
            setFocusable(true);
            setFocusableInTouchMode(true);
            setClickable(true);
            setBackgroundResource(getId() == R.id.btn_full ? R.drawable.bg_selector_keyboard_menu_left : R.drawable.bg_selector_keyboard_menu_right);
        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class KeyboardTextViewT91 extends TextView {
        public KeyboardTextViewT91(Context context) {
            super(context);
            init();
        }

        public KeyboardTextViewT91(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardTextViewT91(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardTextViewT91(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            setTextColor(focused ? Color.WHITE : Color.BLACK);
            ViewCompat.animate(this).scaleX(focused ? 1.1f : 1f).scaleY(focused ? 1.1f : 1f).start();
            GlideUtils.loadSkin(this, focused, mProdId, R.drawable.bg_selector_keyboard2, GlideUtils.skin_nine_keyboard_focus);
        }

        private void init() {
            setBackgroundResource(R.drawable.bg_selector_keyboard2);
        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class KeyboardTextViewT92 extends TextView {
        public KeyboardTextViewT92(Context context) {
            super(context);
            init();
        }

        public KeyboardTextViewT92(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public KeyboardTextViewT92(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public KeyboardTextViewT92(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            setTextColor(focused ? Color.WHITE : Color.BLACK);
            ViewCompat.animate(this).scaleX(focused ? 1.1f : 1f).scaleY(focused ? 1.1f : 1f).start();
            GlideUtils.loadSkin(this, focused, mProdId, R.drawable.bg_selector_keyboard2, GlideUtils.skin_nine_keyboard_focus);
        }

        private void init() {
            setBackgroundResource(R.drawable.bg_selector_keyboard2);
        }
    }

    @Keep
    @SuppressLint("AppCompatCustomView")
    public static class SearchTextView extends TextView {
        public SearchTextView(Context context) {
            super(context);
        }

        public SearchTextView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public SearchTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public SearchTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void draw(Canvas canvas) {
            TextPaint paint = getPaint();
            paint.setAlpha(100);
            super.draw(canvas);
            paint.setColor(Color.parseColor("#06031e"));
            int dip2px = DisplayUtil.dip2px(getContext(), 2);
            canvas.drawLine(0, getHeight() - dip2px / 2, getWidth(), getHeight() - dip2px / 2, paint);
        }
    }

    private final class KeyboardAdapter extends BaseRecyclerViewAdapter<KeyBoard> {

        public KeyboardAdapter(Context context, List<KeyboardView.KeyBoard> datas) {
            super(context, datas);
        }

        @Override
        public int onLayoutId(int viewType) {
            return viewType == KeyboardView.KeyBoard.T9_TYPE ? R.layout.keyboard_layout_item_t9 : R.layout.keyboard_layout_item_full;
        }

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            final BaseRecyclerViewHolder holder = super.onCreateViewHolder(parent, viewType);

            if (null != holder.itemView) {
                if (viewType == KeyboardView.KeyBoard.FULL_TYPE) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            TextView textView = (TextView) view;
                            CharSequence text = textView.getText();
                            KeyboardView keyboardView = (KeyboardView) holder.itemView.getParent().getParent().getParent();
                            keyboardView.append(String.valueOf(text));
                        }
                    });
                } else {
                    // click
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                View view1 = holder.itemView.findViewById(R.id.t9_item);
                                view1.setBackgroundColor(Color.TRANSPARENT);
                                View view2 = holder.itemView.findViewById(R.id.t9_item_txt);
                                view2.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }

                            int adapterPosition = holder.getAdapterPosition();

                            // 2
                            if (adapterPosition == 0) {
                                View view2 = holder.itemView.findViewById(R.id.t9_item_type2);
                                view2.setVisibility(View.GONE);
                                View view3 = holder.itemView.findViewById(R.id.t9_item_type3);
                                view3.setVisibility(View.GONE);
                                View view1 = holder.itemView.findViewById(R.id.t9_item_type1);
                                view1.setVisibility(View.VISIBLE);
                                View view11 = view1.findViewById(R.id.t9_item_type1_txt1);
                                view11.requestFocus();
                            }
                            // 5
                            else if (adapterPosition == 6 || adapterPosition == 8) {
                                View view1 = holder.itemView.findViewById(R.id.t9_item_type1);
                                view1.setVisibility(View.GONE);
                                View view2 = holder.itemView.findViewById(R.id.t9_item_type2);
                                view2.setVisibility(View.GONE);
                                View view3 = holder.itemView.findViewById(R.id.t9_item_type3);
                                view3.setVisibility(View.VISIBLE);
                                View view31 = view3.findViewById(R.id.t9_item_type3_txt5);
                                view31.requestFocus();
                            }
                            // 4
                            else {
                                View view1 = holder.itemView.findViewById(R.id.t9_item_type1);
                                view1.setVisibility(View.GONE);
                                View view3 = holder.itemView.findViewById(R.id.t9_item_type3);
                                view3.setVisibility(View.GONE);
                                View view2 = holder.itemView.findViewById(R.id.t9_item_type2);
                                view2.setVisibility(View.VISIBLE);
                                View view21 = view2.findViewById(R.id.t9_item_type2_txt4);
                                view21.requestFocus();
                            }
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type1_txt1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type1_txt2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type2_txt1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type2_txt2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type2_txt3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type2_txt4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type3_txt1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type3_txt2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type3_txt3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type3_txt4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                    holder.itemView.findViewById(R.id.t9_item_type3_txt5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appendText(holder, view);
                        }
                    });
                }
            }
            return holder;
        }

        private final void appendText(@Nullable RecyclerView.ViewHolder holder, @NonNull View view) {
            try {
                TextView textView = (TextView) view;
                CharSequence text = textView.getText();
                KeyboardView keyboardView = (KeyboardView) holder.itemView.getParent().getParent().getParent();
                keyboardView.append(String.valueOf(text));
            } catch (Exception e) {
            }
        }

        @Override
        public void onBindViewHolder(final BaseRecyclerViewHolder holder, int position) {
            KeyboardView.KeyBoard data = mDatas.get(position);
            int viewType = data.getType();
            switch (viewType) {
                case KeyboardView.KeyBoard.FULL_TYPE:
                    try {
                        TextView textView = (TextView) holder.itemView;
                        textView.setText(data.getValues().get(0));
                    } catch (Exception e) {
                    }
                    break;
                case KeyboardView.KeyBoard.T9_TYPE:

                    try {
                        List<String> dataValues = data.getValues();
                        // 2
                        if (null != dataValues && dataValues.size() == 2) {

                            View view1 = holder.itemView.findViewById(R.id.t9_item_type1);
                            view1.setVisibility(View.GONE);
                            View view2 = holder.itemView.findViewById(R.id.t9_item_type2);
                            view2.setVisibility(View.GONE);
                            View view3 = holder.itemView.findViewById(R.id.t9_item_type3);
                            view3.setVisibility(View.GONE);

                            holder.setText(R.id.t9_item_type2_txt1, "");
                            holder.setText(R.id.t9_item_type2_txt2, "");
                            holder.setText(R.id.t9_item_type2_txt3, "");
                            holder.setText(R.id.t9_item_type2_txt4, "");

                            holder.setText(R.id.t9_item_type3_txt1, "");
                            holder.setText(R.id.t9_item_type3_txt2, "");
                            holder.setText(R.id.t9_item_type3_txt3, "");
                            holder.setText(R.id.t9_item_type3_txt4, "");
                            holder.setText(R.id.t9_item_type3_txt5, "");

                            String s0 = dataValues.get(0);
                            holder.setText(R.id.t9_item_type1_txt1, s0);
                            String s1 = dataValues.get(1);
                            holder.setText(R.id.t9_item_type1_txt2, s1);
                            holder.setText(R.id.t9_item_txt, s0 + "  " + s1 + "\n");
                        }
                        // 5
                        else if (null != dataValues && dataValues.size() == 5) {

                            View view1 = holder.itemView.findViewById(R.id.t9_item_type1);
                            view1.setVisibility(View.GONE);
                            View view2 = holder.itemView.findViewById(R.id.t9_item_type2);
                            view2.setVisibility(View.GONE);
                            View view3 = holder.itemView.findViewById(R.id.t9_item_type3);
                            view3.setVisibility(View.GONE);

                            holder.setText(R.id.t9_item_type1_txt1, "");
                            holder.setText(R.id.t9_item_type1_txt2, "");

                            holder.setText(R.id.t9_item_type2_txt1, "");
                            holder.setText(R.id.t9_item_type2_txt2, "");
                            holder.setText(R.id.t9_item_type2_txt3, "");
                            holder.setText(R.id.t9_item_type2_txt4, "");

                            String s0 = dataValues.get(0);
                            holder.setText(R.id.t9_item_type3_txt5, s0);
                            String s1 = dataValues.get(1);
                            holder.setText(R.id.t9_item_type3_txt1, s1);
                            String s2 = dataValues.get(2);
                            holder.setText(R.id.t9_item_type3_txt2, s2);
                            String s3 = dataValues.get(3);
                            holder.setText(R.id.t9_item_type3_txt3, s3);
                            String s4 = dataValues.get(4);
                            holder.setText(R.id.t9_item_type3_txt4, s4);
                            holder.setText(R.id.t9_item_txt, s0 + "\n" + s1 + " " + s2 + " " + s3 + " " + s4);

                        }
                        // 4
                        else {

                            View view1 = holder.itemView.findViewById(R.id.t9_item_type1);
                            view1.setVisibility(View.GONE);
                            View view2 = holder.itemView.findViewById(R.id.t9_item_type2);
                            view2.setVisibility(View.GONE);
                            View view3 = holder.itemView.findViewById(R.id.t9_item_type3);
                            view3.setVisibility(View.GONE);

                            holder.setText(R.id.t9_item_type1_txt1, "");
                            holder.setText(R.id.t9_item_type1_txt2, "");

                            holder.setText(R.id.t9_item_type3_txt1, "");
                            holder.setText(R.id.t9_item_type3_txt2, "");
                            holder.setText(R.id.t9_item_type3_txt3, "");
                            holder.setText(R.id.t9_item_type3_txt4, "");
                            holder.setText(R.id.t9_item_type3_txt5, "");

                            String s0 = dataValues.get(0);
                            holder.setText(R.id.t9_item_type2_txt4, s0);
                            String s1 = dataValues.get(1);
                            holder.setText(R.id.t9_item_type2_txt1, s1);
                            String s2 = dataValues.get(2);
                            holder.setText(R.id.t9_item_type2_txt2, s2);
                            String s3 = dataValues.get(3);
                            holder.setText(R.id.t9_item_type2_txt3, s3);
                            holder.setText(R.id.t9_item_txt, s0 + "\n" + s1 + " " + s2 + " " + s3);
                        }

                    } catch (Exception e) {
                    }
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            return mDatas.get(position).getType();
        }
    }
}

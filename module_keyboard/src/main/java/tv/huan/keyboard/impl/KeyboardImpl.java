package tv.huan.keyboard.impl;

import androidx.annotation.NonNull;

import tv.huan.keyboard.listener.OnKeyboardInputListener;

import java.util.LinkedList;

public interface KeyboardImpl {

    LinkedList<String> mInput = new LinkedList<>();
    OnKeyboardInputListener[] mListener = new OnKeyboardInputListener[1];

    default boolean hasListener() {
        try {
            return null != mListener && mListener.length == 1 && null != mListener[0];
        } catch (Exception e) {
            return false;
        }
    }

    default void setOnKeyboardInputListener(OnKeyboardInputListener l) {
        try {
            mListener[0] = null;
            mListener[0] = l;
        } catch (Exception e) {
        }
    }

    default String getInput() {
        int length = mInput.size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String s = mInput.get(i);
            builder.append(s);
        }
        return builder.toString();
    }

    default void delete() {
        mInput.removeLast();
        boolean hasListener = hasListener();
        if (hasListener) {
            String toString = getInput();
            mListener[0].onInput(toString);
        }
    }

    default void clear() {
        mInput.clear();
        boolean hasListener = hasListener();
        if (hasListener) {
            String toString = getInput();
            mListener[0].onInput(toString);
        }
    }

    default void append(@NonNull String v) {
        append(false, v);
    }

    default void append(@NonNull boolean clean, @NonNull String v) {
        if (clean) {
            mInput.clear();
        }
        mInput.addLast(v);
        boolean hasListener = hasListener();
        if (hasListener) {
            String toString = getInput();
            mListener[0].onInput(toString);
        }
    }

//    /**
//     * 默认请求焦点
//     */
//    public void requestKeyboard() {
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        if (null != recyclerView.getAdapter()) {
//            recyclerView.requestFocus();
////            recyclerView.setSelection(12);
//        }
//    }
}

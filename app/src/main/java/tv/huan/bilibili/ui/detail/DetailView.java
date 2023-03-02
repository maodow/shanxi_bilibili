package tv.huan.bilibili.ui.detail;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;

public interface DetailView extends BaseViewImpl {

    void refreshContent();

    void showDialog(@NonNull String title, @NonNull String data1, @NonNull String data2);

    void cancleFavor(@NonNull String cid, @NonNull String recClassId);

    void addFavor(@NonNull String cid, @NonNull String recClassId);

    void updateFavor(boolean status);

    void jumpVip();

    void delayPlayer(int position);
    void startPlayer(int position);
    void nextPlayer(int position);
    void stopPlayer();
}

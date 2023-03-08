package tv.huan.bilibili.ui.detail;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.MediaBean;

public interface DetailView extends BaseViewImpl {

    void refreshContent();

    void showDialog(@NonNull String title, @NonNull String data1, @NonNull String data2);

    void cancleFavor(@NonNull String cid, @NonNull String recClassId);

    void addFavor(@NonNull String cid, @NonNull String recClassId);

    void updateFavor(boolean status);

    void updateVid(@NonNull MediaBean data);

    void updateData(@NonNull MediaBean data, boolean isFromUser);

    void delayPlayer(@NonNull MediaBean data, boolean isFromUser);

    void startPlayer(@NonNull MediaBean data, boolean isFromUser);
    void startPlayerNext();

    void stopPlayer();

    void completePlayer();

    void jumpVip();
}

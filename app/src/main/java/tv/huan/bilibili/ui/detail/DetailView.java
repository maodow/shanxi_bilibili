package tv.huan.bilibili.ui.detail;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.Media;

public interface DetailView extends BaseViewImpl {

    void refreshContent();

    void showDialog(@NonNull String title, @NonNull String data1, @NonNull String data2);

    void cancleFavor(@NonNull String cid, @NonNull String recClassId);

    void addFavor(@NonNull String cid, @NonNull String recClassId);

    void updateFavor(boolean status);

    void jumpVip();

    void delayPlayer(int position);

    void startPlayer(int position);

    void stopPlayer();

    void updatePlayerInfo(int position, String vid);
    void updateVid(String vid);
}

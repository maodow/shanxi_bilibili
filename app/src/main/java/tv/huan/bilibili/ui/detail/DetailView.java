package tv.huan.bilibili.ui.detail;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.MediaBean;

public interface DetailView extends BaseViewImpl {

    void showDialog(@NonNull String title, @NonNull String data1, @NonNull String data2);

    void cancleFavor(@NonNull String cid, @NonNull String recClassId);

    void addFavor(@NonNull String cid, @NonNull String recClassId);

    void updateFavor(boolean status);

    void updatePlayerPosition(@NonNull MediaBean data);

    boolean isPlayerPlayingPosition(@NonNull int position);

    void startPlayerPosition(@NonNull int position);

    void startPlayerPosition(@NonNull MediaBean data);

    void startPlayerPosition(@NonNull MediaBean data, @NonNull int pos,  @NonNull long seek,  boolean isFromUser);

    void jumpVip();

    void huaweiAuth(String cid, long seek);

    void huaweiSucc(String s, long seek);

    int getPlayerNextPosition();

    void startFull();
}

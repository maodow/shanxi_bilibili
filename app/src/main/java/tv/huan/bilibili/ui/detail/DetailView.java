package tv.huan.bilibili.ui.detail;

import androidx.annotation.NonNull;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.MediaBean;

public interface DetailView extends BaseViewImpl {

    void showDialog(@NonNull String title, @NonNull String data1, @NonNull String data2);

    void cancleFavor(@NonNull String cid, @NonNull String recClassId);

    void addFavor(@NonNull String cid, @NonNull String recClassId);

    void updateFavor(boolean status);

    void updateVidAndClassId(@NonNull MediaBean data);

    void delayStartPlayer(@NonNull MediaBean data, boolean isFromUser);

    void checkPlayer(@NonNull MediaBean data, boolean isFromUser);

    void updatePlayerInfo(@NonNull MediaBean data, boolean isFromUser);

    void checkedPlayerPosition(@NonNull MediaBean data);

    void checkedPlayerPositionNext();

    void stopPlayer();

    void completePlayer();

    void jumpVip(boolean isFromUser);

    void stopFull();

    void huaweiAuth(String cid, long seek);

    void huaweiSucc(String s, long seek);
}

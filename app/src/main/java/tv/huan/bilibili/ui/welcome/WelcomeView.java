package tv.huan.bilibili.ui.welcome;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.GetChannelsBean;

public interface WelcomeView extends BaseViewImpl {

    void refreshAD(@NonNull String backgroundUrl);

    void refreshTime(@NonNull String text);

    void next(@NonNull String data, @NonNull int select, @NonNull int type, @NonNull String cid, @NonNull int classId, @NonNull String secondTag);
}

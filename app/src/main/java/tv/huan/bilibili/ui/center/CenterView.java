package tv.huan.bilibili.ui.center;

import androidx.annotation.NonNull;
import tv.huan.bilibili.base.BaseViewImpl;

public interface CenterView extends BaseViewImpl {

    void refreshContent();

    void del(@NonNull int index);
}

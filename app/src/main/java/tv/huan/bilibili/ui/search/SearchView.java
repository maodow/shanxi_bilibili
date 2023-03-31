package tv.huan.bilibili.ui.search;

import androidx.annotation.NonNull;

import java.util.List;

import tv.huan.bilibili.base.BaseViewImpl;
import tv.huan.bilibili.bean.SearchBean;

public interface SearchView extends BaseViewImpl {

    void updateInput();

    void updateKeys(@NonNull List<SearchBean.KeyBean> data);
}

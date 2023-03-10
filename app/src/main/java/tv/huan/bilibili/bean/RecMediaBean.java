package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lib.kalu.leanback.presenter.bean.TvPresenterRowBean;

@Keep
public class RecMediaBean extends MediaBean implements Serializable {

    @Override
    public int getToType() {
        return 1;
    }
}

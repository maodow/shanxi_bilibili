package tv.huan.bilibili.bean.base;

import androidx.annotation.Keep;

import java.util.List;

@Keep
public class BaseAuthorizationBean extends BaseResponsedBean<Object> {

    private String returncode;
    private List<ItemBean> urls;

    public List<ItemBean> getUrls() {
        return urls;
    }

    public void setUrls(List<ItemBean> urls) {
        this.urls = urls;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    @Keep
    public final static class ItemBean {
        private String playurl;

        public String getPlayurl() {
            return playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }
    }

    @Override
    public List<ItemBean> getData() {
        return urls;
    }

    @Override
    public boolean isNext() {
        return true;
    }
}

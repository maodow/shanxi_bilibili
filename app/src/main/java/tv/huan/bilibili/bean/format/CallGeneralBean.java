package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.base.BaseDataBean;

public final class CallGeneralBean extends BaseDataBean implements Serializable {

    private List<GetSubChannelsByChannelBean.ListBean> datas;

    public List<GetSubChannelsByChannelBean.ListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<GetSubChannelsByChannelBean.ListBean> datas) {
        this.datas = datas;
    }
}

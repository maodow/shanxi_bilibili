package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.base.BaseDataBean;

public final class CallGeneralBean extends BaseDataBean implements Serializable {

    private List<GetSubChannelsByChannelBean.ListBean> templateDatas;

    public List<GetSubChannelsByChannelBean.ListBean> getTemplateDatas() {
        return templateDatas;
    }

    public void setTemplateDatas(List<GetSubChannelsByChannelBean.ListBean> templateDatas) {
        this.templateDatas = templateDatas;
    }
}

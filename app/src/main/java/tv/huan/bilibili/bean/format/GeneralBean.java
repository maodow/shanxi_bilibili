package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.List;

import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;

public final class GeneralBean implements Serializable {

    private int classId;
    private List<GetSubChannelsByChannelBean.ListBean> datas;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<GetSubChannelsByChannelBean.ListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<GetSubChannelsByChannelBean.ListBean> datas) {
        this.datas = datas;
    }
}

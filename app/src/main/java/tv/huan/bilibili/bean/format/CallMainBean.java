package tv.huan.bilibili.bean.format;

import java.io.Serializable;
import java.util.List;

import lib.kalu.leanback.tab.model.TabModel;

public final class CallMainBean implements Serializable {

    private int position;
    private List<TabModel> tabModels;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<TabModel> getTabModels() {
        return tabModels;
    }

    public void setTabModels(List<TabModel> tabModels) {
        this.tabModels = tabModels;
    }
}

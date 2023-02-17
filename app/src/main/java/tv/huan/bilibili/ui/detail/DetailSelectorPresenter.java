package tv.huan.bilibili.ui.detail;


import tv.huan.bilibili.ui.detail.template.DetailTemplateFav;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanJi;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanQi;

public class DetailSelectorPresenter extends lib.kalu.leanback.selector.BasePresenterSelector {

    @Override
    protected void init() {
        // 播放器
        addPresenterCustom(DetailTemplatePlayer.DetailTemplatePlayerObject.class, new DetailTemplatePlayer());
        // 猜你喜欢
        addPresenterCustom(DetailTemplateFav.DetailTemplateFavList.class, new DetailTemplateFav());
        // 选期列表
        addPresenterCustom(DetailTemplateXuanQi.DetailTemplateXuanQiList.class, new DetailTemplateXuanQi());
        // 选集列表
        addPresenterCustom(DetailTemplateXuanJi.DetailTemplateXuanJiList.class, new DetailTemplateXuanJi());
    }
}

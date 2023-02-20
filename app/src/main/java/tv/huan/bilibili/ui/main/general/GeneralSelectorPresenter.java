package tv.huan.bilibili.ui.main.general;


import tv.huan.bilibili.ui.main.general.template.GeneralTemplate1;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate10;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate11;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate12;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate13;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate14;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate15;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate16;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate17;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate18;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate2;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate20;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate21;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate3;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate4;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate5;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate6;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate7;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate8;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate9;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplateBottom;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplateClass;

public class GeneralSelectorPresenter extends lib.kalu.leanback.selector.BasePresenterSelector {

    @Override
    protected void init() {
        // 1
        addPresenterCustom(GeneralTemplateBottom.class, new GeneralTemplateBottom());
        addPresenterCustom(GeneralTemplateClass.GeneralTemplateClassList.class, new GeneralTemplateClass());
        // 2
        addPresenterCustom(GeneralTemplate1.GeneralTemplate1List.class, new GeneralTemplate1());
        addPresenterCustom(GeneralTemplate2.GeneralTemplate2List.class, new GeneralTemplate2());
        addPresenterCustom(GeneralTemplate3.GeneralTemplate3List.class, new GeneralTemplate3());
        addPresenterCustom(GeneralTemplate4.GeneralTemplate4List.class, new GeneralTemplate4());
        addPresenterCustom(GeneralTemplate5.GeneralTemplate5List.class, new GeneralTemplate5());
        addPresenterCustom(GeneralTemplate6.GeneralTemplate6List.class, new GeneralTemplate6());
        addPresenterCustom(GeneralTemplate7.GeneralTemplate7List.class, new GeneralTemplate7());
        addPresenterCustom(GeneralTemplate8.GeneralTemplate8List.class, new GeneralTemplate8());
        addPresenterCustom(GeneralTemplate9.GeneralTemplate9List.class, new GeneralTemplate9());
        addPresenterCustom(GeneralTemplate10.GeneralTemplate10List.class, new GeneralTemplate10());
        addPresenterCustom(GeneralTemplate11.GeneralTemplate11List.class, new GeneralTemplate11());
        addPresenterCustom(GeneralTemplate12.GeneralTemplate12List.class, new GeneralTemplate12());
        addPresenterCustom(GeneralTemplate13.GeneralTemplate13List.class, new GeneralTemplate13());
        addPresenterCustom(GeneralTemplate14.GeneralTemplate14List.class, new GeneralTemplate14());
        addPresenterCustom(GeneralTemplate15.GeneralTemplate15List.class, new GeneralTemplate15());
        addPresenterCustom(GeneralTemplate16.GeneralTemplate16List.class, new GeneralTemplate16());
        addPresenterCustom(GeneralTemplate17.GeneralTemplate17List.class, new GeneralTemplate17());
        addPresenterCustom(GeneralTemplate18.GeneralTemplate18List.class, new GeneralTemplate18());
        addPresenterCustom(GeneralTemplate20.GeneralTemplate20List.class, new GeneralTemplate20());
        addPresenterCustom(GeneralTemplate21.GeneralTemplate21List.class, new GeneralTemplate21());
    }
}

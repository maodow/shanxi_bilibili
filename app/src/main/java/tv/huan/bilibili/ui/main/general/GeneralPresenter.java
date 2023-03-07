
package tv.huan.bilibili.ui.main.general;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.format.GeneralBean;
import tv.huan.bilibili.http.HttpClient;
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
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate19;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate2;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate20;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate21;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate22;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate3;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate4;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate5;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate6;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate7;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate8;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplate9;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplateBottom;
import tv.huan.bilibili.ui.main.general.template.GeneralTemplateClass;

@Keep
public class GeneralPresenter extends BasePresenterImpl<GeneralView> {
    public GeneralPresenter(@NonNull GeneralView commonView) {
        super(commonView);
    }

    protected final void setAdapter() {
        VerticalGridView verticalGridView = getView().findViewById(R.id.general_list);
        GeneralSelectorPresenter selectorPresenter = new GeneralSelectorPresenter();
        ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(selectorPresenter);
        ItemBridgeAdapter bridgeAdapter = new ItemBridgeAdapter(objectAdapter);
        verticalGridView.setAdapter(bridgeAdapter);
    }

    protected final void request() {

        VerticalGridView verticalGridView = getView().findViewById(R.id.general_list);
        RecyclerView.Adapter objectAdapter = verticalGridView.getAdapter();
        int itemCount = objectAdapter.getItemCount();
        if (itemCount <= 0) {
            getSubChannelsByChannel();
        } else {
            refreshTemplate17Rec();
        }
    }

    private void refreshTemplate17Rec() {
        try {
            // 1
            VerticalGridView verticalGridView = getView().findViewById(R.id.general_list);
            ItemBridgeAdapter objectAdapter = (ItemBridgeAdapter) verticalGridView.getAdapter();
            ArrayObjectAdapter arrayObjectAdapter = (ArrayObjectAdapter) objectAdapter.getAdapter();

            int position = -1;
            int size = arrayObjectAdapter.size();
            for (int i = 0; i < size; i++) {
                Object o = arrayObjectAdapter.get(i);
                if (null == o)
                    continue;
                if (o instanceof GeneralTemplate17.GeneralTemplate17List) {
                    position = i;
                    break;
                }
            }
            // update
            if (position != -1) {
                arrayObjectAdapter.notifyArrayItemRangeChanged(position, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSubChannelsByChannel() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                // 瀑布流
                .flatMap(new Function<Boolean, Observable<BaseBean<GetSubChannelsByChannelBean>>>() {
                    @Override
                    public Observable<BaseBean<GetSubChannelsByChannelBean>> apply(Boolean b) {
                        int channelId = getView().getIntExtra(GeneralFragment.BUNDLE_CHANNELID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getSubChannelsByChannel(channelId, 0, Integer.MAX_VALUE);
                    }
                })
                // 瀑布流 => 数据处理
                .map(new Function<BaseBean<GetSubChannelsByChannelBean>, GeneralBean>() {
                    @Override
                    public GeneralBean apply(BaseBean<GetSubChannelsByChannelBean> data) {

                        GeneralBean generalBean = new GeneralBean();

                        // 瀑布流
                        try {
                            generalBean.setDatas(data.getData().getList());
                        } catch (Exception e) {
                            generalBean.setDatas(new ArrayList<>());
                        }

                        // classId
                        try {
                            int classId = getView().getIntExtra(GeneralFragment.BUNDLE_CLASSID, -1);
                            if (classId == -1)
                                throw new Exception();
                            generalBean.setClassId(classId);
                        } catch (Exception e) {
                            generalBean.setClassId(0);
                        }

                        // 上报
//                        try {
//                            int channelId = getView().getIntExtra(GeneralFragment.BUNDLE_CHANNELID, 0);
//                            String name = getView().getStringExtra(GeneralFragment.BUNDLE_NAME);
//                            reportChannelLoadFinished(channelId, name);
//                        } catch (Exception e) {
//                        }

                        return generalBean;
                    }
                })
                // 二级菜单
                .flatMap(new Function<GeneralBean, ObservableSource<BaseBean<GetSubChannelsByChannelBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<GetSubChannelsByChannelBean>> apply(GeneralBean data) {
                        int classId = data.getClassId();
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getClassByPrentId(classId, s);
                    }
                })
                // 二级菜单 => 数据处理
                .map(new Function<BaseBean<GetSubChannelsByChannelBean>, GeneralBean>() {
                    @Override
                    public GeneralBean apply(BaseBean<GetSubChannelsByChannelBean> resp) {
                        GeneralBean generalBean;
                        try {
                            generalBean = new Gson().fromJson(resp.getExtra(), GeneralBean.class);
                        } catch (Exception e) {
                            generalBean = new GeneralBean();
                        }

                        List<GetSubChannelsByChannelBean.ListBean> newsData = new LinkedList<>();
                        List<GetSubChannelsByChannelBean.ListBean> oldsData = generalBean.getDatas();
                        int size = oldsData.size();
                        // 模板 => 遍历
                        for (int i = 0; i < size; i++) {

                            // 二级分类[固定第二个]
                            if (i == 1) {
                                GetSubChannelsByChannelBean classData = resp.getData();
                                if (null != classData) {
                                    List<GetSubChannelsByChannelBean.ClassesBean> classes = classData.getClasses();
                                    if (null != classes) {
                                        int num = classes.size();
                                        if (num > 0) {
                                            // 1
                                            GetSubChannelsByChannelBean.ListBean generalClass = new GetSubChannelsByChannelBean.ListBean();
                                            generalClass.setPreTemplate(-1);
                                            // 2
                                            if (num > 5) {
                                                num = 5;
                                            }
                                            // 3
                                            ArrayList<GetSubChannelsByChannelBean.ListBean.TemplateBean> tempList = new ArrayList<>();
                                            for (int n = 0; n < num; n++) {
                                                GetSubChannelsByChannelBean.ClassesBean temp = classes.get(n);
                                                if (null == temp)
                                                    continue;
                                                String name = temp.getName();
                                                GetSubChannelsByChannelBean.ListBean.TemplateBean bean = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
                                                bean.setName(name);
                                                bean.setClassId(resp.getData().getParent().getId());
                                                bean.setToType(2);
                                                tempList.add(bean);
                                            }
                                            // 4
                                            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
                                            bean.setName("更多分类");
                                            bean.setClassId(resp.getData().getParent().getId());
                                            bean.setToType(2);
                                            tempList.add(bean);
                                            // 5
                                            generalClass.setTemplateData(tempList);
                                            // 6
                                            newsData.add(generalClass);
                                        }
                                    }
                                }
                            }

                            GetSubChannelsByChannelBean.ListBean listBean = oldsData.get(i);
                            if (null == listBean)
                                continue;
                            List<GetSubChannelsByChannelBean.ListBean.TemplateBean> templateData = listBean.getTemplateData();
                            if (null == templateData)
                                continue;
                            int num = templateData.size();
                            if (num <= 0)
                                continue;
                            List<GetSubChannelsByChannelBean.ListBean.TemplateBean> tempList = new ArrayList<>();
                            for (int n = 0; n < num; n++) {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean temp = templateData.get(n);
                                if (null == temp)
                                    continue;
                                temp.setClassId(generalBean.getClassId());
                                int preTemplate = listBean.getPreTemplate();
                                // 模板1
                                if (1 == preTemplate) {
                                    if (n == 0) {
                                        tempList.add(temp);
                                    }
                                }
                                // 模板16
                                else if (16 == preTemplate) {
                                    if (n == 0) {
                                        tempList.add(new GetSubChannelsByChannelBean.ListBean.TemplateBean());
                                        tempList.add(temp);
                                    }
                                }
                                // 模板17
                                else if (17 == preTemplate) {
                                    if (n == 1) {
                                        tempList.add(temp);
                                    }
                                }
                                tempList.add(temp);
                            }
                            listBean.setTemplateData(tempList);
                            newsData.add(listBean);
                        }
                        return generalBean;
                    }
                })
                // 瀑布流 => 填充数据
                .map(new Function<GeneralBean, Boolean>() {
                    @Override
                    public Boolean apply(GeneralBean generalBean) {
                        try {
                            List<GetSubChannelsByChannelBean.ListBean> datas = generalBean.getDatas();
                            if (null != datas) {
                                int size = datas.size();
                                if (size > 0) {
                                    VerticalGridView verticalGridView = getView().findViewById(R.id.general_list);
                                    RecyclerView.Adapter adapter = verticalGridView.getAdapter();
                                    if (null != adapter && adapter instanceof ItemBridgeAdapter) {
                                        ObjectAdapter objectAdapter = ((ItemBridgeAdapter) adapter).getAdapter();
                                        if (null != objectAdapter && objectAdapter instanceof ArrayObjectAdapter) {
                                            // 1
                                            ((ArrayObjectAdapter) objectAdapter).clear(false);
                                            // 2
                                            for (int i = 0; i < size; i++) {
                                                GetSubChannelsByChannelBean.ListBean bean = datas.get(i);
                                                if (null == bean)
                                                    continue;

                                                List<GetSubChannelsByChannelBean.ListBean.TemplateBean> templateData = bean.getTemplateData();
                                                int preTemplate = bean.getPreTemplate();

                                                // 测试模板
                                                if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
                                                    for (int n = 1; n <= 22; n++) {
                                                        if (n == 19)
                                                            continue;
                                                        ArrayList<GetSubChannelsByChannelBean.ListBean.TemplateBean> temps = new ArrayList<>();
                                                        for (GetSubChannelsByChannelBean.ListBean.TemplateBean temp : templateData) {
                                                            GetSubChannelsByChannelBean.ListBean.TemplateBean clone = temp.clone();
                                                            temps.add(clone);
                                                        }
                                                        for (GetSubChannelsByChannelBean.ListBean.TemplateBean temp : templateData) {
                                                            GetSubChannelsByChannelBean.ListBean.TemplateBean clone = temp.clone();
                                                            temps.add(clone);
                                                        }
                                                        temps.get(0).setTempTitle("模板" + n);
                                                        addPresenter((ArrayObjectAdapter) objectAdapter, n, temps);
                                                    }
                                                    break;
                                                }
                                                // 正常显示
                                                else {
                                                    if (preTemplate <= 18) {
                                                        addPresenter((ArrayObjectAdapter) objectAdapter, preTemplate, templateData);
                                                    } else if (preTemplate == 19) {
                                                        addPresenter((ArrayObjectAdapter) objectAdapter, 20, templateData);
                                                    } else if (preTemplate == 20) {
                                                        addPresenter((ArrayObjectAdapter) objectAdapter, 21, templateData);
                                                    } else if (preTemplate == 21) {
                                                        addPresenter((ArrayObjectAdapter) objectAdapter, 22, templateData);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().showLoading();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                })
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean data) {
                        getView().hideLoading();
                        getView().refreshContent();
                    }
                })
                .subscribe());
    }

    private final void addPresenter(@NonNull ArrayObjectAdapter arrayObjectAdapter,
                                    @NonNull int templateCode,
                                    @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> datas) {
        Collection collection = null;
        Object object = null;
        // 返回顶部
        if (-2 == templateCode) {
            object = new GeneralTemplateBottom();
        }
        // 二级分类
        if (-1 == templateCode) {
            GeneralTemplateClass.GeneralTemplateClassList list = new GeneralTemplateClass.GeneralTemplateClassList();
            list.addAll(datas);
            object = list;
        }
        // 模板1
        else if (1 == templateCode) {
            GeneralTemplate1.GeneralTemplate1List list = new GeneralTemplate1.GeneralTemplate1List();
            list.addAll(datas);
            object = list;
        }
        // 模板2
        else if (2 == templateCode) {
            GeneralTemplate2.GeneralTemplate2List list = new GeneralTemplate2.GeneralTemplate2List();
            list.addAll(datas);
            object = list;
        }
        // 模板3
        else if (3 == templateCode) {
            GeneralTemplate3.GeneralTemplate3List list = new GeneralTemplate3.GeneralTemplate3List();
            list.addAll(datas);
            object = list;
        }
        // 模板4
        else if (4 == templateCode) {
            GeneralTemplate4.GeneralTemplate4List list = new GeneralTemplate4.GeneralTemplate4List();
            list.addAll(datas);
            object = list;
        }
        // 模板5
        else if (5 == templateCode) {
            GeneralTemplate5.GeneralTemplate5List list = new GeneralTemplate5.GeneralTemplate5List();
            list.addAll(datas);
            object = list;
        }
        // 模板6
        else if (6 == templateCode) {
            GeneralTemplate6.GeneralTemplate6List list = new GeneralTemplate6.GeneralTemplate6List();
            list.addAll(datas);
            object = list;
        }
        // 模板7
        else if (7 == templateCode) {
            GeneralTemplate7.GeneralTemplate7List list = new GeneralTemplate7.GeneralTemplate7List();
            list.addAll(datas);
            object = list;
        }
        // 模板8
        else if (8 == templateCode) {
            GeneralTemplate8.GeneralTemplate8List list = new GeneralTemplate8.GeneralTemplate8List();
            list.addAll(datas);
            object = list;
        }
        // 模板9
        else if (9 == templateCode) {
            GeneralTemplate9.GeneralTemplate9List list = new GeneralTemplate9.GeneralTemplate9List();
            list.addAll(datas);
            object = list;
        }
        // 模板10
        else if (10 == templateCode) {
            GeneralTemplate10.GeneralTemplate10List list = new GeneralTemplate10.GeneralTemplate10List();
            list.addAll(datas);
            object = list;
        }
        // 模板11
        else if (11 == templateCode) {
            GeneralTemplate11.GeneralTemplate11List list = new GeneralTemplate11.GeneralTemplate11List();
            list.addAll(datas);
            object = list;
        }
        // 模板12
        else if (12 == templateCode) {
            GeneralTemplate12.GeneralTemplate12List list = new GeneralTemplate12.GeneralTemplate12List();
            list.addAll(datas);
            object = list;
        }
        // 模板13
        else if (13 == templateCode) {
            GeneralTemplate13.GeneralTemplate13List list = new GeneralTemplate13.GeneralTemplate13List();
            list.addAll(datas);
            object = list;
        }
        // 模板14
        else if (14 == templateCode) {
            GeneralTemplate14.GeneralTemplate14List list = new GeneralTemplate14.GeneralTemplate14List();
            list.addAll(datas);
            object = list;
        }
        // 模板15
        else if (15 == templateCode) {
            GeneralTemplate15.GeneralTemplate15List list = new GeneralTemplate15.GeneralTemplate15List();
            list.addAll(datas);
            object = list;
        }
        // 模板16
        else if (16 == templateCode) {
            GeneralTemplate16.GeneralTemplate16List list = new GeneralTemplate16.GeneralTemplate16List();
            list.addAll(datas);
            object = list;
        }
        // 模板17
        else if (17 == templateCode) {
            GeneralTemplate17.GeneralTemplate17List list = new GeneralTemplate17.GeneralTemplate17List();
            list.addAll(datas);
            object = list;
        }
        // 模板18
        else if (18 == templateCode) {
            GeneralTemplate18.GeneralTemplate18List list = new GeneralTemplate18.GeneralTemplate18List();
            list.addAll(datas);
            object = list;
        }
        // 模板19
        else if (19 == templateCode) {
            GeneralTemplate19.GeneralTemplate19List list = new GeneralTemplate19.GeneralTemplate19List();
            list.addAll(datas);
            object = list;
        }
        // 模板20
        else if (20 == templateCode) {
            GeneralTemplate20.GeneralTemplate20List list = new GeneralTemplate20.GeneralTemplate20List();
            list.addAll(datas);
            object = list;
        }
        // 模板21
        else if (21 == templateCode) {
            GeneralTemplate21.GeneralTemplate21List list = new GeneralTemplate21.GeneralTemplate21List();
            list.addAll(datas);
            object = list;
        }
        // 模板22
        else if (22 == templateCode) {
            GeneralTemplate22.GeneralTemplate22List list = new GeneralTemplate22.GeneralTemplate22List();
            list.addAll(datas);
            object = list;
        }

        if (null != object) {
            arrayObjectAdapter.add(object);
        }
        if (null != collection) {
            int size = arrayObjectAdapter.size();
            arrayObjectAdapter.addAll(size, collection, false);
        }
    }
}

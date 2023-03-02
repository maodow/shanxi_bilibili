
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
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
                .flatMap(new Function<Boolean, Observable<BaseBean<GetSubChannelsByChannelBean>>>() {
                    @Override
                    public Observable<BaseBean<GetSubChannelsByChannelBean>> apply(Boolean b) {
                        int channelId = getView().getIntExtra(GeneralFragment.BUNDLE_CHANNELID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getSubChannelsByChannel(channelId, 0, Integer.MAX_VALUE);
                    }
                })
                // second class
                .flatMap(new Function<BaseBean<GetSubChannelsByChannelBean>, Observable<BaseBean<GetSubChannelsByChannelBean>>>() {
                    @Override
                    public Observable<BaseBean<GetSubChannelsByChannelBean>> apply(BaseBean<GetSubChannelsByChannelBean> response) {
                        int classId = getView().getIntExtra(GeneralFragment.BUNDLE_CLASSID, 0);
                        if (classId > 0) {
                            String extra;
                            try {
                                extra = new Gson().toJson(response.getData());
                            } catch (Exception e) {
                                e.printStackTrace();
                                extra = "{}";
                            }
                            return HttpClient.getHttpClient().getHttpApi().getClassByPrentId(classId, extra);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<BaseBean<GetSubChannelsByChannelBean>>() {
                                @Override
                                public void subscribe(ObservableEmitter<BaseBean<GetSubChannelsByChannelBean>> observableEmitter) {
                                    BaseBean<GetSubChannelsByChannelBean> baseBean = new BaseBean<>();
                                    String extra;
                                    try {
                                        extra = new Gson().toJson(response.getData());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        extra = "{}";
                                    }
                                    baseBean.setExtra(extra);
                                    observableEmitter.onNext(baseBean);
                                }
                            });
                        }
                    }
                })
                .map(new Function<BaseBean<GetSubChannelsByChannelBean>, List<GetSubChannelsByChannelBean.ListBean>>() {
                    @Override
                    public List<GetSubChannelsByChannelBean.ListBean> apply(BaseBean<GetSubChannelsByChannelBean> response) {
                        ArrayList<GetSubChannelsByChannelBean.ListBean> list = new ArrayList<>();
                        try {

                            String extra = response.getExtra();
//                            Log.e("GeneralPresenter", "getClassByPrentId => extra = " + extra);
//                            Type type = new TypeToken<List<User>>(){}.getType();
                            GetSubChannelsByChannelBean data = new Gson().fromJson(extra, GetSubChannelsByChannelBean.class);
                            List<GetSubChannelsByChannelBean.ListBean> datas1 = data.getList();
//                            Log.e("GeneralPresenter", "getClassByPrentId => datas1 = " + datas1);
                            int size1 = datas1.size();
                            int classId = getView().getIntExtra(GeneralFragment.BUNDLE_CLASSID, 0);
                            for (int i = 0; i < size1; i++) {

                                // 二级分类
                                if (i == 1) {
                                    GetSubChannelsByChannelBean data1 = response.getData();
                                    if (null != data1) {
                                        List<GetSubChannelsByChannelBean.ClassesBean> classes = data1.getClasses();
                                        if (null != classes) {
                                            GetSubChannelsByChannelBean.ListBean classBean = new GetSubChannelsByChannelBean.ListBean();
                                            classBean.setPreTemplate(-1);
                                            int size = classes.size();
                                            if (size > 5) {
                                                size = 5;
                                            }
                                            ArrayList<GetSubChannelsByChannelBean.ListBean.TemplateBean> templateBeans = new ArrayList<>();
                                            if (size > 0) {
                                                for (int j = 0; j < size; j++) {
                                                    GetSubChannelsByChannelBean.ClassesBean temp = classes.get(j);
                                                    if (null == temp)
                                                        continue;
                                                    String name = temp.getName();
                                                    GetSubChannelsByChannelBean.ListBean.TemplateBean bean = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
                                                    bean.setName(name);
                                                    bean.setClassId(data1.getParent().getId());
                                                    bean.setToType(2);
                                                    templateBeans.add(bean);
                                                }
                                            }

                                            // 更多分类
                                            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
                                            bean.setName("更多分类");
                                            bean.setClassId(data1.getParent().getId());
                                            bean.setToType(2);
                                            templateBeans.add(bean);

                                            classBean.setTemplateData(templateBeans);
                                            list.add(classBean);
                                        }
                                    }
                                }

                                GetSubChannelsByChannelBean.ListBean listBean = datas1.get(i);
                                if (null == listBean)
                                    continue;
                                List<GetSubChannelsByChannelBean.ListBean.TemplateBean> datas2 = listBean.getTemplateData();
                                if (null == datas2)
                                    continue;
                                int size2 = datas2.size();
                                if (size2 < 0)
                                    continue;
                                List<GetSubChannelsByChannelBean.ListBean.TemplateBean> result = new ArrayList<>();
                                for (int j = 0; j < size2; j++) {
                                    GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = datas2.get(j);
                                    if (null == templateBean)
                                        continue;
                                    templateBean.setClassId(classId);
                                    // 模板1
                                    int preTemplate = listBean.getPreTemplate();
                                    if (1 == preTemplate) {
                                        int size = result.size();
                                        // left
                                        if (size == 0) {
                                            GetSubChannelsByChannelBean.ListBean.TemplateBean temp = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
                                            temp.copyPic(templateBean);
                                            result.add(temp);
                                        }
                                    }
                                    // 模板16
                                    else if (16 == preTemplate && result.isEmpty()) {
                                        int size = result.size();
                                        // center
                                        if (size == 1) {
                                            GetSubChannelsByChannelBean.ListBean.TemplateBean temp = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
                                            temp.copyPic(templateBean);
                                            result.add(temp);
                                        }
                                    }
                                    // 模板17
                                    else if (17 == preTemplate) {

                                        int size = result.size();
                                        // left
                                        if (size == 0) {
//                                            Context context = getView().getContext();
//                                            String s = CacheUtil.getCache(context, "user_look");
//                                            Type type = new TypeToken<ArrayList<LookBean>>() {
//                                            }.getType();
//                                            ArrayList<LookBean> recs = new Gson().fromJson(s, type);
//                                            if (null == recs) {
//                                                recs = new ArrayList<>();
//                                            }
//                                            templateBean.setGeneralTemplate17Recs(recs);
                                        }
                                        // center
                                        else if (size == 1) {
                                            GetSubChannelsByChannelBean.ListBean.TemplateBean temp = new GetSubChannelsByChannelBean.ListBean.TemplateBean();
//                                            temp.setNewPicHz(templateBean.getNewPicHz());
//                                            temp.setNewPicVt(templateBean.getNewPicVt());
                                            result.add(temp);
                                        }
                                    }
                                    result.add(templateBean);
                                }
                                listBean.setTemplateData(result);
                                list.add(listBean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return list;
                    }
                })
                .map(new Function<List<GetSubChannelsByChannelBean.ListBean>, Boolean>() {
                    @Override
                    public Boolean apply(List<GetSubChannelsByChannelBean.ListBean> listBeans) {
                        try {
                            addPresenters(listBeans);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                // 上报
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) {
                        try {
                            int channelId = getView().getIntExtra(GeneralFragment.BUNDLE_CHANNELID, 0);
                            String name = getView().getStringExtra(GeneralFragment.BUNDLE_NAME);
                            reportChannelLoadFinished(channelId, name);
                        } catch (Exception e) {
                        }
                        return aBoolean;
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
//                        Log.e("GeneralPresenter", throwable.getMessage(), throwable);
                        getView().hideLoading();
                    }
                })
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean data) {
//                        Log.e("GeneralPresenter", "getSubChannelsByChannel => " + new Gson().toJson(list));
                        getView().hideLoading();
                        getView().refreshContent();
                    }
                })
                .subscribe());
    }

    protected final void addPresenters(@NonNull List<GetSubChannelsByChannelBean.ListBean> list) {
        if (null == list)
            return;
        int size = list.size();
        if (size <= 0)
            return;

        VerticalGridView verticalGridView = getView().findViewById(R.id.general_list);
        RecyclerView.Adapter adapter = verticalGridView.getAdapter();
        if (null == adapter || !(adapter instanceof ItemBridgeAdapter))
            return;
        ObjectAdapter objectAdapter = ((ItemBridgeAdapter) adapter).getAdapter();
        if (null == objectAdapter || !(objectAdapter instanceof ArrayObjectAdapter))
            return;

        // clean
        ((ArrayObjectAdapter) objectAdapter).clear(false);

        // 1
        for (int i = 0; i < size; i++) {
            GetSubChannelsByChannelBean.ListBean bean = list.get(i);
            if (null == bean)
                continue;

            List<GetSubChannelsByChannelBean.ListBean.TemplateBean> templateData = bean.getTemplateData();
            int preTemplate = bean.getPreTemplate();

            // 测试模板
            if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
                if (preTemplate == BuildConfig.HUAN_TEST_TEMPLATE_CODE) {
                    for (int n = 1; n <= 22; n++) {
                        addPresenter((ArrayObjectAdapter) objectAdapter, n, templateData);
                    }
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
        // 模板-回到顶部
        addPresenter((ArrayObjectAdapter) objectAdapter, -2, null);
    }

    private final void addPresenter(@NonNull ArrayObjectAdapter arrayObjectAdapter, @NonNull int templateCode, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> datas) {
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

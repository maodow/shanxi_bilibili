package tv.huan.bilibili.ui.welcome;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import lib.kalu.frame.mvp.util.CacheUtil;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.GetPopupInfoBeanBase;
import tv.huan.bilibili.bean.ServerSettingData;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallWelcomeBean;
import tv.huan.bilibili.bean.local.LocalBean;
import tv.huan.bilibili.help.ConfigHelp;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.utils.ADUtil;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.LogUtil;

public class WelcomePresenter extends BasePresenterImpl<WelcomeView> {

    public WelcomePresenter(@NonNull WelcomeView welcomeView) {
        super(welcomeView);
    }

    protected void request() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        emitter.onNext(true);
                    }
                })
                // 欢网 => 广告sdk
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) {
                        try {
                            ADUtil.adSplash(getView().getContext());
                            LogUtil.log("WelcomePresenter => request => adSplash =>");
                        } catch (Exception e) {
                        }
                        return aBoolean;
                    }
                })
                // 首次app上报
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<Object>>>() {
                    @Override
                    public Observable<BaseResponsedBean<Object>> apply(Boolean aBoolean) {
                        LogUtil.log("WelcomePresenter => request => insertFirstLogin =>");
                        return HttpClient.getHttpClient().getHttpApi().insertFirstLogin();
                    }
                })
                // 打开app上报
                .map(new Function<BaseResponsedBean<Object>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<Object> data) {
                        try {
                            reportAppActivation();
                            LogUtil.log("WelcomePresenter => request => reportAppActivation =>");
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                // 接口 => 观看历史
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(Boolean data) {
                        return HttpClient.getHttpClient().getHttpApi().getBookmark(0, 3, null);
                    }
                })
                // 数据处理 => 观看历史
                .map(new Function<BaseResponsedBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<FavBean> resp) {
                        try {
                            FavBean data = resp.getData();
                            if (null == data)
                                throw new Exception();
                            List<FavBean.ItemBean> oldList = data.getRows();
                            if (null == oldList || oldList.size() <= 0)
                                throw new Exception();
                            ArrayList<LocalBean> newList = new ArrayList<>();
                            for (FavBean.ItemBean t : oldList) {
                                if (null == t)
                                    continue;
                                if (newList.size() >= 3)
                                    break;
                                LocalBean o = new LocalBean();
                                o.setCid(t.getCid());
                                o.setName(t.getAlbumName());
                                o.setLocal_img(t.getAlbum().getPicture(true));
                                o.setLocal_index(oldList.indexOf(t));
                                o.setLocal_status(t.getStatusRec());
                                newList.add(o);
                            }
                            String s = new Gson().toJson(newList);
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_HISTORY, s);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                // 接口 => 我的收藏
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(Boolean data) {
                        return HttpClient.getHttpClient().getHttpApi().getFavList(0, 3, null);
                    }
                })
                // 数据处理 => 我的收藏
                .map(new Function<BaseResponsedBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<FavBean> resp) {
                        try {
                            FavBean data = resp.getData();
                            if (null == data)
                                throw new Exception();
                            List<FavBean.ItemBean> oldList = data.getRows();
                            if (null == oldList || oldList.size() <= 0)
                                throw new Exception();
                            ArrayList<LocalBean> newList = new ArrayList<>();
                            for (FavBean.ItemBean t : oldList) {
                                if (null == t)
                                    continue;
                                if (newList.size() >= 3)
                                    break;
                                LocalBean o = new LocalBean();
                                o.setCid(t.getCid());
                                o.setName(t.getAlbum().getName());
                                o.setLocal_img(t.getAlbum().getPicture(true));
                                o.setLocal_index(oldList.indexOf(t));
                                newList.add(o);
                            }
                            String s = new Gson().toJson(newList);
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR, s);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                // 初始化数据
                .map(new Function<Boolean, CallWelcomeBean>() {
                    @Override
                    public CallWelcomeBean apply(Boolean data) {
                        CallWelcomeBean welcomeBean = new CallWelcomeBean();
                        // 3
                        int select = getView().getIntExtra(WelcomeActivity.INTENT_SELECT, 1);
                        welcomeBean.setSelect(select);

                        int type;
                        try {
                            String extra = getView().getStringExtra(WelcomeActivity.INTENT_TYPE);
                            type = Integer.parseInt(extra);
                        } catch (Exception e) {
                            type = getView().getIntExtra(WelcomeActivity.INTENT_TYPE, MainActivity.INTENT_TYPE_DEFAULT);
                        }
                        welcomeBean.setType(type);

                        String cid = null;
                        try {
                            cid = getView().getStringExtra(WelcomeActivity.INTENT_CID);
                        } catch (Exception e) {
                        }
                        welcomeBean.setCid(cid);

                        int classId;
                        try {
                            String extra = getView().getStringExtra(WelcomeActivity.INTENT_CLASSID);
                            classId = Integer.parseInt(extra);
                        } catch (Exception e) {
                            classId = getView().getIntExtra(WelcomeActivity.INTENT_CLASSID, -1);
                        }
                        welcomeBean.setClassId(classId);

                        String secondTag = null;
                        try {
                            secondTag = getView().getStringExtra(WelcomeActivity.INTENT_SECOND_TAG);
                        } catch (Exception e) {
                        }
                        welcomeBean.setSecondTag(secondTag);
                        return welcomeBean;
                    }
                })
                //获取服务器配置
                .flatMap(new Function<CallWelcomeBean, Observable<BaseResponsedBean<ServerSettingData>>>() {
                    @Override
                    public Observable<BaseResponsedBean<ServerSettingData>> apply(CallWelcomeBean data) {
                        LogUtil.log("WelcomePresenter => request => 获取服务器配置接口");
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getSetting(s);
                    }
                })

                //获取服务器配置-数据整理
                .map(new Function<BaseResponsedBean<ServerSettingData>, CallWelcomeBean>() {
                    @Override
                    public CallWelcomeBean apply(BaseResponsedBean<ServerSettingData> response) {
                        LogUtil.log("WelcomePresenter => 获取服务器配置-数据整理");

                        CallWelcomeBean welcomeBean;
                        try {
                            welcomeBean = new Gson().fromJson(response.getExtra(), CallWelcomeBean.class);
                        } catch (Exception e) {
                            welcomeBean = new CallWelcomeBean();
                        }
                        ServerSettingData data = response.getData();
                        welcomeBean.setSettingData(data);
                        ConfigHelp.Companion.getINSTANCE().init(data);
                        return welcomeBean;
                    }
                })

                // 广告接口
                .flatMap(new Function<CallWelcomeBean, Observable<GetPopupInfoBeanBase>>() {
                    @Override
                    public Observable<GetPopupInfoBeanBase> apply(CallWelcomeBean data) {
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getPopupInfo("1", BoxUtil.getUserId(), s);
                    }
                })
                // 下载广告
                .map(new Function<GetPopupInfoBeanBase, CallWelcomeBean>() {
                    @Override
                    public CallWelcomeBean apply(GetPopupInfoBeanBase loadPageIcon) {
                        CallWelcomeBean welcomeBean;
                        try {
                            welcomeBean = new Gson().fromJson(loadPageIcon.getExtra(), CallWelcomeBean.class);
                        } catch (Exception e) {
                            welcomeBean = new CallWelcomeBean();
                        }

                        try {
                            GetPopupInfoBeanBase.LoadingPicBean loading_pic = loadPageIcon.getLoading_pic();
                            String display_time = loading_pic.getDisplay_time();
                            if (null != display_time && display_time.length() > 0) {
                                int parseInt = Integer.parseInt(display_time);
                                welcomeBean.setAdTime(parseInt);
                            } else {
                                welcomeBean.setAdTime(0);
                            }
                        } catch (Exception e) {
                            welcomeBean.setAdTime(0);
                        }
                        try {
                            GetPopupInfoBeanBase.LoadingPicBean loading_pic = loadPageIcon.getLoading_pic();
                            String picture_hd = loading_pic.getPicture_HD();
                            welcomeBean.setAdUrl(picture_hd);
                        } catch (Exception e) {
                        }
                        return welcomeBean;
                    }
                })
                // 频道接口
                .flatMap(new Function<CallWelcomeBean, Observable<BaseResponsedBean<GetChannelsBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<GetChannelsBean>> apply(CallWelcomeBean data) {
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getChannels(1, 100, s);
                    }
                })
                // 整理数据
                .map(new Function<BaseResponsedBean<GetChannelsBean>, CallWelcomeBean>() {
                    @Override
                    public CallWelcomeBean apply(BaseResponsedBean<GetChannelsBean> response) {
                        CallWelcomeBean welcomeBean;
                        try {
                            welcomeBean = new Gson().fromJson(response.getExtra(), CallWelcomeBean.class);
                        } catch (Exception e) {
                            welcomeBean = new CallWelcomeBean();
                        }

                        ArrayList<GetChannelsBean.ItemBean> list = new ArrayList<>();
                        GetChannelsBean.ItemBean bean = new GetChannelsBean.ItemBean();
                        String string = getView().getString(R.string.tab_mine);
                        bean.setName(string);
                        list.add(bean);
                        try {
                            List<GetChannelsBean.ItemBean> datas = response.getData().getList();
                            list.addAll(datas);
                        } catch (Exception e) {
                        }
                        String s = new Gson().toJson(list);
                        welcomeBean.setData(s);

                        int size = list.size();
                        if (size == 1) {
                            welcomeBean.setSelect(0);
                        } else {
                            int select = welcomeBean.getSelect();
                            if (select + 1 >= size) {
                                welcomeBean.setSelect(1);
                            }
                        }

                        return welcomeBean;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        //  getView().showLoading();
                        LogUtil.log("WelcomePresenter => request => doOnSubscribe");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtil.log("WelcomePresenter => request => doOnError =>");
                        getView().showToast(throwable);
                        getView().next();
                    }
                })
                .doOnNext(new Consumer<CallWelcomeBean>() {
                    @Override
                    public void accept(CallWelcomeBean data) {
                        if (data.containsAd()) {
                            getView().setVisibility(R.id.welcome_img, View.VISIBLE);
                            getView().updateBackground(data.getAdUrl());
                            intervalTime(data.getSettingData().getUpgrade(), data.getData(), data.getSelect(), data.getType(), data.getCid(), data.getClassId(), data.getSecondTag(), data.getAdTime());
                        } else {
                            getView().next(data.getSettingData().getUpgrade(), data.getData(), data.getSelect(), data.getType(), data.getCid(), data.getClassId(), data.getSecondTag());
                        }
                    }
                })
                .subscribe());
    }

    private void intervalTime(@NonNull ServerSettingData.UpgradeBean upgradeBean, @NonNull String data, @NonNull int select, @NonNull int type, @NonNull String cid, @NonNull int classId, @NonNull String secondTag, @NonNull int time) {

        // 延时1s ，每间隔1s，时间单位
        addDisposable(Observable.interval(1, 1, TimeUnit.SECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.e("WelcomePresenter", "intervalTime => " + aLong);
                        // 取消订阅
                        if (aLong >= time) {
                            dispose();
                            getView().next(upgradeBean, data, select, type, cid, classId, secondTag);
                        } else {
                            int num = (int) (time - aLong);
                            getView().setVisibility(R.id.welcome_time, View.VISIBLE);
                            getView().setText(R.id.welcome_time, num + "s");
                        }
                    }
                })
                .subscribe());
    }
}

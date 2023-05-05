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
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.GetPopupInfoBeanBase;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallWelcomeBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.utils.ADUtil;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.heilongjiang.HeilongjiangUtil;

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
                // sdk => init_WorkerThread
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) throws Exception {
                        try {
                            if (BuildConfig.HUAN_CHECK_CMCC_INIT) {
                                boolean init_workerThread = HeilongjiangUtil.init_WorkerThread(getView().getContext());
                                LogUtil.log("WelcomePresenter => request => init_workerThread = " + init_workerThread);
                                if (!init_workerThread)
                                    throw new Exception("移动sdk初始化错误");
                            }
                            return aBoolean;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // sdk => getUserId
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) throws Exception {
                        try {
                            if (BuildConfig.HUAN_CHECK_USER_ID) {
                                String userId = HeilongjiangUtil.getUserId(getView().getContext());
                                LogUtil.log("WelcomePresenter => request => userId = " + userId);
                                if (null == userId || userId.length() <= 0) {
                                    String s = getView().getString(R.string.welcome_warning);
                                    throw new Exception(s);
                                }
                            }
                            return aBoolean;
                        } catch (Exception e) {
                            throw e;
                        }
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
                // 首次打开app上报
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean data) {
                        try {
                            reportAppActivation();
                            LogUtil.log("WelcomePresenter => request => reportAppActivation =>");
                        } catch (Exception e) {
                        }
                        return data;
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
                // 广告接口
                .flatMap(new Function<CallWelcomeBean, Observable<GetPopupInfoBeanBase>>() {
                    @Override
                    public Observable<GetPopupInfoBeanBase> apply(CallWelcomeBean data) {
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getPopupInfo(s);
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
                            intervalTime(data.getData(), data.getSelect(), data.getType(), data.getCid(), data.getClassId(), data.getSecondTag(), data.getAdTime());
                        } else {
                            getView().next(data.getData(), data.getSelect(), data.getType(), data.getCid(), data.getClassId(), data.getSecondTag());
                        }
                    }
                })
                .subscribe());
    }

    private void intervalTime(@NonNull String data, @NonNull int select, @NonNull int type, @NonNull String cid, @NonNull int classId, @NonNull String secondTag, @NonNull int time) {

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
                            getView().next(data, select, type, cid, classId, secondTag);
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

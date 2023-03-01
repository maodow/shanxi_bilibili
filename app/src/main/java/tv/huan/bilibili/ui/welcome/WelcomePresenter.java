package tv.huan.bilibili.ui.welcome;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONObject;

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
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.WelcomeBean;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.LoadPageIcon;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.heilongjiang.HeilongjiangApi;

public class WelcomePresenter extends BasePresenterImpl<WelcomeView> {

    public WelcomePresenter(@NonNull WelcomeView welcomeView) {
        super(welcomeView);
    }

    protected void request() {

        addDisposable(Observable.create(new ObservableOnSubscribe<WelcomeBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<WelcomeBean> emitter) {
                        WelcomeBean welcomeBean = new WelcomeBean();
                        try {
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
                        } catch (Exception e) {
                        }
                        emitter.onNext(welcomeBean);
                    }
                })
                // 初始化支付sdk
                .map(new Function<WelcomeBean, WelcomeBean>() {
                    @Override
                    public WelcomeBean apply(WelcomeBean data) {
                        LogUtil.log("WelcomePresenter => request => 初始化支付sdk");
                        try {
                            Context context = getView().getContext();
                            HeilongjiangApi.init(context);
                        } catch (Exception e) {
                        }
                        return data;
                    }
                })
                // 获取userId
                .map(new Function<WelcomeBean, WelcomeBean>() {
                    @Override
                    public WelcomeBean apply(WelcomeBean data) throws Exception {
                        LogUtil.log("WelcomePresenter => request => 获取userId");
                        try {
                            if (BuildConfig.HUAN_CHECK_USERID) {
                                Context context = getView().getContext();
                                String userId = HeilongjiangApi.getUserId(context);
                                if (null == userId || userId.length() <= 0)
                                    throw new Exception("获取userId失败, 请稍后重试");
                            }
                            return data;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // 首次打开app上报
                .map(new Function<WelcomeBean, WelcomeBean>() {
                    @Override
                    public WelcomeBean apply(WelcomeBean data) {
                        LogUtil.log("WelcomePresenter => request => 首次打开app上报");
                        try {
                            reportAppActivation();
                        } catch (Exception e) {
                        }
                        return data;
                    }
                })
                // 广告接口
                .flatMap(new Function<WelcomeBean, Observable<LoadPageIcon>>() {
                    @Override
                    public Observable<LoadPageIcon> apply(WelcomeBean data) {
                        LogUtil.log("WelcomePresenter => request => 广告接口");
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getLoadPage(BoxUtil.getCa(), s);
                    }
                })
                // 下载广告
                .map(new Function<LoadPageIcon, WelcomeBean>() {
                    @Override
                    public WelcomeBean apply(LoadPageIcon loadPageIcon) {
                        LogUtil.log("WelcomePresenter => request => 下载广告");

                        WelcomeBean welcomeBean;
                        try {
                            welcomeBean = new Gson().fromJson(loadPageIcon.getExtra(), WelcomeBean.class);
                        } catch (Exception e) {
                            welcomeBean = new WelcomeBean();
                        }

                        try {
                            LoadPageIcon.LoadingPicBean loading_pic = loadPageIcon.getLoading_pic();
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
                            LoadPageIcon.LoadingPicBean loading_pic = loadPageIcon.getLoading_pic();
                            String picture_hd = loading_pic.getPicture_HD();
                            welcomeBean.setAdUrl(picture_hd);
                        } catch (Exception e) {
                        }
                        return welcomeBean;
                    }
                })
                // 频道接口
                .flatMap(new Function<WelcomeBean, Observable<BaseBean<GetChannelsBean>>>() {
                    @Override
                    public Observable<BaseBean<GetChannelsBean>> apply(WelcomeBean data) {
                        LogUtil.log("WelcomePresenter => request => 频道接口");
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getChannels(1, 100, s);
                    }
                })
                // 整理数据
                .map(new Function<BaseBean<GetChannelsBean>, WelcomeBean>() {
                    @Override
                    public WelcomeBean apply(BaseBean<GetChannelsBean> response) {
                        LogUtil.log("WelcomePresenter => request => 整理数据");

                        WelcomeBean welcomeBean;
                        try {
                            welcomeBean = new Gson().fromJson(response.getExtra(), WelcomeBean.class);
                        } catch (Exception e) {
                            welcomeBean = new WelcomeBean();
                        }

                        ArrayList<GetChannelsBean.ItemBean> list = new ArrayList<>();
                        GetChannelsBean.ItemBean bean = new GetChannelsBean.ItemBean();
                        bean.setName("我的");
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
                        LogUtil.log("WelcomePresenter => request => doOnError");
                        try {
                            String s = throwable.getMessage();
                            if (null != s && s.length() > 0) {
                               getView().showWarning(s);
                            }
                        } catch (Exception e) {
                        }
                    }
                })
                .doOnNext(new Consumer<WelcomeBean>() {
                    @Override
                    public void accept(WelcomeBean data) {
                        LogUtil.log("WelcomePresenter => request => doOnNext => "+new Gson().toJson(data));
                        if (data.containsAd()) {
                            getView().refreshBackground(data.getAdUrl());
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
                            getView().refreshTime("广告:" + num + "s");
                        }
                    }
                })
                .subscribe());
    }
}

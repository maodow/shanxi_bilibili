package tv.huan.bilibili.ui.welcome;

import android.util.Log;

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
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.ui.main.MainActivity;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.LoadPageIcon;
import tv.huan.bilibili.utils.BoxUtil;

public class WelcomePresenter extends BasePresenterImpl<WelcomeView> {

    public WelcomePresenter(@NonNull WelcomeView welcomeView) {
        super(welcomeView);
    }

    protected void request() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        // 首次打开app上报
                        reportAppActivation();
                        emitter.onNext(true);
                    }
                })
                // 广告
                .flatMap(new Function<Boolean, Observable<LoadPageIcon>>() {
                    @Override
                    public Observable<LoadPageIcon> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().getLoadPage(BoxUtil.getCa());
                    }
                })
                // 下载广告
                .map(new Function<LoadPageIcon, String>() {
                    @Override
                    public String apply(LoadPageIcon loadPageIcon) {
                        Log.e("WelcomePresenter", "getLoadPage => " + new Gson().toJson(loadPageIcon));
                        JSONObject object = new JSONObject();
                        try {
                            LoadPageIcon.LoadingPicBean loading_pic = loadPageIcon.getLoading_pic();
                            String display_time = loading_pic.getDisplay_time();
                            if (null != display_time && display_time.length() > 0) {
                                int parseInt = Integer.parseInt(display_time);
                                object.put("time", parseInt);
                            } else {
                                object.put("time", 0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            LoadPageIcon.LoadingPicBean loading_pic = loadPageIcon.getLoading_pic();
                            String picture_hd = loading_pic.getPicture_HD();
                            object.put("img", picture_hd);
                        } catch (Exception e) {
                        }
                        return object.toString();
                    }
                })
                // 频道
                .flatMap(new Function<String, Observable<BaseBean<GetChannelsBean>>>() {
                    @Override
                    public Observable<BaseBean<GetChannelsBean>> apply(String s) {
                        return HttpClient.getHttpClient().getHttpApi().getChannels(1, 100, s);
                    }
                })
                // 整理数据
                .map(new Function<BaseBean<GetChannelsBean>, Object[]>() {
                    @Override
                    public Object[] apply(BaseBean<GetChannelsBean> response) {
                        Log.e("WelcomePresenter", "getChannels => " + new Gson().toJson(response));

                        ArrayList<GetChannelsBean.ItemBean> list = new ArrayList<>();

                        // 1
                        GetChannelsBean.ItemBean bean = new GetChannelsBean.ItemBean();
                        bean.setName("我的");
                        list.add(bean);

                        // 2
                        try {
                            List<GetChannelsBean.ItemBean> datas = response.getData().getList();
                            list.addAll(datas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 3
                        int size = list.size();
                        int select = getView().getIntExtra(WelcomeActivity.INTENT_SELECT, 1);
                        if (size == 1) {
                            select = 0;
                        } else if (select + 1 >= size) {
                            select = 1;
                        }

                        // 3
                        String img = null;
                        int time = 0;
                        try {
                            String extra = response.getExtra();
                            JSONObject object = new JSONObject(extra);
                            img = object.optString("img", null);
                            time = object.optInt("time", 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 5
                        Object[] objects = new Object[8];
                        objects[0] = new Gson().toJson(list);
                        objects[1] = select;
                        objects[2] = img;
                        objects[3] = time;

                        int type;
                        try {
                            String extra = getView().getStringExtra(WelcomeActivity.INTENT_TYPE);
                            type = Integer.parseInt(extra);
                        } catch (Exception e) {
                            e.printStackTrace();
                            type = getView().getIntExtra(WelcomeActivity.INTENT_TYPE, MainActivity.INTENT_TYPE_DEFAULT);
                        }
                        objects[4] = type;

                        String cid = null;
                        try {
                            cid = getView().getStringExtra(WelcomeActivity.INTENT_CID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        objects[5] = cid;

                        int classId;
                        try {
                            String extra = getView().getStringExtra(WelcomeActivity.INTENT_CLASSID);
                            classId = Integer.parseInt(extra);
                        } catch (Exception e) {
                            e.printStackTrace();
                            classId = getView().getIntExtra(WelcomeActivity.INTENT_CLASSID, -1);
                        }
                        objects[6] = classId;

                        String secondTag = null;
                        try {
                            secondTag = getView().getStringExtra(WelcomeActivity.INTENT_SECOND_TAG);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        objects[7] = secondTag;

                        return objects;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        //  getView().showLoading();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        // getView().hideLoading();
                    }
                })
                .doOnNext(new Consumer<Object[]>() {
                    @Override
                    public void accept(Object[] objects) {

                        //  getView().hideLoading();

                        // 1
                        int time = (int) objects[3];
                        String backgroundUrl = (String) objects[2];
                        int select = (int) objects[1];
                        String data = (String) objects[0];
                        int type = (int) objects[4];
                        String cid = (String) objects[5];
                        int classId = (int) objects[6];
                        String secondTag = (String) objects[7];

                        // 2
                        if (null != backgroundUrl && backgroundUrl.length() > 0 && time > 0) {
                            getView().refreshBackground(backgroundUrl);
                            intervalTime(data, select, type, cid, classId, secondTag, time);
                        } else {
                            getView().next(data, select, type, cid, classId, secondTag);
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

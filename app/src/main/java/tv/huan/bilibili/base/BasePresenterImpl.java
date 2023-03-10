package tv.huan.bilibili.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.istv.ystframework.utils.DeviceUtil;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.BasePresenter;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.utils.ReportUtils;
import tv.huan.heilongjiang.HeilongjiangApi;

public class BasePresenterImpl<M extends BaseViewImpl> extends BasePresenter {

    public BasePresenterImpl(@NonNull M baseView) {
        super(baseView);
    }

    @Override
    protected M getView() {
        return (M) super.getView();
    }

    protected final void reportAppActivation() {
        boolean containsInstallTime = getView().containsInstallTime();
        if (containsInstallTime)
            return;
        getView().updateInstallTime();
        String mac = DeviceUtil.getMac();
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.appActivation(mac, prodId);
        logReport(object);
    }

    protected final void reportHomePageEnter(int channel) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.homePageEnter(prodId, channel);
        logReport(object);
    }

    protected final void reportChannelLoadFinished(int channelId, String channelName) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.channelLoadFinished(prodId, channelId, channelName);
        logReport(object);
    }

    protected final void reportSearchResultItemNum(String keyword) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.searchResultItemNum(prodId, 1, keyword);
        logReport(object);
    }

    protected final void reportSearchResultItemClicked(String keyword, String cid) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.searchResultItemClicked(prodId, keyword, cid);
        logReport(object);
    }

    protected final void reportDetailLoadFinished(String cid) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.detailLoadFinished(prodId, cid);
        logReport(object);
    }

    protected final void reportExitRetentionExposure(String cid) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.exitRetentionExposure(prodId, cid);
        logReport(object);
    }

    public final void reportExitRetentionClick(String cid, String name) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.exitRetentionClick(prodId, cid, name);
        logReport(object);
    }

    public final void reportTopicEnterDetail(@NonNull String cid, int sceneId, int topicId, String topicName) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.topicEnterDetail(prodId, cid, sceneId, topicId, topicName);
        logReport(object);
    }

    public final void reportTopicSubtypeClicked(@NonNull String cid, int sceneId, int topicId, String topicName) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.topicEnterDetail(prodId, cid, sceneId, topicId, topicName);
        logReport(object);
    }

    public final void reportAppExit() {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.appExit(prodId);
        logReport(object);
    }

    public final void reportDetailRecommendShow(String cid) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.detailRecommendShow(prodId, cid);
        logReport(object);
    }

    public final void reportDetailSelectionsButtonShow(String cid) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.detailSelectionsButtonShow(prodId, cid);
        logReport(object);
    }

    public final void reportTopicLoadFinished(int sceneId) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.topicLoadFinished(prodId, sceneId);
        logReport(object);
    }

    public final void reportPlayVodStop(String cid, String vid, long start, long end) {
        int prodId = getView().getProdId();
        JSONObject object = ReportUtils.playVodStop(prodId, cid, vid, start, end);
        logReport(object);
    }

    private final void logReport(@NonNull JSONObject object) {

//        addDisposable(
        Observable.create(new ObservableOnSubscribe<RequestBody>() {
                    @Override
                    public void subscribe(ObservableEmitter<RequestBody> emitter) {
                        String toString = object.toString();
                        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), toString);
                        emitter.onNext(requestBody);
                    }
                })
                .flatMap(new Function<RequestBody, Observable<BaseBean<Object>>>() {
                    @Override
                    public Observable<BaseBean<Object>> apply(RequestBody requestBody) {
                        return HttpClient.getHttpClient().getHttpApi().logReport(requestBody);
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("BasePresenterImpl", "logReport => doOnStart => " + object);
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("BasePresenterImpl", "logReport => doOnError => " + throwable.getMessage());
                        }
                    }
                })
                .doOnNext(new Consumer<BaseBean<Object>>() {
                    @Override
                    public void accept(BaseBean<Object> response) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("BasePresenterImpl", "logReport => doOnNext => " + new Gson().toJson(response));
                        }
                    }
                })
                .subscribe();
//    );
    }
    protected final void uploadPlayHistory(String cid, String vid, String classId, int pos, int endFlag, long duration, long position) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        emitter.onNext(true);
                    }
                })
                .flatMap(new Function<Boolean, Observable<BaseBean<Object>>>() {
                    @Override
                    public Observable<BaseBean<Object>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().savePlayHistory(vid, cid, endFlag, classId, pos, position, duration);
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("BasePresenterImpl", "uploadPlayHistory => doOnStart =>");
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("BasePresenterImpl", "uploadPlayHistory => doOnError => " + throwable.getMessage());
                        }
                    }
                })
                .doOnNext(new Consumer<BaseBean<Object>>() {
                    @Override
                    public void accept(BaseBean<Object> response) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("BasePresenterImpl", "uploadPlayHistory => doOnNext => " + new Gson().toJson(response));
                        }
                    }
                })
                .subscribe();
    }
}

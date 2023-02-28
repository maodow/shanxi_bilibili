package tv.huan.bilibili.ui.webview;

import androidx.annotation.NonNull;

import org.checkerframework.checker.units.qual.C;

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
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.WebviewBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.center.CenterActivity;

public class WebviewPresenter extends BasePresenterImpl<WebviewView> {

    public WebviewPresenter(@NonNull WebviewView baseView) {
        super(baseView);
    }

    protected void request() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        boolean help = getView().getBooleanExtra(WebviewActivity.INTENT_HELP, false);
                        observableEmitter.onNext(help);
                    }
                })
                .flatMap(new Function<Boolean, Observable<BaseBean<String>>>() {
                    @Override
                    public Observable<BaseBean<String>> apply(Boolean aBoolean) {
                        // help
                        if (aBoolean) {
                            int i = getView().getIntExtra(WebviewActivity.INTENT_HELP_TYPE, 1);
                            return HttpClient.getHttpClient().getHttpApi().getFileUrl(i);
                        }
                        // web
                        else {
                            return Observable.create(new ObservableOnSubscribe<BaseBean<String>>() {
                                @Override
                                public void subscribe(ObservableEmitter<BaseBean<String>> observableEmitter) throws Exception {
                                    BaseBean<String> baseBean = new BaseBean<>();
                                    baseBean.setData(null);
                                }
                            });
                        }
                    }
                })
                .map(new Function<BaseBean<String>, WebviewBean>() {
                    @Override
                    public WebviewBean apply(BaseBean<String> baseBean) {
                        WebviewBean bean = new WebviewBean();
                        if (null == baseBean.getData()) {
                            bean.setData(false);
                            String s = getView().getStringExtra(WebviewActivity.INTENT_URL);
                            bean.setValue(s);
                        } else {
                            bean.setData(true);
                            bean.setValue(baseBean.getData());
                        }
                        return bean;
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
                .doOnNext(new Consumer<WebviewBean>() {
                    @Override
                    public void accept(WebviewBean webviewBean) {
                        getView().hideLoading();
                        getView().showUrl(webviewBean.getValue());
                    }
                })
                .subscribe());
    }
}

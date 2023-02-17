package tv.huan.bilibili.base;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.BasePresenter;
import lib.kalu.frame.mvp.BaseView;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import tv.huan.bilibili.http.HttpClient;

public class BaseBasePresenterImpl<V> extends BasePresenter {

    public BaseBasePresenterImpl(@NonNull BaseView baseView) {
        super(baseView);
    }

    protected final void logReport(@NonNull JSONObject object) {

        addDisposable(Observable.create(new ObservableOnSubscribe<RequestBody>() {
                    @Override
                    public void subscribe(ObservableEmitter<RequestBody> emitter) {
                        String toString = object.toString();
                        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), toString);
                        emitter.onNext(requestBody);
                    }
                })
                .map(new Function<RequestBody, Call<ResponseBody>>() {
                    @Override
                    public Call<ResponseBody> apply(RequestBody requestBody) {
                        return HttpClient.getHttpClient().getHttpApi().logReport(requestBody);
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnNext(new Consumer<Call<ResponseBody>>() {
                    @Override
                    public void accept(Call<ResponseBody> responseBodyCall) {

                    }
                })
                .subscribe());
    }
}

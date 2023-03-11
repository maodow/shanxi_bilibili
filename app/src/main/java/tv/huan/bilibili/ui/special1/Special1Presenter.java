package tv.huan.bilibili.ui.special1;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.ResponsedBean;
import tv.huan.bilibili.bean.SpecialBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class Special1Presenter extends BasePresenterImpl<Special1View> {

    private final List<SpecialBean.ItemBean> mData = new ArrayList<>();

    public Special1Presenter(@NonNull Special1View specialView) {
        super(specialView);
    }

    protected void setAdapter() {
        Context context = getView().getContext();
        RecyclerView recyclerView = getView().findViewById(R.id.special1_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
                outRect.set(0, 0, offset, 0);
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                View inflate = LayoutInflater.from(context).inflate(R.layout.activity_special1_item, parent, false);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(inflate) {
                };
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAbsoluteAdapterPosition();
                        SpecialBean.ItemBean itemBean = mData.get(position);
                        JumpUtil.nextDetailFromSpecial(v.getContext(), itemBean.getCid(), itemBean.getTempTopicId(), itemBean.getTempTopicId(), itemBean.getTempTopicName());
                    }
                });
                try {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            TextView textView = view.findViewById(R.id.common_poster_name);
                            textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        }
                    });
                } catch (Exception e) {
                }
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                try {
                    SpecialBean.ItemBean itemBean = mData.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.common_poster_name);
                    textView.setText(itemBean.getName());
                } catch (Exception e) {
                }
                try {
                    SpecialBean.ItemBean itemBean = mData.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.common_poster_img);
                    GlideUtils.loadVt(imageView.getContext(), itemBean.getPicture(false), imageView);
                } catch (Exception e) {
                }
                try {
                    SpecialBean.ItemBean itemBean = mData.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.common_poster_vip);
                    GlideUtils.loadVt(imageView.getContext(), itemBean.getVipUrl(), imageView);
                } catch (Exception e) {
                }
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    protected void request() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                .flatMap(new Function<Boolean, Observable<ResponsedBean<SpecialBean>>>() {
                    @Override
                    public Observable<ResponsedBean<SpecialBean>> apply(Boolean aBoolean) {
                        int specialId = getView().getIntExtra(Special1Activity.INTENT_SPECIALID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getSpecialById(specialId);
                    }
                })
                .map(new Function<ResponsedBean<SpecialBean>, String>() {
                    @Override
                    public String apply(ResponsedBean<SpecialBean> specialDataResponsedBean) {
                        String str = null;
                        try {
                            str = specialDataResponsedBean.getData().getImgs().getPoster();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            List<SpecialBean.ItemBean> list = specialDataResponsedBean.getData().getList();
                            mData.clear();
                            mData.addAll(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 上报
                        try {
                            if (mData.size() > 0) {
                                int specialId = getView().getIntExtra(Special1Activity.INTENT_SPECIALID, 0);
                                reportTopicLoadFinished(specialId);
                            }
                        } catch (Exception e) {
                        }
                        return str;
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
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String str) {
                        getView().hideLoading();
                        getView().refreshContent(str);
                    }
                })
                .subscribe());
    }
}

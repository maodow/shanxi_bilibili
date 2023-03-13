package tv.huan.bilibili.ui.special2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.KeyEvent;
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
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.SpecialBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

public class Special2Presenter extends BasePresenterImpl<Special2View> {

    private final List<SpecialBean.ItemBean> mData = new ArrayList<>();

    public Special2Presenter(@NonNull Special2View specialView) {
        super(specialView);
    }


    protected void setAdapter() {
        Context context = getView().getContext();
        RecyclerView recyclerView = getView().findViewById(R.id.special2_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_14);
                outRect.set(0, 0, 0, offset);
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                View view = LayoutInflater.from(context).inflate(R.layout.activity_special2_item, parent, false);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {
                };
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAbsoluteAdapterPosition();
                        SpecialBean.ItemBean itemBean = mData.get(position);
                        JumpUtil.next(v.getContext(), itemBean);
                    }
                });
                view.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        // left
                        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                            return true;
                        }
                        // right
                        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                            return true;
                        }
                        // up
                        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position <= 0) {
                                return true;
                            }
                        }
                        // down
                        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                            int position = holder.getAbsoluteAdapterPosition();
                            int count = ((RecyclerView) v.getParent()).getAdapter().getItemCount();
                            if (position + 1 >= count) {
                                return true;
                            }
                        }
                        return false;
                    }
                });
                view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        try {
                            TextView vName = holder.itemView.findViewById(R.id.special2_item_name);
                            vName.setTextColor(Color.parseColor(hasFocus ? "#3c3c82" : "#ffffff"));
                            TextView vInfo = holder.itemView.findViewById(R.id.special2_item_info);
                            vInfo.setTextColor(Color.parseColor(hasFocus ? "#3c3c82" : "#ffffff"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (hasFocus) {
                            int position = holder.getAbsoluteAdapterPosition();
                            SpecialBean.ItemBean itemBean = mData.get(position);
                            String name = itemBean.getName();
                            String info = "info";
//                            String url = "http://114.118.13.20:8197/data_source/test/001/06/06.m3u8";
                            String url = "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
                            getView().startPlayer(url, name, info);
                        }
                    }
                });
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                try {
                    SpecialBean.ItemBean itemBean = mData.get(position);
                    TextView vName = holder.itemView.findViewById(R.id.special2_item_name);
                    vName.setText(itemBean.getName());
                    TextView vInfo = holder.itemView.findViewById(R.id.special2_item_info);
                    vInfo.setText(itemBean.getName());
                    ImageView imageView = holder.itemView.findViewById(R.id.special2_item_img);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getPicture(true), imageView);
                } catch (Exception e) {
                    e.printStackTrace();
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
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<SpecialBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<SpecialBean>> apply(Boolean aBoolean) {
                        int specialId = getView().getIntExtra(Special2Activity.INTENT_SPECIALID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getSpecialById(specialId);
                    }
                })
                .map(new Function<BaseResponsedBean<SpecialBean>, String>() {
                    @Override
                    public String apply(BaseResponsedBean<SpecialBean> specialDataBaseResponsedBean) {
                        String str = null;
                        try {
                            str = specialDataBaseResponsedBean.getData().getImgs().getPoster();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            List<SpecialBean.ItemBean> list = specialDataBaseResponsedBean.getData().getList();
                            mData.clear();
                            mData.addAll(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 上报
                        try {
                            if (mData.size() > 0) {
                                int specialId = getView().getIntExtra(Special2Activity.INTENT_SPECIALID, 0);
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
                        getView().refreshCards();
                        getView().refreshBackground(str);
                        if (mData.size() > 0) {
                            getView().requestDefault();
                        }
                    }
                })
                .subscribe());
    }
}

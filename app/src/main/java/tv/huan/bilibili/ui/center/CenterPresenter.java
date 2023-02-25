package tv.huan.bilibili.ui.center;

import android.content.Context;
import android.graphics.Rect;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
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
import lib.kalu.leanback.clazz.HorizontalClassLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class CenterPresenter extends BasePresenterImpl<CenterView> {

    private final List<FavBean.ItemBean> mDatas = new ArrayList<>();

    public CenterPresenter(@NonNull CenterView baseView) {
        super(baseView);
    }

    protected final void showTabs() {

        int position = getView().getIntExtra(CenterActivity.INTENT_SELECT, 0);
        if (position < 0 || position < 1) {
            position = 0;
        }
        ArrayList<lib.kalu.leanback.clazz.ClassBean> apis = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            lib.kalu.leanback.clazz.ClassBean classApi = new lib.kalu.leanback.clazz.ClassBean();
            classApi.setChecked(position == 0);
            classApi.setText(i == 0 ? "观看历史" : "我的收藏");
            apis.add(classApi);
        }
        HorizontalClassLayout classLayout = getView().findViewById(R.id.center_tabs);
        classLayout.update(apis);
    }

    protected void showWarning() {
        String s = "<font color='#aaaaaa'>长按</font><font color='#ff6699'>【确定键】</font><font color='#aaaaaa'>删除</font>";
        Spanned html = Html.fromHtml(s);
        getView().setText(R.id.center_warning, html);
    }

    protected void setAdapter() {
        Context context = getView().getContext();
        RecyclerView recyclerView = getView().findViewById(R.id.center_list);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
                outRect.set(0, 0, offset, offset);
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                View inflate = LayoutInflater.from(context).inflate(R.layout.activity_center_type_item, parent, false);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(inflate) {
                };
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAbsoluteAdapterPosition();
                        FavBean.ItemBean itemBean = mDatas.get(position);
                        boolean del = itemBean.isDel();
                        if (del) {
                            String cid = itemBean.getCid();
                            delData(cid, position);
                        } else {
                            JumpUtil.next(v.getContext(), itemBean);
                        }
                    }
                });
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                try {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    View view = holder.itemView.findViewById(R.id.center_item_del);
                    view.setVisibility(itemBean.isDel() ? View.VISIBLE : View.INVISIBLE);
                    TextView textView = holder.itemView.findViewById(R.id.center_item_name);
                    textView.setText(itemBean.getName());
                    ImageView imageView = holder.itemView.findViewById(R.id.center_item_img);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getAlbum().getPicture(false), imageView);
                } catch (Exception e) {
                }
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        });
    }

    protected void request() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> observableEmitter) {
                        int select = getView().getIntExtra(CenterActivity.INTENT_SELECT, 0);
                        observableEmitter.onNext(select);
                    }
                })
                .flatMap(new Function<Integer, Observable<BaseBean<FavBean>>>() {
                    @Override
                    public Observable<BaseBean<FavBean>> apply(Integer v) {
                        if (v == 0) {
                            return HttpClient.getHttpClient().getHttpApi().getBookmark(0, Integer.MAX_VALUE, "");
                        } else {
                            return HttpClient.getHttpClient().getHttpApi().getFavList(0, Integer.MAX_VALUE, "1");
                        }
                    }
                })
                .map(new Function<BaseBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseBean<FavBean> response) {
                        try {
                            List<FavBean.ItemBean> rows = response.getData().getRows();
                            mDatas.clear();
                            mDatas.addAll(rows);
                        } catch (Exception e) {
                        }
                        return mDatas.size() > 0;
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
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().refreshContent();
                        getView().checkNodata(aBoolean);
                    }
                })
                .subscribe());
    }

    private void refreshDel(boolean show) {
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            FavBean.ItemBean itemBean = mDatas.get(i);
            if (null == itemBean)
                continue;
            itemBean.setDel(show);
        }
        getView().refreshContent();
    }

    protected boolean dispatchKey(KeyEvent event) {
        // enter
        if (event.getRepeatCount() > 0 && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            boolean hasDel = false;
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                FavBean.ItemBean itemBean = mDatas.get(i);
                if (null == itemBean)
                    continue;
                hasDel = itemBean.isDel();
                if (hasDel)
                    break;
            }
            if (!hasDel) {
                refreshDel(true);
                return true;
            }
        }
        // back
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            boolean hasDel = false;
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                FavBean.ItemBean itemBean = mDatas.get(i);
                if (null == itemBean)
                    continue;
                hasDel = itemBean.isDel();
                if (hasDel)
                    break;
            }
            if (hasDel) {
                refreshDel(false);
                return true;
            }
        }
        // up
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("CenterPresenter => dispatchKeyEvent => up_action_down => ");
            if (focusId == R.id.center_tabs) {
                HorizontalClassLayout classLayout = (HorizontalClassLayout) getView().getCurrentFocus();
                LogUtil.log("CenterPresenter => dispatchKeyEvent => up_action_down => classLayout = " + classLayout);
                int index = classLayout.getCheckedIndex();
                classLayout.checked(index);
                getView().setFocusable(R.id.center_vip, index > 0);
                getView().setFocusable(R.id.center_search, index <= 0);
            } else if (focusId == R.id.center_search || focusId == R.id.center_vip) {
                return true;
            }
        }
        // down
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.center_search || focusId == R.id.center_vip) {
                LogUtil.log("CenterPresenter => dispatchKeyEvent => down_action_down => ");
                getView().setFocusable(R.id.center_vip, false);
                getView().setFocusable(R.id.center_search, false);
            }
        }
        // right
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("CenterPresenter => dispatchKeyEvent => right_action_down => " + getView().getCurrentFocus());
            if (focusId == R.id.center_search) {
                getView().setFocusable(R.id.center_vip, true);
                getView().setFocusable(R.id.center_search, false);
                return true;
            } else if (focusId == R.id.center_vip) {
                return true;
            }
        }
        // left
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("CenterPresenter => dispatchKeyEvent => left_action_down => " + getView().getCurrentFocus());
            if (focusId == R.id.center_vip) {
                getView().setFocusable(R.id.center_search, true);
                getView().setFocusable(R.id.center_vip, false);
                return true;
            } else if (focusId == R.id.center_search) {
                return true;
            }
        }
        return false;
    }

    private void delData(@NonNull String cid, @NonNull int position) {
//        ClassLayout classLayout = getView().findViewById(R.id.center_class);
//        int index = classLayout.getCheckedIndex();
//        // 观看历史
//        if (index == 1) {
//            addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
//                        @Override
//                        public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
//                            observableEmitter.onNext(true);
//                        }
//                    })
//                    .flatMap(new Function<Boolean, Observable<BaseBean<Object>>>() {
//                        @Override
//                        public Observable<BaseBean<Object>> apply(Boolean aBoolean) {
//                            return HttpClient.getHttpClient().getHttpApi().cancelFav(1, cid);
//                        }
//                    })
//                    .delay(40, TimeUnit.MILLISECONDS)
//                    .compose(ComposeSchedulers.io_main())
//                    .doOnSubscribe(new Consumer<Disposable>() {
//                        @Override
//                        public void accept(Disposable disposable) {
//                            getView().showLoading();
//                        }
//                    })
//                    .doOnError(new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) {
//                            getView().hideLoading();
//                        }
//                    })
//                    .doOnNext(new Consumer<BaseBean<Object>>() {
//                        @Override
//                        public void accept(BaseBean<Object> objectBaseBean) {
//                            getView().hideLoading();
//                            getView().del(position);
//
//                        }
//                    })
//                    .subscribe());
//        }
//        // 我的收藏
//        else {
//            addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
//                        @Override
//                        public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
//                            observableEmitter.onNext(true);
//                        }
//                    })
//                    .flatMap(new Function<Boolean, Observable<BaseBean<Object>>>() {
//                        @Override
//                        public Observable<BaseBean<Object>> apply(Boolean aBoolean) {
//                            return HttpClient.getHttpClient().getHttpApi().deleteBookmark(cid);
//                        }
//                    })
//                    .delay(40, TimeUnit.MILLISECONDS)
//                    .compose(ComposeSchedulers.io_main())
//                    .doOnSubscribe(new Consumer<Disposable>() {
//                        @Override
//                        public void accept(Disposable disposable) {
//                            getView().showLoading();
//                        }
//                    })
//                    .doOnError(new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) {
//                            getView().hideLoading();
//                        }
//                    })
//                    .doOnNext(new Consumer<BaseBean<Object>>() {
//                        @Override
//                        public void accept(BaseBean<Object> objectBaseBean) {
//                            getView().hideLoading();
//                            getView().del(position);
//                        }
//                    })
//                    .subscribe());
//        }
    }
}

package tv.huan.bilibili.ui.center;

import android.content.Context;
import android.graphics.Rect;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
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
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.HorizontalClassLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.OptBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class CenterPresenter extends BasePresenterImpl<CenterView> {

    private final List<FavBean.ItemBean> mDatas = new ArrayList<>();

    public CenterPresenter(@NonNull CenterView baseView) {
        super(baseView);
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
                int position = parent.getChildAdapterPosition(view);
                int bottom = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72) / 8;
                if (position % 4 == 0) {
                    outRect.set(0, 0, offset * 2, bottom);
                } else if (position % 4 == 3) {
                    outRect.set(offset * 2, 0, 0, bottom);
                } else {
                    outRect.set(offset, 0, offset, bottom);
                }

                int transX = offset * 2 / (3 * 2);
                if (position % 4 == 1) {
                    view.setTranslationX(-transX);
                } else if (position % 4 == 2) {
                    view.setTranslationX(transX);
                }
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
                inflate.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int position = holder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            FavBean.ItemBean itemBean = mDatas.get(position);
                            if (!itemBean.isTempDel()) {
                                itemBean.setTempDel(true);
                                getView().updatePosition(position);
                            }
                        }
                        return true;
                    }
                });
                inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        try {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position < 0)
                                throw new Exception();
                            FavBean.ItemBean itemBean = mDatas.get(position);
                            if (!itemBean.isTempDel())
                                throw new Exception();
                            itemBean.setTempDel(false);
                            getView().updatePosition(position);
                        } catch (Exception e) {
                            TextView textView = view.findViewById(R.id.common_poster_name);
                            textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        }
                    }
                });
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            FavBean.ItemBean itemBean = mDatas.get(position);
                            if (itemBean.isTempDel()) {
                                String cid = itemBean.getCid();
                                delData(cid, position);
                            } else {
                                itemBean.setToType(1);
                                JumpUtil.next(v.getContext(), itemBean);
                            }
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
                    view.setVisibility(itemBean.isTempDel() ? View.VISIBLE : View.INVISIBLE);
                } catch (Exception e) {
                }
                try {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.common_poster_name);
                    textView.setText(itemBean.getName());
                } catch (Exception e) {
                }
                try {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.common_poster_status);
                    textView.setText(itemBean.getStatusRec());
                    textView.setVisibility(itemBean.isVisibility() ? View.GONE : View.VISIBLE);
                } catch (Exception e) {
                }
                try {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.common_poster_img);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getAlbum().getPicture(true), imageView);
                } catch (Exception e) {
                }
                try {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.common_poster_vip);
                    GlideUtils.loadVt(imageView.getContext(), itemBean.getAlbum().getVipUrl(), imageView);
                } catch (Exception e) {
                }
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        });
    }

    protected final void requestTabs() {

        addDisposable(Observable.create(new ObservableOnSubscribe<ArrayList<ClassBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<ClassBean>> observableEmitter) {

//                        int position = getView().getIntExtra(CenterActivity.INTENT_SELECT, 0);
//                        if (position < 0 || position < 1) {
//                            position = 0;
//                        }
                ArrayList<lib.kalu.leanback.clazz.ClassBean> apis = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    lib.kalu.leanback.clazz.ClassBean classApi = new lib.kalu.leanback.clazz.ClassBean();
                    classApi.setChecked(i == 0);
                    classApi.setText(i == 0 ? "观看历史" : "我的收藏");
                    apis.add(classApi);
                }
                observableEmitter.onNext(apis);
            }
        }).delay(40, TimeUnit.MILLISECONDS).compose(ComposeSchedulers.io_main()).doOnNext(new Consumer<ArrayList<ClassBean>>() {
            @Override
            public void accept(ArrayList<ClassBean> classBeans) {
                boolean extra = getView().getBooleanExtra(CenterActivity.INTENT_FAVORY, false);
                getView().updateTab(classBeans, extra ? 1 : 0);
                getView().updateFocus();
            }
        }).subscribe());
    }

    protected void showWarning() {
        String s = "<font color='#aaaaaa'>长按</font><font color='#ff6699'>【确定键】</font><font color='#aaaaaa'>删除</font>";
        Spanned html = Html.fromHtml(s);
        getView().setText(R.id.center_warning, html);
    }

    protected void request(int position) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(position == 0);
                    }
                }).flatMap(new Function<Boolean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(Boolean v) {
                        // 观看历史
                        if (v) {
                            return HttpClient.getHttpClient().getHttpApi().getBookmark(0, Integer.MAX_VALUE, null);
                        }
                        // 我的收藏
                        else {
                            return HttpClient.getHttpClient().getHttpApi().getFavList(0, Integer.MAX_VALUE);
                        }
                    }
                }).map(new Function<BaseResponsedBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<FavBean> response) {
                        try {
                            mDatas.clear();
                        } catch (Exception e) {
                        }
                        try {
                            List<FavBean.ItemBean> rows = response.getData().getRows();
                            mDatas.addAll(rows);
                        } catch (Exception e) {
                        }
                        return mDatas.size() <= 0;
                    }
                }).delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().checkNodata(false);
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().refreshContent();
                        getView().checkNodata(aBoolean);
                    }
                }).subscribe());
    }

    private void delData(@NonNull String cid, @NonNull int position) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        HorizontalClassLayout classLayout = getView().findViewById(R.id.center_tabs);
                        int checkedIndex = classLayout.getCheckedIndex();
                        observableEmitter.onNext(checkedIndex == 0);
                    }
                }).flatMap(new Function<Boolean, Observable<BaseResponsedBean<OptBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<OptBean>> apply(Boolean v) {
                        if (v) {
                            return HttpClient.getHttpClient().getHttpApi().deleteBookmark(cid);
                        } else {
                            return HttpClient.getHttpClient().getHttpApi().cancelFavorite(cid);
                        }
                    }
                }).map(new Function<BaseResponsedBean<OptBean>, Integer>() {
                    @Override
                    public Integer apply(BaseResponsedBean<OptBean> resp) throws Exception {
                        try {
                            boolean succ = resp.getData().isSucc();
                            if (!succ)
                                throw new Exception("操作失败");
                            mDatas.remove(position);
                            return position;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }).delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                        getView().showToast(throwable);
                    }
                }).doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) {
                        getView().hideLoading();
                        getView().deletePosition(i);
                    }
                }).subscribe());
    }

    protected boolean dispatchKey(KeyEvent event) {
        // up
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
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
                getView().requestTab();
                return true;
            } else if (focusId == R.id.center_tabs) {
                if (mDatas.size() <= 0) {
                    return true;
                }
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
            } else if (focusId == R.id.center_tabs) {
                HorizontalClassLayout classLayout = (HorizontalClassLayout) getView().getCurrentFocus();
                int index = classLayout.getCheckedIndex();
                if (index >= 1) {
                    return true;
                }
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
            } else if (focusId == R.id.center_tabs) {
                HorizontalClassLayout classLayout = (HorizontalClassLayout) getView().getCurrentFocus();
                int index = classLayout.getCheckedIndex();
                if (index <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
}

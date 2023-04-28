package tv.huan.bilibili.ui.search;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import lib.kalu.frame.mvp.util.OffsetUtil;
import lib.kalu.leanback.list.RecyclerViewGrid;
import lib.kalu.leanback.list.layoutmanager.AutoMeasureNoGridLayoutManager;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.SearchBean;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallSearchBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.keyboard.KeyboardLinearLayout;

public class SearchPresenter extends BasePresenterImpl<SearchView> {

    private final ArrayList<SearchBean.ItemBean> mData = new ArrayList<>();

    public SearchPresenter(@NonNull SearchView searchView) {
        super(searchView);
    }

    protected void setAdapter() {
        Context context = getView().getContext();
        RecyclerView recyclerView = getView().findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new AutoMeasureNoGridLayoutManager(context, 3));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
//                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72);
//                int v = offset / 8;
//                outRect.set(0, 0, offset, offset);
//                int position = parent.getChildAdapterPosition(view);
//
//                int i = position % 4;
//                if (i == 0) {
//                    outRect.set(0, 0, v * 2, 0);
//                } else if (i == 3) {
//                    outRect.set(v * 2, 0, 0, 0);
//                } else {
//                    outRect.set(v, 0, v, 0);
//                }
//
//                if (i == 1) {
//                    view.setTranslationX(-v / 2);
//                } else if (i == 2) {
//                    view.setTranslationX(v / 2);
//                } else {
//                    view.setTranslationX(0);
//                }

                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_48);
                int v = offset / 6;
                outRect.set(0, 0, offset, offset);
                int position = parent.getChildAdapterPosition(view);

                int i = position % 3;
                if (i == 0) {
                    outRect.set(0, 0, v * 2, 0);
                } else if (i == 2) {
                    outRect.set(v * 2, 0, 0, 0);
                } else {
                    outRect.set(v, 0, v, 0);
                }
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                View inflate = LayoutInflater.from(context).inflate(R.layout.activity_search_item, parent, false);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(inflate) {
                };
                try {
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                SearchBean.ItemBean itemBean = mData.get(position);
                                KeyboardLinearLayout keyboardView = getView().findViewById(R.id.search_keyboard);
                                String result = keyboardView.getInput();
                                String lowerCase = result.toLowerCase();
                                JumpUtil.nextDetailFromSearch(v.getContext(), itemBean.getCid(), lowerCase);
                            }
                        }
                    });
                } catch (Exception e) {
                }
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
                    SearchBean.ItemBean itemBean = mData.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.common_poster_name);
                    textView.setText(itemBean.getName());
                } catch (Exception e) {
                }
                try {
                    SearchBean.ItemBean itemBean = mData.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.common_poster_img);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getPicture(true), imageView);
                } catch (Exception e) {
                }
                try {
                    SearchBean.ItemBean itemBean = mData.get(position);
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

    protected void request(boolean isUpdate) {
        addDisposable(Observable.create(new ObservableOnSubscribe<CallSearchBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<CallSearchBean> emitter) {

                        try {
                            // 1
                            KeyboardLinearLayout keyboardView = getView().findViewById(R.id.search_keyboard);
                            String input = keyboardView.getInput();
                            // 2
                            CallSearchBean searchBean = new CallSearchBean();
                            searchBean.setInput(input);
                            if (null == input || input.length() <= 0) {
                                String s1 = getView().getString(R.string.search_hot);
                                searchBean.setTitle(s1);
                            } else {
                                String string = getView().getString(R.string.search_title, input);
                                searchBean.setTitle(string);
                            }
                            // 3
                            emitter.onNext(searchBean);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // 上报 => 搜索关键字
                .map(new Function<CallSearchBean, CallSearchBean>() {
                    @Override
                    public CallSearchBean apply(CallSearchBean data) {
                        String input = data.getInput();
                        if (null != input && input.length() > 0) {
                            reportSearchResultItemNum(input);
                        }
                        return data;
                    }
                })
                // 接口 => 请求
                .flatMap(new Function<CallSearchBean, Observable<BaseResponsedBean<SearchBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<SearchBean>> apply(CallSearchBean data) {
                        String input = data.getInput();
                        if (null != input && input.length() > 0) {
                            int offset = OffsetUtil.getOffset(mData);
                            data.setStart(offset);
                            String s = new Gson().toJson(data);
                            return HttpClient.getHttpClient().getHttpApi().searchBySpell(input, offset, BuildConfig.HUAN_PAGE_NUM, s);
                        } else {
                            data.setStart(0);
                            String s = new Gson().toJson(data);
                            return HttpClient.getHttpClient().getHttpApi().getSearchRecommend(BoxUtil.getProdId(), BuildConfig.HUAN_PAGE_NUM, s);
                        }
                    }
                })
                // 接口 => 数据处理
                .map(new Function<BaseResponsedBean<SearchBean>, CallSearchBean>() {
                    @Override
                    public CallSearchBean apply(BaseResponsedBean<SearchBean> resp) {

                        CallSearchBean searchBean;
                        try {
                            searchBean = new Gson().fromJson(resp.getExtra(), CallSearchBean.class);
                        } catch (Exception e) {
                            searchBean = new CallSearchBean();
                        }

                        String input = searchBean.getInput();
                        if (null != input && input.length() > 0) {
                            try {
                                List<SearchBean.ItemBean> list = resp.getData().getAlbums();
                                mData.addAll(list);
                                int size = list.size();
                                searchBean.setNum(size);
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                List<SearchBean.KeyBean> keys = resp.getData().getKeys();
                                searchBean.setTags(keys);
                            } catch (Exception e1) {
                            }
                            try {
                                List<SearchBean.ItemBean> list = resp.getData().getRecommends();
                                mData.addAll(list);
                                int size = list.size();
                                searchBean.setNum(size);
                            } catch (Exception e1) {
                            }
                        }
                        try {
                            searchBean.setHasData(mData.size() > 0);
                        } catch (Exception e) {
                        }
                        return searchBean;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        if (isUpdate) {
                            mData.clear();
                            getView().notifyDataSetChanged(R.id.search_list);
                        }
                        getView().setVisibility(R.id.search_nodata, View.GONE);
                        getView().setVisibility(R.id.keyboard_tags, View.GONE);
                        getView().updateInput();
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<CallSearchBean>() {
                    @Override
                    public void accept(CallSearchBean data) {
                        getView().hideLoading();
                        getView().setText(R.id.keyboard_title, data.getTitle());
                        if (null == data.getTags()) {
                            getView().setVisibility(R.id.keyboard_tags, View.GONE);
                        } else {
                            getView().setVisibility(R.id.keyboard_tags, View.VISIBLE);
                            if (data.getTags().size() > 0) {
                                getView().updateKeys(data.getTags());
                            }
                        }
                        getView().notifyItemRangeInserted(R.id.search_list, data.getStart(), data.getNum());
                        getView().setVisibility(R.id.search_nodata, data.isHasData() ? View.GONE : View.VISIBLE);
                        if (!isUpdate && data.getNum() <= 0) {
                            getView().showToast(R.string.common_loadmore_empty);
                        }
                    }
                }).subscribe());
    }

    protected boolean dispatchEvent(KeyEvent event) {
        // down
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.search_item) {
                KeyboardLinearLayout keyboardView = getView().findViewById(R.id.search_keyboard);
                String input = keyboardView.getInput();
                if (null != input && input.length() > 0) {
                    RecyclerViewGrid recyclerView = getView().findViewById(R.id.search_list);
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    int v = itemCount % 3;
                    int focusPosition = recyclerView.findFocusPosition();
                    if (v == 0) {
                        if (itemCount - focusPosition <= 3) {
                            request(false);
                        }
                    } else {
                        if (itemCount - focusPosition <= v) {
                            request(false);
                        }
                    }
                }
            }
        }
        return false;
    }
}

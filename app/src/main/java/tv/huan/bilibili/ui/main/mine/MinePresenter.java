
package tv.huan.bilibili.ui.main.mine;

import android.content.Context;
import android.graphics.Rect;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
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
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.ResponsedBean;
import tv.huan.bilibili.bean.format.RefreshBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

@Keep
public class MinePresenter extends BasePresenterImpl<MineView> {

    private int TYPE_ITEM_HEAD = 1;
    private int TYPE_ITEM_TITLE = 2;
    private int TYPE_ITEM_IMG_HISTORY = 3;
    private int TYPE_ITEM_IMG_FAVOR = 4;
    private int TYPE_ITEM_MORE = 5;
    private int TYPE_ITEM_WEB = 6;

    private final List<FavBean.ItemBean> mDatas = new ArrayList<>();

    public MinePresenter(@NonNull MineView commonView) {
        super(commonView);
    }

    protected void setAdapter() {
        Context context = getView().getContext();
        RecyclerView recyclerView = getView().findViewById(R.id.mine_list);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                FavBean.ItemBean itemBean = mDatas.get(position);
                int itemType = itemBean.getTempType();
                return itemType == TYPE_ITEM_HEAD || itemType == TYPE_ITEM_TITLE ? 4 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int index = parent.getChildAdapterPosition(view);
                FavBean.ItemBean itemBean = mDatas.get(index);
                int tempPosition = itemBean.getTempPosition();

                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72) / 8;
                if (tempPosition == 0) {
                    outRect.set(0, 0, offset * 2, 0);
                } else if (tempPosition == 3) {
                    outRect.set(offset * 2, 0, 0, 0);
                } else if (tempPosition == 1 || tempPosition == 2) {
                    outRect.set(offset, 0, offset, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }

                int transX = offset * 2 / (3 * 2);
                if (tempPosition == 1) {
                    view.setTranslationX(-transX);
                } else if (tempPosition == 2) {
                    view.setTranslationX(transX);
                }
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Context context = parent.getContext();

                @LayoutRes
                int res;
                if (viewType == TYPE_ITEM_HEAD) {
                    res = R.layout.fragment_mine_type_head;
                } else if (viewType == TYPE_ITEM_TITLE) {
                    res = R.layout.fragment_mine_type_title;
                } else if (viewType == TYPE_ITEM_MORE) {
                    res = R.layout.fragment_mine_type_more;
                } else if (viewType == TYPE_ITEM_WEB) {
                    res = R.layout.fragment_mine_type_web;
                } else {
                    res = R.layout.fragment_mine_type_img;
                }

                View inflate = LayoutInflater.from(context).inflate(res, parent, false);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(inflate) {
                };

                if (viewType == TYPE_ITEM_HEAD) {
                    inflate.findViewById(R.id.mine_head_shopping).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                ((RecyclerView) v.getParent().getParent().getParent()).scrollToPosition(0);
                            }
                        }
                    });
                }
                // image
                else if (viewType == TYPE_ITEM_IMG_FAVOR || viewType == TYPE_ITEM_IMG_HISTORY) {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            TextView textView = view.findViewById(R.id.common_poster_name);
                            textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                        }
                    });
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position > 0) {
                                FavBean.ItemBean itemBean = mDatas.get(position);
                                itemBean.setToType(1);
                                JumpUtil.next(v.getContext(), itemBean);
                            }
                        }
                    });
                }
                // more
                else if (viewType == TYPE_ITEM_MORE) {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            getView().setSelected(v, R.id.mine_more_icon, hasFocus);
                            getView().setSelected(v, R.id.mine_more_name, hasFocus);

                        }
                    });
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                FavBean.ItemBean itemBean = mDatas.get(position);
                                int toType = itemBean.getToType();
                                if (toType == 8001) {
                                    JumpUtil.nextCenter(v.getContext(), false);
                                } else if (toType == 8002) {
                                    JumpUtil.nextCenter(v.getContext(), true);
                                }
                            }
                        }
                    });
                }
                // web
                else if (viewType == TYPE_ITEM_WEB) {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            getView().setSelected(v, R.id.mine_web_icon, hasFocus);
                            getView().setSelected(v, R.id.mine_web_name, hasFocus);

                        }
                    });
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                FavBean.ItemBean itemBean = mDatas.get(position);
                                if (itemBean.getToType() == 9001) {
                                    JumpUtil.nextWebAbout(v.getContext());
                                } else if (itemBean.getToType() == 9002) {
                                    JumpUtil.nextWebHelp(v.getContext());
                                }
                            }
                        }
                    });
                }
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                int itemViewType = holder.getItemViewType();
                // head
                if (itemViewType == TYPE_ITEM_HEAD) {
                    getView().setText(holder.itemView, R.id.mine_head_uuid, BoxUtil.getUserId());
                    String s = "<font color='#ff6699'>2021-01-01</font><font color='#ffffff'>&#160;&#160;到期</font>";
                    getView().setText(holder.itemView, R.id.mine_head_date, Html.fromHtml(s));
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.mine_head_banner);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getTempBanner(), imageView);
                }
                // title
                else if (itemViewType == TYPE_ITEM_TITLE) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_title_txt, itemBean.getName());
                }
                // item-img
                else if (itemViewType == TYPE_ITEM_IMG_HISTORY || itemViewType == TYPE_ITEM_IMG_FAVOR) {
                    try {
                        FavBean.ItemBean itemBean = mDatas.get(position);
                        getView().setText(holder.itemView, R.id.common_poster_name, itemBean.getName());
                    } catch (Exception e) {
                    }
                    try {
                        FavBean.ItemBean itemBean = mDatas.get(position);
                        getView().setText(holder.itemView, R.id.common_poster_status, itemBean.getStatusRec());
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
                // item-more
                else if (itemViewType == TYPE_ITEM_MORE) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_more_name, itemBean.getName());
                    getView().setImageResource(holder.itemView, R.id.mine_more_icon, itemBean.getTempDrawable());
                }
                // item-web
                else if (itemViewType == TYPE_ITEM_WEB) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_web_name, itemBean.getName());
                    getView().setImageResource(holder.itemView, R.id.mine_web_icon, itemBean.getTempDrawable());
                }
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }

            @Override
            public int getItemViewType(int position) {
                return mDatas.get(position).getTempType();
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
                // 收藏
                .flatMap(new Function<Boolean, Observable<ResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<ResponsedBean<FavBean>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().getFavList(0, 3);
                    }
                })
                // 观看记录
                .flatMap(new Function<ResponsedBean<FavBean>, Observable<ResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<ResponsedBean<FavBean>> apply(ResponsedBean<FavBean> favBeanResponsedBean) {
                        String s;
                        try {
                            List<FavBean.ItemBean> list = favBeanResponsedBean.getData().getRows();
                            s = new Gson().toJson(list);
                        } catch (Exception e) {
                            s = null;
                        }
                        return HttpClient.getHttpClient().getHttpApi().getBookmark(0, 3, s);
                    }
                })
                // 数据处理
                .map(new Function<ResponsedBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(ResponsedBean<FavBean> response) {

                        // clean
                        mDatas.clear();

                        // 1 => head
                        FavBean.ItemBean item = new FavBean.ItemBean();
                        item.setTempType(TYPE_ITEM_HEAD);
                        mDatas.add(item);

                        // 2 => title => 观看历史
                        FavBean.ItemBean itemHistoryTitle = new FavBean.ItemBean();
                        itemHistoryTitle.setTempType(TYPE_ITEM_TITLE);
                        itemHistoryTitle.setName("观看历史");
                        mDatas.add(itemHistoryTitle);

                        // 3 => 观看历史 and more
                        try {
                            // 1
                            List<FavBean.ItemBean> list = response.getData().getRows();
                            if (null != list && list.size() > 0) {
                                for (int i = 0; i <= 2; i++) {
                                    FavBean.ItemBean t = list.get(i);
                                    if (null == t)
                                        continue;
                                    t.setTempPosition(i);
                                    t.setTempType(TYPE_ITEM_IMG_HISTORY);
                                    mDatas.add(t);
                                }
                            }
                        } catch (Exception e) {
                        }
                        // 2
                        FavBean.ItemBean itemHistory = new FavBean.ItemBean();
                        itemHistory.setTempType(TYPE_ITEM_MORE);
                        itemHistory.setName("全部历史");
                        itemHistory.setToType(8001);
                        itemHistory.setTempDrawable(R.drawable.ic_selector_common_rec);
                        itemHistory.setTempPosition(mDatas.get(mDatas.size() - 1).getTempPosition() + 1);
                        mDatas.add(itemHistory);

                        // 4 => title => 我的收藏
                        FavBean.ItemBean itemFavorTitle = new FavBean.ItemBean();
                        itemFavorTitle.setTempType(TYPE_ITEM_TITLE);
                        itemFavorTitle.setName("我的收藏");
                        mDatas.add(itemFavorTitle);

                        // 5 => 我的收藏
                        try {
                            // 1
                            String extra = response.getExtra();
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<FavBean.ItemBean>>() {
                            }.getType();
                            List<FavBean.ItemBean> list = gson.fromJson(extra, type);
                            if (null != list && list.size() > 0) {
                                for (int i = 0; i <= 2; i++) {
                                    FavBean.ItemBean t = list.get(i);
                                    if (null == t)
                                        continue;
                                    t.setTempPosition(i);
                                    t.setTempType(TYPE_ITEM_IMG_FAVOR);
                                    mDatas.add(t);
                                }
                            }
                        } catch (Exception e) {
                        }
                        // 2
                        FavBean.ItemBean itemFavor = new FavBean.ItemBean();
                        itemFavor.setTempType(TYPE_ITEM_MORE);
                        itemFavor.setName("全部收藏");
                        itemFavor.setTempPosition(mDatas.get(mDatas.size() - 1).getTempPosition() + 1);
                        itemFavor.setTempDrawable(R.drawable.ic_selector_common_favor2);
                        itemFavor.setToType(8002);
                        mDatas.add(itemFavor);

                        // 7 => title => 更多功能
                        FavBean.ItemBean itemMoreTitle = new FavBean.ItemBean();
                        itemMoreTitle.setTempType(TYPE_ITEM_TITLE);
                        itemMoreTitle.setName("更多功能");
                        mDatas.add(itemMoreTitle);

                        // 8 => 更多功能
                        for (int i = 0; i < 2; i++) {
                            FavBean.ItemBean t = new FavBean.ItemBean();
                            t.setTempType(TYPE_ITEM_WEB);
                            t.setTempPosition(i);
                            if (i == 0) {
                                t.setName("帮助中心");
                                t.setToType(9001);
                                t.setTempDrawable(R.drawable.ic_selector_common_help);
                            } else {
                                t.setName("关于我们");
                                t.setToType(9002);
                                t.setTempDrawable(R.drawable.ic_selector_common_about);
                            }
                            mDatas.add(t);
                        }

                        return true;
                    }
                })
                // Banner
                .flatMap(new Function<Boolean, ObservableSource<ResponsedBean<String>>>() {
                    @Override
                    public ObservableSource<ResponsedBean<String>> apply(Boolean integer) {
                        return HttpClient.getHttpClient().getHttpApi().getFileUrl(1);
                    }
                })
                .map(new Function<ResponsedBean<String>, Boolean>() {
                    @Override
                    public Boolean apply(ResponsedBean<String> responsedBean) {
                        try {
                            mDatas.get(0).setTempBanner(responsedBean.getData());
                        } catch (Exception e) {
                        }
                        return true;
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
                    public void accept(Boolean i) {
                        getView().hideLoading();
                        getView().refreshContent();
                    }
                })
                .subscribe());
    }

    protected void refresh() {
        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                // 我的收藏
                .flatMap(new Function<Boolean, Observable<ResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<ResponsedBean<FavBean>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().getFavList(0, 3);
                    }
                })
                // 我的收藏 => 数据处理
                .map(new Function<ResponsedBean<FavBean>, RefreshBean>() {
                    @Override
                    public RefreshBean apply(ResponsedBean<FavBean> resp) throws Exception {
                        try {
                            LinkedList<FavBean.ItemBean> beans = new LinkedList<>();
                            int start = -1;
                            for (FavBean.ItemBean t : mDatas) {
                                if (null == t)
                                    continue;
                                if (t.getTempType() == TYPE_ITEM_IMG_FAVOR) {
                                    if (start == -1) {
                                        start = mDatas.indexOf(t);
                                    }
                                    beans.add(t);
                                }
                            }
                            for (FavBean.ItemBean t : beans) {
                                mDatas.remove(t);
                            }
                            if (start != -1) {
                                List<FavBean.ItemBean> rows = resp.getData().getRows();
                                int size = rows.size();
                                for (int i = 0; i <= 2; i++) {
                                    FavBean.ItemBean t = rows.get(i);
                                    if (null == t)
                                        continue;
                                    t.setTempPosition(i);
                                    t.setTempType(TYPE_ITEM_IMG_FAVOR);
                                }
                                mDatas.addAll(start, rows);
                                RefreshBean bean = new RefreshBean();
                                bean.setStart(start);
                                bean.setLength(size);
                                return bean;
                            }
                            throw new Exception("error");
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnNext(new Consumer<RefreshBean>() {
                    @Override
                    public void accept(RefreshBean data) {
                        getView().refreshContent(data.getStart(), data.getLength());
                    }
                })
                .subscribe());
    }
}

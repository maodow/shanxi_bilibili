package tv.huan.bilibili.ui.main.mine;

import android.content.Context;
import android.graphics.Rect;
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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
import lib.kalu.frame.mvp.util.CacheUtil;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.local.LocalBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

@Keep
public class MinePresenter extends BasePresenterImpl<MineView> {

    private int TYPE_ITEM_HEAD = 1;
    private int TYPE_ITEM_TITLE = 2;
    private int TYPE_ITEM_IMG_HISTORY = 3;
    private int TYPE_ITEM_IMG_FAVOR = 4;
    private int TYPE_ITEM_MORE = 5;
    private int TYPE_ITEM_WEB = 6;

    private final List<LocalBean> mDatas = new ArrayList<>();

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
                LocalBean itemBean = mDatas.get(position);
                int itemType = itemBean.getLocal_type();
                return itemType == TYPE_ITEM_HEAD || itemType == TYPE_ITEM_TITLE ? 4 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int index = parent.getChildAdapterPosition(view);
                LocalBean itemBean = mDatas.get(index);
                int tempPosition = itemBean.getLocal_index();

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

                @LayoutRes int res;
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
                // 观看历史
                else if (viewType == TYPE_ITEM_IMG_HISTORY) {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            TextView textView = v.findViewById(R.id.common_poster_name);
                            textView.setEllipsize(hasFocus ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            if (hasFocus) {
                                ((RecyclerView) v.getParent()).scrollToPosition(1);
                            }
                        }
                    });
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position > 0) {
                                LocalBean itemBean = mDatas.get(position);
                                itemBean.setToType(1);
                                getView().putBooleanExtra(MineFragment.BUNDLE_REFRESH, true);
                                JumpUtil.next(v.getContext(), itemBean);
                            }
                        }
                    });
                }
                // 我的收藏
                else if (viewType == TYPE_ITEM_IMG_FAVOR) {
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
                                LocalBean itemBean = mDatas.get(position);
                                itemBean.setToType(1);
                                getView().putBooleanExtra(MineFragment.BUNDLE_REFRESH, true);
                                JumpUtil.next(v.getContext(), itemBean);
                            }
                        }
                    });
                }
                // 全部**
                else if (viewType == TYPE_ITEM_MORE) {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            getView().setSelected(v, R.id.mine_more_icon, hasFocus);
                            getView().setSelected(v, R.id.mine_more_name, hasFocus);
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                LocalBean itemBean = mDatas.get(position);
                                int toType = itemBean.getToType();
                                if (toType == 8001) {
                                    if (hasFocus) {
                                        ((RecyclerView) v.getParent()).scrollToPosition(1);
                                    }
                                }
                            }
                        }
                    });
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                LocalBean itemBean = mDatas.get(position);
                                int toType = itemBean.getToType();
                                if (toType == 8001) {
                                    getView().putBooleanExtra(MineFragment.BUNDLE_REFRESH, true);
                                    JumpUtil.nextCenter(v.getContext(), false);
                                } else if (toType == 8002) {
                                    getView().putBooleanExtra(MineFragment.BUNDLE_REFRESH, true);
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
                                LocalBean itemBean = mDatas.get(position);
                                if (itemBean.getToType() == 9001) {
                                    JumpUtil.nextWebHelp(v.getContext());
                                } else if (itemBean.getToType() == 9002) {
                                    JumpUtil.nextWebAbout(v.getContext());
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
//                    String s = "<font color='#ff6699'>2021-01-01</font><font color='#ffffff'>&#160;&#160;到期</font>";
//                    getView().setText(holder.itemView, R.id.mine_head_date, Html.fromHtml(s));
                    getView().setText(holder.itemView, R.id.mine_head_uuid, BoxUtil.getUserId());
                    LocalBean itemBean = mDatas.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.mine_head_banner);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getLocal_banner(), imageView);
                }
                // title
                else if (itemViewType == TYPE_ITEM_TITLE) {
                    LocalBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_title_txt, itemBean.getName());
                }
                // item-img
                else if (itemViewType == TYPE_ITEM_IMG_HISTORY || itemViewType == TYPE_ITEM_IMG_FAVOR) {
                    try {
                        LocalBean itemBean = mDatas.get(position);
                        getView().setText(holder.itemView, R.id.common_poster_name, itemBean.getName());
                    } catch (Exception e) {
                    }
                    try {
                        LocalBean itemBean = mDatas.get(position);
                        getView().setText(holder.itemView, R.id.common_poster_status, itemBean.getLocal_status());
                    } catch (Exception e) {
                    }
                    try {
                        LocalBean itemBean = mDatas.get(position);
                        ImageView imageView = holder.itemView.findViewById(R.id.common_poster_img);
                        GlideUtils.loadHz(imageView.getContext(), itemBean.getLocal_img(), imageView);
                    } catch (Exception e) {
                    }
                    try {
                        LocalBean itemBean = mDatas.get(position);
                        ImageView imageView = holder.itemView.findViewById(R.id.common_poster_vip);
                        GlideUtils.loadVt(imageView.getContext(), itemBean.getLocal_vip(), imageView);
                    } catch (Exception e) {
                    }
                }
                // item-more
                else if (itemViewType == TYPE_ITEM_MORE) {
                    LocalBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_more_name, itemBean.getName());
                    getView().setImageResource(holder.itemView, R.id.mine_more_icon, itemBean.getLocal_drawable());
                }
                // item-web
                else if (itemViewType == TYPE_ITEM_WEB) {
                    LocalBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_web_name, itemBean.getName());
                    getView().setImageResource(holder.itemView, R.id.mine_web_icon, itemBean.getLocal_drawable());
                }
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }

            @Override
            public int getItemViewType(int position) {
                return mDatas.get(position).getLocal_type();
            }
        });
    }

    protected void request() {
        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        emitter.onNext(true);
                    }
                })
                // 接口 => 图片
                .flatMap(new Function<Boolean, ObservableSource<BaseResponsedBean<String>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<String>> apply(Boolean data) {
                        return HttpClient.getHttpClient().getHttpApi().getFileUrl(1, null);
                    }
                })
                // 数据处理 => 图片
                .map(new Function<BaseResponsedBean<String>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<String> resp) {
                        try {
                            LocalBean itemBean = new LocalBean();
                            itemBean.setLocal_type(TYPE_ITEM_HEAD);
                            mDatas.clear();
                            mDatas.add(itemBean);
                        } catch (Exception e) {
                        }
                        try {
                            if (mDatas.isEmpty())
                                throw new Exception();
                            String data = resp.getData();
                            if (null == data)
                                throw new Exception();
                            LocalBean localBean = mDatas.get(0);
                            localBean.setLocal_banner(data);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                // 接口 => 观看历史
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(Boolean data) {
                        return HttpClient.getHttpClient().getHttpApi().getBookmark(0, 3, null);
                    }
                })
                // 数据处理 => 观看历史
                .map(new Function<BaseResponsedBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<FavBean> resp) {

                        // 1
                        try {
                            LocalBean itemTitle = new LocalBean();
                            itemTitle.setLocal_type(TYPE_ITEM_TITLE);
                            itemTitle.setName("观看历史");
                            mDatas.add(itemTitle);
                        } catch (Exception e) {
                        }

                        // 2
                        try {
                            FavBean data = resp.getData();
                            if (null == data)
                                throw new Exception();
                            List<FavBean.ItemBean> oldList = data.getRows();
                            if (null == oldList || oldList.size() <= 0)
                                throw new Exception();
                            ArrayList<LocalBean> newList = new ArrayList<>();
                            for (FavBean.ItemBean t : oldList) {
                                if (null == t)
                                    continue;
                                if (newList.size() >= 3)
                                    break;
                                LocalBean o = new LocalBean();
                                o.setCid(t.getCid());
                                o.setName(t.getAlbumName());
                                o.setLocal_img(t.getAlbum().getPicture(true));
                                o.setLocal_index(oldList.indexOf(t));
                                o.setLocal_status(t.getStatusRec());
                                o.setLocal_type(TYPE_ITEM_IMG_HISTORY);
                                newList.add(o);
                            }
                            String s = new Gson().toJson(newList);
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_HISTORY, s);
                            mDatas.addAll(newList);
                        } catch (Exception e) {
                        }

                        // 3
                        try {
                            LocalBean itemHistory = new LocalBean();
                            itemHistory.setLocal_type(TYPE_ITEM_MORE);
                            itemHistory.setName("全部历史");
                            itemHistory.setToType(8001);
                            itemHistory.setLocal_drawable(R.drawable.ic_selector_common_rec);
                            if (mDatas.get(mDatas.size() - 1).getLocal_type() == TYPE_ITEM_IMG_HISTORY) {
                                itemHistory.setLocal_index(mDatas.get(mDatas.size() - 1).getLocal_index() + 1);
                            } else {
                                itemHistory.setLocal_index(0);
                            }
                            mDatas.add(itemHistory);
                        } catch (Exception e) {
                        }

                        return true;
                    }
                })
                // 接口 => 我的收藏
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(Boolean data) {
                        return HttpClient.getHttpClient().getHttpApi().getFavList(0, 3, null);
                    }
                })
                // 数据处理 => 我的收藏
                .map(new Function<BaseResponsedBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<FavBean> resp) {

                        // 1
                        try {
                            LocalBean itemFavorTitle = new LocalBean();
                            itemFavorTitle.setLocal_type(TYPE_ITEM_TITLE);
                            itemFavorTitle.setName("我的收藏");
                            mDatas.add(itemFavorTitle);
                        } catch (Exception e) {
                        }

                        // 2
                        try {
                            FavBean data = resp.getData();
                            if (null == data)
                                throw new Exception();
                            List<FavBean.ItemBean> oldList = data.getRows();
                            if (null == oldList || oldList.size() <= 0)
                                throw new Exception();
                            ArrayList<LocalBean> newList = new ArrayList<>();
                            for (FavBean.ItemBean t : oldList) {
                                if (null == t)
                                    continue;
                                if (newList.size() >= 3)
                                    break;
                                LocalBean o = new LocalBean();
                                o.setCid(t.getCid());
                                o.setName(t.getAlbum().getName());
                                o.setLocal_img(t.getAlbum().getPicture(true));
                                o.setLocal_index(oldList.indexOf(t));
                                o.setLocal_type(TYPE_ITEM_IMG_FAVOR);
                                newList.add(o);
                            }
                            String s = new Gson().toJson(newList);
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR, s);
                            mDatas.addAll(newList);
                        } catch (Exception e) {
                        }

                        // 3
                        try {
                            LocalBean itemFavor = new LocalBean();
                            itemFavor.setLocal_type(TYPE_ITEM_MORE);
                            itemFavor.setName("全部收藏");
                            if (mDatas.get(mDatas.size() - 1).getLocal_type() == TYPE_ITEM_IMG_FAVOR) {
                                itemFavor.setLocal_index(mDatas.get(mDatas.size() - 1).getLocal_index() + 1);
                            } else {
                                itemFavor.setLocal_index(0);
                            }
                            itemFavor.setLocal_drawable(R.drawable.ic_selector_common_favor3);
                            itemFavor.setToType(8002);
                            mDatas.add(itemFavor);
                        } catch (Exception e) {
                        }

                        return true;
                    }
                })
                // 数据处理 => 更多功能
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean data) {

                        // 1
                        try {
                            LocalBean itemMoreTitle = new LocalBean();
                            itemMoreTitle.setLocal_type(TYPE_ITEM_TITLE);
                            itemMoreTitle.setName("更多功能");
                            mDatas.add(itemMoreTitle);
                        } catch (Exception e) {
                        }

                        // 2
                        try {
                            for (int i = 0; i < 2; i++) {
                                LocalBean t = new LocalBean();
                                t.setLocal_type(TYPE_ITEM_WEB);
                                t.setLocal_index(i);
                                if (i == 0) {
                                    t.setName("帮助中心");
                                    t.setToType(9001);
                                    t.setLocal_drawable(R.drawable.ic_selector_common_help);
                                } else {
                                    t.setName("关于我们");
                                    t.setToType(9002);
                                    t.setLocal_drawable(R.drawable.ic_selector_common_about);
                                }
                                mDatas.add(t);
                            }
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
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean data) {
                        getView().hideLoading();
                        if (mDatas.isEmpty()) {
                            getView().notifyItemRangeChanged(R.id.mine_list, 0, mDatas.size());
                        } else {
                            getView().notifyDataSetChanged(R.id.mine_list);
                        }
                    }
                }).subscribe());
    }

    protected final void updateCard() {
        addDisposable(Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) {
                        String s = new Gson().toJson(mDatas);
                        emitter.onNext(s);
                    }
                })
                // 刷新 => 全部收藏
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String aBoolean) {
                        try {
                            // 1
                            String s = CacheUtil.getCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR);
                            if (null == s || s.length() <= 0)
                                throw new Exception();
                            Type type = new TypeToken<List<LocalBean>>() {
                            }.getType();
                            List<LocalBean> newList = new Gson().fromJson(s, type);
                            // check
                            if (mDatas.get(mDatas.size() - 5).getCid().equals(newList.get(0).getCid()))
                                throw new Exception();
                            for (LocalBean o : newList) {
                                if (null == o) continue;
                                o.setLocal_index(newList.indexOf(o));
                                o.setLocal_type(TYPE_ITEM_IMG_FAVOR);
                            }
                            // 2
                            ArrayList<LocalBean> oldList = new ArrayList<>();
                            for (LocalBean o : mDatas) {
                                if (null == o) continue;
                                int tempType = o.getLocal_type();
                                if (tempType == TYPE_ITEM_IMG_FAVOR) {
                                    oldList.add(o);
                                }
                            }
                            // 3
                            for (LocalBean t : oldList) {
                                if (null == t) continue;
                                mDatas.remove(t);
                            }
                            // 4
                            try {
                                LocalBean itemBean = mDatas.get(mDatas.size() - 4);
                                itemBean.setLocal_index(newList.size());
                            } catch (Exception e) {
                            }
                            // 5
                            try {
                                mDatas.addAll(mDatas.size() - 4, newList);
                            } catch (Exception e) {
                            }
                        } catch (Exception e) {
                        }
                        return aBoolean;
                    }
                })
                // 刷新 => 全部历史
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String aBoolean) {
                        try {
                            // 1
                            String s = CacheUtil.getCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_HISTORY);
                            if (null == s || s.length() <= 0)
                                throw new Exception();
                            Type type = new TypeToken<List<LocalBean>>() {
                            }.getType();
                            List<LocalBean> newList = new Gson().fromJson(s, type);
                            // check
                            if (mDatas.get(2).getCid().equals(newList.get(0).getCid()))
                                throw new Exception();
                            for (LocalBean o : newList) {
                                if (null == o) continue;
                                o.setLocal_index(newList.indexOf(o));
                                o.setLocal_type(TYPE_ITEM_IMG_HISTORY);
                            }
                            // 2
                            ArrayList<LocalBean> oldList = new ArrayList<>();
                            for (LocalBean t : mDatas) {
                                if (null == t) continue;
                                int tempType = t.getLocal_type();
                                if (tempType == TYPE_ITEM_IMG_HISTORY) {
                                    oldList.add(t);
                                }
                            }
                            // 3
                            for (LocalBean t : oldList) {
                                if (null == t) continue;
                                mDatas.remove(t);
                            }
                            // 4
                            try {
                                LocalBean localBean = mDatas.get(2);
                                localBean.setLocal_index(newList.size());
                            } catch (Exception e) {
                            }
                            // 5
                            try {
                                mDatas.addAll(2, newList);
                            } catch (Exception e) {
                            }
                        } catch (Exception e) {
                        }
                        return aBoolean;
                    }
                })
                // 是否变化
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        try {
                            String s1 = new Gson().toJson(mDatas);
                            if (s1.equals(s))
                                throw new Exception();
                            return mDatas.size() - 4;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
//                        getView().showToast("获取收藏缓存失败");
                    }
                }).doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer position) {
                        getView().notifyDataRangeChanged(R.id.mine_list);
                        getView().requestFocusPosition(position);
                    }
                }).subscribe());
    }
}

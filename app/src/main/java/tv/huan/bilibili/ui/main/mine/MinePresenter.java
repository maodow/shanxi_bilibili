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

import com.google.gson.Gson;

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
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallMineBean;
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
                // 观看历史
                else if (viewType == TYPE_ITEM_IMG_HISTORY) {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                ((RecyclerView) v.getParent()).scrollToPosition(1);
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
                                FavBean.ItemBean itemBean = mDatas.get(position);
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
                                FavBean.ItemBean itemBean = mDatas.get(position);
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
                                FavBean.ItemBean itemBean = mDatas.get(position);
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
                                FavBean.ItemBean itemBean = mDatas.get(position);
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
                                FavBean.ItemBean itemBean = mDatas.get(position);
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
        addDisposable(Observable.create(new ObservableOnSubscribe<CallMineBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<CallMineBean> observableEmitter) {
                        CallMineBean mineBean = new CallMineBean();
                        mineBean.setContainsData(mDatas.size() > 0);
                        observableEmitter.onNext(mineBean);
                    }
                })
                // 接口 => 图片
                .flatMap(new Function<CallMineBean, ObservableSource<BaseResponsedBean<String>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<String>> apply(CallMineBean data) {
                        boolean containsData = data.isContainsData();
                        if (containsData) {
                            return Observable.create(new ObservableOnSubscribe<BaseResponsedBean<String>>() {
                                @Override
                                public void subscribe(ObservableEmitter<BaseResponsedBean<String>> emitter) {
                                    BaseResponsedBean<String> responsedBean = new BaseResponsedBean<>();
                                    responsedBean.setExtra(new Gson().toJson(data));
                                    emitter.onNext(responsedBean);
                                }
                            });
                        } else {
                            String s = new Gson().toJson(data);
                            return HttpClient.getHttpClient().getHttpApi().getFileUrl(1, s);
                        }
                    }
                })
                // 数据处理 => 图片
                .map(new Function<BaseResponsedBean<String>, CallMineBean>() {
                    @Override
                    public CallMineBean apply(BaseResponsedBean<String> resp) {

                        try {
                            String data = resp.getData();
                            if (null == data)
                                throw new Exception();
                            FavBean.ItemBean item = new FavBean.ItemBean();
                            item.setTempBanner(data);
                            item.setTempType(TYPE_ITEM_HEAD);
                            mDatas.add(item);
                        } catch (Exception e) {
                            FavBean.ItemBean itemBean = mDatas.remove(0);
                            mDatas.clear();
                            mDatas.add(itemBean);
                        }

                        try {
                            CallMineBean mineBean = new Gson().fromJson(resp.getExtra(), CallMineBean.class);
                            return mineBean;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // 接口 => 观看历史
                .flatMap(new Function<CallMineBean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(CallMineBean data) {
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getBookmark(0, 3, s);
                    }
                })
                // 数据处理 => 观看历史
                .map(new Function<BaseResponsedBean<FavBean>, CallMineBean>() {
                    @Override
                    public CallMineBean apply(BaseResponsedBean<FavBean> resp) {

                        FavBean.ItemBean itemTitle = new FavBean.ItemBean();
                        itemTitle.setTempType(TYPE_ITEM_TITLE);
                        itemTitle.setName("观看历史");
                        mDatas.add(itemTitle);

                        try {
                            List<FavBean.ItemBean> list = resp.getData().getRows();
                            for (int i = 0; i <= 2; i++) {
                                FavBean.ItemBean t = list.get(i);
                                if (null == t)
                                    continue;
                                t.setTempPosition(i);
                                t.setTempType(TYPE_ITEM_IMG_HISTORY);
                                mDatas.add(t);
                            }
                        } catch (Exception e) {
                        }

                        FavBean.ItemBean itemHistory = new FavBean.ItemBean();
                        itemHistory.setTempType(TYPE_ITEM_MORE);
                        itemHistory.setName("全部历史");
                        itemHistory.setToType(8001);
                        itemHistory.setTempDrawable(R.drawable.ic_selector_common_rec);
                        if (mDatas.get(mDatas.size() - 1).getTempType() == TYPE_ITEM_IMG_HISTORY) {
                            itemHistory.setTempPosition(mDatas.get(mDatas.size() - 1).getTempPosition() + 1);
                        } else {
                            itemHistory.setTempPosition(0);
                        }
                        mDatas.add(itemHistory);

                        try {
                            CallMineBean mineBean = new Gson().fromJson(resp.getExtra(), CallMineBean.class);
                            return mineBean;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // 接口 => 我的收藏
                .flatMap(new Function<CallMineBean, Observable<BaseResponsedBean<FavBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavBean>> apply(CallMineBean data) {
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getFavList(0, 3, s);
                    }
                })
                // 数据处理 => 我的收藏
                .map(new Function<BaseResponsedBean<FavBean>, CallMineBean>() {
                    @Override
                    public CallMineBean apply(BaseResponsedBean<FavBean> resp) {

                        FavBean.ItemBean itemFavorTitle = new FavBean.ItemBean();
                        itemFavorTitle.setTempType(TYPE_ITEM_TITLE);
                        itemFavorTitle.setName("我的收藏");
                        mDatas.add(itemFavorTitle);

                        try {
                            List<FavBean.ItemBean> list = resp.getData().getRows();
                            for (int i = 0; i <= 2; i++) {
                                FavBean.ItemBean t = list.get(i);
                                if (null == t)
                                    continue;
                                t.setTempPosition(i);
                                t.setTempType(TYPE_ITEM_IMG_FAVOR);
                                mDatas.add(t);
                            }
                        } catch (Exception e) {
                        }

                        FavBean.ItemBean itemFavor = new FavBean.ItemBean();
                        itemFavor.setTempType(TYPE_ITEM_MORE);
                        itemFavor.setName("全部收藏");
                        if (mDatas.get(mDatas.size() - 1).getTempType() == TYPE_ITEM_IMG_FAVOR) {
                            itemFavor.setTempPosition(mDatas.get(mDatas.size() - 1).getTempPosition() + 1);
                        } else {
                            itemFavor.setTempPosition(0);
                        }
                        itemFavor.setTempDrawable(R.drawable.ic_selector_common_favor2);
                        itemFavor.setToType(8002);
                        mDatas.add(itemFavor);

                        try {
                            CallMineBean mineBean = new Gson().fromJson(resp.getExtra(), CallMineBean.class);
                            return mineBean;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // 数据处理 => 更多功能
                .map(new Function<CallMineBean, CallMineBean>() {
                    @Override
                    public CallMineBean apply(CallMineBean data) {

                        FavBean.ItemBean itemMoreTitle = new FavBean.ItemBean();
                        itemMoreTitle.setTempType(TYPE_ITEM_TITLE);
                        itemMoreTitle.setName("更多功能");
                        mDatas.add(itemMoreTitle);

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

                        try {
                            data.setLength(mDatas.size());
                        } catch (Exception e) {
                        }
                        return data;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        if (mDatas.size() <= 0) {
                            getView().showLoading();
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                })
                .doOnNext(new Consumer<CallMineBean>() {
                    @Override
                    public void accept(CallMineBean data) {
                        getView().hideLoading();
                        if (data.isContainsData()) {
                            getView().notifyItemRangeChanged(R.id.mine_list, 0, data.getLength());
                        } else {
                            getView().notifyDataSetChanged(R.id.mine_list);
                        }
                    }
                })
                .subscribe());
    }
}

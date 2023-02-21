
package tv.huan.bilibili.ui.main.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.BasePresenter;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;

@Keep
public class MinePresenter extends BasePresenter<MineView> {

    private int TYPE_ITEM_HEAD = 1;
    private int TYPE_ITEM_TITLE = 2;
    private int TYPE_ITEM_IMG = 3;
    private int TYPE_ITEM_MORE = 4;
    private int TYPE_ITEM_WEB = 5;

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
                int itemType = itemBean.getItemType();
                return itemType == TYPE_ITEM_HEAD || itemType == TYPE_ITEM_TITLE ? 4 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_9);
                int position = parent.getChildAdapterPosition(view);

                FavBean.ItemBean itemBean = mDatas.get(position);
                int itemType = itemBean.getItemType();
                if (itemType == TYPE_ITEM_HEAD || itemType == TYPE_ITEM_TITLE)
                    return;

                // 改变大小
                int index = itemBean.getIndex();
                if (index == 0) {
                    outRect.set(0, 0, offset * 2, 0);
                } else if (index == 3) {
                    outRect.set(offset * 2, 0, 0, 0);
                } else {
                    outRect.set(offset, 0, offset, 0);
                }
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @SuppressLint("MissingInflatedId")
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
                    inflate.findViewById(R.id.mine_head_shopping).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "vip", Toast.LENGTH_SHORT).show();
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
                            if (position > 0) {
                                FavBean.ItemBean itemBean = mDatas.get(position);
                                int jumpType = itemBean.getJumpType();
                                JumpUtil.nextCenter(v.getContext(), jumpType == 0 ? true : false);
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
                            if (position > 0) {
                                FavBean.ItemBean itemBean = mDatas.get(position);
                                int jumpType = itemBean.getJumpType();
                                JumpUtil.nextWeb(v.getContext(), jumpType == 0 ? "http://www.baidu.com" : "http://www.hao123.com");
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
                    getView().setText(holder.itemView, R.id.mine_head_shopping, "续费会员");
                    getView().setText(holder.itemView, R.id.mine_head_uuid, BoxUtil.getCa());
                    String s = "<font color='#ffffff'>2021-01-01</font><font color='#00ff00'>  到期</font>";
                    getView().setText(holder.itemView, R.id.mine_head_date, Html.fromHtml(s));
                }
                // title
                else if (itemViewType == TYPE_ITEM_TITLE) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_title_txt, itemBean.getTitle());
                }
                // item-img
                else if (itemViewType == TYPE_ITEM_IMG) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_img_name, itemBean.getTitle());
                    ImageView imageView = holder.itemView.findViewById(R.id.mine_img_icon);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getAlbum().getNewPicHz(), imageView);
                }
                // item-more
                else if (itemViewType == TYPE_ITEM_MORE) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_more_name, itemBean.getTitle());
                    getView().setImageResource(holder.itemView, R.id.mine_more_icon, itemBean.getIcon());
                }
                // item-web
                else if (itemViewType == TYPE_ITEM_WEB) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                    getView().setText(holder.itemView, R.id.mine_web_name, itemBean.getTitle());
                    getView().setImageResource(holder.itemView, R.id.mine_web_icon, itemBean.getIcon());
                }
                // item-img
                else if (itemViewType == TYPE_ITEM_IMG) {
                    FavBean.ItemBean itemBean = mDatas.get(position);
                }
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }

            @Override
            public int getItemViewType(int position) {
                try {
                    return mDatas.get(position).getItemType();
                } catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }
            }
        });
    }

    protected void request() {

        if (mDatas.size() > 0)
            return;

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                // 收藏
                .flatMap(new Function<Boolean, Observable<BaseBean<FavBean>>>() {
                    @Override
                    public Observable<BaseBean<FavBean>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().getFavList(0, 3, "1");
                    }
                })
                // 观看记录
                .flatMap(new Function<BaseBean<FavBean>, Observable<BaseBean<FavBean>>>() {
                    @Override
                    public Observable<BaseBean<FavBean>> apply(BaseBean<FavBean> favBeanBaseBean) {
                        String s;
                        try {
                            List<FavBean.ItemBean> list = favBeanBaseBean.getData().getRows();
                            s = new Gson().toJson(list);
                        } catch (Exception e) {
                            s = null;
                        }
                        return HttpClient.getHttpClient().getHttpApi().getBookmark(0, 3, s);
                    }
                })
                .map(new Function<BaseBean<FavBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseBean<FavBean> response) {

                        // clean
                        mDatas.clear();

                        // 1 => head
                        FavBean.ItemBean item = new FavBean.ItemBean();
                        item.setItemType(TYPE_ITEM_HEAD);
                        mDatas.add(item);


                        // 2 => title => 观看历史
                        FavBean.ItemBean itemT1 = new FavBean.ItemBean();
                        itemT1.setItemType(TYPE_ITEM_TITLE);
                        itemT1.setTitle("观看历史");
                        mDatas.add(itemT1);

                        // 3 => 观看历史 and more
                        try {
                            // 1
                            List<FavBean.ItemBean> list = response.getData().getRows();
                            int index = -1;
                            if (null != list && list.size() > 0) {
                                for (int i = 0; i <= 2; i++) {
                                    FavBean.ItemBean itemBean = list.get(i);
                                    if (null == itemBean)
                                        continue;
                                    index = i;
                                    itemBean.setIndex(index);
                                    itemBean.setItemType(TYPE_ITEM_IMG);
                                    mDatas.add(itemBean);
                                }
                            }
                            // 2
                            FavBean.ItemBean more = new FavBean.ItemBean();
                            more.setItemType(TYPE_ITEM_MORE);
                            more.setTitle("全部历史");
                            more.setJumpType(0);
                            more.setIndex(index == -1 ? 0 : index + 1);
                            more.setIcon(R.drawable.ic_selector_common_rec);
                            mDatas.add(more);
                        } catch (Exception e) {
                        }

                        // 4 => title => 我的收藏
                        FavBean.ItemBean itemT3 = new FavBean.ItemBean();
                        itemT3.setItemType(TYPE_ITEM_TITLE);
                        itemT3.setTitle("我的收藏");
                        mDatas.add(itemT3);

                        // 5 => 我的收藏
                        try {
                            // 1
                            String extra = response.getExtra();
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<FavBean.ItemBean>>() {
                            }.getType();
                            List<FavBean.ItemBean> list = gson.fromJson(extra, type);
                            int index = -1;
                            if (null != list && list.size() > 0) {
                                for (int i = 0; i <= 2; i++) {
                                    FavBean.ItemBean itemBean = list.get(i);
                                    if (null == itemBean)
                                        continue;
                                    index = i;
                                    itemBean.setIndex(index);
                                    itemBean.setItemType(TYPE_ITEM_IMG);
                                    mDatas.add(itemBean);
                                }
                            }
                            // 2
                            FavBean.ItemBean more = new FavBean.ItemBean();
                            more.setIndex(index == -1 ? 0 : index + 1);
                            more.setItemType(TYPE_ITEM_MORE);
                            more.setJumpType(1);
                            more.setTitle("全部收藏");
                            more.setIcon(R.drawable.ic_selector_common_fav);
                            mDatas.add(more);
                        } catch (Exception e) {
                        }

                        // 7 => title => 更多功能
                        FavBean.ItemBean itemT5 = new FavBean.ItemBean();
                        itemT5.setItemType(TYPE_ITEM_TITLE);
                        itemT5.setTitle("更多功能");
                        mDatas.add(itemT5);

                        // 8 => 更多功能
                        for (int i = 0; i < 2; i++) {
                            FavBean.ItemBean itemBean = new FavBean.ItemBean();
                            itemBean.setIndex(i);
                            itemBean.setItemType(TYPE_ITEM_WEB);
                            item.setJumpType(i);
                            if (i == 0) {
                                itemBean.setTitle("帮助中心");
                                itemBean.setIcon(R.drawable.ic_selector_common_help);
                            } else {
                                itemBean.setTitle("关于我们");
                                itemBean.setIcon(R.drawable.ic_selector_common_about);
                            }
                            mDatas.add(itemBean);
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
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().refreshContent();
                    }
                })
                .subscribe());
    }
}
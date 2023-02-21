package tv.huan.bilibili.ui.filter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.BasePresenter;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.tags.TagsLayout;
import lib.kalu.leanback.tags.model.TagBean;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.FilterBean;
import tv.huan.bilibili.bean.GetSecondTagAlbumsBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.SearchAlbumByTypeNews;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class FilterPresenter extends BasePresenter<FilterView> {

    private final List<GetSecondTagAlbumsBean.ItemBean> mData = new ArrayList<>();

    public FilterPresenter(@NonNull FilterView detailView) {
        super(detailView);
    }

    protected void setAdapter() {
        RecyclerView recyclerView = getView().findViewById(R.id.filter_content);
        GridLayoutManager layoutManager = new GridLayoutManager(getView().getContext(), 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_96);
                int v = offset / 10;
                outRect.set(0, 0, offset, offset);
                int position = parent.getChildAdapterPosition(view);

                int i = position % 5;
                if (i == 0) {
                    outRect.set(0, 0, v * 2, 0);
                } else if (i == 4) {
                    outRect.set(v * 2, 0, 0, 0);
                } else {
                    outRect.set(v, 0, v, 0);
                }

                if (i == 1) {
                    view.setTranslationX(-v / 2);
                } else if (i == 3) {
                    view.setTranslationX(v / 2);
                } else {
                    view.setTranslationX(0);
                }
            }
        });
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                View inflate = LayoutInflater.from(context).inflate(R.layout.activity_filter_item, parent, false);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(inflate) {
                };
                try {
                    inflate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = holder.getAbsoluteAdapterPosition();
                            if (position >= 0) {
                                GetSecondTagAlbumsBean.ItemBean itemBean = mData.get(position);
                                JumpUtil.next(v.getContext(), itemBean);
                            }
                        }
                    });
                } catch (Exception e) {
                }
                try {
                    inflate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            TextView textView = view.findViewById(R.id.filter_item_name);
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
                    GetSecondTagAlbumsBean.ItemBean itemBean = mData.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.filter_item_name);
                    textView.setText(itemBean.getName());
                    ImageView imageView = holder.itemView.findViewById(R.id.filter_item_img);
                    GlideUtils.loadVt(imageView.getContext(), itemBean.getNewPicVt(), imageView);
                } catch (Exception e) {
                }
            }

            @Override
            public int getItemCount() {
                return mData.size();
            }
        });
    }

    protected void requestInit() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                // 分类
                .flatMap(new Function<Boolean, Observable<BaseBean<GetSubChannelsByChannelBean>>>() {
                    @Override
                    public Observable<BaseBean<GetSubChannelsByChannelBean>> apply(Boolean aBoolean) {
                        int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getClassByPrentId(classId, null);
                    }
                })
                // 分类 - 处理数据
                .map(new Function<BaseBean<GetSubChannelsByChannelBean>, JSONObject>() {
                    @Override
                    public JSONObject apply(BaseBean<GetSubChannelsByChannelBean> response) {

                        JSONObject data = new JSONObject();
                        // classBean
                        try {
                            ArrayList<ClassBean> classApis = new ArrayList<>();
                            List<GetSubChannelsByChannelBean.ClassesBean> classes = response.getData().getClasses();
                            // 筛选
                            ClassBean shaixuanApi = new ClassBean();
                            shaixuanApi.setDrawableChecked(R.drawable.ic_filter_active);
                            shaixuanApi.setDrawableHighlight(R.drawable.ic_filter_focus);
                            shaixuanApi.setDrawableNormal(R.drawable.ic_filter);
                            shaixuanApi.setText("筛选");
                            int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                            shaixuanApi.setCode(String.valueOf(classId));
                            String secondTag = getView().getStringExtra(FilterActivity.INTENT_SECOND_TAG);
                            if (null == secondTag || secondTag.length() <= 0) {
                                secondTag = "筛选";
                            }
                            shaixuanApi.setChecked("筛选".equals(secondTag));
                            classApis.add(shaixuanApi);
                            // item
                            for (int i = 0; i < classes.size(); i++) {
                                GetSubChannelsByChannelBean.ClassesBean bean = classes.get(i);
                                ClassBean classApi = new ClassBean();
                                classApi.setText(bean.getName());
                                classApi.setCode(String.valueOf(bean.getId()));
                                classApi.setChecked(secondTag.equals(bean.getName()));
                                classApis.add(classApi);
                            }
                            String s = new Gson().toJson(classApis);
                            data.put("filterClass", s);
                        } catch (Exception e) {
                        }
                        try {
                            List<GetSubChannelsByChannelBean.ClassesBean> list = response.getData().getClasses();
                            int id = 0;
                            int size = list.size();
                            String secondTag = getView().getStringExtra(FilterActivity.INTENT_SECOND_TAG);
                            for (int i = 0; i < size; i++) {
                                GetSubChannelsByChannelBean.ClassesBean bean = list.get(i);
                                if (null == bean) continue;
                                String name = bean.getName();
                                if (secondTag.equals(name)) {
                                    id = bean.getId();
                                    break;
                                }
                            }
                            if (id == 0) {
                                GetSubChannelsByChannelBean.ClassesBean bean = list.get(0);
                                id = bean.getId();
                            }
                            data.put("secondId", id);
                        } catch (Exception e) {
                        }
                        try {
                            data.put("filterId", response.getData().getParent().getId());
                        } catch (Exception e) {
                        }
                        try {
                            data.put("filterTitle", response.getData().getParent().getName());
                        } catch (Exception e) {
                        }
                        return data;
                    }
                })
                // 热搜标签
                .flatMap(new Function<JSONObject, ObservableSource<BaseBean<LinkedHashMap<String, List<String>>>>>() {
                    @Override
                    public ObservableSource<BaseBean<LinkedHashMap<String, List<String>>>> apply(JSONObject data) {
                        String s = data.toString();
                        int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getFilterTypes(classId, s);
                    }
                })
                // 热搜标签 - 处理
                .map(new Function<BaseBean<LinkedHashMap<String, List<String>>>, JSONObject>() {
                    @Override
                    public JSONObject apply(BaseBean<LinkedHashMap<String, List<String>>> response) {

                        JSONObject data;
                        try {
                            data = new JSONObject(response.getExtra());
                        } catch (Exception e) {
                            data = new JSONObject();
                        }

                        LinkedHashMap<String, List<TagBean>> tagsApi = new LinkedHashMap<>();
                        try {
                            LinkedHashMap<String, List<String>> tags = response.getData();
                            for (String key : tags.keySet()) {
                                if (null == key || key.length() == 0) continue;
                                List<String> values = tags.get(key);
                                if (null == values || values.size() <= 0) continue;

                                // 全部
                                LinkedList<TagBean> list = new LinkedList<>();
                                TagBean firstBean = new TagBean();
                                firstBean.setText("全部");
                                firstBean.setId(0);
                                firstBean.setTextColorFocus(Color.parseColor("#1c967f"));
                                firstBean.setTextColorSelect(Color.parseColor("#1dffdc"));
                                firstBean.setTextColorDetault(Color.parseColor("#ffffff"));
                                firstBean.setBackgroundResourceDefault(R.drawable.bg_filter_tag_unfocus);
                                firstBean.setBackgroundResourceFocus(R.drawable.bg_filter_tag_focus);
                                firstBean.setBackgroundResourceSelect(R.drawable.bg_filter_tag_unfocus);
                                list.addFirst(firstBean);
                                for (int n = 0; n < values.size(); n++) {

                                    String value = values.get(n);
                                    if (null == value || value.length() <= 0) continue;

                                    TagBean tagsModel = new TagBean();
                                    tagsModel.setText(value);
                                    tagsModel.setId(0);
                                    tagsModel.setTextColorFocus(Color.parseColor("#1c967f"));
                                    tagsModel.setTextColorSelect(Color.parseColor("#1dffdc"));
                                    tagsModel.setTextColorDetault(Color.parseColor("#ffffff"));
                                    tagsModel.setBackgroundResourceDefault(R.drawable.bg_filter_tag_unfocus);
                                    tagsModel.setBackgroundResourceFocus(R.drawable.bg_filter_tag_focus);
                                    tagsModel.setBackgroundResourceSelect(R.drawable.bg_filter_tag_unfocus);
                                    list.addLast(tagsModel);
                                }
                                tagsApi.put(key, list);
                            }
                            //
                            data.put("filterTags", new Gson().toJson(tagsApi));
                        } catch (Exception e) {
                        }
                        return data;
                    }
                })
                // 列表数据
                .flatMap(new Function<JSONObject, ObservableSource<BaseBean<GetSecondTagAlbumsBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<GetSecondTagAlbumsBean>> apply(JSONObject data) {
                        int secondId;
                        try {
                            secondId = data.optInt("secondId", 0);
                        } catch (Exception e) {
                            secondId = 0;
                        }
                        String s = data.toString();
                        return HttpClient.getHttpClient().getHttpApi().getSecondTagAlbums(secondId, 0, Integer.MAX_VALUE, s);
                    }
                })
                // 列表数据 - 处理
                .map(new Function<BaseBean<GetSecondTagAlbumsBean>, JSONObject>() {
                    @Override
                    public JSONObject apply(BaseBean<GetSecondTagAlbumsBean> response) {
                        try {
                            mData.clear();
                            mData.addAll(response.getData().getAlbums());
                        } catch (Exception e) {
                        }
                        JSONObject object;
                        try {
                            object = new JSONObject(response.getExtra());
                        } catch (Exception e) {
                            object = new JSONObject();
                        }
                        return object;
                    }
                }).map(new Function<JSONObject, FilterBean>() {
                    @Override
                    public FilterBean apply(JSONObject data) {
                        FilterBean filterBean = new FilterBean();
                        try {
                            int id = data.optInt("filterId", 0);
                            filterBean.setFilterId(id);
                        } catch (Exception e) {
                        }
                        try {
                            String title = data.optString("filterTitle", null);
                            filterBean.setFilterTitle(title);
                        } catch (Exception e) {
                        }
                        try {
                            String s = data.optString("filterClass");
                            Type type = new TypeToken<ArrayList<ClassBean>>() {
                            }.getType();
                            List<ClassBean> classApi = new Gson().fromJson(s, type);
                            filterBean.setFilterClass(classApi);
                        } catch (Exception e) {
                        }
                        try {
                            String s = data.optString("filterTags");
                            Type type = new TypeToken<LinkedHashMap<String, List<TagBean>>>() {
                            }.getType();
                            LinkedHashMap<String, List<TagBean>> map = new Gson().fromJson(s, type);
                            filterBean.setFilterTags(map);
                        } catch (Exception e) {
                        }
                        return filterBean;
                    }
                }).delay(40, TimeUnit.MILLISECONDS).compose(ComposeSchedulers.io_main()).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<FilterBean>() {
                    @Override
                    public void accept(FilterBean data) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("FilterPresenter => " + new Gson().toJson(data));
                        }
                        getView().hideLoading();
                        getView().refreshTags(data.getFilterTags());
                        getView().refreshClass(data.getFilterClass(), data.getFilterTitle());
                        getView().refreshContent(true);
                    }
                }).subscribe());
    }

    protected void searchAlbumByTypeNews() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        emitter.onNext(true);
                    }
                }).flatMap(new Function<Boolean, ObservableSource<BaseBean<SearchAlbumByTypeNews>>>() {
                    @Override
                    public ObservableSource<BaseBean<SearchAlbumByTypeNews>> apply(Boolean aBoolean) {

                        TagsLayout tagsLinearLayout = getView().findViewById(R.id.filter_tags);
                        Map<String, String> hashMap = tagsLinearLayout.getData();
                        for (String key : hashMap.keySet()) {
                            String s = hashMap.get(key);
                            if ("全部".equals(s)) {
                                hashMap.put(key, "");
                            }
                        }

                        int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                        JSONObject object = new JSONObject();
                        try {
                            object.putOpt("prodId", BoxUtil.getProdId());
                            object.putOpt("offset", 0);
                            object.putOpt("size", Integer.MAX_VALUE);
                            object.putOpt("classId", classId);
                        } catch (Exception e) {
                        }
                        try {
                            for (String key : hashMap.keySet()) {
                                if (null == key || key.length() == 0) continue;
                                object.putOpt(key, hashMap.get(key));
                            }
                        } catch (Exception e) {
                        }
                        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                        RequestBody requestBody = RequestBody.create(mediaType, object.toString());
                        return HttpClient.getHttpClient().getHttpApi().searchAlbumByTypeNews(requestBody);
                    }
                }).map(new Function<BaseBean<SearchAlbumByTypeNews>, Boolean>() {
                    @Override
                    public Boolean apply(BaseBean<SearchAlbumByTypeNews> searchAlbumByTypeNewsBaseBean) {
                        try {
                            mData.clear();
                            mData.addAll(searchAlbumByTypeNewsBaseBean.getData().getAlbums());
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
                        // loading
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        try {
                            mData.clear();
                        } catch (Exception e) {
                        }
                        // loading
                        getView().hideLoading();
                        getView().refreshContent(false);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() {
                        // loading
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        // loading
                        getView().hideLoading();
                        getView().refreshContent(false);
                    }
                }).subscribe());
    }

    protected void requestContent(@NonNull String classId) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                }).flatMap(new Function<Boolean, Observable<BaseBean<GetSecondTagAlbumsBean>>>() {
                    @Override
                    public Observable<BaseBean<GetSecondTagAlbumsBean>> apply(Boolean aBoolean) {
                        int id = Integer.parseInt(classId);
                        return HttpClient.getHttpClient().getHttpApi().getSecondTagAlbums(id, 0, Integer.MAX_VALUE, null);
                    }
                }).map(new Function<BaseBean<GetSecondTagAlbumsBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseBean<GetSecondTagAlbumsBean> response) {

                        try {
                            mData.clear();
                            mData.addAll(response.getData().getAlbums());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        // loading
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        try {
                            mData.clear();
                        } catch (Exception e) {
                        }
                        // loading
                        getView().hideLoading();
                        getView().refreshContent(false);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() {
                        // loading
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        // loading
                        getView().hideLoading();
                        getView().refreshContent(false);
                    }
                }).subscribe());
    }
}
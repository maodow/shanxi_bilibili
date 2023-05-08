package tv.huan.bilibili.ui.filter;

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
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import lib.kalu.frame.mvp.util.OffsetUtil;
import lib.kalu.leanback.clazz.ClassBean;
import lib.kalu.leanback.clazz.ClassScrollView;
import lib.kalu.leanback.list.RecyclerViewGrid;
import lib.kalu.leanback.list.layoutmanager.AutoMeasureNoGridLayoutManager;
import lib.kalu.leanback.tags.TagsLayout;
import lib.kalu.leanback.tags.model.TagBean;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.GetSecondTagAlbumsBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.SearchAlbumByTypeNews;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallFilterBean;
import tv.huan.bilibili.bean.format.CallPageBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class FilterPresenter extends BasePresenterImpl<FilterView> {

    private final List<GetSecondTagAlbumsBean.ItemBean> mData = new ArrayList<>();

    public FilterPresenter(@NonNull FilterView detailView) {
        super(detailView);
    }

    protected void setAdapter() {
        RecyclerView recyclerView = getView().findViewById(R.id.filter_content);
        recyclerView.setLayoutManager(new AutoMeasureNoGridLayoutManager(getView().getContext(), 4));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
//                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_96);
//                int v = offset / 10;
//                outRect.set(0, 0, offset, offset);
//                int position = parent.getChildAdapterPosition(view);
//
//                int i = position % 5;
//                if (i == 0) {
//                    outRect.set(0, 0, v * 2, 0);
//                } else if (i == 4) {
//                    outRect.set(v * 2, 0, 0, 0);
//                } else {
//                    outRect.set(v, 0, v, 0);
//                }
//
//                if (i == 1) {
//                    view.setTranslationX(-v / 2);
//                } else if (i == 3) {
//                    view.setTranslationX(v / 2);
//                } else {
//                    view.setTranslationX(0);
//                }

                int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_72);
                int v = offset / 8;
                outRect.set(0, 0, offset, offset);
                int position = parent.getChildAdapterPosition(view);

                int i = position % 4;
                if (i == 0) {
                    outRect.set(0, 0, v * 2, 0);
                } else if (i == 3) {
                    outRect.set(v * 2, 0, 0, 0);
                } else {
                    outRect.set(v, 0, v, 0);
                }

                if (i == 1) {
                    view.setTranslationX(-v / 2);
                } else if (i == 2) {
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
                    GetSecondTagAlbumsBean.ItemBean itemBean = mData.get(position);
                    TextView textView = holder.itemView.findViewById(R.id.common_poster_name);
                    textView.setText(itemBean.getName());
                } catch (Exception e) {
                }
                try {
                    GetSecondTagAlbumsBean.ItemBean itemBean = mData.get(position);
                    ImageView imageView = holder.itemView.findViewById(R.id.common_poster_img);
                    GlideUtils.loadHz(imageView.getContext(), itemBean.getPicture(true), imageView);
                } catch (Exception e) {
                }
                try {
                    GetSecondTagAlbumsBean.ItemBean itemBean = mData.get(position);
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
                // 分类
                .flatMap(new Function<Boolean, Observable<BaseResponsedBean<GetSubChannelsByChannelBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<GetSubChannelsByChannelBean>> apply(Boolean aBoolean) {
                        int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getClassByPrentId(classId, null);
                    }
                })
                // 分类 - 处理数据
                .map(new Function<BaseResponsedBean<GetSubChannelsByChannelBean>, JSONObject>() {
                    @Override
                    public JSONObject apply(BaseResponsedBean<GetSubChannelsByChannelBean> response) {

                        JSONObject data = new JSONObject();
                        // classBean
                        try {
                            ArrayList<ClassBean> classApis = new ArrayList<>();
                            List<GetSubChannelsByChannelBean.ClassesBean> classes = response.getData().getClasses();
                            // 筛选
                            String name = getView().getString(R.string.filter_name);
                            ClassBean shaixuanApi = new ClassBean();
                            shaixuanApi.setLeftDrawableFocus(R.drawable.ic_filter_focus);
                            shaixuanApi.setLeftDrawableChecked(R.drawable.ic_filter_focus);
                            shaixuanApi.setLeftDrawable(R.drawable.ic_filter);
                            shaixuanApi.setText(name);
                            int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                            shaixuanApi.setCode(String.valueOf(classId));
                            String secondTag = getView().getStringExtra(FilterActivity.INTENT_SECOND_TAG);
                            if (null == secondTag || secondTag.length() <= 0) {
                                secondTag = name;
                            }
                            shaixuanApi.setChecked(name.equals(secondTag));
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
                .flatMap(new Function<JSONObject, ObservableSource<BaseResponsedBean<LinkedHashMap<String, List<String>>>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<LinkedHashMap<String, List<String>>>> apply(JSONObject data) {
                        String s = data.toString();
                        int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                        return HttpClient.getHttpClient().getHttpApi().getFilterTypes(classId, s);
                    }
                })
                // 热搜标签 - 处理
                .map(new Function<BaseResponsedBean<LinkedHashMap<String, List<String>>>, JSONObject>() {
                    @Override
                    public JSONObject apply(BaseResponsedBean<LinkedHashMap<String, List<String>>> response) {

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
                                firstBean.setText(getView().getString(R.string.filter_all));
                                list.addFirst(firstBean);
                                for (int n = 0; n < values.size(); n++) {

                                    String value = values.get(n);
                                    if (null == value || value.length() <= 0) continue;

                                    TagBean tagsModel = new TagBean();
                                    tagsModel.setText(value);
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
                .flatMap(new Function<JSONObject, ObservableSource<BaseResponsedBean<GetSecondTagAlbumsBean>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<GetSecondTagAlbumsBean>> apply(JSONObject data) {
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
                .map(new Function<BaseResponsedBean<GetSecondTagAlbumsBean>, JSONObject>() {
                    @Override
                    public JSONObject apply(BaseResponsedBean<GetSecondTagAlbumsBean> response) {
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
                }).map(new Function<JSONObject, CallFilterBean>() {
                    @Override
                    public CallFilterBean apply(JSONObject data) {
                        CallFilterBean filterBean = new CallFilterBean();
//                        try {
//                            int id = data.optInt("filterId", 0);
//                            filterBeang.setFilterId(id);
//                        } catch (Exception e) {
//                        }
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

                            int size = classApi.size();
                            for (int i = 0; i < size; i++) {
                                ClassBean t = classApi.get(i);
                                if (null == t)
                                    continue;
                                boolean checked = t.isChecked();
                                if (checked) {
                                    filterBean.setFilterCheckPosition(i);
                                    break;
                                }
                            }
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
                }).delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().setVisibility(R.id.filter_nodata, View.GONE);
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<CallFilterBean>() {
                    @Override
                    public void accept(CallFilterBean data) {
                        if (BuildConfig.HUAN_LOG) {
                            LogUtil.log("FilterPresenter => " + new Gson().toJson(data));
                        }
                        getView().hideLoading();
                        getView().refreshTags(data.getFilterTags());
                        getView().refreshClass(data.getFilterClass(), data.getFilterTitle(), data.getFilterCheckPosition());
                        int size = mData.size();
                        if (size > 0) {
                            getView().notifyItemRangeInserted(R.id.filter_content, 0, size);
                            getView().requestFocus(R.id.filter_content);
                        } else {
                            getView().cleanFocusClass();
                        }
                        getView().setVisibility(R.id.filter_nodata, size > 0 ? View.GONE : View.VISIBLE);
                        getView().checkTags();
                    }
                }).subscribe());
    }

    protected void searchAlbumByTypeNews(boolean switchTab) {

        addDisposable(Observable.create(new ObservableOnSubscribe<JSONObject>() {
                    @Override
                    public void subscribe(ObservableEmitter<JSONObject> emitter) throws Exception {
                        try {
                            // 1
                            JSONObject object = new JSONObject();
                            // 2
                            int offset = OffsetUtil.getOffset(mData);
                            object.putOpt("prodId", BoxUtil.getProdId());
                            object.putOpt("offset", offset);
                            object.putOpt("size", BuildConfig.HUAN_PAGE_NUM);
                            int classId = getView().getIntExtra(FilterActivity.INTENT_CLASSID, 0);
                            object.putOpt("classId", classId);
                            // 2
                            TagsLayout tagsLayout = getView().findViewById(R.id.filter_tags);
                            Map<String, String> map = tagsLayout.getData();
                            for (String key : map.keySet()) {
                                if (null == key || key.length() <= 0)
                                    continue;
                                String value = map.get(key);
                                if ("全部".equals(value))
                                    continue;
                                object.putOpt(key, value);
                            }
                            // 3
                            emitter.onNext(object);
                        } catch (Exception e) {
                            throw new Exception();
                        }
                    }
                })
                // 接口 => 筛选结果
                .flatMap(new Function<JSONObject, ObservableSource<BaseResponsedBean<SearchAlbumByTypeNews>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<SearchAlbumByTypeNews>> apply(JSONObject data) {
                        int offset = data.optInt("offset", 0);
                        CallPageBean pageBean = new CallPageBean();
                        pageBean.setStart(offset);
                        String s = new Gson().toJson(pageBean);
                        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                        RequestBody requestBody = RequestBody.create(mediaType, data.toString());
                        return HttpClient.getHttpClient().getHttpApi().searchAlbumByTypeNews(requestBody, s);
                    }
                })
                // 数据处理 => 筛选结果
                .map(new Function<BaseResponsedBean<SearchAlbumByTypeNews>, CallPageBean>() {
                    @Override
                    public CallPageBean apply(BaseResponsedBean<SearchAlbumByTypeNews> resp) {
                        CallPageBean pageBean;
                        try {
                            pageBean = new Gson().fromJson(resp.getExtra(), CallPageBean.class);
                        } catch (Exception e) {
                            pageBean = new CallPageBean();
                        }
                        try {
                            List<GetSecondTagAlbumsBean.ItemBean> list = resp.getData().getAlbums();
                            mData.addAll(list);
                            pageBean.setNum(list.size());
                            pageBean.setHasData(mData.size() > 0);
                        } catch (Exception e) {
                        }
                        return pageBean;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        if (switchTab) {
                            mData.clear();
                            getView().notifyDataSetChanged(R.id.filter_content);
                        }
                        if (switchTab) {
                            getView().checkTags();
                        }
                        getView().setVisibility(R.id.filter_nodata, View.GONE);
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<CallPageBean>() {
                    @Override
                    public void accept(CallPageBean data) {
                        getView().hideLoading();
                        getView().notifyItemRangeInserted(R.id.filter_content, data.getStart(), data.getNum());
                        getView().setVisibility(R.id.filter_nodata, data.isHasData() ? View.GONE : View.VISIBLE);
                        if (!switchTab && data.getNum() <= 0) {
                            getView().showToast(R.string.common_loadmore_empty);
                        }
                    }
                }).subscribe());
    }

    protected void requestContent(boolean switchTab) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        try {
                            ClassScrollView classLayout = getView().findViewById(R.id.filter_class);
                            CharSequence code = classLayout.getCheckedCode();
                            int parseInt = Integer.parseInt(code.toString());
                            emitter.onNext(parseInt);
                        } catch (Exception e) {
                            throw new Exception();
                        }
                    }
                })
                // 接口 => 查询数据库中某个分类的所有绑定专辑信息
                .flatMap(new Function<Integer, Observable<BaseResponsedBean<GetSecondTagAlbumsBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<GetSecondTagAlbumsBean>> apply(Integer id) {
                        int offset = OffsetUtil.getOffset(mData);
                        CallPageBean pageBean = new CallPageBean();
                        pageBean.setStart(offset);
                        String s = new Gson().toJson(pageBean);
                        return HttpClient.getHttpClient().getHttpApi().getSecondTagAlbums(id, offset, BuildConfig.HUAN_PAGE_NUM, s);
                    }
                })
                // 数据处理 => 查询数据库中某个分类的所有绑定专辑信息
                .map(new Function<BaseResponsedBean<GetSecondTagAlbumsBean>, CallPageBean>() {
                    @Override
                    public CallPageBean apply(BaseResponsedBean<GetSecondTagAlbumsBean> resp) {
                        CallPageBean pageBean;
                        try {
                            pageBean = new Gson().fromJson(resp.getExtra(), CallPageBean.class);
                        } catch (Exception e) {
                            pageBean = new CallPageBean();
                        }
                        try {
                            List<GetSecondTagAlbumsBean.ItemBean> list = resp.getData().getAlbums();
                            pageBean.setNum(list.size());
                            mData.addAll(list);
                            pageBean.setHasData(mData.size() > 0);
                        } catch (Exception e) {
                        }
                        return pageBean;
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        if (switchTab) {
                            mData.clear();
                            getView().notifyDataSetChanged(R.id.filter_content);
                        }
                        if (switchTab) {
                            getView().checkTags();
                        }
                        getView().setVisibility(R.id.filter_nodata, View.GONE);
                        getView().showLoading();
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<CallPageBean>() {
                    @Override
                    public void accept(CallPageBean data) {
                        getView().hideLoading();
                        getView().notifyItemRangeInserted(R.id.filter_content, data.getStart(), data.getNum());
                        getView().setVisibility(R.id.filter_nodata, data.isHasData() ? View.GONE : View.VISIBLE);
                        if (!switchTab && data.getNum() <= 0) {
                            getView().showToast(R.string.common_loadmore_empty);
                        }
                    }
                }).subscribe());
    }

    protected boolean dispatchEvent(KeyEvent event) {
        // up
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("FilterPresenter => dispatchEvent => up");
            if (focusId == R.id.filter_item) {
                ClassScrollView classLayout = (ClassScrollView) getView().findViewById(R.id.filter_class);
                int index = classLayout.getCheckedIndex();
                if (index == 0) {
                    View focus = getView().getCurrentFocus();
                    RecyclerView recyclerView = getView().findViewById(R.id.filter_content);
                    int adapterPosition = recyclerView.getChildAdapterPosition(focus);
                    getView().setVisibility(R.id.filter_tags, adapterPosition <= 9 ? View.VISIBLE : View.GONE);
                } else {
                    getView().setVisibility(R.id.filter_tags, View.GONE);
                }
            }
        }
        // down
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.filter_tags) {
                TagsLayout tagsLayout = getView().findViewById(R.id.filter_tags);
                int rowCount = tagsLayout.getRowCount();
                int checkedIndexRow = tagsLayout.getCheckedIndexRow();
                if (checkedIndexRow + 1 >= rowCount) {
                    RecyclerViewGrid recyclerView = getView().findViewById(R.id.filter_content);
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);
                    if (null != viewHolder) {
                        viewHolder.itemView.requestFocus();
                    }
                    return true;
                }
            } else if (focusId == R.id.filter_item) {
                RecyclerViewGrid recyclerView = getView().findViewById(R.id.filter_content);
                int itemCount = recyclerView.getAdapter().getItemCount();
                int v = itemCount % 4;
                int focusPosition = recyclerView.findFocusPosition();

                ClassScrollView classLayout = getView().findViewById(R.id.filter_class);
                int checkedIndex = classLayout.getCheckedIndex();
                if (checkedIndex == 0) {
                    getView().setVisibility(R.id.filter_tags, focusPosition >= 4 ? View.GONE : View.VISIBLE);
                } else {
                    getView().setVisibility(R.id.filter_tags, View.GONE);
                }

                if (v == 0) {
                    if (itemCount - focusPosition <= 4) {
                        if (checkedIndex == 0) {
                            searchAlbumByTypeNews(false);
                        } else {
                            requestContent(false);
                        }
                    }
                } else {
                    if (itemCount - focusPosition <= v) {
                        if (checkedIndex == 0) {
                            searchAlbumByTypeNews(false);
                        } else {
                            requestContent(false);
                        }
                    }
                }
            }
        }
        // right
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.filter_class) {
                ClassScrollView classLayout = getView().findViewById(R.id.filter_class);
                int index = classLayout.getCheckedIndex();
                if (index <= 0) {
                    boolean visibility = getView().isVisibility(R.id.filter_tags);
                    if (visibility) {
                        getView().requestFocus(R.id.filter_tags);
                    } else {
                        getView().requestFocus(R.id.filter_content);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

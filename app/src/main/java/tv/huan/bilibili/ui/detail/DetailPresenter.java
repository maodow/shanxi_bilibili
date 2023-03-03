package tv.huan.bilibili.ui.detail;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
import lib.kalu.frame.mvp.util.CacheUtil;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.bean.FavorBean;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.DetailGridView;
import tv.huan.bilibili.widget.player.PlayerView;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.Album;
import tv.huan.bilibili.bean.Auth2Bean;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.LookBean;
import tv.huan.bilibili.bean.Media;
import tv.huan.bilibili.bean.ProgramDetail;
import tv.huan.bilibili.bean.ProgramInfoDetail;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.detail.template.DetailTemplateFavor;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanJi;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanQi;
import tv.huan.bilibili.utils.Constants;

public class DetailPresenter extends BasePresenterImpl<DetailView> {
    public DetailPresenter(@NonNull DetailView detailView) {
        super(detailView);
    }

    protected boolean checkPlayer() {
        try {
            PlayerView playerView = getView().findViewById(R.id.detail_player_item_video);
            LogUtil.log("DetailPresenter => checkPlayer => playerView = " + playerView);
            if (null == playerView) {
                VerticalGridView gridView = getView().findViewById(R.id.detail_list);
                LogUtil.log("DetailPresenter => checkPlayer => gridView = " + gridView);
                RecyclerView.Adapter adapter = gridView.getAdapter();
                if (null == adapter) {
                    return true;
                } else {
                    int itemCount = adapter.getItemCount();
                    if (itemCount <= 0) {
                        return true;
                    } else {
                        gridView.smoothScrollToPosition(0);
                        return false;
                    }
                }
            } else {
                boolean full = playerView.isFull();
                boolean floats = playerView.isFloat();
                LogUtil.log("DetailPresenter => checkPlayer => full = " + full);
                LogUtil.log("DetailPresenter => checkPlayer => floats = " + floats);
                if (full) {
                    playerView.stopFull();
                    return false;
                } else if (floats) {
                    playerView.stopFloat();
                    return false;
                } else {
                    playerView.release();
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtil.log("DetailPresenter => checkPlayer => " + e.getMessage());
            return false;
        }
    }

    protected void refreshLook() {

        String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
        if (null == cid || cid.length() <= 0) return;

        Context context = getView().getContext();
        String s = CacheUtil.getCache(context, "user_look");
        if (null == s || s.length() <= 0) {
            s = "[]";
        }
        Type type = new TypeToken<List<LookBean>>() {
        }.getType();
        List<LookBean> recs = new Gson().fromJson(s, type);
        int position = -1;
        int size = recs.size();
        for (int i = 0; i < size; i++) {
            LookBean temp = recs.get(i);
            if (null == temp) continue;
            String str = temp.getCid();
            if (cid.equals(str)) {
                position = i;
                break;
            }
        }
        LinkedList<LookBean> list = new LinkedList<>();
        list.addAll(recs);
        // exist
        if (position != -1) {
            LookBean bean = list.remove(position);
            list.addFirst(bean);
        } else {
            LookBean bean = new LookBean();
            bean.setCid(cid);
            list.addFirst(bean);
        }
        String s1 = new Gson().toJson(list);
        CacheUtil.setCache(context, "user_look", s1);
    }

    protected final void setAdapter() {
        VerticalGridView gridView = getView().findViewById(R.id.detail_list);
        DetailSelectorPresenter selectorPresenter = new DetailSelectorPresenter();
        ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(selectorPresenter);
        ItemBridgeAdapter bridgeAdapter = new ItemBridgeAdapter(objectAdapter);
        gridView.setAdapter(bridgeAdapter);
    }

    protected void request() {
        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        emitter.onNext(true);
                    }
                })
                // 媒资数据
                .flatMap(new Function<Boolean, ObservableSource<BaseBean<ProgramInfoDetail>>>() {
                    @Override
                    public ObservableSource<BaseBean<ProgramInfoDetail>> apply(Boolean o) {
                        String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                        if (null == cid) {
                            cid = "";
                        }
                        return HttpClient.getHttpClient().getHttpApi().getMediasByCid2(cid);
                    }
                })
                // 媒资鉴权
                .flatMap(new Function<BaseBean<ProgramInfoDetail>, ObservableSource<Auth2Bean>>() {
                    @Override
                    public ObservableSource<Auth2Bean> apply(BaseBean<ProgramInfoDetail> programInfoDetailBaseBean) {

                        // 上报 => 详情页加载完成
                        try {
                            String cid = getView().getStringExtra(DetailActivity.INTENT_CID, null);
                            if (null != cid && cid.length() > 0) {
                                reportDetailLoadFinished(cid);
                            }
                        } catch (Exception e) {
                        }
                        // 上报 => 来自搜索
                        try {
                            boolean booleanExtra = getView().getBooleanExtra(DetailActivity.INTENT_FROM_SEARCH, false);
                            if (booleanExtra) {
                                String keys = getView().getStringExtra(DetailActivity.INTENT_FROM_SEARCH_KEY, null);
                                if (null != keys && keys.length() > 0) {
                                    String cid = getView().getStringExtra(DetailActivity.INTENT_CID, null);
                                    if (null != cid && cid.length() > 0) {
                                        reportSearchResultItemClicked(keys, cid);
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }


                        ProgramInfoDetail data = programInfoDetailBaseBean.getData();
                        boolean auth;
                        try {
                            ProgramDetail album = data.getAlbum();
                            // 付费
                            if (0 != album.getProductType()) {
                                auth = true;
                            } else {
                                auth = false;
                            }
                        } catch (Exception e) {
                            auth = false;
                        }

                        if (auth) { //专区请求周期返回专区购买的列表
                            String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                            if (null == cid) {
                                cid = "";
                            }
                            String s = new Gson().toJson(data);
                            LogUtil.log("DetailPresenter => s = " + s);
                            return HttpClient.getHttpClient().getHttpApi().auth2(cid, s);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<Auth2Bean>() {
                                @Override
                                public void subscribe(ObservableEmitter<Auth2Bean> emitter) {
                                    Auth2Bean bean = new Auth2Bean();
                                    bean.setFree(true);
                                    String s = new Gson().toJson(data);
                                    bean.setExtra(s);
                                    emitter.onNext(bean);
                                }
                            });
                        }
                    }
                })
                // 数据处理
                .map(new Function<Auth2Bean, ProgramInfoDetail>() {
                    @Override
                    public ProgramInfoDetail apply(Auth2Bean auth2Bean) {

                        String s;
                        try {
                            String extra = auth2Bean.getExtra();
                            if (null != extra && extra.length() > 0) {
                                s = extra;
                            } else {
                                s = "{}";
                            }
                        } catch (Exception e) {
                            s = "{}";
                        }
                        ProgramInfoDetail data = new Gson().fromJson(s, ProgramInfoDetail.class);
                        String whiteListVip = auth2Bean.getWhiteListVip();

                        // 免费 || 白名单
                        if (auth2Bean.isFree() || "1".equals(whiteListVip)) {
                            data.setVipFree(true);
                        }
                        // 付费
                        else {

                            ProgramDetail album = data.getAlbum();
                            // 关联产品编码计费包
                            List<ProgramDetail.Item> productCodes = album.getProductCodes();
                            int length;
                            try {
                                length = productCodes.size();
                            } catch (Exception e) {
                                length = 0;
                            }

                            // 单点会员
                            try {
                                for (int i = 0; i < length; i++) {
                                    ProgramDetail.Item item = productCodes.get(i);
                                    if (null == item) continue;
                                    int productType = item.getProductType();
                                    if (1 != productType) continue;
                                    String code = item.getCode();
                                    if (null == code || code.length() <= 0) continue;
                                    data.setVipLinkDanDian(true);
                                    String singleAuth = auth2Bean.getSingleAuth();
                                    if ("1".equals(singleAuth)) {
                                        data.setVipPassDanDian(true);
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                            }

                            // 周期会员
                            try {
                                for (int i = 0; i < length; i++) {
                                    ProgramDetail.Item item = productCodes.get(i);
                                    if (null == item) continue;
                                    int productType = item.getProductType();
                                    if (3 != productType) continue;
                                    String code = item.getCode();
                                    if (null == code || code.length() <= 0) continue;
                                    data.setVipLinkZhouQi(true);
                                    String vip = auth2Bean.getVip();
                                    if ("1".equals(vip)) {
                                        data.setVipPassZhouQi(true);
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                            }

                            // 专区会员
                            try {
                                for (int i = 0; i < length; i++) {
                                    ProgramDetail.Item item = productCodes.get(i);
                                    if (null == item) continue;
                                    int productType = item.getProductType();
                                    if (4 != productType) continue;
                                    String code = item.getCode();
                                    if (null == code || code.length() <= 0) continue;
                                    data.setVipLinkZhuanQu(true);
                                    List<Auth2Bean.VipBean> specialList = auth2Bean.getVipSpecialList();
                                    int size;
                                    try {
                                        size = specialList.size();
                                    } catch (Exception e) {
                                        size = 0;
                                    }
                                    for (int j = 0; j < size; j++) {
                                        Auth2Bean.VipBean vipBean = specialList.get(j);
                                        String v = vipBean.getCode();
                                        if (null == v || v.length() <= 0) continue;
                                        if (v.equals(code)) {
                                            data.setVipPassZhuanQu(true);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        return data;
                    }
                })
                // 收藏状态
                .flatMap(new Function<ProgramInfoDetail, ObservableSource<BaseBean<FavorBean>>>() {
                    @Override
                    public ObservableSource<BaseBean<FavorBean>> apply(ProgramInfoDetail programInfoDetail) {
                        String s = new Gson().toJson(programInfoDetail);
                        String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                        return HttpClient.getHttpClient().getHttpApi().checkFavorite(cid, s);
                    }
                })
                // 数据处理
                .map(new Function<BaseBean<FavorBean>, ProgramInfoDetail>() {
                    @Override
                    public ProgramInfoDetail apply(BaseBean<FavorBean> favorBeanBaseBean) {
                        ProgramInfoDetail programInfoDetail;
                        try {
                            programInfoDetail = new Gson().fromJson(favorBeanBaseBean.getExtra(), ProgramInfoDetail.class);
                        } catch (Exception e) {
                            programInfoDetail = new ProgramInfoDetail();
                        }
                        try {
                            programInfoDetail.setFavor(favorBeanBaseBean.getData().isFavor());
                        } catch (Exception e) {
                        }
                        return programInfoDetail;
                    }
                })
                // 播放器
                .map(new Function<ProgramInfoDetail, ProgramInfoDetail>() {
                    @Override
                    public ProgramInfoDetail apply(ProgramInfoDetail data) {
                        try {

                            VerticalGridView verticalGridView = getView().findViewById(R.id.detail_list);
                            RecyclerView.Adapter adapter = verticalGridView.getAdapter();
                            ObjectAdapter objectAdapter = ((ItemBridgeAdapter) adapter).getAdapter();

                            DetailTemplatePlayer.DetailTemplatePlayerObject object = new DetailTemplatePlayer.DetailTemplatePlayerObject();

                            // aaaMediaAuth
                            boolean aaaPass = false;
                            if (BuildConfig.HUAN_AUTH) {
                                try {
                                    boolean vipFree = data.isVipFree();
                                    if (vipFree) {
                                        aaaPass = true;
                                    } else {
                                        // 关联产品编码计费包
                                        ProgramDetail album = data.getAlbum();
                                        List<ProgramDetail.Item> productCodes = album.getProductCodes();
                                        int num = productCodes.size();
                                        for (int i = 0; i < num; i++) {
                                            if (aaaPass) break;
                                            ProgramDetail.Item item = productCodes.get(i);
                                            if (null == item) continue;
                                            String code = item.getCode();
                                            if (null == code || code.length() <= 0) continue;
                                            try {
                                                aaaPass = false;
                                            } catch (Exception e) {
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    aaaPass = false;
                                }
                            } else {
                                aaaPass = true;
                            }
                            if (aaaPass) {
                                object.setVideoUrl("");
                            } else {
                                object.setVideoUrl(null);
                            }
                            object.setImageUrl(data.getAlbum().getPicture(true));
                            object.setTag(data.getAlbum().getSplitTag());
                            object.setTitle(data.getAlbum().getTitle());
                            object.setInfo(data.getAlbum().getInfo());
                            object.setPicList(data.getAlbum().getPicList());
                            object.setCid(data.getAlbum().getCid());
                            object.setRecClassId(data.getRecClassId());
                            object.setFavorStatus(data.isFavor());
                            ((ArrayObjectAdapter) objectAdapter).add(object);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return data;
                    }
                })
                // 选期列表
                .map(new Function<ProgramInfoDetail, ProgramInfoDetail>() {
                    @Override
                    public ProgramInfoDetail apply(ProgramInfoDetail data) {

                        boolean show;
                        try {
                            List<Media> medias = data.getMedias();
                            ProgramDetail detail = data.getAlbum();

                            // 容错1
                            if (detail.getType() != Constants.AlbumType.FILM && medias == null) {
                                show = false;
                            }
                            // 容错2
                            else if (detail.getType() != Constants.AlbumType.FILM && medias.size() == 0) {
                                show = false;
                            }
                            // 电影
                            else if (detail.getType() == Constants.AlbumType.FILM) {
                                show = false;
                            }
                            //  选期 => 教育、体育、综艺
                            else if (detail.getType() == Constants.AlbumType.EDUCATION || detail.getType() == Constants.AlbumType.SPORTS || detail.getType() == Constants.AlbumType.VARIETY) {
                                show = true;
                            }
                            // 选集
                            else {
                                show = false;
                            }
                        } catch (Exception e) {
                            show = false;
                        }

                        if (show) {
                            try {
                                VerticalGridView verticalGridView = getView().findViewById(R.id.detail_list);
                                RecyclerView.Adapter adapter = verticalGridView.getAdapter();
                                ObjectAdapter objectAdapter = ((ItemBridgeAdapter) adapter).getAdapter();
                                DetailTemplateXuanQi.DetailTemplateXuanQiList list = new DetailTemplateXuanQi.DetailTemplateXuanQiList();
                                List<Media> medias = data.getMedias();
                                int size = medias.size();
                                for (int i = 0; i < size; i++) {
                                    Media media = medias.get(i);
                                    if (null == media)
                                        continue;
                                    list.add(media);
                                }
                                ((ArrayObjectAdapter) objectAdapter).add(list);
                            } catch (Exception e) {
                            }
                        }

                        // 上报
                        try {
                            if (show) {
                                String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                                reportDetailSelectionsButtonShow(cid);
                            }
                        } catch (Exception e) {
                        }

                        return data;
                    }
                })
                // 选集列表
                .map(new Function<ProgramInfoDetail, ProgramInfoDetail>() {
                    @Override
                    public ProgramInfoDetail apply(ProgramInfoDetail data) {

                        boolean show = true;
                        try {
                            List<Media> medias = data.getMedias();
                            ProgramDetail detail = data.getAlbum();

                            // 容错1
                            if (detail.getType() != Constants.AlbumType.FILM && medias == null) {
                                show = false;
                            }
                            // 容错2
                            else if (detail.getType() != Constants.AlbumType.FILM && medias.size() == 0) {
                                show = false;
                            }
                            // 电影
                            else if (detail.getType() == Constants.AlbumType.FILM) {
                                show = false;
                            }
                            //  选期 => 教育、体育、综艺
                            else if (detail.getType() == Constants.AlbumType.EDUCATION || detail.getType() == Constants.AlbumType.SPORTS || detail.getType() == Constants.AlbumType.VARIETY) {
                                show = false;
                            }
                            // 选集
                            else {
                                show = true;
                            }
                        } catch (Exception e) {
                            show = false;
                        }

                        if (show) {
                            try {

                                DetailTemplateXuanJi.DetailTemplateXuanJiList list = new DetailTemplateXuanJi.DetailTemplateXuanJiList();
                                List<Media> medias = data.getMedias();
                                int size = medias.size();
                                for (int i = 0; i < size; i++) {
                                    Media t = medias.get(i);
                                    t.setName(String.valueOf(i + 1));
                                }
                                list.addAll(medias);

                                VerticalGridView verticalGridView = getView().findViewById(R.id.detail_list);
                                RecyclerView.Adapter adapter = verticalGridView.getAdapter();
                                ObjectAdapter objectAdapter = ((ItemBridgeAdapter) adapter).getAdapter();
                                ((ArrayObjectAdapter) objectAdapter).add(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // 上报
                        try {
                            if (show) {
                                String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                                reportDetailSelectionsButtonShow(cid);
                            }
                        } catch (Exception e) {
                        }

                        return data;
                    }
                })
                // 猜你喜欢
                .map(new Function<ProgramInfoDetail, ProgramInfoDetail>() {
                    @Override
                    public ProgramInfoDetail apply(ProgramInfoDetail data) {
                        VerticalGridView verticalGridView = getView().findViewById(R.id.detail_list);
                        RecyclerView.Adapter adapter = verticalGridView.getAdapter();
                        DetailTemplateFavor.DetailTemplateFavList list = new DetailTemplateFavor.DetailTemplateFavList();
                        try {
                            List<Album> recAlbums = data.getRecAlbums();
                            int size = recAlbums.size();
                            for (int i = 0; i < size; i++) {
                                Album album = recAlbums.get(i);
                                if (null == album) continue;
                                album.setHead("猜你喜欢");
                                list.add(album);
                            }
                        } catch (Exception e) {
                        }

                        if (list.size() > 0) {
                            ObjectAdapter objectAdapter = ((ItemBridgeAdapter) adapter).getAdapter();
                            ((ArrayObjectAdapter) objectAdapter).add(list);
                        }

                        // 上报
                        try {
                            if (list.size() > 0) {
                                String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                                reportDetailRecommendShow(cid);
                            }
                        } catch (Exception e) {
                        }

                        return data;
                    }
                })
                // 是否显示vip按钮
                .map(new Function<ProgramInfoDetail, Boolean>() {
                    @Override
                    public Boolean apply(ProgramInfoDetail programInfoDetail) {

//                        boolean show;
//                        try {
//
//                            boolean passZhouQi = programInfoDetail.isVipPassZhouQi();
//                            boolean linkZhouQi = programInfoDetail.isVipLinkZhouQi();
//
//                            boolean linkZhuanQu = programInfoDetail.isVipLinkZhuanQu();
//                            boolean passZhuanQu = programInfoDetail.isVipPassZhuanQu();
//
//                            boolean linkDanDian = programInfoDetail.isVipLinkDanDian();
//                            boolean passDanDian = programInfoDetail.isVipPassDanDian();
//
//                            // 周期会员
//                            if (linkZhouQi && passZhouQi) {
//                                show = false;
//                            }
//                            // 专区会员
//                            else if (linkZhuanQu && passZhuanQu) {
//                                show = false;
//                            }
//                            // 单点会员
//                            else if (linkDanDian && passDanDian) {
//                                show = false;
//                            }
//                            // 存在关联, 未购买
//                            else if (linkZhouQi || linkZhuanQu || linkDanDian) {
//                                show = true;
//                            }
//                            // 其他情况, 隐藏
//                            else {
//                                show = false;
//                            }
//                        } catch (Exception e) {
//                            show = false;
//                        }
                        delayPlayer(0);
                        return true;
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
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().refreshContent();
                    }
                }).subscribe());
    }

    protected void addFavor(@NonNull String cid, @NonNull String recClassId) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                .flatMap(new Function<Boolean, Observable<BaseBean<FavorBean>>>() {
                    @Override
                    public Observable<BaseBean<FavorBean>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().addFavorite(cid, recClassId);
                    }
                })
                .map(new Function<BaseBean<FavorBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseBean<FavorBean> favorBeanBaseBean) {
                        try {
                            return favorBeanBaseBean.getData().isFavor();
                        } catch (Exception e) {
                            return false;
                        }
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
                        getView().updateFavor(true);
                    }
                })
                .subscribe());
    }

    protected void cancleFavor(@NonNull String cid) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                .flatMap(new Function<Boolean, Observable<BaseBean<Object>>>() {
                    @Override
                    public Observable<BaseBean<Object>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().cancelFavorite(cid);
                    }
                })
                .map(new Function<BaseBean<Object>, Boolean>() {
                    @Override
                    public Boolean apply(BaseBean<Object> favorBeanBaseBean) {
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
                        getView().updateFavor(false);
                    }
                })
                .subscribe());
    }

    protected boolean dispatchEvent(KeyEvent event) {
        // back
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            boolean checkPlayer = checkPlayer();
            if (checkPlayer) {
                ((Activity) getView()).onBackPressed();
            }
            return true;
        }
        // down
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.detail_player_item_vip || focusId == R.id.detail_player_item_full || focusId == R.id.detail_player_item_favor) {
                DetailGridView gridView = getView().findViewById(R.id.detail_list);
                boolean containsXuanJi = gridView.containsXuanJi();
                if (containsXuanJi) {
                    gridView.requestFocusEpisodePlayingItem();
                    return true;
                }
            }
        }
        return false;
    }

    protected final void delayPlayer(int position) {
        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                .delay(4, TimeUnit.SECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().startPlayer(position);
                    }
                })
                .subscribe());
    }
}

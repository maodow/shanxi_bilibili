package tv.huan.bilibili.ui.detail;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.base.BasePresenterImpl;
import tv.huan.bilibili.base.BaseThirdResponse;
import tv.huan.bilibili.bean.FavorBean;
import tv.huan.bilibili.bean.GetLastBookmark;
import tv.huan.bilibili.bean.GetMediasByCid2Bean;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.bean.MediaDetailBean;
import tv.huan.bilibili.bean.RecMediaBean;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallDetailBean;
import tv.huan.bilibili.bean.format.CallOptBean;
import tv.huan.bilibili.bean.local.LocalBean;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.detail.template.DetailTemplateFavor;
import tv.huan.bilibili.ui.detail.template.DetailTemplatePlayer;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanJi;
import tv.huan.bilibili.ui.detail.template.DetailTemplateXuanQi;
import tv.huan.bilibili.utils.DevicesUtils;
import tv.huan.bilibili.utils.ADUtil;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.DetailGridView;
import tv.huan.bilibili.widget.player.PlayerView;
import tv.huan.bilibili.widget.player.PlayerViewForDetail;
import tv.huan.heilongjiang.HeilongjiangUtil;

public class DetailPresenter extends BasePresenterImpl<DetailView> {
    public DetailPresenter(@NonNull DetailView detailView) {
        super(detailView);
    }

    protected final void setAdapter() {
        VerticalGridView gridView = getView().findViewById(R.id.detail_list);
        DetailSelectorPresenter selectorPresenter = new DetailSelectorPresenter();
        ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter(selectorPresenter);
        ItemBridgeAdapter bridgeAdapter = new ItemBridgeAdapter(objectAdapter);
        gridView.setAdapter(bridgeAdapter);
    }

    protected void request() {
        addDisposable(Observable.create(new ObservableOnSubscribe<CallDetailBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<CallDetailBean> emitter) {
                        CallDetailBean detailBean = new CallDetailBean();
                        emitter.onNext(detailBean);
                    }
                })
                // 媒资数据
                .flatMap(new Function<CallDetailBean, ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>> apply(CallDetailBean data) throws Exception {
                        try {
                            String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                            if (null == cid || cid.length() <= 0)
                                throw new Exception("cid error: " + cid);
                            String s = new Gson().toJson(data);
                            return HttpClient.getHttpClient().getHttpApi().getMediasByCid2(cid, s);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                // 媒资数据 => 数据处理
                .map(new Function<BaseResponsedBean<GetMediasByCid2Bean>, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(BaseResponsedBean<GetMediasByCid2Bean> getMediasByCid2BeanBaseResponsedBean) {
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
                        // 上报 => 来自挽留
                        try {
                            boolean booleanExtra = getView().getBooleanExtra(DetailActivity.INTENT_FROM_WANLIU, false);
                            if (booleanExtra) {
                                String keys = getView().getStringExtra(DetailActivity.INTENT_FROM_WANLIU_KEY, null);
                                if (null != keys && keys.length() > 0) {
                                    String cid = getView().getStringExtra(DetailActivity.INTENT_CID, null);
                                    if (null != cid && cid.length() > 0) {
                                        reportExitRetentionClick(cid, keys);
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                        // 上报 => 来自专题
                        try {
                            boolean booleanExtra = getView().getBooleanExtra(DetailActivity.INTENT_FROM_SPECIAL, false);
                            if (booleanExtra) {
                                int sceneId = getView().getIntExtra(DetailActivity.INTENT_FROM_SPECIAL_SCENEID, -1);
                                int topId = getView().getIntExtra(DetailActivity.INTENT_FROM_SPECIAL_TOPID, -1);
                                String topName = getView().getStringExtra(DetailActivity.INTENT_FROM_SPECIAL_TOPNAME, null);
                                if (null != topName && topName.length() > 0 && sceneId != -1 && topId != -1) {
                                    String cid = getView().getStringExtra(DetailActivity.INTENT_CID, null);
                                    if (null != cid && cid.length() > 0) {
                                        reportTopicEnterDetail(cid, sceneId, topId, topName);
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }

                        CallDetailBean detailBean;
                        try {
                            detailBean = new Gson().fromJson(getMediasByCid2BeanBaseResponsedBean.getExtra(), CallDetailBean.class);
                        } catch (Exception e) {
                            detailBean = new CallDetailBean();
                        }

                        // recClassId
                        try {
                            detailBean.setRecClassId(getMediasByCid2BeanBaseResponsedBean.getData().getRecClassId());
                        } catch (Exception e) {
                            detailBean.setRecClassId("");
                        }

                        // 播放策略
                        try {
                            GetMediasByCid2Bean data = getMediasByCid2BeanBaseResponsedBean.getData();
                            // 免费
                            if (0 == data.getAlbum().getPayStatus()) {
                                data.getAlbum().setPlayType(Integer.MAX_VALUE);
                            }
                            // 收费
                            else {
                            }
                        } catch (Exception e) {
                        }

                        // 详情
                        try {
                            GetMediasByCid2Bean data = getMediasByCid2BeanBaseResponsedBean.getData();
                            detailBean.setAlbum(data.getAlbum());
                        } catch (Exception e) {
                            detailBean.setAlbum(null);
                        }

                        // 剧集
                        try {
                            GetMediasByCid2Bean data = getMediasByCid2BeanBaseResponsedBean.getData();
                            detailBean.setMedias(data.getMedias());
                        } catch (Exception e) {
                            detailBean.setMedias(null);
                        }

                        // 猜你喜欢
                        try {
                            GetMediasByCid2Bean data = getMediasByCid2BeanBaseResponsedBean.getData();
                            detailBean.setRecAlbums(data.getRecAlbums());
                        } catch (Exception e) {
                            detailBean.setRecAlbums(null);
                        }
                        return detailBean;
                    }
                })
                // 观看记录
                .flatMap(new Function<CallDetailBean, ObservableSource<BaseResponsedBean<GetLastBookmark>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<GetLastBookmark>> apply(CallDetailBean data) {
                        String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getLastBookmark(cid, s);
                    }
                })
                // 观看记录 => 数据处理
                .map(new Function<BaseResponsedBean<GetLastBookmark>, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(BaseResponsedBean<GetLastBookmark> response) {
                        CallDetailBean detailBean;
                        try {
                            detailBean = new Gson().fromJson(response.getExtra(), CallDetailBean.class);
                        } catch (Exception e) {
                            detailBean = new CallDetailBean();
                        }
                        // 起播数据pos
                        try {
                            GetLastBookmark data = response.getData();
                            if (null == data)
                                throw new Exception("data error: null");
                            detailBean.setSeek(data.getSeek());
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => " + e.getMessage());
                            detailBean.setSeek(0);
                        }
                        // 起播数据seek
                        try {
                            GetLastBookmark data = response.getData();
                            if (null == data)
                                throw new Exception("data error: null");
                            List<MediaBean> medias = detailBean.getMedias();
                            if (null == medias)
                                throw new Exception("media error: null");
                            int size = medias.size();
                            if (size <= 0)
                                throw new Exception("size error: " + size);
                            int pos = data.getPos();
                            if (pos < 0 || pos + 1 >= size) {
                                pos = 0;
                            }
                            detailBean.setPos(pos);
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => " + e.getMessage());
                            detailBean.setPos(0);
                        }
                        // 填充数据
                        try {
                            List<MediaBean> medias = detailBean.getMedias();
                            if (null == medias)
                                throw new Exception();
                            int size = medias.size();
                            if (size <= 0)
                                throw new Exception();
                            int playType = detailBean.getAlbum().getPlayType();
                            int type = detailBean.getAlbum().getDetailType();
                            int pos = detailBean.getPos();
                            long seek = detailBean.getSeek();
                            for (int i = 0; i < size; i++) {
                                MediaBean mediaBean = medias.get(i);
                                if (null == mediaBean)
                                    continue;
                                mediaBean.setTempPlayType(playType);
                                mediaBean.setTempType(type);
                                mediaBean.setPos(i);
                                mediaBean.setSeek(i == pos ? seek : 0);
                            }
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => " + e.getMessage());
                        }
                        return detailBean;
                    }
                })
                // 收藏
                .flatMap(new Function<CallDetailBean, ObservableSource<BaseResponsedBean<FavorBean>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<FavorBean>> apply(CallDetailBean detailBean) {
                        String s = new Gson().toJson(detailBean);
                        String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                        return HttpClient.getHttpClient().getHttpApi().checkFavorite(cid, s);
                    }
                })
                // 收藏 => 数据处理
                .map(new Function<BaseResponsedBean<FavorBean>, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(BaseResponsedBean<FavorBean> data) {

                        CallDetailBean detailBean;
                        try {
                            detailBean = new Gson().fromJson(data.getExtra(), CallDetailBean.class);
                        } catch (Exception e) {
                            detailBean = new CallDetailBean();
                        }

                        // 收藏状态
                        try {
                            detailBean.setFavor(data.getData().isSucc());
                        } catch (Exception e) {
                        }
                        return detailBean;
                    }
                })
                // 播放器 => ui
                .map(new Function<CallDetailBean, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(CallDetailBean data) {
                        try {
                            // 1
                            DetailTemplatePlayer.DetailTemplatePlayerObject playerObject = new DetailTemplatePlayer.DetailTemplatePlayerObject();
                            playerObject.setTempFavor(data.isFavor());
                            boolean xuanJi = data.getAlbum().isXuanJi();
                            boolean xuanQi = data.getAlbum().isXuanQi();
                            if (xuanJi || xuanQi) {
                                playerObject.setTempMoiveCode(null);
                                playerObject.setTempPlayType(0);
                            } else {
                                try {
                                    playerObject.setTempMoiveCode(data.getMedias().get(0).getMovieCode());
                                } catch (Exception e) {
                                    playerObject.setTempMoiveCode(null);
                                }
                                try {
                                    playerObject.setTempPlayType(data.getAlbum().getPlayType());
                                } catch (Exception e) {
                                    playerObject.setTempPlayType(0);
                                }
                            }
                            int payType = data.getAlbum().getPayStatus();
                            playerObject.setTempPayType(payType); //收费类型：免费/付费
                            playerObject.setTempLegnth(data.getMedias().size());
                            playerObject.setTempType(data.getAlbum().getDetailType());
                            playerObject.setTempRecClassId(data.getRecClassId());
                            playerObject.setTempTag(data.getAlbum().getSplitTag());
                            playerObject.setTempTitle(data.getAlbum().getName());
                            playerObject.setTemoInfo(data.getAlbum().getInfo());
                            playerObject.setTempImageUrl(data.getAlbum().getPicture(true));
                            playerObject.setTempPicList(data.getAlbum().getPicList());
                            playerObject.setTempVideoUrl(null);
                            playerObject.setCid(data.getAlbum().getCid());
                            playerObject.setRecClassId(data.getRecClassId());
                            // 2
                            ArrayObjectAdapter objectAdapter = getView().getArrayObjectAdapter(R.id.detail_list);
                            objectAdapter.add(playerObject, false);
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => 播放器 => " + e.getMessage());
                        }
                        return data;
                    }
                })
                // 选期列表 => ui
                .map(new Function<CallDetailBean, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(CallDetailBean data) {
                        try {
                            MediaDetailBean detail = data.getAlbum();
                            boolean xuanQi = detail.isXuanQi();
                            if (!xuanQi) throw new Exception();
                            List<MediaBean> medias = data.getMedias();
                            if (null == medias || medias.size() <= 0) throw new Exception();
                            // 1
                            String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                            reportDetailSelectionsButtonShow(cid);
                            // 2
                            DetailTemplateXuanQi.DetailTemplateXuanQiList xuanqiData = new DetailTemplateXuanQi.DetailTemplateXuanQiList();
                            xuanqiData.addAll(data.getMedias());
                            // 3
                            ArrayObjectAdapter objectAdapter = getView().getArrayObjectAdapter(R.id.detail_list);
                            objectAdapter.add(xuanqiData, false);
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => 选期列表 => " + e.getMessage());
                        }
                        return data;
                    }
                })
                // 选集列表 => ui
                .map(new Function<CallDetailBean, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(CallDetailBean data) {
                        try {
                            MediaDetailBean detail = data.getAlbum();
                            boolean xuanJi = detail.isXuanJi();
                            if (!xuanJi) throw new Exception();
                            List<MediaBean> medias = data.getMedias();
                            if (null == medias || medias.size() <= 0) throw new Exception();
                            // 1
                            String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                            reportDetailSelectionsButtonShow(cid);
                            // 2
                            DetailTemplateXuanJi.DetailTemplateXuanJiList xuanjiData = new DetailTemplateXuanJi.DetailTemplateXuanJiList();
                            xuanjiData.addAll(data.getMedias());
                            // 3
                            ArrayObjectAdapter objectAdapter = getView().getArrayObjectAdapter(R.id.detail_list);
                            objectAdapter.add(xuanjiData, false);
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => 选集列表 => " + e.getMessage());
                        }
                        return data;
                    }
                })
                // 猜你喜欢 => ui
                .map(new Function<CallDetailBean, CallDetailBean>() {
                    @Override
                    public CallDetailBean apply(CallDetailBean data) {
                        try {
                            List<RecMediaBean> albums = data.getRecAlbums();
                            if (null == albums || albums.size() <= 0) throw new Exception();
                            // 1
                            String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                            reportDetailRecommendShow(cid);
                            // 2
                            DetailTemplateFavor.DetailTemplateFavList favorData = new DetailTemplateFavor.DetailTemplateFavList();
                            for (RecMediaBean bean : albums) {
                                if (null == bean) continue;
                                favorData.add(bean);
                            }
                            // 3
                            ArrayObjectAdapter objectAdapter = getView().getArrayObjectAdapter(R.id.detail_list);
                            objectAdapter.add(favorData, false);
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => request => 猜你喜欢 => " + e.getMessage());
                        }
                        return data;
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
                        getView().notifyDataSetChanged(R.id.detail_list);
                    }
                }).doOnNext(new Consumer<CallDetailBean>() {
                    @Override
                    public void accept(CallDetailBean data) {
                        getView().hideLoading();
                        getView().notifyDataRangeInsertLeanBack(R.id.detail_list);
                        getView().startPlayerPosition(data.getMedias().get(data.getPos()), data.getPos(), data.getSeek(), false);
                    }
                }).subscribe());
    }

    protected void addFavor(@NonNull String cid, @NonNull String recClassId) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                }).flatMap(new Function<Boolean, Observable<BaseResponsedBean<FavorBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<FavorBean>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().addFavorite(cid, recClassId);
                    }
                })
                // 数据处理
                .map(new Function<BaseResponsedBean<FavorBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<FavorBean> response) {
                        try {
                            FavorBean data = response.getData();
                            if (null == data)
                                throw new Exception();
                            return data.isSucc();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                })
                // 查询媒资详情
                .flatMap(new Function<Boolean, ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>> apply(Boolean data) {
                        if (data) {
                            return HttpClient.getHttpClient().getHttpApi().getMediasByCid2(cid, null);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<BaseResponsedBean<GetMediasByCid2Bean>>() {
                                @Override
                                public void subscribe(ObservableEmitter<BaseResponsedBean<GetMediasByCid2Bean>> emitter) {
                                    BaseResponsedBean baseResponsedBean = new BaseResponsedBean();
                                    emitter.onNext(baseResponsedBean);
                                }
                            });
                        }
                    }
                })
                // 数据处理 => 收藏记录
                .map(new Function<BaseResponsedBean<GetMediasByCid2Bean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<GetMediasByCid2Bean> response) {
                        try {
                            // 1
                            String s = CacheUtil.getCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR);
                            Type type = new TypeToken<List<LocalBean>>() {
                            }.getType();
                            List<LocalBean> list = new Gson().fromJson(s, type);
                            // 2
                            MediaDetailBean album = response.getData().getAlbum();
                            String name = album.getName();
                            String picture = album.getPicture(true);
                            String cid1 = album.getCid();
                            LocalBean localBean = new LocalBean();
                            localBean.setLocal_img(picture);
                            localBean.setName(name);
                            localBean.setCid(cid1);
                            // 3
                            list.add(0, localBean);
                            String s1 = new Gson().toJson(list);
                            // 4
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR, s1);
                            LogUtil.log("DetailPresenter => addFavor => cache succ");
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => addFavor => cache fail");
                        }
                        try {
                            GetMediasByCid2Bean data = response.getData();
                            if (null == data)
                                throw new Exception();
                            return true;
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
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().updateFavor(true);
                    }
                }).subscribe());
    }

    protected void cancleFavor(@NonNull String cid) {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                }).flatMap(new Function<Boolean, Observable<BaseResponsedBean<CallOptBean>>>() {
                    @Override
                    public Observable<BaseResponsedBean<CallOptBean>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().cancelFavorite(cid);
                    }
                })
                // 数据处理
                .map(new Function<BaseResponsedBean<CallOptBean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<CallOptBean> response) {
                        try {
                            CallOptBean data = response.getData();
                            if (null == data)
                                throw new Exception();
                            return data.isSucc();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                })
                // 查询媒资详情
                .flatMap(new Function<Boolean, ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>> apply(Boolean data) {
                        if (data) {
                            return HttpClient.getHttpClient().getHttpApi().getMediasByCid2(cid, null);
                        } else {
                            return Observable.create(new ObservableOnSubscribe<BaseResponsedBean<GetMediasByCid2Bean>>() {
                                @Override
                                public void subscribe(ObservableEmitter<BaseResponsedBean<GetMediasByCid2Bean>> emitter) {
                                    BaseResponsedBean baseResponsedBean = new BaseResponsedBean();
                                    emitter.onNext(baseResponsedBean);
                                }
                            });
                        }
                    }
                })
                // 数据处理 => 收藏记录
                .map(new Function<BaseResponsedBean<GetMediasByCid2Bean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<GetMediasByCid2Bean> response) {
                        try {
                            // 1
                            String s = CacheUtil.getCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR);
                            Type type = new TypeToken<List<LocalBean>>() {
                            }.getType();
                            List<LocalBean> list = new Gson().fromJson(s, type);
                            // 2
                            MediaDetailBean album = response.getData().getAlbum();
                            // 3
                            int index = -1;
                            for (LocalBean o : list) {
                                if (null == o)
                                    continue;
                                if (o.getCid().equals(album.getCid())) {
                                    index = list.indexOf(o);
                                    break;
                                }
                            }
                            // 4
                            if (index != -1) {
                                list.remove(index);
                            }
                            String s1 = new Gson().toJson(list);
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_FAVOR, s1);
                            LogUtil.log("DetailPresenter => cancleFavor => cache succ");
                        } catch (Exception e) {
                            LogUtil.log("DetailPresenter => cancleFavor => cache fail");
                        }
                        try {
                            GetMediasByCid2Bean data = response.getData();
                            if (null == data)
                                throw new Exception();
                            return true;
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
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().updateFavor(false);
                    }
                }).subscribe());
    }

    protected boolean dispatchEvent(KeyEvent event) {
        // center
        if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            try {
                PlayerViewForDetail playerView = getView().findViewById(R.id.detail_player_item_video);
                boolean isFull = playerView.isFull();
                // full
                if (isFull) {
                    playerView.toggle();
                    return true;
                }
            } catch (Exception e) {
            }
        }
        // down
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.detail_player_item_vip || focusId == R.id.detail_player_item_full || focusId == R.id.detail_player_item_favor) {
                DetailGridView gridView = getView().findViewById(R.id.detail_list);
                boolean dispatchKeyEvent = gridView.dispatchKeyEvent(View.FOCUS_DOWN);
                if (dispatchKeyEvent) {
                    return true;
                }
            }
        }
        // up
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.detail_item_favor) {
                DetailGridView gridView = getView().findViewById(R.id.detail_list);
                boolean dispatchKeyEvent = gridView.dispatchKeyEvent(View.FOCUS_UP);
                if (dispatchKeyEvent) {
                    return true;
                }
            }
        }
        // back
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            try {
                PlayerViewForDetail playerView = getView().findViewById(R.id.detail_player_item_video);
                boolean isFull = playerView.isFull();
                // full
                if (isFull) {
                    playerView.dispatchKeyEvent(event);
                    return true;
                }
                // report
                else {
                    DetailGridView gridView = getView().findViewById(R.id.detail_list);
                    long position = gridView.getPlayerPosition();
                    long duration = gridView.getPlayerDuraion();
                    getView().putLongExtra(DetailActivity.INTENT_CUR_POSITION, position);
                    getView().putLongExtra(DetailActivity.INTENT_CUR_DURATION, duration);
                    releasePlayer();
                    return false;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    protected void onBackPressedTodo() {
        addDisposable(Observable.create(new ObservableOnSubscribe<LocalBean>() {
                    // 上报数据
                    @Override
                    public void subscribe(ObservableEmitter<LocalBean> emitter) {

                        String cid = getView().getStringExtra(DetailActivity.INTENT_CID, "");
                        String vid = getView().getStringExtra(DetailActivity.INTENT_VID, "");
                        // 1
                        long start = getView().getLongExtra(DetailActivity.INTENT_START_TIME, 0);
                        long end = System.currentTimeMillis();
                        reportPlayVodStop(cid, vid, start, end);
                        // 2
                        long duration = getView().getLongExtra(DetailActivity.INTENT_CUR_DURATION, 0);
                        long position = getView().getLongExtra(DetailActivity.INTENT_CUR_POSITION, 0);
                        boolean isEnd = position > 0 && duration > 0 && position >= duration;
                        String classId = getView().getStringExtra(DetailActivity.INTENT_REC_CLASSID, "");
                        int pos = getView().getIntExtra(DetailActivity.INTENT_INDEX, 1);
                        int endFlag = isEnd ? 0 : 1;
                        uploadPlayHistory(cid, vid, classId, pos, endFlag, duration, position);
                        // 3
                        LocalBean o = new LocalBean();
                        o.setPos(pos);
                        if (position > 0 && duration > 0 && position == duration) {
                            o.setLocal_status("已看完");
                        } else if (position <= 0 && duration <= 0) {
                            o.setLocal_status("观看至 1%");
                        } else {
                            o.setLocal_status("观看至 " + (int) position * 100 / duration + "%");
                        }
                        // 4
                        emitter.onNext(o);
                    }
                })
                // 媒资数据
                .flatMap(new Function<LocalBean, ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>>>() {
                    @Override
                    public ObservableSource<BaseResponsedBean<GetMediasByCid2Bean>> apply(LocalBean data) {
                        String cid = getView().getStringExtra(DetailActivity.INTENT_CID);
                        if (null == cid) {
                            cid = "";
                        }
                        String s = new Gson().toJson(data);
                        return HttpClient.getHttpClient().getHttpApi().getMediasByCid2(cid, s);
                    }
                })
                // 数据处理
                .map(new Function<BaseResponsedBean<GetMediasByCid2Bean>, Boolean>() {
                    @Override
                    public Boolean apply(BaseResponsedBean<GetMediasByCid2Bean> responsedBean) {

                        LocalBean localBean;
                        try {
                            localBean = new Gson().fromJson(responsedBean.getExtra(), LocalBean.class);
                        } catch (Exception e) {
                            localBean = new LocalBean();
                        }

                        // 观看记录
                        try {
                            // 1
                            String s = CacheUtil.getCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_HISTORY);
                            Type type = new TypeToken<List<LocalBean>>() {
                            }.getType();
                            List<LocalBean> list = new Gson().fromJson(s, type);
                            // 2
                            MediaDetailBean album = responsedBean.getData().getAlbum();
                            localBean.setCid(album.getCid());
                            localBean.setLocal_img(album.getPicture(true));
                            localBean.setName(album.getName2(localBean.getPos()));
                            for (LocalBean o : list) {
                                if (null == o)
                                    continue;
                                String cid = o.getCid();
                                if (cid.equals(localBean.getCid())) {
                                    list.remove(o);
                                    break;
                                }
                            }
                            // 3
                            list.add(0, localBean);
                            String s1 = new Gson().toJson(list);
                            // 4
                            CacheUtil.setCache(getView().getContext(), BuildConfig.HUAN_JSON_LOCAL_HISTORY, s1);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                })
                // 销毁ad-sdk
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) {
                        try {
                            ADUtil.adRelease();
                        } catch (Exception e) {
                        }
                        return aBoolean;
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
                        getView().callFinish();
                    }
                }).doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        getView().hideLoading();
                        getView().callFinish();
                    }
                }).subscribe());
    }

    protected void requestHuaweiAuth(String playCode, long seek) {
        addDisposable(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext(playCode);
            }
        })
                .flatMap(new Function<String, Observable<BaseThirdResponse>>() {
                    @Override
                    public Observable<BaseThirdResponse> apply(String code) throws Exception {
                        JSONObject object = new JSONObject();
                        LogUtil.log("Detail@@@获取播放链接, movieCode: "+code);
                        object.put("cid", code);
                        object.put("tid", "-1"); //栏目标识
                        object.put("supercid", "-1");
                        object.put("playType", "1"); //1：VOD播放  2：TV播放   4：TVOD播放   5：片花播放   6：书签播放   8：直播频道、轮播频道的节目播放
                        object.put("contentType", "0"); //0：视频VOD  1：视频频道  2：音频频道  3：频道  4：音频VOD   10：VOD   300：频道节目
                        object.put("businessType", "1"); //业务类型  1：VOD  2：LIVETV  4：PLTV  5：TVOD   6:LIVETV&PLTV
                        object.put("idflag", "1"); //当前OTT牌照方使用自己的CODE时，务必"1"。 0：内部ID标记   1：外部牌照商CODE

                        String json = String.valueOf(object);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
                        String epgServer = DevicesUtils.INSTANCE.getEpgServer();
                        String reqUrl = "";
                        if(!TextUtils.isEmpty(epgServer)){
                            reqUrl = epgServer.concat(BuildConfig.PLAY_API);
                        }
                        return HttpClient.getHttpClient().getHttpApi().getPlayUrl(reqUrl, requestBody);
                    }
                })
                // 获取播放地址 => 数据处理
                .map(new Function<BaseThirdResponse, String>() {
                    @Override
                    public String apply(BaseThirdResponse resp) throws Exception {
                        try {
                            String url = resp.getUrls().get(0).getPlayurl();
                            if (null == url || url.length() <= 0)
                                throw new Exception();
                            return url;
                        } catch (Exception e) {
                            throw new Exception("Detail@@@获取播放链接失败！errCode => " + resp.getReturncode()+"， "+resp.getDescription());
                        }
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().showToast(throwable);
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        LogUtil.log("Detail@@@获取播放链接, onGetPlayUrlSuccess(): "+s);
                        getView().huaweiSucc(s, seek);
                    }
                })
                .subscribe());
    }

    protected void releasePlayer() {
        try {
            PlayerViewForDetail playerView = getView().findViewById(R.id.detail_player_item_video);
            if (null == playerView)
                throw new Exception();
            playerView.setPlayWhenReady(false);
            playerView.stop();
            playerView.pause();
            playerView.release();
        } catch (Exception e) {
        }
    }
}

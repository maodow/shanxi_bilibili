package tv.huan.bilibili.http;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import lib.kalu.frame.mvp.interceptor.OkhttpInterceptorStandard;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import tv.huan.bilibili.bean.Auth2BeanBase;
import tv.huan.bilibili.bean.ExitBean;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.FavorBean;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.GetMediasByCid2Bean;
import tv.huan.bilibili.bean.GetPopupInfoBeanBase;
import tv.huan.bilibili.bean.GetSecondTagAlbumsBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.SearchAlbumByTypeNews;
import tv.huan.bilibili.bean.SearchBean;
import tv.huan.bilibili.bean.SpecialBean;
import tv.huan.bilibili.bean.base.BaseAuthorizationBean;
import tv.huan.bilibili.bean.base.BaseDataBean;
import tv.huan.bilibili.bean.base.BaseResponsedBean;
import tv.huan.bilibili.bean.format.CallOptBean;

public interface HttpApi {

    // 日志上报
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("log/report")
    Observable<BaseResponsedBean<Object>> logReport(@Body RequestBody requestBody);

    // 退出挽留
    @GET("apk/wanLiu")
    Observable<BaseResponsedBean<List<ExitBean>>> getExit(@Query("size") int size);

    // 获取loading和弹窗
    @GET("advert/getPopupInfo")
    Observable<GetPopupInfoBeanBase> getPopupInfo(@Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // fileType为文件类型，1Banner， 2为帮助中心，3为关于我们
    @GET("apk/getFileUrl")
    Observable<BaseResponsedBean<String>> getFileUrl(@Query("fileType") int fileType,
                                                     @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 删除历史
    @GET("data/delBookmarkById")
    Observable<BaseResponsedBean<CallOptBean>> deleteBookmark(@Query("cid") String cid);

    // 添加收藏
    @GET("data/addFavorite")
    Observable<BaseResponsedBean<FavorBean>> addFavorite(@Query("cid") String cid,
                                                         @Query("classId") String classId);

    // 取消收藏
    @GET("data/cancelFavorite")
    Observable<BaseResponsedBean<CallOptBean>> cancelFavorite(@Query("cid") String cid);

    // 查询观看记录
    @GET("data/getLastBookmark")
    Observable<BaseResponsedBean<BaseDataBean>> getLastBookmark(@Query("cid") String cid,
                                                                @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 查询收藏
    @GET("data/checkFavorite")
    Observable<BaseResponsedBean<FavorBean>> checkFavorite(@Query("cid") String cid,
                                                           @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取频道
    @GET("channel/getChannels")
    Observable<BaseResponsedBean<GetChannelsBean>> getChannels(@Query("modId") int modId,
                                                               @Query("size") int size,
                                                               @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 白名单
    @POST("apk/auth2")
    Observable<Auth2BeanBase> auth2(@Query("cid") String cid,
                                    @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取影片详情
    @GET("media/getMediasByCid2/{cid}")
    Observable<BaseResponsedBean<GetMediasByCid2Bean>> getMediasByCid2(@Path("cid") String cid,
                                                                       @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 首字母搜索
    @GET("album/getAlbumBySpell2")
    Observable<BaseResponsedBean<SearchBean>> searchBySpell(@Query("spell") String spell,
                                                            @Query("offset") int offset,
                                                            @Query("pages") int size,
                                                            @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 热搜推荐
    @GET("recomend/getSearchRecommendByProdId/{prodId}")
    Observable<BaseResponsedBean<SearchBean>> getSearchRecommend(@Path("prodId") int prodId,
                                                                 @Query("pageSize") int pageSize,
                                                                 @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 根据classId获取筛选条件
    @GET("album/getFilterTypes2")
    Observable<BaseResponsedBean<LinkedHashMap<String, List<String>>>> getFilterTypes(@Query("classId") int classId,
                                                                                      @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取二级标签
    @GET("class/getClassByPrentId/{classId}")
    Observable<BaseResponsedBean<GetSubChannelsByChannelBean>> getClassByPrentId(@Path("classId") int classId,
                                                                                 @Query(OkhttpInterceptorStandard.EXTRA) String extra);


    // 获取筛选结果
    @POST("album/searchAlbumByTypeNews")
    Observable<BaseResponsedBean<SearchAlbumByTypeNews>> searchAlbumByTypeNews(@Body RequestBody requestBody,
                                                                               @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取播放记录
    @GET("data/getBookmark2")
    Observable<BaseResponsedBean<FavBean>> getBookmark(@Query("offset") int offset,
                                                       @Query("size") int size,
                                                       @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 查询收藏列表
    @GET("data/getFavList")
    Observable<BaseResponsedBean<FavBean>> getFavList(@Query("offset") int offset,
                                                      @Query("size") int size,
                                                      @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 查询数据库中某个分类的所有绑定专辑信息
    @GET("album/getAlbumByClassId2/{classId}")
    Observable<BaseResponsedBean<GetSecondTagAlbumsBean>> getSecondTagAlbums(@Path("classId") int classId,
                                                                             @Query("offset") int offset,
                                                                             @Query("size") int size,
                                                                             @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 根据频道ID获取子频道列表
    @GET("channel/getSubChannelsByChannel")
    Observable<BaseResponsedBean<GetSubChannelsByChannelBean>> getSubChannelsByChannel(@Query("channelId") int channelId,
                                                                                       @Query("offset") int offset,
                                                                                       @Query("size") int size);

    // 根据专题ID获取专题信息
    @GET("channel/getSpecialById")
    Observable<BaseResponsedBean<SpecialBean>> getSpecialById(@Query("specialId") int specialId);

    // 添加播放记录
    @GET("data/savePlayHistory")
    Observable<BaseResponsedBean<Object>> savePlayHistory(@Query("vid") String vid,
                                                          @Query("cid") String cid,
                                                          @Query("endFlag") int endFlag, //是否播放完成  0未完  1观看结束
                                                          @Query("classId") String classId,
                                                          @Query("pos") int pos, //集数从0开始
                                                          @Query("playTime") long playTime, //当前播放时长
                                                          @Query("playLength") long playLength); //当前视频时长

    // 华为播放鉴权
    @POST()
    Observable<BaseAuthorizationBean> huaweiAuth(@Url String url,
                                                 @Body RequestBody requestBody,
                                                 @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    //    // 获取HuanId
//    @GET("apk/getHuanId")
//    Observable<BaseBean<HuanInfo>> getHuanId(@Query(OkhttpInterceptorStandard.EXTRA) String extra);

//    // 获取黑名单
//    @GET
//    Observable<BlackList> getBlackList(@Url String url);

//    // 升级版本号获取
//    @Headers("Content-Type: application/json")
//    @GET
//    Observable<JSONObject> getUpdateJson(@Url String url);

//    // 获取皮肤资源
//    @GET
//    Observable<JSONObject> getIcon(@Url String url);
}

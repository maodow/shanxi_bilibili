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
import tv.huan.bilibili.bean.Auth2Bean;
import tv.huan.bilibili.bean.ResponsedBean;
import tv.huan.bilibili.bean.ExitBean;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.FavorBean;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.GetMediasByCid2Bean;
import tv.huan.bilibili.bean.GetPopupInfoBean;
import tv.huan.bilibili.bean.GetSecondTagAlbumsBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.SearchAlbumByTypeNews;
import tv.huan.bilibili.bean.SearchBean;
import tv.huan.bilibili.bean.SpecialBean;
import tv.huan.bilibili.bean.format.OptBean;

public interface HttpApi {

    // 日志上报
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("log/report")
    Observable<ResponsedBean<Object>> logReport(@Body RequestBody requestBody);

    // 退出挽留
    @GET("apk/wanLiu")
    Observable<ResponsedBean<List<ExitBean>>> getExit(@Query("size") int size);

    // 获取loading和弹窗
    @GET("advert/getPopupInfo")
    Observable<GetPopupInfoBean> getPopupInfo(@Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // fileType为文件类型，1Banner， 2为帮助中心，3为关于我们
    @GET("apk/getFileUrl")
    Observable<ResponsedBean<String>> getFileUrl(@Query("fileType") int fileType);

    // 删除历史
    @GET("data/delBookmarkById")
    Observable<ResponsedBean<OptBean>> deleteBookmark(@Query("cid") String cid);

    // 添加收藏
    @GET("data/addFavorite")
    Observable<ResponsedBean<FavorBean>> addFavorite(@Query("cid") String cid,
                                                     @Query("classId") String classId);

    // 取消收藏
    @GET("data/cancelFavorite")
    Observable<ResponsedBean<OptBean>> cancelFavorite(@Query("cid") String cid);

    // 查询收藏
    @GET("data/checkFavorite")
    Observable<ResponsedBean<FavorBean>> checkFavorite(@Query("cid") String cid,
                                                       @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取频道
    @GET("channel/getChannels")
    Observable<ResponsedBean<GetChannelsBean>> getChannels(@Query("modId") int modId,
                                                           @Query("size") int size,
                                                           @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 白名单
    @POST("apk/auth2")
    Observable<Auth2Bean> auth2(@Query("cid") String cid,
                                @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取影片详情
    @GET("media/getMediasByCid2/{cid}")
    Observable<ResponsedBean<GetMediasByCid2Bean>> getMediasByCid2(@Path("cid") String cid);

    // 首字母搜索
    @GET("album/getAlbumBySpell2")
    Observable<ResponsedBean<SearchBean>> searchBySpell(@Query("spell") String spell,
                                                        @Query("offset") int offset,
                                                        @Query("size") int size,
                                                        @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 热搜推荐
    @GET("recomend/getSearchRecommendByProdId/{prodId}")
    Observable<ResponsedBean<SearchBean>> getSearchRecommend(@Path("prodId") int prodId,
                                                             @Query("pageSize") int pageSize,
                                                             @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 根据classId获取筛选条件
    @GET("album/getFilterTypes2")
    Observable<ResponsedBean<LinkedHashMap<String, List<String>>>> getFilterTypes(@Query("classId") int classId,
                                                                                  @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 获取二级标签
    @GET("class/getClassByPrentId/{classId}")
    Observable<ResponsedBean<GetSubChannelsByChannelBean>> getClassByPrentId(@Path("classId") int classId,
                                                                             @Query(OkhttpInterceptorStandard.EXTRA) String extra);


    // 获取筛选结果
    @POST("album/searchAlbumByTypeNews")
    Observable<ResponsedBean<SearchAlbumByTypeNews>> searchAlbumByTypeNews(@Body RequestBody requestBody);

    // 获取播放记录
    @GET("data/getBookmark2")
    Observable<ResponsedBean<FavBean>> getBookmark(@Query("offset") int offset,
                                                   @Query("size") int size,
                                                   @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 查询收藏列表
    @GET("data/getFavList")
    Observable<ResponsedBean<FavBean>> getFavList(@Query("offset") int offset,
                                                  @Query("size") int size);

    // 查询数据库中某个分类的所有绑定专辑信息
    @GET("album/getAlbumByClassId2/{classId}")
    Observable<ResponsedBean<GetSecondTagAlbumsBean>> getSecondTagAlbums(@Path("classId") int classId,
                                                                         @Query("offset") int offset,
                                                                         @Query("size") int size,
                                                                         @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    // 根据频道ID获取子频道列表
    @GET("channel/getSubChannelsByChannel")
    Observable<ResponsedBean<GetSubChannelsByChannelBean>> getSubChannelsByChannel(@Query("channelId") int channelId,
                                                                                   @Query("offset") int offset,
                                                                                   @Query("size") int size);

    // 根据专题ID获取专题信息
    @GET("channel/getSpecialById")
    Observable<ResponsedBean<SpecialBean>> getSpecialById(@Query("specialId") int specialId);

    // 添加播放记录
    @GET("data/savePlayHistory")
    Observable<ResponsedBean<Object>> savePlayHistory(@Query("vid") String vid,
                                                      @Query("cid") String cid,
                                                      @Query("endFlag") int endFlag, //是否播放完成  0未完  1观看结束
                                                      @Query("classId") String classId,
                                                      @Query("pos") int pos, //集数从0开始
                                                      @Query("playTime") long playTime, //当前播放时长
                                                      @Query("playLength") long playLength); //当前视频时长

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

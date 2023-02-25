package tv.huan.bilibili.http;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import lib.kalu.frame.mvp.interceptor.OkhttpInterceptorStandard;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import tv.huan.bilibili.bean.AccessToken;
import tv.huan.bilibili.bean.AddHistory;
import tv.huan.bilibili.bean.Auth2Bean;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.BlackList;
import tv.huan.bilibili.bean.DataBean;
import tv.huan.bilibili.bean.ExitBean;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.GetSecondTagAlbumsBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.bean.HuanInfo;
import tv.huan.bilibili.bean.LoadPageIcon;
import tv.huan.bilibili.bean.ProgramInfo;
import tv.huan.bilibili.bean.ProgramInfoDetail;
import tv.huan.bilibili.bean.SearchAlbumByTypeNews;
import tv.huan.bilibili.bean.SearchBean;
import tv.huan.bilibili.bean.SearchRecommendBean;
import tv.huan.bilibili.bean.SpecialBean;
import tv.huan.bilibili.bean.UrlType;

public interface HttpApi {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("log/report")
    Observable<BaseBean<Object>> logReport(@Body RequestBody requestBody);

    /**
     * 获取频道
     *
     * @param modId
     * @return
     */
    @GET("channel/getChannels")
    Observable<BaseBean<GetChannelsBean>> getChannels(@Query("modId") int modId,
                                                      @Query("size") int size,
                                                      @Query(OkhttpInterceptorStandard.EXTRA) String extra);


    /**
     * 获取HuanID
     *
     * @param uid
     * @return
     */
    @GET("apk/getHuanId")
    Observable<DataBean<HuanInfo>> getHuanId(@Query("uid") String uid);


    /**
     * 添加收藏
     *
     * @param productId
     * @param cid
     * @param classId
     * @return
     */
    @GET("data/addFavorite")
    Observable<DataBean<AddHistory>> addFavorite(@Query("productId") String productId,
                                                 @Query("cid") String cid,
                                                 @Query("classId") String classId);


    /**
     * 取消收藏
     *
     * @param productId
     * @param cid
     * @return
     */
    @GET("data/cancelFavorite")
    Observable<DataBean<AddHistory>> cancelFavorite(@Query("productId") String productId,
                                                    @Query("cid") String cid);

    /**
     * 查询收藏
     *
     * @param productId
     * @param cid
     * @return
     */
    @GET("data/checkFavorite")
    Observable<DataBean<AddHistory>> checkFavorite(@Query("productId") String productId,
                                                   @Query("cid") String cid);


    /**
     * 获取影片详情
     *
     * @param cid 专辑ID
     */
    @GET("media/getMediasByCid/{cid}")
    Observable<DataBean<ProgramInfoDetail>> getMediasByCid(@Path("cid") String cid);

    /**
     * 获取影片详情
     *
     * @param cid 专辑ID
     */
    @GET("media/getMediasByCid2/{cid}")
    Observable<BaseBean<ProgramInfoDetail>> getMediasByCid2(@Path("cid") String cid);

    /**
     * 河北用户周期鉴权接口
     *
     * @return x
     */
    @POST("apk/auth2")
    Observable<Auth2Bean> auth2(@Query("cid") String cid,
                                @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    /**
     * 获取详情页推荐
     *
     * @param size
     * @param classId
     * @param page
     * @return
     */
    @GET("album/getRecAlbumByClassId/{classId}")
    Observable<DataBean<ProgramInfo>> getRecAlbumByClassId(
            @Path("classId") String classId,
            @Query("page") String page,
            @Query("size") String size);

    //ADD XIECHENG

    /**
     * 首字母搜索
     *
     * @param spell  x
     * @param offset x
     * @param size   x
     * @return x
     */
    @GET("album/getAlbumBySpell2")
    Observable<BaseBean<SearchBean>> searchBySpell(@Query("spell") String spell,
                                                   @Query("offset") int offset,
                                                   @Query("size") int size);

    /**
     * 获取热搜推荐
     *
     * @param pageSize x
     * @return x
     */
    @GET("recomend/getSearchRecommendByProdId/{prodId}")
    Observable<BaseBean<SearchRecommendBean>> getSearchRecommend(@Path("prodId") int prodId,
                                                                 @Query("pageSize") int pageSize);

    /**
     * 根据classId获取筛选条件
     *
     * @param classId x
     * @return x
     */
    @GET("album/getFilterTypes2")
    Observable<BaseBean<LinkedHashMap<String, List<String>>>> getFilterTypes(@Query("classId") int classId,
                                                                             @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    /**
     * 获取二级标签
     *
     * @param classId x
     * @return x
     */
    @GET("class/getClassByPrentId/{classId}")
    Observable<BaseBean<GetSubChannelsByChannelBean>> getClassByPrentId(@Path("classId") int classId,
                                                                        @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    /**
     * 获取筛选结果
     *
     * @param offset  偏移量
     * @param size    一页个数
     * @param classId 分类ID
     * @param cSort   最新/好评
     * @param cTag    二级标签
     * @param cAerea  区域
     * @param cYear   年份
     */
//    @GET("album/searchAlbumByType")
//    Observable<FilterAlbumsBean> searchAlbumByType(@Query("prodId") int prodId,
//                                                   @Query("offset") int offset,
//                                                   @Query("size") int size,
//                                                   @Query("classId") int classId,
//                                                   @Query("cSort") String cSort,
//                                                   @Query("cTag") String cTag,
//                                                   @Query("cAerea") String cAerea,

    /**
     * 获取筛选结果
     */
    @POST("album/searchAlbumByTypeNews")
    Observable<BaseBean<SearchAlbumByTypeNews>> searchAlbumByTypeNews(@Body RequestBody requestBody);

    /**
     * 查询顶层分类下的专辑信息并排序
     *
     * @param offset  偏移量
     * @param size    一页个数
     * @param classId 分类ID
     * @param type    排序类型(默认按置顶顺序排列)
     *                1:上映时间
     *                2.评分高低
     *                3.上映时间+评分高低
     *                4.上映时间+影片名称
     *                5.评分高低 + 上映时间 + 影片名称
     */
//    @GET("album/getAlbumByClassIdsAndSort2")
//    Observable<FilterAlbumsBean> getAlbumByClassIdsAndSort(@Query("offset") int offset,
//                                                           @Query("size") int size,
//                                                           @Query("prodId") int prodId,
//                                                           @Query("classId") int classId,
//                                                           @Query("type") int type);

    /**
     * 根据用户ID及offset获取播放记录
     *
     * @param offset 偏移量
     * @param size   一页个数
     */
    @GET("data/getBookmark2")
    Observable<BaseBean<FavBean>> getBookmark(@Query("offset") int offset,
                                              @Query("size") int size,
                                              @Query(OkhttpInterceptorStandard.EXTRA) String extra);

    /**
     * 根据huanId及offset查询收藏列表
     *
     * @param offset 偏移量
     * @param size   一页个数
     */
    @GET("data/getFavList")
    Observable<BaseBean<FavBean>> getFavList(@Query("offset") int offset,
                                             @Query("size") int size,
                                             @Query("productId") String productId);

    /**
     * 取消收藏
     *
     * @param productId x
     * @param cid       x
     * @return x
     */
    @GET("data/cancelFavorite")
    Observable<BaseBean<Object>> cancelFav(@Query("productId") int productId,
                                           @Query("cid") String cid);

    /**
     * 删除历史
     *
     * @param cid x
     * @return x
     */
    @GET("data/delBookmarkById")
    Observable<BaseBean<Object>> deleteBookmark(@Query("cid") String cid);

    /**
     * 查询数据库中某个分类的所有绑定专辑信息
     *
     * @param classId x
     * @param offset  x
     * @param size    x
     * @return x
     */
    @GET("album/getAlbumByClassId2/{classId}")
    Observable<BaseBean<GetSecondTagAlbumsBean>> getSecondTagAlbums(@Path("classId") int classId,
                                                                    @Query("offset") int offset,
                                                                    @Query("size") int size,
                                                                    @Query(OkhttpInterceptorStandard.EXTRA) String extra);


    /**
     * 根据频道ID获取子频道列表
     *
     * @param channelId X
     * @param offset    X
     * @param size      X
     * @return X
     */
    @GET("channel/getSubChannelsByChannel")
    Observable<BaseBean<GetSubChannelsByChannelBean>> getSubChannelsByChannel(@Query("channelId") int channelId,
                                                                              @Query("offset") int offset,
                                                                              @Query("size") int size);

    /**
     * 根据专题ID获取专题信息
     *
     * @param specialId 专题ID
     */
    @GET("channel/getSpecialById")
    Observable<BaseBean<SpecialBean>> getSpecialById(@Query("specialId") int specialId);

//    /**
//     * 用户鉴权
//     *
//     * @param uid x
//     * @return x
//     */
//    @GET("apk/auth")
//    Observable<AuthBean> getAuth(@Query("qua") String qua, @Query("uid") String uid, @Query("guid") String bossId, @Query("cid") String cid);


//    /**
//     * 获取会员开通状态
//     *
//     * @return x
//     */
//    @GET("apk/txAuth")
//    Observable<VipBean> getTxAuth(@Query("guid") String guid);


    /**
     * 获取CDN类型
     *
     * @param uid x
     * @return zx 或hw 中兴或华为
     */
    @GET("getPlayUrlType")
    Observable<DataBean<UrlType>> getUrlType(@Query("uid") String uid);


    /**
     * 获取access_token
     */
    @GET("apk/get_token")
    Observable<DataBean<AccessToken>> getAccessToken(@Query("uid") String uid);


    /**
     * 获取黑名单
     */
    @GET
    Observable<BlackList> getBlackList(@Url String url);

    /**
     * 获取access_token
     */
    @GET("data/saveIssueBox")
    Observable<DataBean> reportBlackDevInfo(@Query("mac") String mac,
                                            @Query("boxType") String boxType,
                                            @Query("sysVersion") String sysVersion,
                                            @Query("osVersion") String osVersion);


    /**
     * 退出挽留
     *
     * @param size
     * @return
     */
    @GET("apk/wanLiu")
    Observable<BaseBean<List<ExitBean>>> getExit(@Query("size") int size);

    /**
     * 升级版本号获取
     *
     * @param url
     * @return
     */
    @Headers("Content-Type: application/json")
    @GET
    Observable<JSONObject> getUpdateJson(@Url String url);

    /**
     * 获取皮肤资源
     *
     * @param url
     * @return
     */
    @GET
    Observable<JSONObject> getIcon(@Url String url);

    /**
     * 获取loading和弹窗
     *
     * @return
     */
    @GET("advert/getPopupInfo")
    Observable<LoadPageIcon> getLoadPage(@Query("phoneNumber") String phoneNumber);


    /**
     * 单点订购新
     *
     * @return
     */
    @POST("album/getAlbumByUserId")
    Observable<BaseBean<Object>> getOrdersForSingle(@Query("offset") int offset,
                                                    @Query("size") int size);


    /**
     * 单点订购新
     *
     * @return
     */
    @GET("data/insertTmpOrder")
    Observable<Object> insertTmpOrder(@Query("areaInfo") String areaInfo,
                                      @Query("businessId") int businessId,
                                      @Query("comment") String comment,
                                      @Query("custId") String custId,
                                      @Query("orderNo") String orderNo,
                                      @Query("orderType") int orderType,
                                      @Query("price") String price,
                                      @Query("productId") String productId);
}

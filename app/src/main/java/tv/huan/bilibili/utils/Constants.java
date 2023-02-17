package tv.huan.bilibili.utils;

/**
 * Created by $Knight on 2019/2/28.
 */
public class Constants {
    /**
     * RecyclerView Item 默认放大倍数
     */
//    public static final float DEFAULT_SCALE_SIZE = 1.1f;

    //    public static String AUTHSTATUS = "";//周期鉴权 0非1会员
    //    public static String ENDDATE = "";//周期会员到期天数缓存
    public static boolean ISBUYREGINOS = false;//缓存
    //    public static List<AuthBean> ISBUYlists = new ArrayList<>();//缓存
    public static String diversionFST = "";

    /**
     * PV上报id
     */
    public static String pvid = "";


    /**
     * 内部存储key
     */
    public static class Key {
        public static String VUID = "vuid";//用户ID vuid对应服务器huanId
        public static String VTOKEN = "vtoken";//vtoken
        public static String ACCESS_TOKEN = "ACCESS_TOKEN"; //ACCESS_TOKEN
        public static String ACCESS_TOKEN_EXPIREIN = "ACCESS_TOKEN_EXPIREIN"; //accessToken过期时间
        public static String SESS_KEY_EXPIREIN = "SESS_KEY_EXPIREIN"; //sessKey过期时间
        public static String PAY_WEBURL = "PAY_WEBURL"; //支付WEB地址
        public static String CDN_URL_TYPE = "cdn_url_type";//CDN url类型 zx或hw
        //        public static String AUTH_STATUS = "auth_status";//会员状态，0未支付未开通，1已支付已开通，2已支付正在开通
        public static String ROM_VER = "rom_ver";//rom版本
        public static String IPTVNO = "iptv_no";//账号
        public static String ICON_OPEN = "ICON_OPEN";//是否动态加载皮肤
        public static String POPUP_PIC = "POPUP_PIC";//弹窗图地址
        public static String LOADING_PIC = "LOADING_PIC";//loading图地址

//        public static String CAID = "CAID";//CA卡号
//        public static String VIP_ENDTIME = "vipendtime";

//        public static String CANPAY = "canpay";//canpay
//        public static String VIPSTRTIP = "vipstrtip";//isvipstr
//        public static String UNVIPSTRTIP = "unvipstrtip";//isnot
    }

    /**
     * 存放EventBus的一些事件类型
     */
    public static class Event {
        /**
         * 视频鉴权成功
         */
        public static final String VIDEO_AUTH_SUCCESS = "VIDEO_AUTH_SUCCESS";
        /**
         * 视频鉴权失败
         */
        public static final String VIDEO_AUTH_FAIL = "VIDEO_AUTH_FAIL";
        /**
         * 视频界面关闭
         */
        public static final String PLAYER_PAGE_FINISH = "PLAYER_PAGE_FINISH";
    }

//    /**
//     * 影片专辑类型
//     */
//    public static class AlbumType {
//        public static final int FILM = 1; //电影
//        public static final int TELEPLAY = 2; //电视剧
//        public static final int ANIME = 3; //动漫
//        public static final int SPORTS = 4; //体育
//        public static final int RECREATION = 5; //娱乐
//        public static final int GAME = 6; //游戏
//        public static final int DOCUMENTARY = 9; //纪录片
//        public static final int VARIETY = 10; //综艺
//        public static final int MUSIC = 22; //音乐
//        public static final int NEWS = 23; //新闻
//        public static final int FASHION = 25; //时尚
//        public static final int TOURISM = 26; //旅游
//        public static final int EDUCATION = 27; //教育
//        public static final int KEJI = 28; //科技
//        public static final int CAR = 29; //汽车
//        public static final int LIFE = 31; //生活
//        public static final int COOPERATION = 50; //合作
//        public static final int MUYING = 60; //母婴
//        public static final int CHILDREN = 106; //少儿
//    }

    /**
     * 河北影片专辑类型
     */
    public static class AlbumType {

        /**
         * 河北广电
         */
        public static final int FILM = 300; //电影
        public static final int TELEPLAY = 301; //电视剧
        public static final int CHILDREN = 302; //少儿
        public static final int ANIME = 303; //动漫
        public static final int VARIETY = 304; //综艺
        public static final int DOCUMENTARY = 305; //纪录片
        public static final int EDUCATION = 306; //教育
        public static final int SPORTSLIFE = 307; //健身
        public static final int SPORTS = 308; //体育
        public static final int LIFE = 309; //茶文化
        public static final int GETFISH = 310; //垂钓

        /**
         * 内蒙广电
         */
//        public static final int FILM = 1; //电影
//        public static final int TELEPLAY = 2; //电视剧
//        public static final int CHILDREN = 3; //少儿
//        public static final int EDUCATION = 4; //教育
//        public static final int SPORTS = 5; //体育
//        public static final int GAME = 6; //游戏
//        public static final int DOCUMENTARY = 9; //纪录片
//        public static final int VARIETY = 10; //综艺
//        public static final int MUSIC = 22; //音乐
//        public static final int NEWS = 23; //新闻
//        public static final int FASHION = 25; //时尚
//        public static final int TOURISM = 26; //旅游
//        public static final int KEJI = 28; //科技
//        public static final int CAR = 29; //汽车
//        public static final int LIFE = 31; //生活
//        public static final int COOPERATION = 50; //合作
//        public static final int MUYING = 60; //母婴
//        public static final int ANIME = -1; //母婴
    }

    /**
     * 付费类型
     */
    public static class PayType {
        public static final int FREE = 8; //电影
        public static final int MOONTIMES = 5; //包月单点
        public static final int VIPPAY = 4; //会员用券
        public static final int MOON = 6; //仅限包月
        public static final int TIMES = 7; //单片付费
    }

    /**
     * 页面之间传递参数的静态常量
     */
    public static final class PageParams {
        public static final String specialId = "specialId"; //专题页面专题ID
    }

    /**
     * @param types -> 栏目类型转换
     * @return
     */
//    public static String getType(int types) {
//        String type = "其他";
//        switch (types) {
//            case 1:
//                type = "电影";
//                break;
//            case 2:
//                type = "电视剧";
//                break;
//            case 3:
//                type = "动漫";
//                break;
//            case 4:
//                type = "体育";
//                break;
//            case 5:
//                type = "娱乐";
//                break;
//            case 6:
//                type = "游戏";
//                break;
//            case 9:
//                type = "纪录片";
//                break;
//            case 10:
//                type = "综艺";
//                break;
//            case 22:
//                type = "音乐";
//                break;
//            case 23:
//                type = "新闻";
//                break;
//            case 25:
//                type = "时尚";
//                break;
//            case 26:
//                type = "旅游";
//                break;
//            case 27:
//                type = "教育";
//                break;
//            case 28:
//                type = "科技";
//                break;
//            case 29:
//                type = "汽车";
//                break;
//            case 31:
//                type = "生活";
//                break;
//            case 50:
//                type = "合作";
//                break;
//            case 60:
//                type = "母婴";
//                break;
//            case 106:
//                type = "少儿";
//                break;
//        }
//        return type;
//    }


//    public static final int FILM = 300; //电影
//    public static final int TELEPLAY = 301; //电视剧
//    public static final int CHILDREN = 302; //少儿
//    public static final int ANIME = 303; //动漫
//    public static final int VARIETY = 304; //综艺
//    public static final int DOCUMENTARY = 305; //纪录片
//    public static final int EDUCATION = 306; //教育
//    public static final int SPORTSLIFE = 307; //健身
//    public static final int SPORTS = 308; //体育
//    public static final int LIFE = 309; //茶文化
//    public static final int GETFISH = 310; //垂钓
    public static String getType(int types) {
        String type = "其他";
        switch (types) {
            case 300:
                type = "电影";
                break;
            case 301:
                type = "电视剧";
                break;
            case 302:
                type = "少儿";
                break;
            case 303:
                type = "动漫";
                break;
            case 304:
                type = "综艺";
                break;
            case 305:
                type = "纪录片";
                break;
            case 306:
                type = "教育";
                break;
            case 307:
                type = "健身";
                break;
            case 308:
                type = "体育";
                break;
            case 309:
                type = "茶文化";
                break;
            case 310:
                type = "垂钓";
                break;

        }
        return type;
    }

    public static class huashiParam {
        public static String stbmac = DeviceUtils.getMacAddress();
        public static String epgAddress = "";
        public static String cdnAddress = "";
        public static String cdnType = "";
        public static String userID = "";
//        public static String sPwd                   ="";
//        public static String account                ="";
//        public static String accountPassword        ="";
//        public static String stbSn                  ="";
//        public static String cmsurl                 ="";
//        public static String hdcrurl                ="";
//        public static String cdnAddressBack         ="";
//        public static String platformurl            ="";
//        public static String cmsurlbackup           ="";
//        public static String platformurlbackup      ="";
//        public static String tvid                   ="";
//        public static String modelName              ="";
//        public static String firmwareVersion        ="";
//        public static String manufacturer           ="";
//        public static String platUrlZte             ="";
//        public static String platUrlZteBack         ="";
//        public static String cdnDispatchUrl         ="";
//        public static String cdnDispatchUrlBack     ="";
//        public static String launcher               ="";
//        public static String ottTerminalType        ="";
//        public static String activeUserType         ="";

        public static String OTTUserToken = "OTTUserToken";
        public static String AuthCode = "AuthCode";
        public static String IsThirdPartyCDN = "";
        public static String CDNTPDomainName = "";
    }


    // sp KEY: LKoiGH09GFnmghfs
    public static final String CP_ID = "HSTV"; //河北第三方CP的ID
//    public static String USER_ID; //用户ID
    public static String SPKEY = "LKoiGH09GFnmghfs"; //
    public static String PRODUCT_ID = "hbgd_hstv"; //周期产品
    public static String PRODUCT_SIN_ID = "hbgd_hstvdd"; //单点产品hbgd_hstvdd
    //    public static final String PAY_BROADCAST_ACTION = "com.starcor.aaa.app.authorities.pay.hstv"; //支付广播
    public static final String PAY_BROADCAST_ACTION = "com.starcor.aaa.app.authorities.pay.result"; //支付广播


    //业务动作类型MEDIA_AUTH：媒资鉴权；MEDIA_AUTH_AND_URL：媒资鉴权并获取播放地址；PRODUCT_AUTH：产品包鉴权
    public static final String MEDIA_AUTH = "MEDIA_AUTH";
    public static final String MEDIA_AUTH_AND_URL = "MEDIA_AUTH_AND_URL";
    public static final String PRODUCT_AUTH = "PRODUCT_AUTH";


//    public static int isSuperVip = 0;//超级用户白名单
}



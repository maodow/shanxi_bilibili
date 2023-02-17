package tv.huan.bilibili.utils;

import android.os.Build;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import tv.huan.bilibili.BuildConfig;

/**
 * Create by Richard
 * 2019-07-31 13:39
 */
public class ReportUtils {

    /**
     * 首次打开app。 如果rom版本变化，则重新上报
     *
     * @param mac x
     */
    public JSONObject appActivation(String mac, int prodId) {
        JSONObject object = create("app_activation", prodId);
        try {
            object.put("mac", mac);
            object.put("ip", AppUtils.getEtherNetIP());
            object.put("ver", BuildConfig.VERSION_NAME);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 进入APP首页
     *
     * @param channel
     */
    public JSONObject homePageEnter(int prodId, int channel) {
        JSONObject object = create("home_page_enter", prodId);
        try {
            object.put("ver", BuildConfig.VERSION_NAME);
            object.put("channel", channel);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 进入频道页并加载数据完成
     */
    public JSONObject channelLoadFinished(int prodId, int channelId, String channelName) {
        JSONObject object = create("channel_load_finished", prodId);
        try {
            object.put("channel_id", channelId);
            object.put("channel_name", channelName);
            object.put("channel_type", "频道");
        } catch (Exception e) {
        }
        return object;
    }

//    /**
//     * 频道下子分类切换量
//     */
//    public void channelTabClicked(int channelId, String channelName, String subType) {
//        try {
//            JSONObject jsonObject = getBaseObject("channel_tab_clicked");
//            jsonObject.put("channel_id", channelId);
//            jsonObject.put("channel_name", channelName);
//            jsonObject.put("subtype", subType);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 从频道分类点击进入详情页
//     */
//    public void channelEnterDetail(int channelId, String channelName, String subType, String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("channel_enter_detail");
//            jsonObject.put("channel_id", channelId);
//            jsonObject.put("channel_name", channelName);
//            jsonObject.put("subtype", subType);
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 进入专题页并加载数据完成
//     */
//    public void topicLoadFinished(String sceneId) {
//        try {
//            JSONObject jsonObject = getBaseObject("topic_load_finished");
//            jsonObject.put("scene_id", sceneId);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 进入某专题列表页并加载数据完成
//     *
//     * @param sceneId   x
//     * @param topicId   x
//     * @param topicName x
//     */
//    public void topicSubtypeClicked(String sceneId, int topicId, String topicName) {
//        try {
//            JSONObject jsonObject = getBaseObject("topic_subtype_clicked");
//            jsonObject.put("scene_id", sceneId);
//            jsonObject.put("topic_id", topicId);
//            jsonObject.put("topic_name", topicName);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 从专题列表页点击进入详情页
//     *
//     * @param sceneId   x
//     * @param topicId   x
//     * @param topicName x
//     * @param cid       x
//     */
//    public void topicEnterDetail(String sceneId, int topicId, String topicName, String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("topic_enter_detail");
//            jsonObject.put("scene_id", sceneId);
//            jsonObject.put("topic_id", topicId);
//            jsonObject.put("topic_name", topicName);
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 点击搜索按钮
//     *
//     * @param keyword x
//     * @param num     x
//     */
//    public void searchResultItemNum(String keyword, int num) {
//        try {
//            JSONObject jsonObject = getBaseObject("search_result_item_num");
//            jsonObject.put("keyword", keyword);
//            jsonObject.put("num", num);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//
//            JSONObject json = new JSONObject();
//            json.put("keyword", keyword);
//            json.put("num", num);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 点击搜索结果
//     *
//     * @param keyword x
//     * @param cid     x
//     */
//    public void searchResultItemClicked(String keyword, String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("search_result_item_clicked");
//            jsonObject.put("keyword", keyword);
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//
//            JSONObject json = new JSONObject();
//            json.put("keyword", keyword);
//            json.put("cid", cid);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 进入播放历史页并加载数据完成
//     */
//    public void myHistoryListShow() {
//        try {
//            JSONObject jsonObject = getBaseObject("my_history_list_show");
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 点击播放历史进入详情页
//     *
//     * @param cid x
//     */
//    public void myHistoryListClicked(String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("my_history_list_clicked");
//            jsonObject.put("tab_name", "播放记录");
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//
//            JSONObject json = new JSONObject();
//            json.put("cid", cid);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 进入详情页并加载数据完成
//     *
//     * @param cid     x
//     * @param prePage x
//     * @param preInfo x
//     */
//    public void detailLoadFinished(String cid, String prePage, String preInfo) {
//        try {
//            JSONObject jsonObject = getBaseObject("detail_load_finished");
//            jsonObject.put("cid", cid);
//            jsonObject.put("pre_page", prePage);
//            jsonObject.put("pre_info", preInfo);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//
//            JSONObject json = new JSONObject();
//            json.put("cid", cid);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 点击播放按钮
//     *
//     * @param cid x
//     */
//    public void detailPlayClicked(String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("detail_play_clicked");
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 详情页加载成功并且选集按钮露出
//     *
//     * @param cid x
//     */
//    public void detailSelectionsButtonShow(String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("detail_selections_button_show");
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 点击某选集中某一项并成功跳转播放页
//     *
//     * @param cid x
//     */
//    public void detailSelectionsItemClicked(String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("detail_selections_item_clicked");
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 相关推荐结果加载完成
//     *
//     * @param cid x
//     */
//    public void detailRecommendShow(String cid) {
//        try {
//            JSONObject jsonObject = getBaseObject("detail_recommend_show");
//            jsonObject.put("cid", cid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 点击相关推荐内某一项并成功跳转
//     *
//     * @param cid x
//     */
//    public void detailRecommendItemClicked(String cid, String toCid) {
//        try {
//            JSONObject jsonObject = getBaseObject("detail_recommend_item_clicked");
//            jsonObject.put("cid", cid);
//            jsonObject.put("to_cid", toCid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 订购成功
//     */
//    public void orderSuccess(String cid, String vid) {
//        try {
//            JSONObject jsonObject = getBaseObject("order_success");
//            jsonObject.put("cid", cid);
//            jsonObject.put("vid", vid);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 播放记录
//     */
//    public void playVodStop(String startTime, String endTime, long playTime, String cid, String vid) {
//        try {
//            JSONObject jsonObject = getBaseObject("play_vod_stop");
////        jsonObject.put("start_time", startTime);
////        jsonObject.put("end_time", endTime);
//            jsonObject.put("play_time", playTime);
//            jsonObject.put("cid", cid);
//            jsonObject.put("vid", vid);
//            jsonObject.put("ip", AppUtils.getEtherNetIP());
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 退出
//     */
//    public void exitApp(long useTime) {
//        try {
//            JSONObject jsonObject = getBaseObject("app_exit");
//            jsonObject.put("use_time", useTime);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * loading 弹窗
//     * type 1为loading图  2为弹窗图
//     * subtype 1为弹出  2为点击
//     */
//    public void loadOrPopup(String type, String subtype) {
//        try {
//            JSONObject jsonObject = getBaseObject("popup_clicked");
//            jsonObject.put("type", type);
//            jsonObject.put("subtype", subtype);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**************/
//
//    /**
//     * 退出挽留，点击跳转
//     *
//     * @param recommend_id
//     * @param recommend_name
//     */
//    public void exitRetentionClick(String recommend_id, String recommend_name) {
//        try {
//            JSONObject jsonObject = getBaseObject("exit_retention_click");
//            jsonObject.put("user_id", PublicGetClientUuid.getCa());
//            jsonObject.put("model", Build.MODEL);
//            jsonObject.put("recommend_id", recommend_id);
//            jsonObject.put("recommend_name", recommend_name);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 退出挽留弹窗露出
//     *
//     * @param recommend_id
//     */
//    public void exitRetentionExposure(String recommend_id) {
//        try {
//            JSONObject jsonObject = getBaseObject("exit_retention_exposure");
//            jsonObject.put("user_id", PublicGetClientUuid.getCa());
//            jsonObject.put("model", Build.MODEL);
//            jsonObject.put("recommend_id", recommend_id);
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 播放记录
//     */
//    public void playVodStop(String startTime, String endTime, String playTime, String cid, String vid) {
//        try {
//            JSONObject jsonObject = getBaseObject("play_vod_stop");
//            jsonObject.put("start_time", startTime);
//            jsonObject.put("end_time", endTime);
//            jsonObject.put("play_time", playTime);
//            jsonObject.put("cid", cid);
//            jsonObject.put("vid", vid);
//            jsonObject.put("ip", AppUtils.getEtherNetIP());
//            jsonObject.put("area_code", AppUtils.getEtherNetIP());
//            jsonObject.put("user_id", PublicGetClientUuid.getCa());
//            jsonObject.put("model", Build.MODEL);
//            jsonObject.put("type", "1");
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 订购失败上报
//     * {"action":"order_fail","user_id":"xxx","model":"","ver":"1","business_id":2}
//     */
//    public void orderFail() {
//        try {
//            JSONObject jsonObject = getBaseObject("order_fail");
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("user_id", PublicGetClientUuid.getCa());
//            jsonObject.put("model", Build.MODEL);
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     *
//     */
//    public void order_trigger() {
//        try {
//            JSONObject jsonObject = getBaseObject("order_fail");
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("user_id", PublicGetClientUuid.getCa());
//            jsonObject.put("model", Build.MODEL);
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 触发订购上报
//     * {"action":"order_trigger","user_id":"xxx","model":"","ver":"1","cid":"xxx","vid":"xxx","area_info":"xxxx","business_id":2,"result":"0/1"}   0代表触发订购 ，1带表订购成功
//     */
//    public void orderTriggerBeigin(String cid, String vid, String order_enter) {
//        try {
//            StringBuilder builder = new StringBuilder();
//            builder.append("{\"soft_version\":\"");
//            String sw_ver = MySystemProperties.get("persist.sys.hwconfig.sw_ver");
//            builder.append(sw_ver);
//            builder.append("\",\"card_id\":\"");
//            String ca = PublicGetClientUuid.getCa(HuanApp.getContext());
//            builder.append(ca);
//            builder.append("\"}");
//
//            JSONObject jsonObject = getBaseObject("order_trigger");
//            jsonObject.put("result", "0");
//            jsonObject.put("cid", cid);
//            jsonObject.put("vid", vid);
//            jsonObject.put("order_enter", order_enter);
//            jsonObject.put("area_info", builder.toString());
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }
//
//    /**
//     * 触发订购上报
//     * {"action":"order_trigger","user_id":"xxx","model":"","ver":"1","cid":"xxx","vid":"xxx","area_info":"xxxx","business_id":2,"result":"0/1"}   0代表触发订购 ，1带表订购成功
//     */
//    public void orderTriggerSucc(String cid, String vid, String order_enter) {
//        try {
//            StringBuilder builder = new StringBuilder();
//            builder.append("{\"soft_version\":\"");
//            String sw_ver = MySystemProperties.get("persist.sys.hwconfig.sw_ver");
//            builder.append(sw_ver);
//            builder.append("\",\"card_id\":\"");
//            String ca = PublicGetClientUuid.getCa(HuanApp.getContext());
//            builder.append(ca);
//            builder.append("\"}");
//
//            JSONObject jsonObject = getBaseObject("order_trigger");
//            jsonObject.put("result", "1");
//            jsonObject.put("cid", cid);
//            jsonObject.put("vid", vid);
//            jsonObject.put("order_enter", order_enter);
//            jsonObject.put("area_info", builder.toString());
//            jsonObject.put("business_id", HuanApp.getProd(null));
//            jsonObject.put("ver", BuildConfig.VERSION_NAME);
//            actionReport(jsonObject);
//        } catch (Exception e) {
//        }
//    }

    /**********/

    private final JSONObject create(@NonNull String action, @NonNull int prodId) {
        JSONObject object = new JSONObject();
        try {
            object.put("action", action);
            object.put("user_id", BoxUtil.getCa());
            object.put("model", Build.MODEL);
            object.put("ver", BuildConfig.VERSION_NAME);
            object.put("business_id", prodId);
        } catch (Exception e) {
        }
        return object;
    }
}
package tv.huan.bilibili.utils;

import android.os.Build;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import tv.huan.bilibili.BuildConfig;

public final class ReportUtils {

    /**
     * 详情页加载成功并且选集按钮露出
     *
     * @param cid x
     */
    public static JSONObject detailSelectionsButtonShow(int prodId, String cid) {
        JSONObject object = create("detail_selections_button_show", prodId);
        try {
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 点击某选集中某一项并成功跳转播放页
     *
     * @param cid x
     */
    public static JSONObject detailSelectionsItemClicked(int prodId, String cid) {
        JSONObject object = create("detail_selections_item_clicked", prodId);
        try {
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 相关推荐结果加载完成
     *
     * @param cid x
     */
    public static JSONObject detailRecommendShow(int prodId, String cid) {
        JSONObject object = create("detail_recommend_show", prodId);
        try {
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 首次打开app。 如果rom版本变化，则重新上报
     *
     * @param mac x
     */
    public static JSONObject appActivation(String mac, int prodId) {
        JSONObject object = create("app_activation", prodId);
        try {
            object.put("mac", mac);
            object.put("ip", BoxUtil.getEtherNetIP());
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
    public static JSONObject homePageEnter(int prodId, int channel) {
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
    public static JSONObject channelLoadFinished(int prodId, int channelId, String channelName) {
        JSONObject object = create("channel_load_finished", prodId);
        try {
            object.put("channel_id", channelId);
            object.put("channel_name", channelName);
            object.put("channel_type", "频道");
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 点击搜索按钮
     *
     * @param keyword x
     * @param num     x
     */
    public static JSONObject searchResultItemNum(int prodId, int num, String keyword) {
        JSONObject object = create("search_result_item_num", prodId);
        try {
            object.put("keyword", keyword);
            object.put("num", num);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 点击搜索结果
     *
     * @param keyword x
     * @param cid     x
     */
    public static JSONObject searchResultItemClicked(int prodId, String keyword, String cid) {
        JSONObject object = create("search_result_item_clicked", prodId);
        try {
            object.put("keyword", keyword);
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 进入详情页并加载数据完成
     */
    public static JSONObject detailLoadFinished(int prodId, String cid) {
        JSONObject object = create("detail_load_finished", prodId);
        try {
            object.put("pre_page", "");
            object.put("pre_info", "");
            if (null == cid) {
                cid = "";
            }
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 退出挽留弹窗露出
     */
    public static JSONObject exitRetentionExposure(int prodId, String cid) {
        JSONObject object = create("exit_retention_exposure", prodId);
        try {
            object.put("recommend_id", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 退出挽留，点击跳转
     */
    public static JSONObject exitRetentionClick(int prodId, String cid, String name) {
        JSONObject object = create("exit_retention_click", prodId);
        try {
            object.put("recommend_id", cid);
            object.put("recommend_name", name);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 退出
     */
    public static JSONObject appExit(int prodId) {
        JSONObject object = create("app_exit", prodId);
        try {
            object.put("use_time", System.currentTimeMillis());
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 进入专题页并加载数据完成
     */
    public static JSONObject topicLoadFinished(int prodId, int sceneId) {
        JSONObject object = create("topic_load_finished", prodId);
        try {
            object.put("scene_id", sceneId);
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
//
//
//
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


    /**
     * 点击相关推荐内某一项并成功跳转
     */
    public static JSONObject detailRecommendItemClicked(int prodId, String cid, String toCid) {
        JSONObject object = create("detail_recommend_item_clicked", prodId);
        try {
            object.put("cid", cid);
            object.put("to_cid", toCid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 点击播放按钮
     *
     * @param prodId
     * @param cid
     * @return
     */
    public static JSONObject detailPlayClicked(int prodId, int cid) {
        JSONObject object = create("detail_play_clicked", prodId);
        try {
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 播放记录
     */
    public static JSONObject playVodStop(int prodId, String cid, String vid, long start, long end) {
        JSONObject object = create("play_vod_stop", prodId);
        try {
            String startTime = TimeUtils.millis2String(start).replace(":", "").replace("-", "").trim();
            object.put("start_time", startTime);
            String endTime = TimeUtils.millis2String(end).replace(":", "").replace("-", "").trim();
            object.put("end_time", endTime);
            String playTime = String.valueOf((end - start) / 1000);
            object.put("play_time", playTime);
            object.put("cid", cid);
            object.put("vid", vid);
        } catch (Exception e) {
        }
        return object;
    }

    /**
     * 从专题列表页点击进入详情页
     *
     * @param sceneId   x
     * @param topicId   x
     * @param topicName x
     * @param cid       x
     */
    public static JSONObject topicEnterDetail(int prodId, String cid, int sceneId, int topicId, String topicName) {
        JSONObject object = create("topic_enter_detail", prodId);
        try {
            object.put("scene_id", sceneId);
            object.put("topic_id", topicId);
            object.put("topic_name", topicName);
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    // 进入某专题列表页并加载数据完成
    public static JSONObject topicSubtypeClicked(int prodId, String cid, String sceneId, int topicId, String topicName) {
        JSONObject object = create("topic_subtype_clicked", prodId);
        try {
            object.put("scene_id", sceneId);
            object.put("topic_id", topicId);
            object.put("topic_name", topicName);
            object.put("cid", cid);
        } catch (Exception e) {
        }
        return object;
    }

    /**********/

    private static JSONObject create(@NonNull String action, @NonNull int prodId) {
        JSONObject object = new JSONObject();
        try {
            object.put("action", action);
            object.put("user_id", BoxUtil.getCa());
            object.put("model", Build.MODEL);
            object.put("ver", BuildConfig.VERSION_NAME);
            object.put("business_id", prodId);
            object.put("ip", BoxUtil.getEtherNetIP());
            object.put("area_code", BoxUtil.getEtherNetIP());
        } catch (Exception e) {
        }
        return object;
    }
}
package tv.huan.bilibili.ui.detail.template;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.leanback.widget.Presenter;
import org.json.JSONObject;
import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.config.start.StartBuilder;
import lib.kalu.mediaplayer.core.component.ComponentPause;
import lib.kalu.mediaplayer.listener.OnPlayerChangeListener;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.MediaBean;
import tv.huan.bilibili.listener.OnStatusChangeListener;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.AuthUtils;
import tv.huan.bilibili.utils.BoxUtil;
import tv.huan.bilibili.utils.DevicesUtils;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.common.CommonPicView;
import tv.huan.bilibili.widget.player.PlayerViewForDetail;
import tv.huan.bilibili.widget.player.component.PlayerComponentInit;
import tv.huan.bilibili.widget.player.component.PlayerComponentVip;

public final class DetailTemplatePlayer extends Presenter {

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        LogUtil.log("DetailTemplatePlayer => onViewAttachedToWindow");
        stopFloat(holder.view);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        LogUtil.log("DetailTemplatePlayer => onViewDetachedFromWindow");
        startFloat(holder.view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
//        LogUtil.log("DetailTemplatePlayer => onCreateViewHolder");
        Context context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_detail_item_player, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
//        LogUtil.log("DetailTemplatePlayer => onUnbindViewHolder");
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
//        LogUtil.log("DetailTemplatePlayer => onBindViewHolder");

        try {
            int id = viewHolder.view.getId();
            Object tag = viewHolder.view.getTag(id);
            if (null != tag)
                throw new Exception();
            viewHolder.view.setTag(id, 1);
            addListener(viewHolder.view, o);
        } catch (Exception e) {
        }

        // 默认焦点
        try {
            AuthUtils.getInstance().doAuth(new OnStatusChangeListener() {
                @Override
                public void onPass() {
                    if (BuildConfig.HUAN_CHECK_VIP) {
                        viewHolder.view.findViewById(R.id.detail_player_item_vip).setVisibility(View.GONE);
                        viewHolder.view.findViewById(R.id.detail_player_item_full).requestFocus();
                    }
                }

                @Override
                public void onFail() {
                    if (BuildConfig.HUAN_CHECK_VIP) {
                        viewHolder.view.findViewById(R.id.detail_player_item_vip).setVisibility(View.VISIBLE);
                        viewHolder.view.findViewById(R.id.detail_player_item_vip).requestFocus();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 播放器信息
        try {
            PlayerViewForDetail playerView = viewHolder.view.findViewById(R.id.detail_player_item_video);
            PlayerComponentInit component = playerView.findComponent(PlayerComponentInit.class);
            component.setData((MediaBean) o);
            component.show();
        } catch (Exception e) {
        }

        // vip
        try {
            PlayerViewForDetail playerView = viewHolder.view.findViewById(R.id.detail_player_item_video);
            PlayerComponentVip component = playerView.findComponent(PlayerComponentVip.class);
            component.setData((MediaBean) o);
        } catch (Exception e) {
        }

        try {
            TextView textView = viewHolder.view.findViewById(R.id.detail_player_item_favor);
            textView.setSelected(((MediaBean) o).isTempFavor());
            textView.setText(viewHolder.view.getResources().getString(((MediaBean) o).isTempFavor() ? R.string.detail_favor_yes : R.string.detail_favor_no));
            JSONObject object = new JSONObject();
            object.put("cid", ((MediaBean) o).getCid());
            object.put("recClassId", ((MediaBean) o).getRecClassId());
            textView.setTag(R.id.detail_player_item_favor, object);

        } catch (Exception e) {
        }
        try {
            TextView textView = viewHolder.view.findViewById(R.id.detail_player_item_tag);
            textView.setText(((MediaBean) o).getTempTag());
        } catch (Exception e) {
        }
        try {
            TextView textView = viewHolder.view.findViewById(R.id.detail_player_item_title);
            textView.setText(((MediaBean) o).getTempTitle());
        } catch (Exception e) {
        }
        try {
            PlayerViewForDetail playerView = viewHolder.view.findViewById(R.id.detail_player_item_video);
            ComponentPause component = playerView.findComponent(ComponentPause.class);
            component.setComponentTitleText(((MediaBean) o).getTempTitle());
        } catch (Exception e) {
        }
        try {
            TextView textView = viewHolder.view.findViewById(R.id.detail_player_item_info);
            textView.setText(((MediaBean) o).getTemoInfo());
        } catch (Exception e) {
        }
        try {
            LinearLayout linearLayout = viewHolder.view.findViewById(R.id.detail_player_item_pic);
            int childCount = linearLayout.getChildCount();
            if (childCount > 1)
                throw new Exception();
            for (int i = 0; i < childCount - 1; i++) {
                linearLayout.removeViewAt(i);
            }
            int length;
            try {
                length = ((MediaBean) o).getTempPicList().length;
            } catch (Exception e) {
                length = 1;
            }
            Context context = linearLayout.getContext();
            int height = context.getResources().getDimensionPixelOffset(R.dimen.dp_16);
            for (int i = 0; i < length; i++) {
                CommonPicView picView = new CommonPicView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
                int margin = linearLayout.getContext().getResources().getDimensionPixelOffset(i == length - 1 ? R.dimen.dp_8 : R.dimen.dp_4);
                params.setMargins(0, 0, margin, 0);
                picView.setLayoutParams(params);

                //陕西, 此处VIP小图标，及图标后的文本标签，需apk自行判断添加
                boolean isFree = ((MediaBean) o).getTempPayType() == 0; //媒资收费或免费
                GlideUtils.loadDrawableResources(picView, isFree ? 0 : R.drawable.ic_huiyuan);
                linearLayout.addView(picView, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFavor(View view, boolean status) {
        try {
            TextView textView = view.findViewById(R.id.detail_player_item_favor);
            textView.setText(view.getResources().getString(status ? R.string.detail_favor_yes : R.string.detail_favor_no));
            textView.setSelected(status);
        } catch (Exception e) {
        }
    }

    public void updatePosition(View view, @NonNull MediaBean data) {
        try {
            PlayerViewForDetail playerView = view.findViewById(R.id.detail_player_item_video);
            PlayerComponentInit componentInit = playerView.findComponent(PlayerComponentInit.class);
            componentInit.updatePosition(data.getEpisodeIndex());
            componentInit.show();

            //暂停时显示的集数
            ComponentPause component = playerView.findComponent(ComponentPause.class);
            String epIndex = view.getResources().getString(R.string.detail_playing_position, data.getEpisodeIndex() + 1);
            TextView textView = componentInit.findViewById(R.id.detail_player_item_data_name);
            String mediaTitle = textView.getText().toString();
            component.setComponentTitleText(mediaTitle.concat(epIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public boolean checkPlayerPlayingPosition(View view, int position) {
//        try {
//            PlayerView playerView = view.findViewById(R.id.detail_player_item_video);
//            String url = playerView.getUrl();
//            if (null == url || url.length() <= 0)
//                return false;
//            PlayerComponentInit componentInit = playerView.findComponent(PlayerComponentInit.class);
//            return componentInit.checkPlayerPlayingPosition(position);
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public void checkVipStatus(View view, MediaBean data, @NonNull long seek, boolean isFromUser) {
        LogUtil.log("DetailTemplatePlayer => checkVipStatus => isFromUser = " + isFromUser);
        try {
            LogUtil.log("DetailTemplatePlayer => checkAccount");
            AuthUtils.getInstance().doAuth(new OnStatusChangeListener() {
                @Override
                public void onPass() {
                    startHuawei(view, data, seek);
                }

                @Override
                public void onFail() {
                    showVip(view);
                    jumpVip(view);
                }
            });
        } catch (Exception e) {
            LogUtil.log("DetailTemplatePlayer => checkAccount => " + e.getMessage());
        }
    }

    private void showVip(View view) {
        try {
            PlayerViewForDetail playerView = view.findViewById(R.id.detail_player_item_video);
            PlayerComponentVip component = playerView.findComponent(PlayerComponentVip.class);
            component.show();
        } catch (Exception e) {
            LogUtil.log("DetailTemplatePlayer => showVip => " + e.getMessage());
        }
    }

    private void jumpVip(View view) {
        try {
            Activity activity = WrapperUtil.getWrapperActivity(view.getContext());
            if (null == activity)
                throw new Exception("activity error: null");
            if (!(activity instanceof DetailActivity))
                throw new Exception("activity error: " + activity);
            ((DetailActivity) activity).jumpVip();
        } catch (Exception e) {
            LogUtil.log("DetailTemplatePlayer => jumpVip => " + e.getMessage());
        }
    }

    public void startHuawei(View view, MediaBean data, @NonNull long seek) {
        if (BuildConfig.HUAN_HUAWEI_AUTH) {
            try {
                Activity activity = WrapperUtil.getWrapperActivity(view.getContext());
                if (null == activity)
                    throw new Exception("activity error: null");
                if (!(activity instanceof DetailActivity))
                    throw new Exception("activity error: " + activity);
                String platformType = DevicesUtils.INSTANCE.getPlatform();
                String playCode = "0".equals(platformType) ? data.getHwMovieCode() : data.getMovieCode();
                ((DetailActivity) activity).huaweiAuth(playCode, seek);
            } catch (Exception e) {
                LogUtil.log("DetailTemplatePlayer => startHuawei => " + e.getMessage());
            }
        } else {
            try {
                String url = BoxUtil.getTestVideoUrl();
                startPlayer(view, url, seek);
            } catch (Exception e) {
                LogUtil.log("DetailTemplatePlayer => startHuawei => " + e.getMessage());
            }
        }
    }

    public long getPosition(View view) {
        try {
            PlayerViewForDetail playerView = view.findViewById(R.id.detail_player_item_video);
            return playerView.getPosition();
        } catch (Exception e) {
            LogUtil.log("DetailTemplatePlayer => getPosition => " + e.getMessage());
            return 0;
        }
    }

    public long getDuration(View view) {
        try {
            PlayerViewForDetail playerView = view.findViewById(R.id.detail_player_item_video);
            return playerView.getDuration();
        } catch (Exception e) {
            LogUtil.log("DetailTemplatePlayer => getDuration => " + e.getMessage());
            return 0;
        }
    }

    public void startPlayer(View view, String s, long seek) {
        try {
            LogUtil.log("DetailTemplatePlayer => startPlayer => thread = " + Thread.currentThread().getName() + ", seek = " + seek + ", s = " + s);
            StartBuilder.Builder builder = new StartBuilder.Builder();
            builder.setLoop(false);
            builder.setSeek(seek);
            PlayerViewForDetail playerView = view.findViewById(R.id.detail_player_item_video);
            playerView.start(builder.build(), s);
        } catch (Exception e) {
            LogUtil.log("DetailTemplatePlayer => startPlayer => " + e.getMessage());
        }
    }

    public void stopFull(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view.getParent().getParent().getParent();
            PlayerViewForDetail playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.stopFull();
        } catch (Exception e) {
        }
    }

    public void startFull(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view.getParent().getParent().getParent();
            PlayerViewForDetail playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.startFull();
        } catch (Exception e) {
        }
    }

    private void stopFloat(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view.getParent().getParent().getParent();
            PlayerViewForDetail playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.stopFloat();
        } catch (Exception e) {
        }
    }

    private void startFloat(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view.getParent().getParent().getParent();
            PlayerViewForDetail playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.startFloat();
        } catch (Exception e) {
        }
    }

    private void addListener(View viewGroup, Object o) {

        // 简介
        try {

            viewGroup.findViewById(R.id.detail_player_item_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = WrapperUtil.getWrapperActivity(v.getContext());
                    if (null != activity && activity instanceof DetailActivity) {
                        ViewGroup group = (ViewGroup) v.getParent();
                        TextView v1 = group.findViewById(R.id.detail_player_item_title);
                        String s1 = v1.getText().toString();
                        TextView v2 = group.findViewById(R.id.detail_player_item_tag);
                        String s2 = v2.getText().toString();
                        TextView v3 = group.findViewById(R.id.detail_player_item_info);
                        String s3 = v3.getText().toString();
                        ((DetailActivity) activity).showDialog(s1, s2, s3);
                    }
                }
            });
        } catch (Exception e) {
        }

        // 会员
        try {
            viewGroup.findViewById(R.id.detail_player_item_vip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = WrapperUtil.getWrapperActivity(v.getContext());
                    LogUtil.log("DetailTemplatePlayer => createViewHolder => onClick => activity = " + activity);
                    if (null != activity && activity instanceof DetailActivity) {
                        ((DetailActivity) activity).jumpVip();
                    }
                }
            });
        } catch (Exception e) {
        }

        // 收藏
        try {
            viewGroup.findViewById(R.id.detail_player_item_favor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = WrapperUtil.getWrapperActivity(v.getContext());
                    if (null != activity && activity instanceof DetailActivity) {
                        try {
                            JSONObject object = (JSONObject) v.getTag(R.id.detail_player_item_favor);
                            String cid = object.optString("cid", "");
                            String recClassId = object.optString("recClassId", "");
                            String text = ((TextView) v).getText().toString();
                            String s = v.getResources().getString(R.string.detail_favor_no);
                            if (s.equalsIgnoreCase(text)) {
                                ((DetailActivity) activity).addFavor(cid, recClassId);
                            } else {
                                ((DetailActivity) activity).cancleFavor(cid, recClassId);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            });
        } catch (Exception e) {
        }

        // 全屏
        try {
            viewGroup.findViewById(R.id.detail_player_item_full).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup viewGroup = (ViewGroup) v.getParent().getParent().getParent();
                    PlayerViewForDetail playerView = viewGroup.findViewById(R.id.detail_player_item_video);
                    playerView.startFull();
                }
            });
        } catch (Exception e) {
        }

        try {
            PlayerViewForDetail playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.setPlayerChangeListener(new OnPlayerChangeListener() {

                @Override
                public void onChange(int playState) {
                    switch (playState) {
                        // 播放完成
                        case PlayerType.StateType.STATE_END:
                            try {
                                if (null == o)
                                    throw new Exception();
                                Activity activity = WrapperUtil.getWrapperActivity(playerView.getContext());
                                if (null == activity)
                                    throw new Exception();
                                if (!(activity instanceof DetailActivity))
                                    throw new Exception();
                                MediaBean mediaBean = (MediaBean) o;
                                // 剧集
                                if (mediaBean.isXuanJi() || mediaBean.isXuanQi()) {
                                    int tempLegnth = mediaBean.getTempLegnth();
                                    PlayerComponentInit component = playerView.findComponent(PlayerComponentInit.class);
                                    int position = component.getPosition();
                                    if (position >= tempLegnth) {
                                        component.updatePosition(0);
                                        ((DetailActivity) activity).startPlayerPosition(0);
                                    } else {
                                        component.updatePosition(position);
                                        ((DetailActivity) activity).startPlayerPosition(position);
                                    }
                                }
                                // 电影
                                else {
                                    ((DetailActivity) activity).startPlayerPosition(mediaBean);
                                }
                            } catch (Exception e) {
                            }
                            break;
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public static class DetailTemplatePlayerObject extends MediaBean {
    }
}
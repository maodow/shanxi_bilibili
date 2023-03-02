package tv.huan.bilibili.ui.detail.template;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.Presenter;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import lib.kalu.frame.mvp.util.WrapperUtil;
import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.listener.OnChangeListener;
import tv.huan.bilibili.R;
import tv.huan.bilibili.dialog.InfoDialog;
import tv.huan.bilibili.ui.detail.DetailActivity;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.common.CommonPicView;
import tv.huan.bilibili.widget.player.PlayerView;

public final class DetailTemplatePlayer extends Presenter {
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        LogUtil.log("DetailTemplatePlayer => onViewAttachedToWindow");
        stopFloat(holder.view);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        LogUtil.log("DetailTemplatePlayer => onViewDetachedFromWindow");
        startFloat(holder.view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        LogUtil.log("DetailTemplatePlayer => onCreateViewHolder");
        return createViewHolder(viewGroup);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        LogUtil.log("DetailTemplatePlayer => onUnbindViewHolder");
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        LogUtil.log("DetailTemplatePlayer => onBindViewHolder");

        boolean updateOnlyFavor;
        try {
            updateOnlyFavor = ((DetailTemplatePlayerObject) o).isUpdateOnlyFavor();
            ((DetailTemplatePlayerObject) o).setUpdateOnlyFavor(false);
        } catch (Exception e) {
            updateOnlyFavor = false;
        }

        if (updateOnlyFavor) {
            try {
                TextView textView = viewHolder.view.findViewById(R.id.detail_player_item_favor);
                textView.setSelected(((DetailTemplatePlayerObject) o).isFavorStatus());
                textView.setText(viewHolder.view.getResources().getString(((DetailTemplatePlayerObject) o).isFavorStatus() ? R.string.detail_favor_yes : R.string.detail_favor_no));
            } catch (Exception e) {
            }
        } else {
            stopFloat(viewHolder.view);
            stopPlayer(viewHolder.view);
            showWarning(viewHolder.view, o);
            showData(viewHolder, o);
        }
    }

    private void showData(ViewHolder viewHolder, Object o) {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {

                        String cdnUrl;
                        try {
                            DetailTemplatePlayerObject data = (DetailTemplatePlayerObject) o;
                            cdnUrl = data.getVideoUrl();
                        } catch (Exception e) {
                            cdnUrl = null;
                        }
                        observableEmitter.onNext(cdnUrl);
                    }
                })
                .delay(4, TimeUnit.SECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        LogUtil.log("DetailTemplatePlayer => showData => doOnSubscribe");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtil.log("DetailTemplatePlayer => showData => doOnError => " + throwable.getMessage());
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        LogUtil.log("DetailTemplatePlayer => showData => doOnNext => playUrl = " + s);
                        hideWarning(viewHolder.view);
                        startPlayer(viewHolder.view, s);
                    }
                })
                .subscribe();

    }

    private void showWarning(View view, Object o) {
        LogUtil.log("DetailTemplatePlayer => showWarning");
        try {
            view.findViewById(R.id.detail_player_item_logo).setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_player_item_cover).setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_player_item_warning).setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_player_item_index).setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_player_item_sign).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
        try {
            ImageView imageView = view.findViewById(R.id.detail_player_item_cover);
            GlideUtils.loadHz(imageView.getContext(), ((DetailTemplatePlayerObject) o).getImageUrl(), imageView);
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.detail_player_item_favor);
            textView.setSelected(((DetailTemplatePlayerObject) o).isFavorStatus());
            textView.setText(view.getResources().getString(((DetailTemplatePlayerObject) o).isFavorStatus() ? R.string.detail_favor_yes : R.string.detail_favor_no));
            JSONObject object = new JSONObject();
            object.put("cid", ((DetailTemplatePlayerObject) o).getCid());
            object.put("recClassId", ((DetailTemplatePlayerObject) o).getRecClassId());
            textView.setTag(R.id.detail_player_item_favor, object);

        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.detail_player_item_tag);
            textView.setText(((DetailTemplatePlayerObject) o).getTag());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.detail_player_item_title);
            textView.setText(((DetailTemplatePlayerObject) o).getTitle());
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.detail_player_item_index);
            String string = textView.getResources().getString(R.string.detail_playing_index, ((DetailTemplatePlayerObject) o).getTitle(), ((DetailTemplatePlayerObject) o).getPlayingIndex());
            textView.setText(string);
        } catch (Exception e) {
        }
        try {
            TextView textView = view.findViewById(R.id.detail_player_item_info);
            textView.setText(((DetailTemplatePlayerObject) o).getInfo());
        } catch (Exception e) {
        }
        try {
            LinearLayout linearLayout = view.findViewById(R.id.detail_player_item_pic);
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                linearLayout.removeViewAt(i);
            }
            String[] picList = ((DetailTemplatePlayerObject) o).getPicList();
            int length;
            try {
                length = picList.length;
            } catch (Exception e) {
                length = 0;
            }
            Context context = linearLayout.getContext();
            int height = context.getResources().getDimensionPixelOffset(R.dimen.dp_16);
            for (int i = 0; i < length; i++) {
                CommonPicView picView = new CommonPicView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
                int margin = linearLayout.getContext().getResources().getDimensionPixelOffset(i == length - 1 ? R.dimen.dp_8 : R.dimen.dp_4);
                params.setMargins(0, 0, margin, 0);
                picView.setLayoutParams(params);
                GlideUtils.loadHz(picView.getContext(), picList[i], picView);
                linearLayout.addView(picView, i);
            }
        } catch (Exception e) {
        }
    }

    private void hideWarning(View viewGroup) {
        LogUtil.log("DetailTemplatePlayer => hideWarning");
        try {
            viewGroup.findViewById(R.id.detail_player_item_logo).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.detail_player_item_cover).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.detail_player_item_warning).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.detail_player_item_index).setVisibility(View.GONE);
            viewGroup.findViewById(R.id.detail_player_item_sign).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    private void startPlayer(View view, String s) {
        LogUtil.log("DetailTemplatePlayer => startPlayer");
        try {
            PlayerView videoLayout = view.findViewById(R.id.detail_player_item_video);
            videoLayout.start(s);
        } catch (Exception e) {
        }
    }

    private void stopPlayer(View view) {
        LogUtil.log("DetailTemplatePlayer => stopPlayer");
        try {
            PlayerView videoLayout = view.findViewById(R.id.detail_player_item_video);
            videoLayout.stop();
            videoLayout.release();
        } catch (Exception e) {
        }
    }

    private void stopPlayer(ViewHolder viewHolder, Object o) {
        try {
            ViewGroup viewGroup = (ViewGroup) viewHolder.view.getParent().getParent().getParent();
            PlayerView playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.stopFloat();
        } catch (Exception e) {
        }
    }

    private void stopFloat(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view.getParent().getParent().getParent();
            PlayerView playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.stopFloat();
        } catch (Exception e) {
        }
    }

    private void startFloat(View view) {
        try {
            ViewGroup viewGroup = (ViewGroup) view.getParent().getParent().getParent();
            PlayerView playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.startFloat();
        } catch (Exception e) {
        }
    }

    private ViewHolder createViewHolder(ViewGroup viewGroup) {
        try {
            Context context = viewGroup.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.activity_detail_item_player, viewGroup, false);
            // 简介
            view.findViewById(R.id.detail_player_item_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = WrapperUtil.getWrapperActivity(v.getContext());
                    LogUtil.log("DetailTemplatePlayer => createViewHolder => onClick => activity = " + activity);
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
            // 会员
            view.findViewById(R.id.detail_player_item_vip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = WrapperUtil.getWrapperActivity(v.getContext());
                    LogUtil.log("DetailTemplatePlayer => createViewHolder => onClick => activity = " + activity);
                    if (null != activity && activity instanceof DetailActivity) {
                        ((DetailActivity) activity).jumpVip();
                    }
                }
            });
            // 收藏
            view.findViewById(R.id.detail_player_item_favor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = WrapperUtil.getWrapperActivity(v.getContext());
                    LogUtil.log("DetailTemplatePlayer => createViewHolder => onClick => activity = " + activity);
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
            // 全屏
            view.findViewById(R.id.detail_player_item_full).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup viewGroup = (ViewGroup) v.getParent().getParent().getParent();
                    PlayerView playerView = viewGroup.findViewById(R.id.detail_player_item_video);
                    String url = playerView.getUrl();
                    if (null != url && url.length() > 0) {
                        playerView.setOnChangeListener(new OnChangeListener() {
                            @Override
                            public void onWindow(int state) {
                                if (state == PlayerType.WindowType.FULL) {
                                    playerView.setFocusable(true);
                                    playerView.requestFocus();
                                } else if (state == PlayerType.WindowType.NORMAL) {
                                    playerView.setFocusable(false);
                                    v.requestFocus();
                                }
                            }
                        });
                        playerView.startFull();
                    }
                }
            });
            return new ViewHolder(view);
        } catch (Exception e) {
            return null;
        }
    }

    public static class DetailTemplatePlayerObject {

        private boolean updateOnlyFavor = false;

        private String cid;
        private String recClassId;
        private boolean favorStatus;

        private String tag;
        private String title;
        private String info;
        private String[] picList;

        private String imageUrl;
        private String videoUrl;
        private boolean showVip;
        private int playingIndex = 0;

        public boolean isFavorStatus() {
            return favorStatus;
        }

        public void setFavorStatus(boolean favorStatus) {
            this.favorStatus = favorStatus;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getRecClassId() {
            return recClassId;
        }

        public void setRecClassId(String recClassId) {
            this.recClassId = recClassId;
        }

        public boolean isUpdateOnlyFavor() {
            return updateOnlyFavor;
        }

        public void setUpdateOnlyFavor(boolean updateOnlyFavor) {
            this.updateOnlyFavor = updateOnlyFavor;
        }

        public int getPlayingIndex() {
            return playingIndex + 1;
        }

        public void setPlayingIndex(int playingIndex) {
            this.playingIndex = playingIndex;
        }

        public String[] getPicList() {
            return picList;
        }

        public void setPicList(String[] picList) {
            this.picList = picList;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isShowVip() {
            return showVip;
        }

        public void setShowVip(boolean showVip) {
            this.showVip = showVip;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return "https://img1.baidu.com/it/u=1966616150,2146512490&fm=253&fmt=auto&app=138&f=JPEG?w=751&h=500";
//            return imageUrl;
        }

        public String getVideoUrl() {
//            return "http://39.134.19.248:6610/yinhe/2/ch00000090990000001335/index.m3u8?virtualDomain=yinhe.live_hls.zte.com";
//            return videoUrl;
            return "http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&amp;bizid=1023&amp;hy=SH&amp;fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400";
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
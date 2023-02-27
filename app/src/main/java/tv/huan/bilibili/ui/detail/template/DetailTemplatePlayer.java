package tv.huan.bilibili.ui.detail.template;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.Presenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import lib.kalu.mediaplayer.config.player.PlayerType;
import lib.kalu.mediaplayer.listener.OnChangeListener;
import lib.kalu.mediaplayer.util.ActivityUtils;
import tv.huan.bilibili.utils.LogUtil;
import tv.huan.bilibili.widget.player.PlayerView;
import tv.huan.bilibili.R;
import tv.huan.bilibili.dialog.InfoDialog;

public class DetailTemplatePlayer extends Presenter {
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        LogUtil.log("DetailTemplatePlayer => onViewAttachedToWindow");
        try {
            ViewGroup viewGroup = (ViewGroup) holder.view.getParent().getParent().getParent();
            PlayerView playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.stopFloat();
        } catch (Exception e) {
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        LogUtil.log("DetailTemplatePlayer => onViewDetachedFromWindow");
        try {
            ViewGroup viewGroup = (ViewGroup) holder.view.getParent().getParent().getParent();
            PlayerView playerView = viewGroup.findViewById(R.id.detail_player_item_video);
            playerView.startFloat();
        } catch (Exception e) {
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        LogUtil.log("DetailTemplatePlayer => onCreateViewHolder");
        try {
            Context context = viewGroup.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.activity_detail_item_player, viewGroup, false);
            // 简介
            view.findViewById(R.id.detail_player_item_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    Activity activity = ActivityUtils.getActivity(v.getContext());
                    if (null == activity)
                        return;
                    // 2
                    ViewGroup group = (ViewGroup) v.getParent();
                    TextView v1 = group.findViewById(R.id.detail_player_item_title);
                    String s1 = v1.getText().toString();
                    TextView v2 = group.findViewById(R.id.detail_player_item_tag);
                    String s2 = v2.getText().toString();
                    TextView v3 = group.findViewById(R.id.detail_player_item_info);
                    String s3 = v3.getText().toString();
                    // 3
                    Bundle bundle = new Bundle();
                    bundle.putString(InfoDialog.BUNDLE_NAME, s1);
                    bundle.putString(InfoDialog.BUNDLE_DATA1, s2);
                    bundle.putString(InfoDialog.BUNDLE_DATA2, s3);
                    // 4
                    InfoDialog dialog = new InfoDialog();
                    dialog.setArguments(bundle);
                    dialog.show(((AppCompatActivity) activity).getSupportFragmentManager(), null);
                    Toast.makeText(v.getContext(), "简介", Toast.LENGTH_SHORT).show();
                }
            });
            // 会员
            view.findViewById(R.id.detail_player_item_vip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "会员", Toast.LENGTH_SHORT).show();
                }
            });
            // 收藏
            view.findViewById(R.id.detail_player_item_favor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "收藏", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        LogUtil.log("DetailTemplatePlayer => onUnbindViewHolder");
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object o) {
        LogUtil.log("DetailTemplatePlayer => onBindViewHolder");
        delayPlayer(viewHolder, o);
    }

    private void delayPlayer(ViewHolder viewHolder, Object o) {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {

                        PlayerView videoLayout = viewHolder.view.findViewById(R.id.detail_player_item_video);
                        String url = videoLayout.getUrl();
                        if (null != url && url.length() > 0)
                            throw new Exception();

                        String cdnUrl;
                        try {
                            DetailTemplatePlayerObject data = (DetailTemplatePlayerObject) o;
                            cdnUrl = data.getCdnUrl();
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
//                        View view = viewHolder.view.findViewById(R.id.detail_player_item_vip);
//                        view.setVisibility(data.showVip ? View.VISIBLE : View.GONE);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        PlayerView videoLayout = viewHolder.view.findViewById(R.id.detail_player_item_video);
                        videoLayout.restart();
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        hideUI(viewHolder);
                        startPlayer(viewHolder, s);
                    }
                })
                .subscribe();

    }

    private void hideUI(ViewHolder viewHolder) {
        try {
            viewHolder.view.findViewById(R.id.detail_player_item_logo).setVisibility(View.GONE);
            viewHolder.view.findViewById(R.id.detail_player_item_img).setVisibility(View.GONE);
            viewHolder.view.findViewById(R.id.detail_player_item_warning).setVisibility(View.GONE);
            viewHolder.view.findViewById(R.id.detail_player_item_position).setVisibility(View.GONE);
            viewHolder.view.findViewById(R.id.detail_player_item_sign).setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    private void startPlayer(ViewHolder viewHolder, String s) {
        try {
            PlayerView videoLayout = viewHolder.view.findViewById(R.id.detail_player_item_video);
            videoLayout.start(s);
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

    public static class DetailTemplatePlayerObject {

        private String cdnUrl;
        private boolean showVip;

        public boolean isShowVip() {
            return showVip;
        }

        public void setShowVip(boolean showVip) {
            this.showVip = showVip;
        }

        public String getCdnUrl() {
            return cdnUrl;
        }

        public void setCdnUrl(String cdnUrl) {
            this.cdnUrl = cdnUrl;
        }
    }
}
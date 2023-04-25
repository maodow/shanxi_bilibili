//package tv.huan.bilibili.widget.player;
//
//import android.content.Context;
//import android.graphics.Rect;
//import android.os.Build;
//import android.os.Handler;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import lib.kalu.mediaplayer.config.player.PlayerType;
//import lib.kalu.mediaplayer.core.component.ComponentApi;
//import tv.huan.bilibili.R;
//
//public class PlayerComponentSeekPlus extends RelativeLayout implements ComponentApi {
//    protected ControllerWrapper mControllerWrapper;
//    private boolean isOK = true;
//
//    private List<Media> mMedias = new ArrayList<>();
//
//    public PlayerComponentSeekPlus(@NonNull Context context) {
//        super(context);
//        this.init();
//    }
//
//    private void init() {
//        // step1
//        LayoutInflater.from(getContext()).inflate(R.layout.common_layout_player_component_seek, this, true);
//
//        // step11
//        setAdapter();
//
//        // step2, 5.1以下系统SeekBar高度需要设置成WRAP_CONTENT
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            ProgressBar progressBar = findViewById(R.id.module_mediaplayer_component_seek_pb);
//            progressBar.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        }
//
//        // step3
////        View play = findViewById(R.id.module_mediaplayer_component_seek_play);
////        if (null != play) {
////            play.setOnClickListener(new OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (null != mControllerWrapper) {
////                        boolean playing = mControllerWrapper.isPlaying();
////                        if (playing) {
////                            mControllerWrapper.pause();
////                        } else {
////                            mControllerWrapper.resume();
////                        }
////                    }
////                }
////            });
////        }
//
//        // step4
//        View back = findViewById(R.id.module_mediaplayer_component_seek_back);
//        if (null != back) {
//            back.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), "back", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//        // step5
//        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
//        if (null != sb) {
//            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
////        // sb开始拖动
////        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
////        if (null != sb) {
////            sb.setTag(1);
////        }
////        // pb
////        ProgressBar pb = findViewById(R.id.module_mediaplayer_component_seek_pb);
////        if (null != pb) {
////            pb.setTag(1);
////        }
//                    if (null != mControllerWrapper) {
//                        mControllerWrapper.pause();
//                    }
//                    mControllerWrapper.stopFadeOut();
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
////        // sb结束拖动
////        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
////        if (null != sb) {
////            sb.setTag(null);
////        }
////        // pb
////        ProgressBar pb = findViewById(R.id.module_mediaplayer_component_seek_pb);
////        if (null != pb) {
////            pb.setTag(null);
////        }
//                    if (null != mControllerWrapper) {
//                        mControllerWrapper.resume();
//                    }
//                }
//
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                    onSeekChanged(progress, fromUser);
//                }
//            });
//        }
////        View viewFull = findViewById(R.id.module_mediaplayer_controller_bottom_full);
////        viewFull.setOnClickListener(this);
////        View viewPlayer = findViewById(R.id.module_mediaplayer_controller_bottom_play);
////        viewPlayer.setOnClickListener(this);
//    }
//
//    private void setAdapter() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        RecyclerView recyclerView = getView().findViewById(R.id.module_mediaplayer_component_list);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                int offset = getResources().getDimensionPixelOffset(R.dimen.dp_14);
//                outRect.set(0, 0, offset, 0);
//            }
//        });
//        recyclerView.setAdapter(new RecyclerView.Adapter() {
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                Context context = parent.getContext();
//                View view = LayoutInflater.from(context).inflate(R.layout.common_layout_player_component_seek_item, null);
//                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {
//
//                };
//                view.setOnKeyListener(new OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//                            v.clearFocus();
//                            showSeek();
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                view.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int position = holder.getAbsoluteAdapterPosition();
//                        Media media = mMedias.get(position);
//                        Toast.makeText(v.getContext(), media.getIndex() + "", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                return holder;
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//                TextView textView = holder.itemView.findViewById(R.id.common_xuanji);
//                Media media = mMedias.get(position);
//                textView.setText(media.getIndex() + "");
//            }
//
//            @Override
//            public int getItemCount() {
//                return mMedias.size();
//            }
//        });
//    }
//
//    /**
//     * 是否显示底部进度条，默认显示
//     */
//    public void showBottomProgress(boolean show) {
//        ProgressBar pb = findViewById(R.id.module_mediaplayer_component_seek_pb);
//        if (null == pb)
//            return;
//        pb.setVisibility(show ? View.VISIBLE : View.GONE);
//    }
//
//    @Override
//    public void attach(@NonNull ControllerWrapper controllerWrapper) {
//        mControllerWrapper = controllerWrapper;
//    }
//
//    @Override
//    public View getView() {
//        return this;
//    }
//
//    @Override
//    public void onVisibilityChanged(boolean isVisible, Animation anim) {
////        View view = findViewById(R.id.module_mediaplayer_controller_bottom_progress);
////        View viewRoot = findViewById(R.id.module_mediaplayer_controller_bottom_root);
////        if (isVisible) {
////            viewRoot.setVisibility(VISIBLE);
////            if (anim != null) {
////                viewRoot.startAnimation(anim);
////            }
////            if (mIsShowBottomProgress) {
////                view.setVisibility(GONE);
////            }
////        } else {
////            viewRoot.setVisibility(GONE);
////            if (anim != null) {
////                viewRoot.startAnimation(anim);
////            }
////            if (mIsShowBottomProgress) {
////                view.setVisibility(VISIBLE);
////                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
////                animation.setDuration(300);
////                view.startAnimation(animation);
////            }
////        }
//    }
//
//    @Override
//    public void onPlayStateChanged(int playState) {
//        switch (playState) {
//            case PlayerType.StateType.STATE_LOADING_STOP:
//                bringToFront();
//                setVisibility(View.VISIBLE);
//                break;
//            case PlayerType.StateType.STATE_LOADING_START:
//                setVisibility(View.GONE);
//                break;
//        }
//
////        View viewPlayer = findViewById(R.id.module_mediaplayer_controller_bottom_play);
////        View viewRoot = findViewById(R.id.module_mediaplayer_controller_bottom_root);
////        ProgressBar progressBar = findViewById(R.id.module_mediaplayer_controller_bottom_progress);
////        switch (playState) {
////            case PlayerType.StateType.STATE_INIT:
////            case PlayerType.StateType.STATE_BUFFERING_START:
////                setVisibility(GONE);
////                progressBar.setProgress(0);
////                progressBar.setSecondaryProgress(0);
////                SeekBar seekBar = findViewById(R.id.module_mediaplayer_controller_bottom_seek);
////                seekBar.setProgress(0);
////                seekBar.setSecondaryProgress(0);
////                break;
////            case PlayerType.StateType.STATE_START_ABORT:
////            case PlayerType.StateType.STATE_LOADING_START:
////            case PlayerType.StateType.STATE_LOADING_STOP:
////            case PlayerType.StateType.STATE_ERROR:
////            case PlayerType.StateType.STATE_ONCE_LIVE:
////                setVisibility(GONE);
////                break;
////            case PlayerType.StateType.STATE_START:
////                viewPlayer.setSelected(true);
////                if (mIsShowBottomProgress) {
////                    if (mControllerWrapper.isShowing()) {
////                        progressBar.setVisibility(GONE);
////                        viewRoot.setVisibility(VISIBLE);
////                    } else {
////                        viewRoot.setVisibility(GONE);
////                        progressBar.setVisibility(VISIBLE);
////                    }
////                } else {
////                    viewRoot.setVisibility(GONE);
////                }
////                setVisibility(VISIBLE);
////                //开始刷新进度
////                mControllerWrapper.startProgress();
////                break;
////            case PlayerType.StateType.STATE_PAUSE:
////                viewPlayer.setSelected(false);
////                break;
////            case PlayerType.StateType.STATE_BUFFERING_STOP:
////            case PlayerType.StateType.STATE_END:
////                viewPlayer.setSelected(mControllerWrapper.isPlaying());
////                break;
////    }
//    }
//
//    @Override
//    public void onWindowStateChanged(int playerState) {
////        View viewFull = findViewById(R.id.module_mediaplayer_controller_bottom_full);
////        View viewRoot = findViewById(R.id.module_mediaplayer_controller_bottom_root);
////        ProgressBar progressBar = findViewById(R.id.module_mediaplayer_controller_bottom_progress);
////        switch (playerState) {
////            case PlayerType.WindowType.NORMAL:
////                viewFull.setSelected(false);
////                break;
////            case PlayerType.WindowType.FULL:
////                viewFull.setSelected(true);
////                break;
////        }
////
////        Activity activity = PlayerUtils.scanForActivity(getContext());
////        if (activity != null && mControllerWrapper.hasCutout()) {
////            int orientation = activity.getRequestedOrientation();
////            int cutoutHeight = mControllerWrapper.getCutoutHeight();
////            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
////                viewRoot.setPadding(0, 0, 0, 0);
////                progressBar.setPadding(0, 0, 0, 0);
////            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
////                viewRoot.setPadding(cutoutHeight, 0, 0, 0);
////                progressBar.setPadding(cutoutHeight, 0, 0, 0);
////            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
////                viewRoot.setPadding(0, 0, cutoutHeight, 0);
////                progressBar.setPadding(0, 0, cutoutHeight, 0);
////            }
////        }
//    }
//
//    @Override
//    public void onLockStateChanged(boolean isLocked) {
//        onVisibilityChanged(!isLocked, null);
//    }
//
//
//    /****************************************/
//
//    @Override
//    public void seekProgress(@NonNull boolean fromUser, @NonNull long position, @NonNull long duration) {
//
////        MediaLogUtil.log("ComponentSeek => seekProgress => duration = " + duration + ", position = " + position);
//        if (position <= 0 && duration <= 0)
//            return;
//
//        int visibility = getVisibility();
//        if (visibility != View.VISIBLE)
//            return;
//
//        // pb
//        ProgressBar pb = findViewById(R.id.module_mediaplayer_component_seek_pb);
//        if (null != pb && pb.getVisibility() == View.VISIBLE) {
//            refreshProgress((int) position, (int) duration);
//        }
//
//        // sb
//        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
//        if (null != sb && sb.getVisibility() == View.VISIBLE) {
//            refreshSeek((int) position, (int) duration);
//        }
//
//        // time
//        TextView viewMax = findViewById(R.id.module_mediaplayer_component_seek_max);
//        TextView viewPosition = findViewById(R.id.module_mediaplayer_component_seek_position);
//        // 1
//        // ms => s
//        long c = position / 1000;
//        long c1 = c / 60;
//        long c2 = c % 60;
//        StringBuilder builder1 = new StringBuilder();
//        if (c1 < 10) {
//            builder1.append("0");
//        }
//        builder1.append(c1);
//        builder1.append(":");
//        if (c2 < 10) {
//            builder1.append("0");
//        }
//        builder1.append(c2);
//        String s1 = builder1.toString();
//        viewPosition.setText(s1);
//
//        // 2
//        // ms => s
//        StringBuilder builder2 = new StringBuilder();
//        long d = duration / 1000;
//        long d1 = d / 60;
//        long d2 = d % 60;
//        if (d1 < 10) {
//            builder2.append("0");
//        }
//        builder2.append(d1);
//        builder2.append(":");
//        if (d2 < 10) {
//            builder2.append("0");
//        }
//        builder2.append(d2);
//        String s2 = builder2.toString();
//        viewMax.setText(s2);
////        MediaLogUtil.log("ComponentSeek => seekProgress => s1 = " + s1 + ", s2 = " + s2);
//    }
//
//    @Override
//    public boolean seekForward(boolean callback) {
//        View view = getView().findViewById(R.id.module_mediaplayer_component_seek);
//        if (view.getVisibility() != View.VISIBLE)
//            return true;
//
//        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
//        if (null != sb && sb.getVisibility() == View.VISIBLE) {
//            int max = sb.getMax();
//            int progress = sb.getProgress();
//            if (progress < max) {
//                int next = progress + Math.abs(max) / 200;
////                MediaLogUtil.log("ComponentSeek => seekForward =>  callback = " + callback + ", progress = " + progress + ", next = " + next + ", max = " + max);
//                if (next > max) {
//                    next = max;
//                }
//                refreshSeek(next, 0);
//                if (callback) {
//                    onSeekChanged(progress, true);
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public boolean seekRewind(boolean callback) {
//        View view = getView().findViewById(R.id.module_mediaplayer_component_seek);
//        if (view.getVisibility() != View.VISIBLE)
//            return true;
//
//        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
////        MediaLogUtil.log("ComponentSeek => seekRewind => callback = " + callback);
//        if (null != sb && sb.getVisibility() == View.VISIBLE) {
//            int progress = sb.getProgress();
//            if (progress > 0) {
//                int max = sb.getMax();
//                int next = progress - Math.abs(max) / 200;
//                if (next < 0) {
//                    next = 0;
//                }
//                refreshSeek(next, 0);
//                if (callback) {
//                    onSeekChanged(progress, true);
//                }
//            }
//        }
//        return true;
//    }
//
//    /****************************************/
//
////    private final Handler mHandler = new Handler(this);
//
////    @Override
////    public boolean handleMessage(@NonNull Message msg) {
////        if (msg.what == 0x123456) {
////            setVisibility(View.GONE);
////        }
////        return false;
////    }
//    @Override
//    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
//        super.onVisibilityChanged(changedView, visibility);
//    }
//
//    private void autoGone() {
////        mHandler.removeCallbacksAndMessages(null);
////        mHandler.sendEmptyMessageDelayed(0x123456, 10000);
////        postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                if (isOK) {
////                    setVisibility(View.GONE);
////                }
////            }
////        }, 10000);
//    }
//
//    private void onSeekChanged(int progress, boolean fromUser) {
//        if (!fromUser)
//            return;
//        if (null == mControllerWrapper)
//            return;
//        long max = mControllerWrapper.getMax();
//        boolean looping = mControllerWrapper.isLooping();
//        mControllerWrapper.seekTo(true, progress, max, looping);
//    }
//
//    private void refreshSeek(int progress, int max) {
//        SeekBar sb = findViewById(R.id.module_mediaplayer_component_seek_sb);
//        if (null == sb)
//            return;
//        sb.setProgress(progress);
//        sb.setSecondaryProgress(progress);
//        if (max > 0) {
//            sb.setMax(max);
//        }
//    }
//
//    private void refreshProgress(int progress, int max) {
//        ProgressBar pb = findViewById(R.id.module_mediaplayer_component_seek_pb);
//        if (null == pb)
//            return;
//        pb.setProgress(progress);
//        pb.setSecondaryProgress(progress);
//        if (max > 0) {
//            pb.setMax(max);
//        }
//    }
//
//    /******************/
//
//    protected void showCollect(@NonNull List<Media> list) {
////        Toast.makeText(getContext(), "showCollect", Toast.LENGTH_SHORT).show();
//        // 1
//        View view1 = getView().findViewById(R.id.module_mediaplayer_component_seek);
//        view1.setVisibility(View.GONE);
//        // 2
//        View view2 = getView().findViewById(R.id.module_mediaplayer_component_collect);
//        view2.setVisibility(View.VISIBLE);
//        // 3
//        RecyclerView recyclerView = getView().findViewById(R.id.module_mediaplayer_component_list);
//        int itemCount = recyclerView.getAdapter().getItemCount();
//        if (itemCount <= 0) {
//            mMedias.clear();
//            mMedias.addAll(list);
//            recyclerView.getAdapter().notifyDataSetChanged();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(0);
//                    viewHolder.itemView.requestFocus();
//                }
//            }, 40);
//        } else {
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(0);
//            viewHolder.itemView.requestFocus();
//        }
//    }
//
//    protected void showSeek() {
////        Toast.makeText(getContext(), "showSeek", Toast.LENGTH_SHORT).show();
//        // 1
//        View view1 = getView().findViewById(R.id.module_mediaplayer_component_seek);
//        view1.setVisibility(View.VISIBLE);
//        // 2
//        View view2 = getView().findViewById(R.id.module_mediaplayer_component_collect);
//        view2.setVisibility(View.GONE);
//    }
//}
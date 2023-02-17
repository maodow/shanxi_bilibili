package tv.huan.bilibili.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lib.kalu.frame.mvp.BaseActivity;
import lib.kalu.frame.mvp.BasePresenter;
import lib.kalu.frame.mvp.transformer.ComposeSchedulers;
import lib.kalu.leanback.tab.TabLayout;
import lib.kalu.leanback.tab.model.TabModel;
import lib.kalu.leanback.tab.model.TabModelImage;
import lib.kalu.leanback.tab.model.TabModelText;
import lib.kalu.leanback.tags.TagsLayout;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.BaseBean;
import tv.huan.bilibili.bean.ExitBean;
import tv.huan.bilibili.bean.GetChannelsBean;
import tv.huan.bilibili.bean.MainBean;
import tv.huan.bilibili.dialog.ExitDialog;
import tv.huan.bilibili.http.HttpClient;
import tv.huan.bilibili.ui.main.general.GeneralFragment;
import tv.huan.bilibili.ui.main.mine.MineFragment;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(@NonNull MainView mainView) {
        super(mainView);
    }

    protected void autoNext() {
        int type = getView().getIntExtra(MainActivity.INTENT_TYPE, -1);
        // 专辑
        if (type == MainActivity.INTENT_TYPE_DETAIL) {
            String cid = getView().getStringExtra(MainActivity.INTENT_CID);
            int classId = getView().getIntExtra(MainActivity.INTENT_CLASSID, -1);
            JumpUtil.nextDetail(getView().getContext(), cid, classId);
        }
        // 筛选
        else if (type == MainActivity.INTENT_TYPE_FILTER) {
            String secondTag = getView().getStringExtra(MainActivity.INTENT_SECOND_TAG);
            int classId = getView().getIntExtra(MainActivity.INTENT_CLASSID, -1);
            JumpUtil.nextFilter(getView().getContext(), secondTag, classId);
        }
    }

    protected void showFragment(int position) {

        addDisposable(Observable.create(new ObservableOnSubscribe<List<GetChannelsBean.ItemBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GetChannelsBean.ItemBean>> emitter) throws Exception {
                        try {
                            String extra = getView().getStringExtra(MainActivity.INTENT_TABS);
                            Type type = new TypeToken<List<GetChannelsBean.ItemBean>>() {
                            }.getType();
                            List<GetChannelsBean.ItemBean> datas = new Gson().fromJson(extra, type);
                            if (null == datas || datas.size() <= 0)
                                throw new Exception("datas is null");
                            emitter.onNext(datas);
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                .map(new Function<List<GetChannelsBean.ItemBean>, Fragment>() {
                    @Override
                    public Fragment apply(List<GetChannelsBean.ItemBean> data) {
                        try {
                            String tag = "fragment" + position;
                            Fragment fragment = getView().findFragmentByTag(tag);
                            if (null == fragment) {
                                GetChannelsBean.ItemBean bean = data.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putInt(GeneralFragment.BUNDLE_INDEX, position);
                                bundle.putString(GeneralFragment.BUNDLE_NAME, bean.getName());
                                bundle.putInt(GeneralFragment.BUNDLE_CHANNELID, bean.getId());
                                bundle.putInt(GeneralFragment.BUNDLE_CLASSID, bean.getClassId());
                                if (position == 0) {
                                    fragment = new MineFragment();
                                } else {
                                    fragment = new GeneralFragment();
                                }
                                fragment.setArguments(bundle);
                                getView().addFragment(fragment, R.id.main_content, tag);
                            }
                            return fragment;
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        try {
                            TabLayout tabLayout = getView().findViewById(R.id.main_tabs);
                            int count = tabLayout.getItemCount();
                            if (count > 0) {
                                for (int i = 0; i < count; i++) {
                                    String tag = "fragment" + i;
                                    Fragment fragment = getView().findFragmentByTag(tag);
                                    if (null == fragment)
                                        continue;
                                    if (fragment instanceof GeneralFragment) {
                                        ((GeneralFragment) fragment).clearMessage();
                                    }
                                    getView().hideFragment(fragment);
                                }
                            }
                        } catch (Exception e) {
                            LogUtil.log("MainPresenter => showFragment => " + e.getMessage());
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtil.log("MainPresenter => showFragment => fail");
                        LogUtil.log("MainPresenter => showFragment => " + throwable.getMessage());
                    }
                })
                .doOnNext(new Consumer<Fragment>() {
                    @Override
                    public void accept(Fragment fragment) {
                        LogUtil.log("MainPresenter => showFragment => succ");
                        LogUtil.log("MainPresenter => showFragment => fragment = " + fragment);
                        getView().showFragment(fragment);
                    }
                })
                .subscribe());
    }

    protected void refreshTabs() {

        addDisposable(Observable.create(new ObservableOnSubscribe<MainBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<MainBean> observableEmitter) {

                        // 0
                        int select = getView().getIntExtra(MainActivity.INTENT_SELECT, 0);
                        String extra = getView().getStringExtra(MainActivity.INTENT_TABS);
                        Type type = new TypeToken<List<GetChannelsBean.ItemBean>>() {
                        }.getType();
                        List<GetChannelsBean.ItemBean> datas = new Gson().fromJson(extra, type);

                        ArrayList<TabModel> list = new ArrayList<>();
                        int size = datas.size();
                        for (int i = 0; i < size; i++) {

                            GetChannelsBean.ItemBean bean = datas.get(i);
                            if (null == bean)
                                continue;

                            TabModel tabModel;

                            // 图片
                            if (null != bean.getImgs() && null != bean.getImgs().getIcon() && null != bean.getImgs().getFocus() && null != bean.getImgs().getStay()) {
                                tabModel = new TabModelImage();
                                tabModel.setImagePlaceholderResource(R.mipmap.ic_launcher);
                                tabModel.setImageUrlFocus(bean.getImgs().getFocus());
                                tabModel.setImageUrlChecked(bean.getImgs().getStay());
                                tabModel.setImageUrlNormal(bean.getImgs().getIcon());
                            }
                            // 文字
                            else {
                                tabModel = new TabModelText();
                                tabModel.setText(bean.getName());
                                tabModel.setTextColorResourceNormal(R.color.color_bfbfbf);
                                tabModel.setTextColorResourceChecked(R.color.color_ff6699);
                                tabModel.setTextColorResourceFocus(R.color.color_black);
                                tabModel.setBackgroundResourceFocus(R.drawable.bg_shape_tab_focus);
                                tabModel.setBackgroundResourceChecked(R.drawable.bg_shape_tab_selected);
                                tabModel.setBackgroundResourceNormal(R.drawable.bg_shape_tab);
                            }
                            list.add(tabModel);
                        }

                        MainBean mainBean = new MainBean();
                        mainBean.setPosition(select);
                        mainBean.setTabModels(list);
                        observableEmitter.onNext(mainBean);
                    }
                })
                .delay(40, TimeUnit.MILLISECONDS)
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        getView().showLoading();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().hideLoading();
                    }
                })
                .doOnNext(new Consumer<MainBean>() {
                    @Override
                    public void accept(MainBean data) {
                        getView().hideLoading();
                        getView().refreshTabs(data.getTabModels(), data.getPosition());
                    }
                })
                .subscribe());
    }

    protected void requestExit() {

        addDisposable(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                        observableEmitter.onNext(true);
                    }
                })
                .flatMap(new Function<Boolean, Observable<BaseBean<List<ExitBean>>>>() {
                    @Override
                    public Observable<BaseBean<List<ExitBean>>> apply(Boolean aBoolean) {
                        return HttpClient.getHttpClient().getHttpApi().getExit(2);
                    }
                })
                .map(new Function<BaseBean<List<ExitBean>>, String>() {
                    @Override
                    public String apply(BaseBean<List<ExitBean>> response) {
                        ArrayList<ExitBean> list = new ArrayList<>();
                        try {
                            List<ExitBean> data = response.getData();
                            list.addAll(data);
                        } catch (Exception e) {
                        }
                        String s = new Gson().toJson(list);
                        return s;
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
//                        getView().showLoading();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
//                        getView().hideLoading();
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        getView().updateExit(s);
                    }
                })
                .subscribe());
    }

    protected boolean dispatchKey(KeyEvent event) {
        // back
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            String extra = getView().getStringExtra("intent_exit");
            if (null != extra && extra.length() > 0) {
                BaseActivity activity = (BaseActivity) getView();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                ExitDialog dialog = new ExitDialog();
                Bundle bundle = new Bundle();
                bundle.putString(ExitDialog.BUNDLE_DATA, extra);
                dialog.setArguments(bundle);
                dialog.show(fragmentManager, "");
                return true;
            }
        }
        // up
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("MainPresenter => dispatchKeyEvent => up_action_down => ");
            if (focusId == R.id.main_tabs) {
                TabLayout tabLayout = (TabLayout) getView().getCurrentFocus();
                LogUtil.log("MainPresenter => dispatchKeyEvent => up_action_down => tabLayout = " + tabLayout);
                tabLayout.checkedCurrentItem();
                int index = tabLayout.getCheckedIndex();
                getView().setFocusable(R.id.main_vip, index > 0);
                getView().setFocusable(R.id.main_search, index <= 0);
            } else if (focusId == R.id.main_search || focusId == R.id.main_vip) {
                return true;
            }
        }
        // down
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            int focusId = getView().getCurrentFocusId();
            if (focusId == R.id.main_search || focusId == R.id.main_vip) {
                LogUtil.log("MainPresenter => dispatchKeyEvent => down_action_down => ");
                getView().setFocusable(R.id.main_vip, false);
                getView().setFocusable(R.id.main_search, false);
                TabLayout tabLayout = getView().findViewById(R.id.main_tabs);
                tabLayout.focusCurrentItem();
                return true;
            }
        }
        // right
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("MainPresenter => dispatchKeyEvent => right_action_down => " + getView().getCurrentFocus());
            if (focusId == R.id.main_search) {
                getView().setFocusable(R.id.main_vip, true);
                getView().setFocusable(R.id.main_search, false);
                return true;
            } else if (focusId == R.id.main_vip) {
                return true;
            }
        }
        // left
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            int focusId = getView().getCurrentFocusId();
            LogUtil.log("MainPresenter => dispatchKeyEvent => left_action_down => " + getView().getCurrentFocus());
            if (focusId == R.id.main_vip) {
                getView().setFocusable(R.id.main_search, true);
                getView().setFocusable(R.id.main_vip, false);
                return true;
            } else if (focusId == R.id.main_search) {
                return true;
            }
        }
        return false;
    }

    protected void showBackground(int position) {
        addDisposable(Observable.create(new ObservableOnSubscribe<Drawable>() {
                    @Override
                    public void subscribe(ObservableEmitter<Drawable> emitter) throws Exception {
                        try {
                            String extra = getView().getStringExtra(MainActivity.INTENT_TABS);
                            Type type = new TypeToken<List<GetChannelsBean.ItemBean>>() {
                            }.getType();
                            List<GetChannelsBean.ItemBean> datas = new Gson().fromJson(extra, type);
                            GetChannelsBean.ItemBean bean = datas.get(position);
                            String poster = bean.getImgs().getPoster();
                            if (null == poster || poster.length() <= 0)
                                throw new Exception("url is null");
                            RequestOptions options = new RequestOptions()
                                    .dontAnimate()
                                    .dontTransform()
                                    .dontAnimate()
                                    .dontTransform()
                                    .encodeQuality(40)
                                    .format(DecodeFormat.PREFER_RGB_565)
                                    .priority(Priority.LOW)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .skipMemoryCache(false);
                            Drawable drawable = Glide.with(getView().getContext())
                                    .load(poster)
                                    .apply(options)
                                    .submit()
                                    .get();
                            if (null == drawable)
                                throw new Exception("drawable is null");
                            emitter.onNext(drawable);
                        } catch (Exception e) {
                            throw new Exception();
                        }
                    }
                })
                .compose(ComposeSchedulers.io_main())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
//                        LogUtil.log("MainPresenter => refreshBackground => fail");
                        getView().setWindowBackgroundColorRes(R.color.color_171819);
                    }
                })
                .doOnNext(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) {
//                        LogUtil.log("MainPresenter => refreshBackground => succ");
                        getView().setWindowBackgroundDrawable(drawable);
                    }
                })
                .subscribe());
    }
}

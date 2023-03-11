package tv.huan.bilibili.ui.main.general.template;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.leanback.presenter.ListTvGridPresenter;
import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.R;
import tv.huan.bilibili.bean.FavBean;
import tv.huan.bilibili.bean.GetSubChannelsByChannelBean;
import tv.huan.bilibili.utils.GlideUtils;
import tv.huan.bilibili.utils.JumpUtil;
import tv.huan.bilibili.utils.LogUtil;

public class GeneralTemplate20 extends ListTvGridPresenter<GetSubChannelsByChannelBean.ListBean.TemplateBean> {

    @Override
    public String initRowTitle(Context context) {
        if (BuildConfig.HUAN_TEST_TEMPLATE_ENABLE) {
            return "模板20";
        }
        else{
            return super.initRowTitle(context);
        }
    }

    @Override
    public void initItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int offset = view.getResources().getDimensionPixelOffset(R.dimen.dp_12);
        int position = parent.getChildAdapterPosition(view);
        int bottom = view.getResources().getDimensionPixelOffset(R.dimen.dp_24);
        if (position == 0) {
            outRect.set(0, 0, offset, bottom);
        } else if (position == 1) {
            outRect.set(offset, 0, 0, bottom);
        } else if (position == 2) {
            outRect.set(0, 0, offset * 2, 0);
        } else if (position == 5) {
            outRect.set(offset * 2, 0, 0, 0);
        } else {
            outRect.set(offset, 0, offset, 0);
        }
    }

    @Override
    public int initPaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_40);
    }

    @Override
    public int initTitlePaddingBottom(@NonNull Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.dp_12);
    }

    @Override
    protected void onCreateHolder(@NonNull Context context, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull View view, @NonNull List<GetSubChannelsByChannelBean.ListBean.TemplateBean> list, @NonNull int i) {

        // type0 type2
        if (i != 1) {
            try {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAbsoluteAdapterPosition();
                        if (position >= 0) {
                            GetSubChannelsByChannelBean.ListBean.TemplateBean bean = list.get(position);
                            JumpUtil.next(v.getContext(), bean);
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
        // type1
        else {

            // onClick
            for (int m = 0; m < 6; m++) {

                View v;
                if (m == 0) {
                    v = view.findViewById(R.id.general_template20b_type1_item1);
                } else if (m == 1) {
                    v = view.findViewById(R.id.general_template20b_type1_item2);
                } else if (m == 2) {
                    v = view.findViewById(R.id.general_template20b_type1_item3);
                } else if (m == 3) {
                    v = view.findViewById(R.id.general_template20b_type2_item1);
                } else if (m == 4) {
                    v = view.findViewById(R.id.general_template20b_type2_item2);
                } else {
                    v = view.findViewById(R.id.general_template20b_type3);
                }

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = view.getId();
                        if (id == R.id.general_template20b_type1_item1) {
                            try {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(2);
                                FavBean tempFav = templateBean.getTempFav();
                                FavBean.ItemBean itemBean = tempFav.getRows().get(0);
                                itemBean.setToType(1);
                                itemBean.setCid(itemBean.getAlbum().getCid());
                                JumpUtil.next(view.getContext(), itemBean);
                            } catch (Exception e) {
                                LogUtil.log("GeneralTemplate20 => onClick => " + e.getMessage());
                            }
                        } else if (id == R.id.general_template20b_type1_item2) {
                            try {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(2);
                                FavBean tempFav = templateBean.getTempFav();
                                FavBean.ItemBean itemBean = tempFav.getRows().get(1);
                                itemBean.setToType(1);
                                itemBean.setCid(itemBean.getAlbum().getCid());
                                JumpUtil.next(view.getContext(), itemBean);
                            } catch (Exception e) {
                                LogUtil.log("GeneralTemplate20 => onClick => " + e.getMessage());
                            }
                        } else if (id == R.id.general_template20b_type2_item1) {
                            try {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(2);
                                FavBean tempFav = templateBean.getTempFav();
                                FavBean.ItemBean itemBean = tempFav.getRows().get(0);
                                itemBean.setToType(1);
                                itemBean.setCid(itemBean.getAlbum().getCid());
                                JumpUtil.next(view.getContext(), itemBean);
                            } catch (Exception e) {
                                LogUtil.log("GeneralTemplate20 => onClick => " + e.getMessage());
                            }
                        } else if (id == R.id.general_template20b_type2_item2) {
                            try {
                                GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean = list.get(2);
                                FavBean tempFav = templateBean.getTempFav();
                                FavBean.ItemBean itemBean = tempFav.getRows().get(1);
                                itemBean.setToType(1);
                                itemBean.setCid(itemBean.getAlbum().getCid());
                                JumpUtil.next(view.getContext(), itemBean);
                            } catch (Exception e) {
                                LogUtil.log("GeneralTemplate20 => onClick => " + e.getMessage());
                            }
                        } else {
                            JumpUtil.nextCenter(view.getContext(), false);
                        }
                    }
                });
            }

            // onFocusChange
            for (int m = 0; m < 6; m++) {
                View v;
                if (m == 0) {
                    v = view.findViewById(R.id.general_template20b_type1_item1);
                } else if (m == 1) {
                    v = view.findViewById(R.id.general_template20b_type1_item2);
                } else if (m == 2) {
                    v = view.findViewById(R.id.general_template20b_type1_item3);
                } else if (m == 3) {
                    v = view.findViewById(R.id.general_template20b_type2_item1);
                } else if (m == 4) {
                    v = view.findViewById(R.id.general_template20b_type2_item2);
                } else {
                    v = view.findViewById(R.id.general_template20b_type3);
                }
                v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        int id = view.getId();
                        if (id == R.id.general_template20b_type1_item1) {
                            TextView v1 = v.findViewById(R.id.general_template20b_type1_item1_name);
                            v1.setActivated(b);
                            v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                            TextView v2 = v.findViewById(R.id.general_template20b_type1_item1_date);
                            v2.setActivated(b);
                            v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } else if (id == R.id.general_template20b_type1_item2) {
                            TextView v1 = v.findViewById(R.id.general_template20b_type1_item2_name);
                            v1.setActivated(b);
                            v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                            TextView v2 = v.findViewById(R.id.general_template20b_type1_item2_date);
                            v2.setActivated(b);
                            v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } else if (id == R.id.general_template20b_type1_item3) {
                            TextView v1 = v.findViewById(R.id.general_template20b_type1_item3_name);
                            v1.setActivated(b);
                            v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } else if (id == R.id.general_template20b_type2_item1) {
                            TextView v1 = v.findViewById(R.id.general_template20b_type2_item1_name);
                            v1.setActivated(b);
                            v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                            TextView v2 = v.findViewById(R.id.general_template20b_type2_item1_date);
                            v2.setActivated(b);
                            v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } else if (id == R.id.general_template20b_type2_item2) {
                            TextView v1 = v.findViewById(R.id.general_template20b_type2_item2_name);
                            v1.setActivated(b);
                            v1.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v1.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                            TextView v2 = v.findViewById(R.id.general_template20b_type2_item2_date);
                            v2.setActivated(b);
                            v2.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            v2.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        } else {
                            TextView textView = v.findViewById(R.id.general_template20b_type3_name);
                            textView.setActivated(b);
                            textView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                            textView.setTextColor(v.getResources().getColor(b ? R.color.color_black : R.color.color_aaaaaa));
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onBindHolder(@NonNull View v, @NonNull GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean, @NonNull int position, @NonNull int viewType) {

        // type1
        if (viewType == 1) {

            // visable
            try {
                FavBean fav = templateBean.getTempFav();
                int num = fav.getRows().size();
                v.findViewById(R.id.general_template20b_type1_item1).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_template20b_type1_item2).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_template20b_type1_item3).setVisibility(num >= 2 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_template20b_type2_item1).setVisibility(num == 1 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_template20b_type2_item2).setVisibility(num == 1 ? View.VISIBLE : View.GONE);
                v.findViewById(R.id.general_template20b_type3).setVisibility(num <= 0 ? View.VISIBLE : View.GONE);
            } catch (Exception e) {
                LogUtil.log("GeneralTemplate20 => onBindHolder => " + e.getMessage());
                v.findViewById(R.id.general_template20b_type1_item1).setVisibility(View.GONE);
                v.findViewById(R.id.general_template20b_type1_item2).setVisibility(View.GONE);
                v.findViewById(R.id.general_template20b_type1_item3).setVisibility(View.GONE);
                v.findViewById(R.id.general_template20b_type2_item1).setVisibility(View.GONE);
                v.findViewById(R.id.general_template20b_type2_item2).setVisibility(View.GONE);
                v.findViewById(R.id.general_template20b_type3).setVisibility(View.VISIBLE);
            }

            // data
            try {
                FavBean fav = templateBean.getTempFav();
                List<FavBean.ItemBean> rows = fav.getRows();
                FavBean.ItemBean bean = rows.get(0);
                TextView textName1 = v.findViewById(R.id.general_template20b_type1_item1_name);
                textName1.setText(bean.getNameRec());
                TextView textState1 = v.findViewById(R.id.general_template20b_type1_item1_date);
                textState1.setText(bean.getStatusRec());
                TextView textName2 = v.findViewById(R.id.general_template20b_type2_item1_name);
                textName2.setText(bean.getNameRec());
                TextView textState2 = v.findViewById(R.id.general_template20b_type2_item1_date);
                textState2.setText(bean.getStatusRec());
            } catch (Exception e) {
                LogUtil.log("GeneralTemplate20 => onBindHolder => " + e.getMessage());
            }

            // data
            try {
                FavBean fav = templateBean.getTempFav();
                List<FavBean.ItemBean> rows = fav.getRows();
                FavBean.ItemBean bean = rows.get(1);
                TextView textName1 = v.findViewById(R.id.general_template20b_type1_item2_name);
                textName1.setText(bean.getNameRec());
                TextView textState1 = v.findViewById(R.id.general_template20b_type1_item2_date);
                textState1.setText(bean.getStatusRec());
                TextView textName2 = v.findViewById(R.id.general_template20b_type2_item2_name);
                textName2.setText(bean.getNameRec());
                TextView textState2 = v.findViewById(R.id.general_template20b_type2_item2_date);
                textState2.setText(bean.getStatusRec());
            } catch (Exception e) {
                LogUtil.log("GeneralTemplate20 => onBindHolder => " + e.getMessage());
            }
        }
        //  type2
        else {
            try {
                TextView textView = v.findViewById(R.id.common_poster_name);
                textView.setText(templateBean.getName());
            } catch (Exception e) {
            }
            try {
                ImageView imageView = v.findViewById(R.id.common_poster_img);
                GlideUtils.loadHz(imageView.getContext(), templateBean.getPicture(true), imageView);
            } catch (Exception e) {
            }
            try {
                ImageView imageView = v.findViewById(R.id.common_poster_vip);
                GlideUtils.loadVt(imageView.getContext(), templateBean.getVipUrl(), imageView);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected int initItemViewType(int position, GetSubChannelsByChannelBean.ListBean.TemplateBean templateBean) {
        if (position <= 1) {
            return 0;
        } else if (position == 2) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected int initLayout(int i) {
        if (i == 0) {
            return R.layout.fragment_general_item_template20a;
        } else if (i == 1) {
            return R.layout.fragment_general_item_template20b;
        } else {
            return R.layout.fragment_general_item_template20c;
        }
    }

    @Override
    protected int initSpan() {
        return 4;
    }

    @Override
    protected int initSpanSize(int position) {
        return position <= 1 ? 2 : 1;
    }

    @Override
    protected int initMax() {
        return 6;
    }

    public static class GeneralTemplate20List extends ArrayList {
    }
}

package tv.huan.bilibili.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Create by Richard
 * 2019-07-29 16:21
 */
public class GetPopupInfoBean extends ResponsedBean {

    private LoadingPicBean loading_pic;
    private PopupPicBean popup_pic;

    public LoadingPicBean getLoading_pic() {
        return loading_pic;
    }

    public void setLoading_pic(LoadingPicBean loading_pic) {
        this.loading_pic = loading_pic;
    }

    public PopupPicBean getPopup_pic() {
        return popup_pic;
    }

    public void setPopup_pic(PopupPicBean popup_pic) {
        this.popup_pic = popup_pic;
    }

    public static class LoadingPicBean {
        /**
         * picture_SD :
         * extras :
         * picture_HD :
         * display_time :
         * webaddress :
         */

        private String picture_SD;
        private String extras;
        private String picture_HD;
        private String display_time;
        private String webaddress;
        private String show_advert;
        private String show_time;

        public String getPicture_SD() {
            return picture_SD;
        }

        public void setPicture_SD(String picture_SD) {
            this.picture_SD = picture_SD;
        }

        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

        public String getPicture_HD() {
            return picture_HD;
        }

        public void setPicture_HD(String picture_HD) {
            this.picture_HD = picture_HD;
        }

        public String getDisplay_time() {
            return display_time;
        }

        public void setDisplay_time(String display_time) {
            this.display_time = display_time;
        }

        public String getWebaddress() {
            return webaddress;
        }

        public void setWebaddress(String webaddress) {
            this.webaddress = webaddress;
        }

        public String getShow_advert() {
            return show_advert;
        }

        public void setShow_advert(String show_advert) {
            this.show_advert = show_advert;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        @Override
        public String toString() {
            return "{" +
                    "picture_SD=\"" + picture_SD + '\"' +
                    ", picture_HD=\"" + picture_HD + '\"' +
                    ", display_time=\"" + display_time + '\"' +
                    ", webaddress=\"" + webaddress + '\"' +
                    ", show_advert=\"" + show_advert + '\"' +
                    ", show_time=\"" + show_time + '\"' +
                    ", extras=\"" + extras + '\"' +
                    '}';
        }
    }

    public static class PopupPicBean implements Parcelable {
        /**
         * picture_SD : http://testsd
         * picture_HD : http://testhd
         * display_time : 3
         * webaddress :
         */
        private String extras;
        private String picture_SD;
        private String picture_HD;
        private String display_time;
        private String webaddress;
        private String show_advert;
        private String show_time;

        protected PopupPicBean(Parcel in) {
            extras = in.readString();
            picture_SD = in.readString();
            picture_HD = in.readString();
            display_time = in.readString();
            webaddress = in.readString();
            show_advert = in.readString();
            show_time = in.readString();
        }

        public static final Creator<PopupPicBean> CREATOR = new Creator<PopupPicBean>() {
            @Override
            public PopupPicBean createFromParcel(Parcel in) {
                return new PopupPicBean(in);
            }

            @Override
            public PopupPicBean[] newArray(int size) {
                return new PopupPicBean[size];
            }
        };

        public String getPicture_SD() {
            return picture_SD;
        }

        public void setPicture_SD(String picture_SD) {
            this.picture_SD = picture_SD;
        }

        public String getPicture_HD() {
            return picture_HD;
        }

        public void setPicture_HD(String picture_HD) {
            this.picture_HD = picture_HD;
        }

        public String getDisplay_time() {
            return display_time;
        }

        public void setDisplay_time(String display_time) {
            this.display_time = display_time;
        }

        public String getWebaddress() {
            return webaddress;
        }

        public void setWebaddress(String webaddress) {
            this.webaddress = webaddress;
        }

        public String getShow_advert() {
            return show_advert;
        }

        public void setShow_advert(String show_advert) {
            this.show_advert = show_advert;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        public String getExtras() {
            return extras;
        }

        public void setExtras(String extras) {
            this.extras = extras;
        }

        @Override
        public String toString() {
            return"{" +
                    "picture_SD=\"" + picture_SD + '\"' +
                    ", picture_HD=\"" + picture_HD + '\"' +
                    ", display_time=\"" + display_time + '\"' +
                    ", webaddress=\"" + webaddress + '\"' +
                    ", show_advert=\"" + show_advert + '\"' +
                    ", show_time=\"" + show_time + '\"' +
                    ", extras=\"" + extras + '\"' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(extras);
            dest.writeString(picture_SD);
            dest.writeString(picture_HD);
            dest.writeString(display_time);
            dest.writeString(webaddress);
            dest.writeString(show_advert);
            dest.writeString(show_time);
        }
    }
}

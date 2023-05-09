package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

import tv.huan.bilibili.BuildConfig;
import tv.huan.bilibili.bean.base.BaseImageBean;
import tv.huan.bilibili.utils.StringUtils;

@Keep
public final class MediaDetailBean extends BaseImageBean implements Serializable {

    private String columnId;
    private int type;
    private int isTrailer;
    private String seconditle;
    private String director;
    private String language;
    private int status;
    private int positiveTrailer;
    private String score;
    private String subtype;
    private String tag;
    private String copyright;
    private String leadingActor;
    private String guests;
    private String publishDate;
    private String episodeAll;
    private String episodeUpdated;
    private String areaName;
    private String year;
    private String videoIds;
    private String description;
    private String resolutionList;
    private String url;
    private String encryptionType;

    private int payStatus; //付费类型  0免费;  1收费
    private int playType = -1; //播放策略(payStatus = 1时生效)  0:全部收费;  1:一集免费;  2:两集免费

    private String price;//名字
    private String productName;//单点名字
    private int validTerm;//单点天数
    private int productType;//1.单点2.专区3会员4专区周期
    private int productCodeStatus; //可否购买

    private String code;//计费吗
    private String splitTag;//标签
    private String[] picList; // 标签集合

    private int detailType; //详情页UI显示分类：1电影   2电视剧   3综艺


    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 电影，电视剧
            if (detailType == BuildConfig.HUAN_TYPE_FILM || detailType == BuildConfig.HUAN_TYPE_TELEPLAY) {
                String separatorStr = "";
                if(!StringUtils.isEmpty(getDirector()) && !StringUtils.isEmpty(getLeadingActor())){
                    separatorStr = ",";
                } else if(StringUtils.isEmpty(getDirector()) && StringUtils.isEmpty(getLeadingActor())){
                    separatorStr = "暂无";
                }

                if (getDirector().equals(getLeadingActor())){
                    stringBuilder.append("演职人员： " + getDirector());
                } else{
                    stringBuilder.append("演职人员： " + getDirector().concat(separatorStr).concat(getLeadingActor()));
                }
                stringBuilder.append("\n");
            }
            // 综艺，体育
            else if (detailType == BuildConfig.HUAN_TYPE_VARIETY || detailType == BuildConfig.HUAN_TYPE_SPORTS) {
                stringBuilder.append("嘉   宾： " + getGuests());
                stringBuilder.append("\n");
            }
            // 少儿，动漫
            else if (detailType == BuildConfig.HUAN_TYPE_ANIME || detailType == BuildConfig.HUAN_TYPE_CHILDREN) {
                stringBuilder.append("导   演： " + getDirector());
                stringBuilder.append("\n");
            }
            // 教育
            else if (detailType == BuildConfig.HUAN_TYPE_EDUCATION) {
            }
            // 默认
            else {
                stringBuilder.append("嘉   宾： " + getDirector());
                stringBuilder.append("\n");
            }

            // 简介
            String desc = getDescription();
            if (desc.contains("杜比")) {
                stringBuilder.append("杜   比： " + desc);
            } else {
                stringBuilder.append("简   介： " + desc);
            }
        } catch (Exception e) {
        }
        return stringBuilder.toString();
    }

    public String[] getPicList() {
        return picList;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public String getSplitTag() {
        String separatStr1 = StringUtils.isEmpty(getScore()) ? "暂无评分 | " : "分 | ";
        String separatStr2 = "";
        if(!StringUtils.isEmpty(getYear()) && !StringUtils.isEmpty(getAreaName())){
            separatStr2 = " | ";
        }
        if(StringUtils.isEmpty(getYear()) && StringUtils.isEmpty(getAreaName())){
            return getScore();
        } else{
            return getScore().concat(separatStr1).concat(getYear()).concat(separatStr2).concat(getAreaName());
        }
    }

    public void setSplitTag(String splitTag) {
        this.splitTag = splitTag;
    }

    public int getDetailType() {
        return detailType;
    }

    public void setDetailType(int detailType) {
        this.detailType = detailType;
    }

    public int getProductCodeStatus() {
        return productCodeStatus;
    }

    public void setProductCodeStatus(int productCodeStatus) {
        this.productCodeStatus = productCodeStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getValidTerm() {
        return validTerm;
    }

    public void setValidTerm(int validTerm) {
        this.validTerm = validTerm;
    }


    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsTrailer() {
        return isTrailer;
    }

    public void setIsTrailer(int isTrailer) {
        this.isTrailer = isTrailer;
    }

    public String getSeconditle() {
        return seconditle;
    }

    public void setSeconditle(String seconditle) {
        this.seconditle = seconditle;
    }

    public String getDirector() {
        if (null == director || director.length() <= 0) {
            return "暂无";
        } else {
            return director;
        }
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPositiveTrailer() {
        return positiveTrailer;
    }

    public void setPositiveTrailer(int positiveTrailer) {
        this.positiveTrailer = positiveTrailer;
    }

    public String getScore() {
        if(null == score || score.equals("无")){
            return "";
        }
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLeadingActor() {
        if (null == leadingActor || leadingActor.length() <= 0) {
            return "暂无";
        } else {
            return leadingActor;
        }
    }

    public void setLeadingActor(String leadingActor) {
        this.leadingActor = leadingActor;
    }

    public String getGuests() {
        if (null == guests || guests.length() <= 0) {
            return "暂无";
        } else {
            return guests;
        }
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getEpisodeAll() {
        return episodeAll;
    }

    public void setEpisodeAll(String episodeAll) {
        this.episodeAll = episodeAll;
    }

    public String getEpisodeUpdated() {
        return episodeUpdated;
    }

    public void setEpisodeUpdated(String episodeUpdated) {
        this.episodeUpdated = episodeUpdated;
    }

    public String getAreaName() {
        if(null == areaName || areaName.equals("无")){
            return "";
        }
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getYear() {
        if(null == year || year.equals("无")){
            return "";
        }
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(String videoIds) {
        this.videoIds = videoIds;
    }

    public String getDescription() {
        try {
            return description.replace("\\n", "\n");
        } catch (Exception e) {
            return "";
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolutionList() {
        return resolutionList;
    }

    public void setResolutionList(String resolutionList) {
        this.resolutionList = resolutionList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public boolean isXuanQi() {
        //  选期 => 教育、体育、综艺
        if (detailType == BuildConfig.HUAN_TYPE_EDUCATION || detailType == BuildConfig.HUAN_TYPE_SPORTS || detailType == BuildConfig.HUAN_TYPE_VARIETY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isXuanJi() {
        // 电影
        if (detailType == BuildConfig.HUAN_TYPE_FILM) {
            return false;
        }
        //  选期 => 教育、体育、综艺
        else if (detailType == BuildConfig.HUAN_TYPE_EDUCATION || detailType == BuildConfig.HUAN_TYPE_SPORTS || detailType == BuildConfig.HUAN_TYPE_VARIETY) {
            return false;
        }
        // 选集
        else {
            return true;
        }
    }
}

package tv.huan.bilibili.bean;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

public class ProgramDetail implements Serializable {

    private String id;
    private String cid;
    private String title;
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
    private String newPicVt;
    private String newPicHz;
    private String picVtPath;
    private String picHzPath;
    private int payStatus; //0周期，-1免费，其他单点
    private String areaName;
    private String year;
    private String videoIds;
    private String description;
    private String resolutionList;
    private String url;
    private String encryptionType;
    private int playType;
    private String price;//名字
    private String productName;//单点名字
    private int validTerm;//单点天数
    private int productType;//1.单点2.专区3会员4专区周期
    private int productCodeStatus; //可否购买


    private String code;//计费吗
    private String pic;//角标
    private String pic2;//角标
    private String splitTag;//标签
    private String[] picList; // 标签集合

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String[] getPicList() {
        return picList;
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public String getSplitTag() {
        return splitTag;
    }

    public void setSplitTag(String splitTag) {
        this.splitTag = splitTag;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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


    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return director;
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
        return leadingActor;
    }

    public void setLeadingActor(String leadingActor) {
        this.leadingActor = leadingActor;
    }

    public String getGuests() {
        return guests;
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

    public String getNewPicVt() {
        return null == newPicVt || newPicVt.isEmpty() ? picVtPath : newPicVt;
    }

    public void setNewPicVt(String newPicVt) {
        this.newPicVt = newPicVt;
    }

    public String getNewPicHz() {
        return null == newPicHz || newPicHz.isEmpty() ? picHzPath : newPicHz;
    }

    public void setNewPicHz(String newPicHz) {
        this.newPicHz = newPicHz;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getYear() {
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
        return description;
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

    public String getPicVtPath() {
        return picVtPath;
    }

    public void setPicVtPath(String picVtPath) {
        this.picVtPath = picVtPath;
    }

    public String getPicHzPath() {
        return picHzPath;
    }

    public void setPicHzPath(String picHzPath) {
        this.picHzPath = picHzPath;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }


    @Override
    public String toString() {
        return "ProgramDetail{" +
                "id='" + id + '\'' +
                ", cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", columnId='" + columnId + '\'' +
                ", type=" + type +
                ", isTrailer=" + isTrailer +
                ", seconditle='" + seconditle + '\'' +
                ", director='" + director + '\'' +
                ", language='" + language + '\'' +
                ", status=" + status +
                ", positiveTrailer=" + positiveTrailer +
                ", score='" + score + '\'' +
                ", subtype='" + subtype + '\'' +
                ", tag='" + tag + '\'' +
                ", copyright='" + copyright + '\'' +
                ", leadingActor='" + leadingActor + '\'' +
                ", guests='" + guests + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", episodeAll='" + episodeAll + '\'' +
                ", episodeUpdated='" + episodeUpdated + '\'' +
                ", newPicVt='" + newPicVt + '\'' +
                ", newPicHz='" + newPicHz + '\'' +
                ", picVtPath='" + picVtPath + '\'' +
                ", picHzPath='" + picHzPath + '\'' +
                ", payStatus=" + payStatus +
                ", areaName='" + areaName + '\'' +
                ", year='" + year + '\'' +
                ", videoIds='" + videoIds + '\'' +
                ", description='" + description + '\'' +
                ", resolutionList='" + resolutionList + '\'' +
                ", url='" + url + '\'' +
                ", encryptionType='" + encryptionType + '\'' +
                ", playType=" + playType +
                ", price='" + price + '\'' +
                ", productName='" + productName + '\'' +
                ", validTerm=" + validTerm +
                ", productType=" + productType +
                ", code='" + code + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }

    // 新加2021年10月26日
    private List<Item> productCodes; // 计费类型【0免费，1单点，2专区单点，3周期，4专区周期】

    public List<Item> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<Item> productCodes) {
        this.productCodes = productCodes;
    }

    @Keep
    public static class Item {

        private int status;
        private int id;
        private int productType;
        private String name;
        private String code;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}

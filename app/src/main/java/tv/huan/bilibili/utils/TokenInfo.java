/**
 * Created by IntelliJ IDEA.
 * User: Nuke
 * Date: 2009-6-17
 * Time: 12:07:22
 * To change this template use File | Settings | File Templates.
 */
package tv.huan.bilibili.utils;

/**
 * token所包含的各元素的信息
 */
public class TokenInfo {
    /**
     * 用户标识
     */
    private String userId;


    /**
     * 计费系统认证的序列号
     */
    private String seq;

    /**
     * EPG系统产生的序列号
     */
    private String ossSeq;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 播放内容名称
     */
    private String contentName;
    /**
     * 系统生成文件的地址
     */
    private String relativeUrl;


    /**
     *
     */
    public TokenInfo() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getOssSeq() {
        return ossSeq;
    }

    public void setOssSeq(String ossSeq) {
        this.ossSeq = ossSeq;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }
}


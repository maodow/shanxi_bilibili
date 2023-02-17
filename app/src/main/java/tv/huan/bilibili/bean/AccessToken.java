package tv.huan.bilibili.bean;

/**
 * Create by Richard
 * 2019-07-25 13:17
 */
public class AccessToken {

    private String id;
    private String accessToken;
    private String expireIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }
}

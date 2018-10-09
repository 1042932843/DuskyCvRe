package nbsix.com.duskycvre.Entity.HomePage;

import android.support.annotation.NonNull;
/**
 * Name: Task
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 广告轮播
 * Date: 2017-05-04 12:18
 */

public class BannerBean {


    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getImg() {
        return img;
    }

    @NonNull
    public String getPublisher() {
        return publisher;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @NonNull
    public String getMsg() {
        return msg;
    }

    @NonNull
    public String getGoodsid() {
        return goodsid;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    private String id;
    @NonNull
    private String img;
    @NonNull
    private String publisher;
    @NonNull
    private String time;
    @NonNull
    private String msg;
    @NonNull
    private String goodsid;
    @NonNull
    private String url;



}

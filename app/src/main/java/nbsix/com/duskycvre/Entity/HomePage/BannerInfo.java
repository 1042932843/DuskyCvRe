package nbsix.com.duskycvre.Entity.HomePage;

import java.util.List;

/**
 * Name: Task
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 广告轮播
 * Date: 2017-05-04 12:18
 */

public class BannerInfo {
    private String code;
    private List<BannerBean> ad;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BannerBean> getAd() {
        return ad;
    }

    public void setAd(List<BannerBean> ad) {
        this.ad = ad;
    }
}

package nbsix.com.duskycvre.Network.api;

import io.reactivex.Observable;
import nbsix.com.duskycvre.Entity.HomePage.BannerInfo;
import retrofit2.http.GET;

/**
 * Name: HomePageService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-06-25 22:44
 */

public interface HomePageService {
        /**
         * banner数据
         */
        @GET("homemaking/api/ad/getAll")
        Observable<BannerInfo> getHomePageRecommendedBannerInfo();
}

package guo.guo.mainitem._2rxjava2.rxjavaItem.Rx_Retrofit2;

import java.util.List;

import guo.guo.other.MsgBean;
import guo.guo.other.URLBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：author
 * 时间：2017/11/15:14:28
 * 说明：
 */

public interface RetService {
    @GET("Platform/NewWeb/ashx/NewWeb.ashx?method=getServerInfoByUser")
    Observable<List<URLBean>> getPath(@Query("userId") String id);

    @GET("Platform/NewWeb/ashx/NewWeb.ashx?method=getUrlInfoByUser")
    Observable<MsgBean> getMessage(@Query("userId") String id);
}

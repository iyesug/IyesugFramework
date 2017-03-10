package com.test.iyesug.iyesugframework.http;

import com.test.iyesug.iyesugframework.data.bean.GirlsBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by Administrator on 2017/2/27.
 */
public interface Api {

    @GET("api/data/{type}/{count}/{page}")
    Observable<GirlsBean> getgirls(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page

    );

}

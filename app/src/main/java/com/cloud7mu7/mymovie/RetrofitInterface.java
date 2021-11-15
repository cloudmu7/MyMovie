package com.cloud7mu7.mymovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    Call<Result> getBoxOffice(@Query("key") String key, @Query("targetDt") String targetDt);

    @Headers("Authorization: KakaoAK 237ba5da6dbfae523eb79f719cbee0bd")
    @GET("/v2/local/search/keyword.json")
    Call<SearchLocalApiResponse> searchPlace (@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);
}

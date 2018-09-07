package com.anwar014.touristspot.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by abc on 04-04-2018.
 */

public interface MyApi {

    @GET("tourist_spot_data.html")
    Call<List<tourist_model>> getData();

}

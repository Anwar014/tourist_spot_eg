package com.anwar014.touristspot.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 04-04-2018.
 */

public class MyService {
    public static MyApi getService(){
        return new Retrofit
                .Builder()
                .baseUrl("https://raw.githubusercontent.com/Anwar014/hello-world/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi.class);
    }
}

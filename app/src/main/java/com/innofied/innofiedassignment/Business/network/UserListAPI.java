package com.innofied.innofiedassignment.Business.network;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohit on 9/23/2019.
 */

public class UserListAPI {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://reqres.in/api/")
                    .build();
        }
        return retrofit;
    }
}

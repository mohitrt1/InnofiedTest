package com.innofied.innofiedassignment.Business.network;

import com.innofied.innofiedassignment.Model.UserListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohit on 9/23/2019.
 */

public interface RetrofitService {
    @GET("users")
    Call<UserListData> getUserList(
            @Query("page") int pageIndex,
            @Query("per_page") int count);
}

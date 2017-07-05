package com.cloudwalkdigital.aims.data;

import com.cloudwalkdigital.aims.data.model.JobOrder;
import com.cloudwalkdigital.aims.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by alleoindong on 7/5/17.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("auth")
    Call<User> authenticate(@Field("email") String email, @Field("password") String password);

    // Job Orders
    @GET("v1/mobile/job-orders")
    Call<List<JobOrder>> getJobOrders(@Query("api_token") String apiToken);
}

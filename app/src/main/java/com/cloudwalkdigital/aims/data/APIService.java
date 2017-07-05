package com.cloudwalkdigital.aims.data;

import com.cloudwalkdigital.aims.data.model.Discussion;
import com.cloudwalkdigital.aims.data.model.JobOrder;
import com.cloudwalkdigital.aims.data.model.Question;
import com.cloudwalkdigital.aims.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @FormUrlEncoded
    @POST("v1/job-orders/{jobOrderId}/discussions")
    Call<Discussion> sendMessage(@Field("message") String message,
                                 @Field("user_id") Integer userId,
                                 @Path("jobOrderId") Integer jobOrderId,
                                 @Query("api_token") String apiToken);

    // Validate
    @GET("v1/validate/ratees/{jobOrderId}/{validateType}")
    Call<List<User>> getRatees(@Path("jobOrderId") Integer jobOrderId,
                               @Path("validateType") String validateType,
                               @Query("api_token") String apiToken);

    @GET("v1/validate/questions/{rateeId}/{jobOrderId}/{validateType}")
    Call<List<Question>> getQuestions(@Path("rateeId") Integer rateeId, @Path("jobOrderId") Integer jobOrderId,
                                      @Path("validateType") String validateType,
                                      @Query("api_token") String apiToken);
}

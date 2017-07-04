package com.cloudwalkdigital.aims.data;

import com.cloudwalkdigital.aims.data.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alleoindong on 7/5/17.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("auth")
    Call<User> authenticate(@Field("email") String email, @Field("password") String password);
}

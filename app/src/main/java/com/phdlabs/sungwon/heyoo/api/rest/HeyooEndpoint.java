package com.phdlabs.sungwon.heyoo.api.rest;

import com.phdlabs.sungwon.heyoo.api.data.LoginData;
import com.phdlabs.sungwon.heyoo.api.data.VerifyData;
import com.phdlabs.sungwon.heyoo.api.response.UserDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by SungWon on 5/8/2017.
 */

public interface HeyooEndpoint {
    /*
        Interface containing all of the endpoints for api calls
     */

    final String AUTHORIZATION = "x-access-token";

    @POST("/users")
    Call<UserDataResponse> register(@Body LoginData loginData);

    @POST("/auth/verify")
    Call<UserDataResponse> verify(@Body VerifyData verifyData);

}

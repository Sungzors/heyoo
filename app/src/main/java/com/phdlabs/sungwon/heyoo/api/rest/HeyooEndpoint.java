package com.phdlabs.sungwon.heyoo.api.rest;

import com.phdlabs.sungwon.heyoo.api.data.CalendarPostData;
import com.phdlabs.sungwon.heyoo.api.data.LoginData;
import com.phdlabs.sungwon.heyoo.api.data.ResendData;
import com.phdlabs.sungwon.heyoo.api.data.VerifyData;
import com.phdlabs.sungwon.heyoo.api.response.CalendarPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.CalendarRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.response.ResendResponse;
import com.phdlabs.sungwon.heyoo.api.response.UserDataResponse;
import com.phdlabs.sungwon.heyoo.api.response.VerifyDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by SungWon on 5/8/2017.
 */

public interface HeyooEndpoint {
    /*
        Interface containing all of the endpoints for api calls
     */

    String TOKEN = "x-access-token";



    @POST("/users")
    Call<UserDataResponse> register(@Body LoginData loginData);

    @POST("/auth/verify")
    Call<VerifyDataResponse> verify(@Body VerifyData verifyData);

    @POST("/auth/resend")
    Call<ResendResponse> resend(@Body ResendData resendData);

    @GET("/calendars")
    Call<CalendarRetrievalResponse> getCalendar(@Header(TOKEN)String token);

    @POST("/calendars")
    Call<CalendarPostResponse> postCalendar(@Header(TOKEN)String token, @Body CalendarPostData data);

}

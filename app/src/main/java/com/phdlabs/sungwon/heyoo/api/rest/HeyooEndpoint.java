package com.phdlabs.sungwon.heyoo.api.rest;

import com.phdlabs.sungwon.heyoo.api.data.CalendarPostData;
import com.phdlabs.sungwon.heyoo.api.data.EventPatchData;
import com.phdlabs.sungwon.heyoo.api.data.EventPostData;
import com.phdlabs.sungwon.heyoo.api.data.LoginData;
import com.phdlabs.sungwon.heyoo.api.data.ResendData;
import com.phdlabs.sungwon.heyoo.api.data.VerifyData;
import com.phdlabs.sungwon.heyoo.api.response.CalendarPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.CalendarRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventMediaPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventPatchResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventPostResponse;
import com.phdlabs.sungwon.heyoo.api.response.EventRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.response.ResendResponse;
import com.phdlabs.sungwon.heyoo.api.response.UserDataResponse;
import com.phdlabs.sungwon.heyoo.api.response.UserRetrievalResponse;
import com.phdlabs.sungwon.heyoo.api.response.VerifyDataResponse;
import com.phdlabs.sungwon.heyoo.model.HeyooAttendee;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("/events")
    Call<EventRetrievalResponse> getEvents(@Header(TOKEN)String token);

    @POST("/events")
    Call<EventPostResponse> postEvents(@Header(TOKEN)String token, @Body EventPostData data);

    @PATCH("/events/{id}")
    Call<EventPatchResponse> patchEvents(@Path("id") int eventID, @Header(TOKEN)String token, @Body EventPatchData data);

    @POST("/events/{id}/media")
    Call<EventMediaPostResponse> postEventMedia(@Path("id") int eventID, @Header(TOKEN)String token, @Body RequestBody data);

    @POST("/events/{id}/attendees")
    Call<EventPostResponse> postAttendees(@Path("id") int eventID, @Header(TOKEN)String token, @Body List<HeyooAttendee> data);

    @POST("/media")
    Call<EventMediaPostResponse> postEmptyMedia(@Header(TOKEN)String token, @Body RequestBody data);

    @GET("/users")
    Call<UserRetrievalResponse> getUsers(@Header(TOKEN)String token);

}

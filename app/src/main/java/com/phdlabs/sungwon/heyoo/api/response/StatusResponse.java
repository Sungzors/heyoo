package com.phdlabs.sungwon.heyoo.api.response;

/**
 * Created by SungWon on 5/8/2017.
 */

public class StatusResponse {

    public static final String STATUS_ERROR = "false";
    public static final String STATUS_SUCCESS = "true";

    private String success;

    public String getStatus() {
        return success;
    }

    public boolean isError() {
        return STATUS_ERROR.equals(success);
    }

    public boolean isSuccess() {
        return STATUS_SUCCESS.equals(success);
    }
}

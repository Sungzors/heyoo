package com.phdlabs.sungwon.heyoo.api.response;

/**
 * Created by SungWon on 5/8/2017.
 */

public class StatusResponse {

    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCESS = "success";

    private String status;

    public String getStatus() {
        return status;
    }

    public boolean isError() {
        return STATUS_ERROR.equals(status);
    }

    public boolean isSuccess() {
        return STATUS_SUCCESS.equals(status);
    }
}

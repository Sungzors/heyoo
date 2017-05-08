package com.phdlabs.sungwon.heyoo.api.event;

/**
 * Created by SungWon on 5/8/2017.
 */

public abstract class Event {

    private boolean isSuccess;
    private String errorMessage;

    public Event() {
        isSuccess = true;
    }

    public Event(String errorMessage) {
        this.errorMessage = errorMessage;
        isSuccess = false;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        isSuccess = errorMessage == null;
    }
}

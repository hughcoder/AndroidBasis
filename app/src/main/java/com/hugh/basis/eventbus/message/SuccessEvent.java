package com.hugh.basis.eventbus.message;

/**
 * Created by chenyw on 2019-07-10.
 */
public class SuccessEvent {
    private String message;

    public SuccessEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

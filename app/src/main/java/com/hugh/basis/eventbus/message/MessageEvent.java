package com.hugh.basis.eventbus.message;

/**
 * Created by chenyw on 2019-07-05.
 */
public class MessageEvent {

    private String message;

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
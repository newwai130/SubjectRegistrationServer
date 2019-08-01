package com.bot.polysubject.exception;

public enum ApiExceptionType {

    UserNotFound(10, "User Not Found");

    private Integer code;
    private  String message;

    ApiExceptionType(int code, String message){
        this.code = code;
        this.message = message;
    }
}

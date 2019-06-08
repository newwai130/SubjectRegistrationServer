package com.bot.polysubject.Exception;

public enum ApiExceptionType {

    RecordNotFound(10, "Record Not Found");

    private Integer code;
    private  String message;

    ApiExceptionType(int code, String message){
        this.code = code;
        this.message = message;
    }
}

package com.bot.polysubject.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException{
    private ApiExceptionType apiExceptionType;
}

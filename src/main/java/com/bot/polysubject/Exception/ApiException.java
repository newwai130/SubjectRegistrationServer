package com.bot.polysubject.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException{
    private ApiExceptionType apiExceptionType;
}

package com.appraisal.common.advice;

import com.appraisal.common.APIResponse;
import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.util.ApiErrorResponseService;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorControllerAdvice {

    private final ApiErrorResponseService apiErrorResponseService;

    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ErrorControllerAdvice::getValidationMessage)
                .collect(Collectors.toList());
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            String className = fieldError.getObjectName();
            String property = fieldError.getField();
            Object invalidValue = fieldError.getRejectedValue();
            String message = fieldError.getDefaultMessage();
            return String.format("%s.%s %s, but it was %s", className, property, message, invalidValue);
        }

        return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse handleJavaxValidationException(MethodArgumentNotValidException e) {
        String errorMessage = getValidationMessage(e.getBindingResult()).toString();
        return apiErrorResponseService.buildErrorResponse(ResponseCode.BAD_REQUEST.getCode(), errorMessage, e);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIResponse handleBadRequestApiException(BadRequestException e) {
        return apiErrorResponseService.buildErrorResponse(e.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public APIResponse handleNotFoundApiException(NotFoundException e) {
        return apiErrorResponseService.buildErrorResponse(e.getCode(), e.getMessage(), e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return apiErrorResponseService
                .buildErrorResponse(ResponseCode.BAD_REQUEST.getCode(), "The request could not be completed due to malformed syntax. Kindly check and try again.", e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse handleGeneralException(Exception e) {
        log.error("Server error", e);
        return apiErrorResponseService.buildErrorResponse("Oops, It's not you. Please try again later");
    }

}

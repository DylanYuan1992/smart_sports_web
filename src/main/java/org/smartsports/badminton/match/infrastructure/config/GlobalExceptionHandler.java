package org.smartsports.badminton.match.infrastructure.config;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static HttpStatusCode SYSTEM_ERROR = HttpStatusCode.valueOf(999999);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseError() {
        return ResponseEntity.internalServerError().body(new ErrorResponse() {
            @Override
            public HttpStatusCode getStatusCode() {
                return SYSTEM_ERROR;
            }

            @Override
            public ProblemDetail getBody() {
                return ProblemDetail.forStatusAndDetail(SYSTEM_ERROR, "System Error");
            }
        });
    }
}
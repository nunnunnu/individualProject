package melonproject.melon.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import melonproject.melon.error.ErrorCode;
import melonproject.melon.error.ErrorResponse;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.RequiredValueOmission;

@RestControllerAdvice
public class ControllerSupport {
    @ExceptionHandler(value = MemberNotFound.class)
    public ResponseEntity<ErrorResponse> memberNotFoundException(MemberNotFound ex){
        ErrorResponse error = ErrorResponse.builder()
        .code(ErrorCode.MEMBER_NOT_FOUND)
        .message("회원정보를 찾을 수 없음")
        .status(400)
        .timestamp(LocalDateTime.now())
        .build();

    return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = RequiredValueOmission.class)
    public ResponseEntity<ErrorResponse> requiredValueOmission(RequiredValueOmission rvo){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.REQUIRED_VALUE_OMISSION)
            .message("필수값 누락")
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
}

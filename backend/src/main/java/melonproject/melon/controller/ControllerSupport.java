package melonproject.melon.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import melonproject.melon.error.ErrorCode;
import melonproject.melon.error.ErrorResponse;
import melonproject.melon.error.NotValidExceptionResponse;
import melonproject.melon.error.custom.JoinException;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NoContentException;
import melonproject.melon.error.custom.NotFoundPlaylistException;
import melonproject.melon.error.custom.NotFoundSongException;
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
    @ExceptionHandler(value = NoContentException.class)
    public ResponseEntity<ErrorResponse> requiredValueOmission(NoContentException rvo){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NO_CONTENT)
            .message("값이 존재하지않음")
            .status(204)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(value = NotFoundPlaylistException.class)
    public ResponseEntity<ErrorResponse> notFoundPlaylistException(NotFoundPlaylistException ndp){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_PLAYLIST)
            .message("플레이리스트를 찾을 수 없음. 번호 오류")
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundSongException.class)
    public ResponseEntity<ErrorResponse> notFoundSongException(NotFoundSongException nds){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_SONG)
            .message("곡을 찾을 수 없음. 번호 오류")
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = JoinException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(JoinException ex) {

        // List<String> errorList = ex
        //         .getBindingResult()
        //         .getFieldErrors()
        //         .stream()
        //         .map(DefaultMessageSourceResolvable::getDefaultMessage)
        //         .collect(Collectors.toList());

        return new ResponseEntity<>(
                NotValidExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message("회원가입 실패")
                        .code(ErrorCode.JOIN_FAILED)
                        .status(400)
                        .err(ex.getErr())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}

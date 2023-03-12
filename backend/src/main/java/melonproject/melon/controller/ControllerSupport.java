package melonproject.melon.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import melonproject.melon.error.ErrorCode;
import melonproject.melon.error.ErrorResponse;
import melonproject.melon.error.NotValidExceptionResponse;
import melonproject.melon.error.custom.CommentInputException;
import melonproject.melon.error.custom.JoinException;
import melonproject.melon.error.custom.MemberNotFound;
import melonproject.melon.error.custom.NoContentException;
import melonproject.melon.error.custom.NotFoundAlbumException;
import melonproject.melon.error.custom.NotFoundComment;
import melonproject.melon.error.custom.NotFoundPlayListSong;
import melonproject.melon.error.custom.NotFoundPlaylistException;
import melonproject.melon.error.custom.NotFoundSongException;
import melonproject.melon.error.custom.RequiredValueOmission;

@RestControllerAdvice
public class ControllerSupport {
    @ExceptionHandler(value = MemberNotFound.class)
    public ResponseEntity<ErrorResponse> memberNotFoundException(MemberNotFound ex){
        ErrorResponse error = ErrorResponse.builder()
        .code(ErrorCode.MEMBER_NOT_FOUND)
        .message(ex.getMessage())
        .status(400)
        .timestamp(LocalDateTime.now())
        .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = RequiredValueOmission.class)
    public ResponseEntity<ErrorResponse> requiredValueOmission(RequiredValueOmission rvo){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.REQUIRED_VALUE_OMISSION)
            .message(rvo.getMessage())
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoContentException.class)
    public ResponseEntity<ErrorResponse> requiredValueOmission(NoContentException rvo){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NO_CONTENT)
            .message(rvo.getMessage())
            .status(204)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(value = NotFoundPlaylistException.class)
    public ResponseEntity<ErrorResponse> notFoundPlaylistException(NotFoundPlaylistException ndp){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_PLAYLIST)
            .message(ndp.getMessage())
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundSongException.class)
    public ResponseEntity<ErrorResponse> notFoundSongException(NotFoundSongException nds){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_SONG)
            .message(nds.getMessage())
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundPlayListSong.class)
    public ResponseEntity<ErrorResponse> notFoundPlayListSong(NotFoundPlayListSong nps){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_PLAYLIST_SONG)
            .message(nps.getMessage())
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundAlbumException.class)
    public ResponseEntity<ErrorResponse> notFoundAlbum(NotFoundAlbumException e){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_ALBUM)
            .message(e.getMessage())
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundComment.class)
    public ResponseEntity<ErrorResponse> notFoundComment(NotFoundComment e){
        ErrorResponse error = ErrorResponse.builder()
            .code(ErrorCode.NOT_FOUND_COMMENT)
            .message(e.getMessage())
            .status(400)
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = JoinException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(JoinException ex) {

        return new ResponseEntity<>(
                NotValidExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message("회원가입 실패")
                        .code(ErrorCode.JOIN_FAILED)
                        .status(400)
                        .err(ex.getErr())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CommentInputException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(CommentInputException ex) {
        return new ResponseEntity<>(
                NotValidExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message("댓글 등록 실패")
                        .code(ErrorCode.COMMENT_FAILED)
                        .status(400)
                        .err(ex.getErr())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}

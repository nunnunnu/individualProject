package melonproject.melon.error;

public enum ErrorCode {

    MEMBER_NOT_FOUND(400, "M001", "회원정보를 찾을 수 없음.(토큰에러)"),
    REQUIRED_VALUE_OMISSION(400, "A001", "필수 값 누락"),
    NO_CONTENT(204, "A002", "조회할 컨텐츠가 없음"),
    NOT_FOUND_SONG(400, "P001", "플레이 리스트 번호 오류"),
    NOT_FOUND_PLAYLIST(400, "S001", "곡 번호 오류"),
    NOT_FOUND_PLAYLIST_SONG(400, "P002", "플레이리스트 번호오류 또는 순서 오류"),
    NOT_FOUND_ALBUM(400, "A003", "조회할 앨범이 없음. 앨범번호 오류"),
    COMMENT_FAILED(400, "C001", "댓글 등록 오류"),
    NOT_FOUND_COMMENT(400, "C002", "댓글을 찾을 수 없음. 부모댓글 번호 오류."),
    JOIN_FAILED(400, "M001", "회원가입오류");

    private final String code;
    private final String message;
    private final int status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
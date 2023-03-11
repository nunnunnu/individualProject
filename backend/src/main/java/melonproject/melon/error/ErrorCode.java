package melonproject.melon.error;

public enum ErrorCode {

    MEMBER_NOT_FOUND(400, "M001", "회원정보를 찾을 수 없음.(토큰에러)"),
    REQUIRED_VALUE_OMISSION(400, "A001", "필수 값 누락"),
    NO_CONTENT(204, "A002", "조회할 컨텐츠가 없음"),
    NOT_FOUND_SONG(400, "P001", "플레이 리스트 번호 오류"),
    NOT_FOUND_PLAYLIST(400, "S001", "곡 번호 오류"),
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
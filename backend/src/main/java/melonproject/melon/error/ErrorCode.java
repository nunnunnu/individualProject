package melonproject.melon.error;

public enum ErrorCode {

    // INVALID_PARAMETER(400, null, "Invalid Request Data"),
    // COUPON_EXPIRATION(410, "C001", "Coupon Was Expired"),
    MEMBER_NOT_FOUND(400, "M001", "회원정보를 찾을 수 없음.(토큰에러)"),
    REQUIRED_VALUE_OMISSION(400, "A001", "필수 값 누락"),
    NO_CONTENT(204, "A002", "조회할 컨텐츠가 없음");

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
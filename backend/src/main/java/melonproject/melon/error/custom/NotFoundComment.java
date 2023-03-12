package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundComment extends CustomException {
    public NotFoundComment() {
        super(ErrorCode.NOT_FOUND_COMMENT);
    }
}
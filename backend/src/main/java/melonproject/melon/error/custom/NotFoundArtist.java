package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundArtist extends CustomException {
    public NotFoundArtist() {
        super(ErrorCode.NOT_FOUND_COMMENT);
    }
}
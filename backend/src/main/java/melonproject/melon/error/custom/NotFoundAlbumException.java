package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundAlbumException extends CustomException {
    public NotFoundAlbumException() {
        super(ErrorCode.NOT_FOUND_ALBUM);
    }
}

package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundSongException extends CustomException{
    
    public NotFoundSongException() {
        super(ErrorCode.NOT_FOUND_SONG);
    }
}

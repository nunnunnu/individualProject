package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundPlaylistException extends CustomException{

    public NotFoundPlaylistException() {
        super(ErrorCode.NOT_FOUND_PLAYLIST);
    }
    
}

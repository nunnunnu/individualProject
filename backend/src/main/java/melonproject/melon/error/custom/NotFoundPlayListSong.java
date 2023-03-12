package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundPlayListSong extends CustomException{

    public NotFoundPlayListSong() {
        super(ErrorCode.NOT_FOUND_PLAYLIST_SONG);
    }
    
}

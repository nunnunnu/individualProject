package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NoContentException extends CustomException{

    public NoContentException() {
        super(ErrorCode.NO_CONTENT);
    }
    
}

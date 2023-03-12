package melonproject.melon.error.custom;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import melonproject.melon.error.ErrorCode;
@Getter
public class CommentInputException extends CustomException{
    private List<String> err = new ArrayList<>();
    
    public CommentInputException(List<String> errs) {
        super(ErrorCode.COMMENT_FAILED);
        err.addAll(errs);
    }
}
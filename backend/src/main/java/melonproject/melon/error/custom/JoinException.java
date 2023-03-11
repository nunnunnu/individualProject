package melonproject.melon.error.custom;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import melonproject.melon.error.ErrorCode;
@Getter
public class JoinException extends CustomException{
    private List<String> err = new ArrayList<>();
    
    public JoinException(List<String> errs) {
        super(ErrorCode.JOIN_FAILED);
        err.addAll(errs);
    }
}

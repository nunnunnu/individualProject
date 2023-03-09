package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class RequiredValueOmission extends CustomException{
    
    private static final long serialVersionUID = -2116671122895194101L;

    public RequiredValueOmission() {
        super(ErrorCode.REQUIRED_VALUE_OMISSION);
    }
}

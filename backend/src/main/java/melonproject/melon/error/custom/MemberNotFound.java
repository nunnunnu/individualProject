package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class MemberNotFound extends CustomException {

    private static final long serialVersionUID = -2116671122895194101L;

    public MemberNotFound() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
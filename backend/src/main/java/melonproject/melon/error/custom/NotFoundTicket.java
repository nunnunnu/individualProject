package melonproject.melon.error.custom;

import melonproject.melon.error.ErrorCode;

public class NotFoundTicket extends CustomException {
    public NotFoundTicket() {
        super(ErrorCode.NOT_FOUND_TICKET);
    }
}
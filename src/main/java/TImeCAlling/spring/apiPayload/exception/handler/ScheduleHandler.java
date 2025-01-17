package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class ScheduleHandler extends GeneralException {
    public ScheduleHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class AlarmListHandler extends GeneralException {

    public AlarmListHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

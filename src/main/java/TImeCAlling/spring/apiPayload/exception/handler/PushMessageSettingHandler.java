package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class PushMessageSettingHandler extends GeneralException {

    public PushMessageSettingHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

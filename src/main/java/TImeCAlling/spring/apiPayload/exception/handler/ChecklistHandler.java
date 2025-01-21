package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class ChecklistHandler extends GeneralException {
    public ChecklistHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

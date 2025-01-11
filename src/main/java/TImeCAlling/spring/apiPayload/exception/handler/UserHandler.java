package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {
    
    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class TokenHandler extends GeneralException {
    public TokenHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}

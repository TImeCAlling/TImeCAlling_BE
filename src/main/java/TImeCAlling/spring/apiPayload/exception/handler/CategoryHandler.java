package TImeCAlling.spring.apiPayload.exception.handler;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.exception.GeneralException;

public class CategoryHandler extends GeneralException {
    public CategoryHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

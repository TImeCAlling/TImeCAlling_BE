package TImeCAlling.spring.apiPayload.code.status;

import TImeCAlling.spring.apiPayload.BaseErrorCode;
import TImeCAlling.spring.apiPayload.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "유저를 찾을 수 없습니다."),

    // 카테고리 관련
    CATEGORY_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "CATEGORY4001", "해당 카테고리가 이미 존재합니다.")
    ;
    
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    
    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }
    
    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}

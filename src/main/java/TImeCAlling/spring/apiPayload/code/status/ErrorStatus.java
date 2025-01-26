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
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    
    // 유저 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "유저를 찾을 수 없습니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 유저입니다."),

    // 토큰 관련 에러
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "TOKEN4002", "잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN4003", "토큰이 만료되었습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4004", "지원되지 않는 JWT 토큰입니다."),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN4005", "액세스 토큰이 만료되지 않았습니다."),

    // 푸시 메세지 세팅 관련 에러
    PUSH_SETTING_NOT_FOUND(HttpStatus.NOT_FOUND, "PUSHSETTING4001", "푸시 메세지 세팅을 찾을 수 없습니다."),

    // S3 이미지 업로드 관련
    FILE_NOT_UPLOADED(HttpStatus.BAD_REQUEST, "S34001", "이미지를 업로드 할 수 없습니다."),
    FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "S34002", "파일이 비어있습니다."),
    FILE_NOT_IMAGE(HttpStatus.BAD_REQUEST, "S34003", "이미지 파일만 업로드 가능합니다."),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.BAD_REQUEST, "S34004", "삭제 중 에러가 발생했습니다."),

    // 일정 관련 에러
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE4001", "일정을 찾을 수 없습니다."),
    SCHEDULE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "SCHEDULE4002", "일정 삭제에 실패했습니다."),
    RECURRING_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE4003", "반복 일정 정보가 정상적으로 저장되지 않았습니다."),
    SCHEDULE_SHARE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "SCHEDULE4004", "일정의 공유 ID를 찾을 수 업습니다."),
    SCHEDULE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "SCHEDULE4005", "이미 존재하는 일정입니다."),
    SCHEDULE_NAME_MISMATCH(HttpStatus.BAD_REQUEST, "SCHEDULE4006", "공유 일정은 이름을 수정할 수 없습니다."),
    SCHEDULE_DATE_MISMATCH(HttpStatus.BAD_REQUEST, "SCHEDULE4007", "공유 일정은 날짜를 수정할 수 없습니다."),
    SCHEDULE_TIME_MISMATCH(HttpStatus.BAD_REQUEST, "SCHEDULE4008", "공유 일정은 시간을 수정할 수 없습니다."),
    SCHEDULE_PLACE_MISMATCH(HttpStatus.BAD_REQUEST, "SCHEDULE4009", "공유 일정은 장소를 수정할 수 없습니다."),
    SCHEDULE_LONGITUDE_MISMATCH(HttpStatus.BAD_REQUEST, "SCHEDULE4010", "공유 일정은 경도를 수정할 수 없습니다."),
    SCHEDULE_LATITUDE_MISMATCH(HttpStatus.BAD_REQUEST, "SCHEDULE4011", "공유 일정은 위도를 수정할 수 없습니다."),

    // 체크리스트 관련 에러
    SPARE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST4001", "해당 값을 찾을 수 없습니다."),
    EXTERNAL_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST4002", "해당 값을 찾을 수 없습니다."),
    LATE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST4003", "해당 값을 찾을 수 없습니다."),
    REASON_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST4004", "해당 값을 찾을 수 없습니다."),
    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "CHECKLIST4005", "체크리스트를 찾을 수 없습니다."),

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

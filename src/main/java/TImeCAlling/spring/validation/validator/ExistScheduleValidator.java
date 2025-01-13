package TImeCAlling.spring.validation.validator;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.repository.schedule.ScheduleRepository;
import TImeCAlling.spring.validation.annotation.ExistSchedule;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistScheduleValidator implements ConstraintValidator<ExistSchedule, Long> {
    private final ScheduleRepository scheduleRepository;

    @Override
    public void initialize(ExistSchedule constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean result = scheduleRepository.existsById(value);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.SCHEDULE_NOT_FOUND.toString()).addConstraintViolation();
        }
        return result;
    }
}


package TImeCAlling.spring.validation.validator;

import TImeCAlling.spring.apiPayload.code.status.ErrorStatus;
import TImeCAlling.spring.repository.schedule.ChecklistRepository;
import TImeCAlling.spring.validation.annotation.ExistChecklist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistChecklistValidator implements ConstraintValidator<ExistChecklist, Long> {
    private final ChecklistRepository checklistRepository;

    @Override
    public void initialize(ExistChecklist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean result = checklistRepository.existsById(value);
        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CHECKLIST_NOT_FOUND.toString()).addConstraintViolation();
        }
        return result;
    }
}


package TImeCAlling.spring.validation.annotation;

import TImeCAlling.spring.validation.validator.ExistChecklistValidator;
import TImeCAlling.spring.validation.validator.ExistScheduleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = ExistChecklistValidator.class)
public @interface ExistChecklist {
    String message() default "해당하는 체크리스트가 존재하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
package TImeCAlling.spring.validation.validator;

import TImeCAlling.spring.validation.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        
        // Enum 값 목록
        Enum<?>[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues == null) {
            return false;
        }
        
        // 단일 값인지 리스트/배열인지 확인
        if (value instanceof String) {
            return isEnumValueValid((String) value, enumValues);
        } else if (value instanceof Iterable<?>) {
            for (Object item : (Iterable<?>) value) {
                if (!(item instanceof String) || !isEnumValueValid((String) item, enumValues)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    private boolean isEnumValueValid(String value, Enum<?>[] enumValues) {
        for (Enum<?> enumValue : enumValues) {
            if (value.equals(enumValue.toString())
                    || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                return true;
            }
        }
        return false;
    }
}

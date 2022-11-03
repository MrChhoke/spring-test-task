package ua.bondar.annotation;

import ua.bondar.validator.DepartmentDTOValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks that the department name is exists
 * @deprecated
 * Better use exceptions for this such checks
 * @see org.springframework.dao.DuplicateKeyException
 */
@Deprecated
@Constraint(validatedBy = DepartmentDTOValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DepartmentExistsConstraint {
    String message() default "The department doesnt exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

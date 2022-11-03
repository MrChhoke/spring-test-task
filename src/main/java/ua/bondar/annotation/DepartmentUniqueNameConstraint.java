package ua.bondar.annotation;

import ua.bondar.validator.DepartmentUniqueNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks that the department name is unique
 * @deprecated
 * Better use exceptions for this such checks
 * @see org.springframework.dao.DuplicateKeyException
 */
@Deprecated
@Constraint(validatedBy = DepartmentUniqueNameValidator.class)
@Target( {ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DepartmentUniqueNameConstraint {
    String message() default "This department has already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

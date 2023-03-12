package com.seraptemel.etsdemo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {

    String message() default "Invalid file type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
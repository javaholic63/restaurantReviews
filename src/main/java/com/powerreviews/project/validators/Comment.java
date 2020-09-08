package com.powerreviews.project.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CommentValidator.class)
@Documented
public @interface Comment {
	
    String message() default "Some words in your comments are not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

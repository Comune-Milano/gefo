package it.dmi.dmifonbe.dmifonpro.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { HtmlEscapeValidator.class })
public @interface HtmlEscape {
    String message() default "Alcuni campi hanno dei caratteri non validi: ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

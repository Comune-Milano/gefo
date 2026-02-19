package it.dmi.dmifonbe.dmifonpro.service.annotations;

import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolationException;

public class HtmlEscapeValidator implements ConstraintValidator<HtmlEscape, String> {

    public HtmlEscapeValidator() {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty()) {
            Pattern pattern = Pattern.compile(
                ".*(?:http|\\\\|\\\\n|\\\\t|\\\\r|:|\\?|\\$|#|&|<|>|\\*|\\{|}|\\[|]|select|update|insert|delete|grant|drop).*",
                Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            );
            Matcher matcher = pattern.matcher(value);
            try {
                if (matcher.matches()) {
                    throw new Exception();
                } else {
                    return true;
                }
            } catch (Exception e) {
                throw new ConstraintViolationException(context.getDefaultConstraintMessageTemplate() + value, null);
            }
        } else return true;
    }
}

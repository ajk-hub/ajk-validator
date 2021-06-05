package io.ashimjk.validator.contracts;

import io.ashimjk.validator.condition.Statement;
import io.ashimjk.validator.result.ValidationResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class DataValidator<T> implements Validator<T> {

    @Getter protected String fieldName = "value";

    public abstract <C> Function<T, C> getProvider();

    public abstract <C> List<Statement<C>> getStatements();

    public abstract DataValidator<T> withFieldName(String fieldName);

    @Override
    public ValidationResult validate(T object) {
        return process(StringUtils.EMPTY, object);
    }

    public ValidationResult process(String parentFieldName, T object) {
        ValidationResult validationResult = new ValidationResult();
        final Function<T, Object> provider = getProvider();

        for (Statement<Object> statement : getStatements()) {
            if (!statement.getCondition().test(provider.apply(object))) {
                validationResult.addViolation(parentFieldName.concat(getFieldName()), statement.getMessage());
                break;
            }
        }

        return validationResult;
    }

}

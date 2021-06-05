package io.ashimjk.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class CustomValidator<T> {

    @Getter protected String fieldName = "value";

    public abstract <C> Function<T, C> getProvider();

    public abstract <C> List<Condition<C>> getConditions();

    public abstract CustomValidator<T> withFieldName(String fieldName);

    public ValidationResult process(String parentFieldName, T object) {
        ValidationResult validationResult = new ValidationResult();
        final Function<T, Object> provider = getProvider();

        for (Condition<Object> condition : getConditions()) {
            if (!condition.getPredicate().test(provider.apply(object))) {
                validationResult.addViolation(parentFieldName.concat(getFieldName()), condition.getMessage());
                break;
            }
        }

        return validationResult;
    }

}

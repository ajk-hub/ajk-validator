package io.ashimjk.validator;

import io.ashimjk.validator.contracts.DataValidator;
import io.ashimjk.validator.result.ValidationResult;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static io.ashimjk.validator.constant.ValidatorConstants.MUST_BE_PROVIDED;

@RequiredArgsConstructor
public class Validators<T> {

    private final T object;
    private String fieldName = "value";

    private final List<Supplier<DataValidator<T>>> customValidators = new ArrayList<>();

    public static <T> Validators<T> of(T object) {
        return new Validators<>(object);
    }

    public Validators<T> validate(Supplier<DataValidator<T>> validator) {
        customValidators.add(validator);
        return this;
    }

    public Validators<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ValidationResult doValidate() {
        return Optional.ofNullable(object)
                       .map(this::executeValidator)
                       .orElseGet(() -> ValidationResult.of(fieldName, MUST_BE_PROVIDED));
    }

    private ValidationResult executeValidator(T object) {
        String parentFieldName = buildFieldName();
        return customValidators.stream()
                               .map(Supplier::get)
                               .map(v -> v.process(parentFieldName, object))
                               .reduce(new ValidationResult(), ValidationResult::with);
    }

    private String buildFieldName() {
        return fieldName.isBlank() ? fieldName : fieldName.concat(".");
    }

}

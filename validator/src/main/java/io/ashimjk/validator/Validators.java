package io.ashimjk.validator;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Validators<T> {

    private final T object;
    private String fieldName = "";

    private final List<CustomValidator<T>> customValidators = new ArrayList<>();

    public static <T> Validators<T> of(T object) {
        return new Validators<>(object);
    }

    public Validators<T> validate(CustomValidator<T> validator) {
        customValidators.add(validator);
        return this;
    }

    public Validators<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ValidationResult doValidate() {
        String fieldName = getFieldName();
        return customValidators.stream()
                               .map(v -> v.process(fieldName, object))
                               .reduce(new ValidationResult(), ValidationResult::with);
    }

    private String getFieldName() {
        return fieldName.isBlank() ? fieldName : fieldName.concat(".");
    }

}

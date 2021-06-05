package io.ashimjk.validator.contracts;

import io.ashimjk.validator.result.ValidationResult;

public interface Validator<T> {

    ValidationResult validate(T object);

}

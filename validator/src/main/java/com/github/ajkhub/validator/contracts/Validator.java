package com.github.ajkhub.validator.contracts;

import com.github.ajkhub.validator.result.ValidationResult;

public interface Validator<T> {

    ValidationResult validate(T object);

}

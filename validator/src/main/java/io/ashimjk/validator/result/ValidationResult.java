package io.ashimjk.validator.result;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    @Getter private final List<Violation> violations = new ArrayList<>();

    public ValidationResult with(ValidationResult result) {
        this.violations.addAll(result.getViolations());
        return this;
    }

    public static ValidationResult of(String violator, String message) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addViolation(violator, message);
        return validationResult;
    }

    public void addViolation(String violator, String message) {
        violations.add(new Violation(violator, message));
    }

}

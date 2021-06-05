package io.ashimjk.sample;

import io.ashimjk.validator.constant.ValidatorConstants;
import io.ashimjk.validator.result.ValidationResult;
import io.ashimjk.validator.result.Violation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AccountValidatorTest {

    private final AccountValidator accountValidator = new AccountValidator();

    @Test
    void validateWithNullAccount() {
        ValidationResult result = accountValidator.validate(null);

        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(tuple("account", ValidatorConstants.MUST_BE_PROVIDED));
    }

    @Test
    void validate() {
        ValidationResult result = accountValidator.validate(new Account());

        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactlyInAnyOrder(
                        tuple("account.name", ValidatorConstants.MUST_BE_PROVIDED),
                        tuple("account.currency", ValidatorConstants.MUST_BE_PROVIDED),
                        tuple("account.amount", ValidatorConstants.MUST_BE_PROVIDED)
                );
    }

}
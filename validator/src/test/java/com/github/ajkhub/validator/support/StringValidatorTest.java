package com.github.ajkhub.validator.support;

import com.github.ajkhub.validator.result.ValidationResult;
import com.github.ajkhub.validator.Account;
import com.github.ajkhub.validator.result.Violation;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import static com.github.ajkhub.validator.constant.ValidatorConstants.MUST_BE_PROVIDED;
import static com.github.ajkhub.validator.constant.ValidatorConstants.MUST_MATCH_PROVIDED_CONDITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class StringValidatorTest {

    @Test
    void fieldName() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .withFieldName("name")
                                                 .mustNotBeBlank()
                                                 .validate(new Account());

        assertViolation(result, tuple("name", MUST_BE_PROVIDED));
    }

    @Test
    void mustNotBeBlank() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .withFieldName("name")
                                                 .mustNotBeBlank()
                                                 .validate(new Account());

        assertViolation(result, tuple("name", MUST_BE_PROVIDED));
    }

    @Test
    void mustNotBeBlankWithMessage() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .withFieldName("name")
                                                 .mustNotBeBlank("name.must.be.provided")
                                                 .validate(new Account());

        assertViolation(result, tuple("name", "name.must.be.provided"));
    }

    @Test
    void lengthMustBeOf() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .lengthMustBeOf(6)
                                                 .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "length.must.be.exactly.6"));
    }

    @Test
    void lengthMustBeBetween() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .lengthMustBeBetween(1, 4)
                                                 .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "length.must.be.between.1.and.4"));
    }

    @Test
    void lengthMustBeBetweenWithMessage() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .lengthMustBeBetween(1, 4, "length.must.be.within.1.and.4")
                                                 .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "length.must.be.within.1.and.4"));
    }

    @Test
    void mustBe() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .mustBe(s -> s.startsWith("abc"))
                                                 .validate(new Account("ashim"));

        assertViolation(result, tuple("value", MUST_MATCH_PROVIDED_CONDITION));
    }

    @Test
    void mustBeWithMessage() {
        ValidationResult result = StringValidator.of(Account::getName)
                                                 .mustBe(s -> s.startsWith("abc"), "must.starts.with.abc")
                                                 .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "must.starts.with.abc"));
    }

    private void assertViolation(ValidationResult result, Tuple name) {
        Assertions.assertThat(result.getViolations())
                  .extracting(Violation::getViolator, Violation::getErrorMessage)
                  .containsExactly(name);
    }

}
package io.ashimjk.validator.support;

import io.ashimjk.validator.Account;
import io.ashimjk.validator.result.ValidationResult;
import io.ashimjk.validator.result.Violation;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import static io.ashimjk.validator.constant.ValidatorConstants.MUST_BE_PROVIDED;
import static io.ashimjk.validator.constant.ValidatorConstants.MUST_MATCH_PROVIDED_CONDITION;
import static io.ashimjk.validator.support.StringValidator.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class StringValidatorTest {

    @Test
    void fieldName() {
        ValidationResult result = of(Account::getName)
                .withFieldName("name")
                .mustNotBeBlank()
                .validate(new Account());

        assertViolation(result, tuple("name", MUST_BE_PROVIDED));
    }

    @Test
    void mustNotBeBlank() {
        ValidationResult result = of(Account::getName)
                .withFieldName("name")
                .mustNotBeBlank()
                .validate(new Account());

        assertViolation(result, tuple("name", MUST_BE_PROVIDED));
    }

    @Test
    void mustNotBeBlankWithMessage() {
        ValidationResult result = of(Account::getName)
                .withFieldName("name")
                .mustNotBeBlank("name.must.be.provided")
                .validate(new Account());

        assertViolation(result, tuple("name", "name.must.be.provided"));
    }

    @Test
    void lengthMustBeOf() {
        ValidationResult result = of(Account::getName)
                .lengthMustBeOf(6)
                .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "length.must.be.exactly.6"));
    }

    @Test
    void lengthMustBeBetween() {
        ValidationResult result = of(Account::getName)
                .lengthMustBeBetween(1, 4)
                .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "length.must.be.between.1.and.4"));
    }

    @Test
    void lengthMustBeBetweenWithMessage() {
        ValidationResult result = of(Account::getName)
                .lengthMustBeBetween(1, 4, "length.must.be.within.1.and.4")
                .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "length.must.be.within.1.and.4"));
    }

    @Test
    void mustBe() {
        ValidationResult result = of(Account::getName)
                .mustBe(s -> s.startsWith("abc"))
                .validate(new Account("ashim"));

        assertViolation(result, tuple("value", MUST_MATCH_PROVIDED_CONDITION));
    }

    @Test
    void mustBeWithMessage() {
        ValidationResult result = of(Account::getName)
                .mustBe(s -> s.startsWith("abc"), "must.starts.with.abc")
                .validate(new Account("ashim"));

        assertViolation(result, tuple("value", "must.starts.with.abc"));
    }

    private void assertViolation(ValidationResult result, Tuple name) {
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(name);
    }

}
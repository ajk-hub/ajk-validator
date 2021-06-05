package io.ashimjk.validator.support;

import io.ashimjk.validator.Account;
import io.ashimjk.validator.result.ValidationResult;
import io.ashimjk.validator.result.Violation;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.ashimjk.validator.constant.ValidatorConstants.*;
import static io.ashimjk.validator.support.BigDecimalValidator.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BigDecimalValidatorTest {

    @Test
    void fieldName() {
        ValidationResult result = of(Account::getAmount)
                .withFieldName("amount")
                .mustNotBeNull()
                .validate(new Account());

        assertViolation(result, tuple("amount", MUST_BE_PROVIDED));
    }

    @Test
    void mustNotBeNull() {
        ValidationResult result = of(Account::getAmount)
                .withFieldName("amount")
                .mustNotBeNull()
                .validate(new Account());

        assertViolation(result, tuple("amount", MUST_BE_PROVIDED));
    }

    @Test
    void mustNotBeNullWithMessage() {
        ValidationResult result = of(Account::getAmount)
                .withFieldName("amount")
                .mustNotBeNull("amount.must.be.provided")
                .validate(new Account());

        assertViolation(result, tuple("amount", "amount.must.be.provided"));
    }

    @Test
    void mustBeGreaterThanZero() {
        ValidationResult result = of(Account::getAmount)
                .mustBeGreaterThanZero()
                .validate(new Account(-1));

        assertViolation(result, tuple("value", MUST_BE_GREATER_THAN_ZERO));
    }

    @Test
    void mustBeBetween() {
        ValidationResult result = of(Account::getAmount)
                .mustBeBetween(BigDecimal.valueOf(1), BigDecimal.valueOf(10))
                .validate(new Account(20));

        assertViolation(result, tuple("value", "must.be.between.1.and.10"));
    }

    @Test
    void mustBeBetweenWithMin() {
        ValidationResult result = of(Account::getAmount)
                .mustBeBetween(BigDecimal.valueOf(1), BigDecimal.valueOf(10))
                .validate(new Account(0));

        assertViolation(result, tuple("value", "must.be.between.1.and.10"));
    }

    @Test
    void mustBeBetweenWithMax() {
        ValidationResult result = of(Account::getAmount)
                .mustBeBetween(BigDecimal.valueOf(1), BigDecimal.valueOf(10))
                .validate(new Account(10));

        assertViolation(result, tuple("value", "must.be.between.1.and.10"));
    }

    @Test
    void mustBeBetweenWithMessage() {
        ValidationResult result = of(Account::getAmount)
                .mustBeBetween(BigDecimal.valueOf(1), BigDecimal.valueOf(10), "range.must.be.within.1.and.10")
                .validate(new Account(20));

        assertViolation(result, tuple("value", "range.must.be.within.1.and.10"));
    }

    @Test
    void mustBe() {
        ValidationResult result = of(Account::getAmount)
                .mustBe(s -> s.equals(BigDecimal.TEN))
                .validate(new Account(5));

        assertViolation(result, tuple("value", MUST_MATCH_PROVIDED_CONDITION));
    }

    @Test
    void mustBeWithMessage() {
        ValidationResult result = of(Account::getAmount)
                .mustBe(s -> s.equals(BigDecimal.TEN), "must.be.equal.to.ten")
                .validate(new Account(8));

        assertViolation(result, tuple("value", "must.be.equal.to.ten"));
    }

    private void assertViolation(ValidationResult result, Tuple name) {
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(name);
    }

}
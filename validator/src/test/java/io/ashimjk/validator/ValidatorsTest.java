package io.ashimjk.validator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.ashimjk.validator.Condition.MUST_BE_PROVIDED_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ValidatorsTest {

    @Test
    void validateBlankCurrency() {
        StringValidator<Account> validator = StringValidator.with(Account::getCurrency)
                                                            .mustNotBeBlank();

        ValidationResult result = createValidator(new Account(), validator);

        assertThat(result.getViolations()).hasSize(1);
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(tuple("value", MUST_BE_PROVIDED_ERROR_MESSAGE));
    }

    @Test
    void validateCurrencyLength() {
        Account deposit = new Account();
        deposit.setCurrency("US");
        StringValidator<Account> validator = StringValidator.with(Account::getCurrency)
                                                            .mustNotBeBlank()
                                                            .mustBe(s -> s.length() == 3);

        ValidationResult result = createValidator(deposit, validator);

        assertThat(result.getViolations()).hasSize(1);
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(tuple("value", MUST_BE_PROVIDED_ERROR_MESSAGE));
    }

    @Test
    void validateCurrencyWithReference() {
        Account deposit = new Account();
        deposit.setCurrency("US");
        StringValidator<Account> currencyValidator = StringValidator.with(Account::getCurrency)
                                                                    .withFieldName("currency")
                                                                    .mustNotBeBlank()
                                                                    .mustBe(s -> s.length() == 3,
                                                                            "currency.length.must.be.exactly.3");

        StringValidator<Account> nameValidator = StringValidator.with(Account::getName)
                                                                .withFieldName("name")
                                                                .mustNotBeBlank("name.must.be.provided");

        ValidationResult result = createValidator(deposit, currencyValidator, nameValidator);

        assertThat(result.getViolations()).hasSize(2);
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(
                        tuple("currency", "currency.length.must.be.exactly.3"),
                        tuple("name", "name.must.be.provided")
                );
    }

    private ValidationResult createValidator(Account deposit, CustomValidator<Account>... validators) {
        final Validators<Account> validator = Validators.of(deposit);
        Arrays.stream(validators).forEach(validator::validate);
        return validator.doValidate();
    }

}
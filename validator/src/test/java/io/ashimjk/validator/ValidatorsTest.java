package io.ashimjk.validator;

import io.ashimjk.validator.contracts.DataValidator;
import io.ashimjk.validator.result.ValidationResult;
import io.ashimjk.validator.result.Violation;
import io.ashimjk.validator.support.StringValidator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Supplier;

import static io.ashimjk.validator.constant.ValidatorConstants.MUST_BE_PROVIDED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ValidatorsTest {

    @Test
    void validate() {
        ValidationResult result = Validators.of(null).doValidate();

        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(tuple("value", MUST_BE_PROVIDED));
    }

    @Test
    void validateBlankCurrency() {
        Supplier<DataValidator<Account>> validator =
                () -> StringValidator.of(Account::getCurrency).mustNotBeBlank();

        ValidationResult result = createValidator(new Account(), validator);

        assertThat(result.getViolations()).hasSize(1);
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(tuple("account.value", MUST_BE_PROVIDED));
    }

    @Test
    void validateCurrencyLength() {
        Account account = new Account();
        account.setCurrency("US");
        Supplier<DataValidator<Account>> validator =
                () -> StringValidator.of(Account::getCurrency)
                                     .mustNotBeBlank()
                                     .mustBe(s -> s.length() == 3);

        ValidationResult result = createValidator(account, validator);

        assertThat(result.getViolations()).hasSize(1);
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(tuple("account.value", "must.match.provided.condition"));
    }

    @Test
    void validateCurrencyWithReference() {
        Account account = new Account();
        account.setCurrency("US");
        Supplier<DataValidator<Account>> currencyValidator =
                () -> StringValidator.of(Account::getCurrency)
                                     .withFieldName("currency")
                                     .mustNotBeBlank()
                                     .mustBe(s -> s.length() == 3, "currency.length.must.be.exactly.3");

        Supplier<DataValidator<Account>> nameValidator =
                () -> StringValidator.of(Account::getName)
                                     .withFieldName("name")
                                     .mustNotBeBlank("name.must.be.provided");

        ValidationResult result = createValidator(account, currencyValidator, nameValidator);

        assertThat(result.getViolations()).hasSize(2);
        assertThat(result.getViolations())
                .extracting(Violation::getViolator, Violation::getErrorMessage)
                .containsExactly(
                        tuple("account.currency", "currency.length.must.be.exactly.3"),
                        tuple("account.name", "name.must.be.provided")
                );
    }

    @SafeVarargs
    private <T> ValidationResult createValidator(T object, Supplier<DataValidator<T>>... validators) {
        final Validators<T> validator = Validators.of(object).withFieldName("account");
        Arrays.stream(validators).forEach(validator::validate);
        return validator.doValidate();
    }

}
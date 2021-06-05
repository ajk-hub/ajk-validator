package com.github.ajkhub.sample;

import com.github.ajkhub.validator.Validators;
import com.github.ajkhub.validator.contracts.Validator;
import com.github.ajkhub.validator.result.ValidationResult;
import com.github.ajkhub.validator.support.BigDecimalValidator;
import com.github.ajkhub.validator.support.StringValidator;

import java.math.BigDecimal;

public class AccountValidator implements Validator<Account> {

    @Override
    public ValidationResult validate(Account object) {
        return Validators.of(object)
                         .withFieldName("account")
                         .validate(this::nameValidator)
                         .validate(this::currencyValidator)
                         .validate(this::amountValidator)
                         .doValidate();
    }

    private StringValidator<Account> nameValidator() {
        return StringValidator.of(Account::getName)
                              .withFieldName("name")
                              .mustNotBeBlank()
                              .lengthMustBeBetween(1, 50);
    }

    private StringValidator<Account> currencyValidator() {
        return StringValidator.of(Account::getCurrency)
                              .withFieldName("currency")
                              .mustNotBeBlank()
                              .lengthMustBeOf(3);
    }

    private BigDecimalValidator<Account> amountValidator() {
        return BigDecimalValidator.of(Account::getAmount)
                                  .withFieldName("amount")
                                  .mustNotBeNull()
                                  .mustBeGreaterThanZero()
                                  .mustBeBetween(BigDecimal.ONE, BigDecimal.TEN);
    }

}

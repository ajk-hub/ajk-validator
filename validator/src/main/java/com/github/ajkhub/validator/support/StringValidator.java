package com.github.ajkhub.validator.support;

import com.github.ajkhub.validator.condition.Statement;
import com.github.ajkhub.validator.contracts.DataValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.ajkhub.validator.constant.ValidatorConstants.MUST_BE_PROVIDED;
import static com.github.ajkhub.validator.constant.ValidatorConstants.MUST_MATCH_PROVIDED_CONDITION;

@RequiredArgsConstructor
public class StringValidator<T> extends DataValidator<T> {

    @Getter private final Function<T, String> provider;
    @Getter private final List<Statement<String>> statements = new ArrayList<>();

    public static <T> StringValidator<T> of(Function<T, String> provider) {
        return new StringValidator<>(provider);
    }

    public StringValidator<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public StringValidator<T> mustNotBeBlank() {
        return this.mustNotBeBlank(MUST_BE_PROVIDED);
    }

    public StringValidator<T> mustNotBeBlank(String message) {
        statements.add(Statement.of(StringUtils::isNotBlank, message));
        return this;
    }

    public StringValidator<T> lengthMustBeOf(int length) {
        statements.add(Statement.of(s -> s.length() == length, "length.must.be.exactly." + length));
        return this;
    }

    public StringValidator<T> lengthMustBeBetween(int min, int max) {
        return this.lengthMustBeBetween(min, max, String.format("length.must.be.between.%s.and.%s", min, max));
    }

    public StringValidator<T> lengthMustBeBetween(int min, int max, String message) {
        Predicate<String> condition = s -> s.length() >= min && s.length() < max;
        statements.add(Statement.of(condition, message));
        return this;
    }

    public StringValidator<T> mustBe(Predicate<String> condition) {
        return this.mustBe(condition, MUST_MATCH_PROVIDED_CONDITION);
    }

    public StringValidator<T> mustBe(Predicate<String> condition, String message) {
        statements.add(Statement.of(condition, message));
        return this;
    }

}

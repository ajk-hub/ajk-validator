package io.ashimjk.validator.support;

import io.ashimjk.validator.condition.Statement;
import io.ashimjk.validator.contracts.DataValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.ashimjk.validator.constant.ValidatorConstants.*;
import static java.math.BigDecimal.ZERO;

@RequiredArgsConstructor
public class BigDecimalValidator<T> extends DataValidator<T> {

    @Getter private final Function<T, BigDecimal> provider;
    @Getter private final List<Statement<BigDecimal>> statements = new ArrayList<>();

    public static <T> BigDecimalValidator<T> of(Function<T, BigDecimal> provider) {
        return new BigDecimalValidator<>(provider);
    }

    public BigDecimalValidator<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public BigDecimalValidator<T> mustNotBeNull() {
        return this.mustNotBeNull(MUST_BE_PROVIDED);
    }

    public BigDecimalValidator<T> mustNotBeNull(String message) {
        statements.add(Statement.of(Objects::nonNull, message));
        return this;
    }

    public BigDecimalValidator<T> mustBeGreaterThanZero() {
        statements.add(Statement.of(b -> ZERO.max(b).equals(b), MUST_BE_GREATER_THAN_ZERO));
        return this;
    }

    public BigDecimalValidator<T> mustBeBetween(BigDecimal min, BigDecimal max) {
        return this.mustBeBetween(min, max, String.format("must.be.between.%s.and.%s", min, max));
    }

    public BigDecimalValidator<T> mustBeBetween(BigDecimal min, BigDecimal max, String message) {
        Predicate<BigDecimal> condition = value -> value.compareTo(min) >= 0 && value.compareTo(max) < 0;
        statements.add(Statement.of(condition, message));
        return this;
    }

    public BigDecimalValidator<T> mustBe(Predicate<BigDecimal> condition) {
        return this.mustBe(condition, MUST_MATCH_PROVIDED_CONDITION);
    }

    public BigDecimalValidator<T> mustBe(Predicate<BigDecimal> condition, String message) {
        statements.add(Statement.of(condition, message));
        return this;
    }

}

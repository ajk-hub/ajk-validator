package io.ashimjk.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class BigDecimalValidator<T> extends CustomValidator<T> {

    @Getter private final Function<T, BigDecimal> provider;
    @Getter private final List<Condition<BigDecimal>> conditions = new ArrayList<>();

    public static <T> BigDecimalValidator<T> with(Function<T, BigDecimal> provider) {
        return new BigDecimalValidator<>(provider);
    }

    public BigDecimalValidator<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public BigDecimalValidator<T> mustNotBeNull() {
        conditions.add(Condition.of(Objects::nonNull));
        return this;
    }

    public BigDecimalValidator<T> mustBe(Predicate<BigDecimal> condition) {
        conditions.add(Condition.of(condition));
        return this;
    }

}

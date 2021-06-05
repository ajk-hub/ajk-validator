package io.ashimjk.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class StringValidator<T> extends CustomValidator<T> {

    @Getter private final Function<T, String> provider;
    @Getter private final List<Condition<String>> conditions = new ArrayList<>();

    public static <T> StringValidator<T> with(Function<T, String> provider) {
        return new StringValidator<>(provider);
    }

    public StringValidator<T> withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public StringValidator<T> mustNotBeBlank() {
        conditions.add(Condition.of(StringUtils::isNotBlank));
        return this;
    }

    public StringValidator<T> mustNotBeBlank(String message) {
        conditions.add(Condition.of(StringUtils::isNotBlank, message));
        return this;
    }

    public StringValidator<T> mustBe(Predicate<String> condition) {
        conditions.add(Condition.of(condition));
        return this;
    }

    public StringValidator<T> mustBe(Predicate<String> condition, String message) {
        conditions.add(Condition.of(condition, message));
        return this;
    }

}

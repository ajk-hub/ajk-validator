package io.ashimjk.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public class Condition<T> {

    public static final String MUST_BE_PROVIDED_ERROR_MESSAGE = "must.be.provided";

    private final Predicate<T> predicate;
    private final String message;

    public static <T> Condition<T> of(Predicate<T> predicate) {
        return Condition.of(predicate, MUST_BE_PROVIDED_ERROR_MESSAGE);
    }

    public static <T> Condition<T> of(Predicate<T> predicate, String message) {
        return new Condition<>(predicate, message);
    }

}

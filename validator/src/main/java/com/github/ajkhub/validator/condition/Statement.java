package com.github.ajkhub.validator.condition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public class Statement<T> {

    private final Predicate<T> condition;
    private final String message;

    public static <T> Statement<T> of(Predicate<T> condition, String message) {
        return new Statement<>(condition, message);
    }

}

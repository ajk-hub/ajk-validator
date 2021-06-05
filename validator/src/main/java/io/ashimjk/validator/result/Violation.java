package io.ashimjk.validator.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Violation {

    private final String violator;
    private final String errorMessage;

}

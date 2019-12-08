package org.yozanshin.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidInputException extends RuntimeException {

    private final String message;
}

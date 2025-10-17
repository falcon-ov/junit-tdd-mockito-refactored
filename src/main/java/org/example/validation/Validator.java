package org.example.validation;

import java.util.List;

public interface Validator<T> {
    List<String> validate(T obj);
}

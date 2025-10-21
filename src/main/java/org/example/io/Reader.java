package org.example.io;

import java.util.List;

public interface Reader<T> {
    List<T> readAll();
}
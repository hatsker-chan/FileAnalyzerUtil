package com.example;

import java.util.List;

public record Result(
        List<Long> integers,
        List<Double> doubles,
        List<String> strings
) {
}

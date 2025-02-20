package com.example;

import java.io.*;
import java.util.ArrayList;

public class Analyzer {
    private final Params params;
    private final ArrayList<String> strings = new ArrayList<>();
    private final ArrayList<Long> integers = new ArrayList<>();
    private final ArrayList<Double> doubles = new ArrayList<>();

    public Analyzer(Params params) {
        this.params = params;
    }

    public Result analyze() {
        for (File file : params.getFiles()) {
            analyzeFile(file);
        }
        return new Result(integers, doubles, strings);
    }

    public Result getResult() {
        return new Result(integers, doubles, strings);
    }

    private void analyzeFile(File file) {
        try (Reader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                Number x;
                if ((x = tryConvertToInteger(line)) != null) {
                    integers.add(x.longValue());
                } else if ((x = tryConvertToDouble(line)) != null) {
                    doubles.add(x.doubleValue());
                } else {
                    strings.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Number tryConvertToInteger(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Number tryConvertToDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
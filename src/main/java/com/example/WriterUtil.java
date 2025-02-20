package com.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class WriterUtil {
    private int intCount;
    private int floatCount;
    private int stringCount;

    private Double min = Double.MAX_VALUE, max = Double.MIN_VALUE, sum = 0d;
    private Integer minLength = Integer.MAX_VALUE, maxLength = Integer.MIN_VALUE;

    private void processNumber(Number number) {
        double value = number.doubleValue();
        min = Math.min(min, value);
        max = Math.max(max, value);
        sum = sum + value;
    }

    private void processString(String string) {
        int length = string.length();
        minLength = Math.min(minLength, length);
        maxLength = Math.max(maxLength, length);
    }

    public void writeResults(Params params, Result result) {
        String prefix = params.getResultFilePrefix();
        String directoryPath = params.getResulDirectoryPath();
        String integersFileName = prefix + "integers.txt";
        String stringsFileName = prefix + "strings.txt";
        String floatsFileName = prefix + "floats.txt";
        boolean appendFlag = params.getAppendToExistingFiles();
        if (!directoryPath.isEmpty()) {
            try {
                Path directory = Paths.get(directoryPath);

                if (!Files.exists(directory)) {
                    Files.createDirectories(directory);
                    if (!Files.exists(directory)) {
                        System.out.println("Directory could not be created" + "\"" + directoryPath + "\"");
                    }
                }

                Path integerFilePath = directory.resolve(integersFileName);
                Path floatFilePath = directory.resolve(floatsFileName);
                Path stringFilePath = directory.resolve(stringsFileName);

                intCount = writeFile(integerFilePath, result.integers(), appendFlag);
                floatCount = writeFile(floatFilePath, result.doubles(), appendFlag);
                stringCount = writeFile(stringFilePath, result.strings(), appendFlag);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            intCount = writeFile(Paths.get(integersFileName), result.integers(), appendFlag);
            floatCount = writeFile(Paths.get(floatsFileName), result.doubles(), appendFlag);
            stringCount = writeFile(Paths.get(stringsFileName), result.strings(), appendFlag);
        }

        if (params.getStatisticsFlag() == Params.StatisticsFlag.Short) {
            printShortStatistics();
        } else if (params.getStatisticsFlag() == Params.StatisticsFlag.Full) {
            printFullStatistics();
        }
    }

    private <T> int writeFile(Path filePath, List<T> data, boolean appendToExisting) {
        int counter = 0;
        try {
            if (data.isEmpty()) {
                return 0;
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                if (!Files.exists(filePath)) {
                    System.out.println("File could not be created" + filePath);
                }
            }
            StandardOpenOption[] openOptions;
            if (appendToExisting) {
                openOptions = new StandardOpenOption[]{StandardOpenOption.APPEND};
            } else {
                openOptions = new StandardOpenOption[]{StandardOpenOption.TRUNCATE_EXISTING};
            }
            try (
                    BufferedWriter bw = new BufferedWriter(Files.newBufferedWriter(filePath, openOptions))
            ) {
                for (T element : data) {
                    bw.write(element.toString());
                    bw.newLine();
                    counter++;
                    if (element instanceof Long) {
                        processNumber((Long) element);
                        intCount++;
                    } else if (element instanceof Double) {
                        processNumber((Double) element);
                        floatCount++;
                    } else {
                        processString((String) element);
                        stringCount++;
                    }
                }
            }
            return counter;
        } catch (IOException e) {
            System.out.println("IO exception during file analysis: " + filePath);
            return counter;
        }
    }

    private void printFullStatistics() {
        Double avg;
        if (intCount + floatCount == 0) {
            min = null;
            max = null;
            avg = null;
        } else {
            avg = sum / (intCount + floatCount);
        }
        if (minLength == Integer.MAX_VALUE && maxLength == Integer.MIN_VALUE) {
            minLength = null;
            maxLength = null;
        }
        System.out.printf("""
                Full statistics:
                    Added integers: %d;
                    Added doubles: %d;
                    Added strings: %d;
                    Max number: %f
                    Min number: %f
                    Numbers sum: %f
                    Average: %f
                    Shortest string length: %d
                    Longest string length: %d
                """, intCount, floatCount, stringCount, max, min, sum, avg, minLength, maxLength);
    }

    private void printShortStatistics() {
        System.out.printf("""
                Short statistics:
                    Added integers: %d;
                    Added doubles: %d;
                    Added strings: %d;
                """, intCount, floatCount, stringCount);
    }
}
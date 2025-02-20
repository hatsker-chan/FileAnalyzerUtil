package com.example;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Params params = parseArgs(args);

        if (params == null) {
            return;
        }

        if (params.getFiles().isEmpty()) {
            System.out.println("No files to analyze");
            return;
        }

        Analyzer analyzer = new Analyzer(params);
        Result result = analyzer.analyze();
        WriterUtil writerUtil = new WriterUtil();
        writerUtil.writeResults(params, result);
    }

    private static Params parseArgs(String[] args) {

        Params.Builder builder = new Params.Builder();

        if (args.length == 0) {
            System.out.println("No arguments given");
            return null;
        }

        Iterator<String> argsIterator = Arrays.stream(args).iterator();
        while (argsIterator.hasNext()) {
            String arg = argsIterator.next();
            switch (arg) {
                case "-s" -> builder.addStatisticsType(Params.StatisticsFlag.Short);
                case "-f" -> builder.addStatisticsType(Params.StatisticsFlag.Full);

                case "-a" -> {
                    builder.addAppendToExistingFilesFlag();
                }
                case "-o" -> {
                    String path = argsIterator.next();
                    if (path.startsWith("/") || path.startsWith("\\")) {
                        path = path.substring(1);
                    }
                    builder.addResultDirectoryPath(path);
                }
                case "-p" -> {
                    String prefix = argsIterator.next();
                    builder.addResultFilePrefix(prefix);
                }
                default -> {
                    File readFile = getFile(arg);
                    if (readFile != null) {
                        builder.addFile(readFile);
                    }
                }
            }
        }
        return builder.build();
    }

    private static File getFile(String filename) {
        File file = new File(filename.trim());
        if (!file.canRead()) {
            System.out.println("Can't read file: " + filename);
            return null;
        }
        return file;
    }
}
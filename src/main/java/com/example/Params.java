package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Params {
    private final String resulDirectoryPath;
    private final String resultFilePrefix;
    private final Boolean appendToExistingFiles;
    private final StatisticsFlag statisticsFlag;
    private final List<File> files;

    private Params(String resulDirectoryPath, String resultFilePrefix, Boolean appendToExistingFiles, StatisticsFlag statisticsFlag, List<File> files) {
        this.resulDirectoryPath = resulDirectoryPath;
        this.resultFilePrefix = resultFilePrefix;
        this.appendToExistingFiles = appendToExistingFiles;
        this.statisticsFlag = statisticsFlag;
        this.files = files;
    }

    public String getResulDirectoryPath() {
        return resulDirectoryPath;
    }

    public String getResultFilePrefix() {
        return resultFilePrefix;
    }

    public Boolean getAppendToExistingFiles() {
        return appendToExistingFiles;
    }

    public StatisticsFlag getStatisticsFlag() {
        return statisticsFlag;
    }

    public List<File> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Params{" +
                "resulDirectoryPath='" + resulDirectoryPath + '\'' +
                ", resultFilePrefix='" + resultFilePrefix + '\'' +
                ", appendToExistingFiles=" + appendToExistingFiles +
                ", statistics=" + statisticsFlag +
                ", files=" + files +
                '}';
    }

    public enum StatisticsFlag {
        Short, Full
    }

    public static class Builder {
        private final ArrayList<File> files = new ArrayList<>();
        private String resultDirectoryPath = "";
        private String resultFilePrefix = "";
        private Boolean appendToExistingFiles = false;
        private StatisticsFlag statisticsFlag = null;

        public Builder addResultDirectoryPath(String resultPath) {
            this.resultDirectoryPath = resultPath;
            return this;
        }

        public Builder addResultFilePrefix(String resultFilePrefix) {
            this.resultFilePrefix = resultFilePrefix;
            return this;
        }

        public Builder addAppendToExistingFilesFlag() {
            this.appendToExistingFiles = true;
            return this;
        }

        public Builder addStatisticsType(StatisticsFlag statisticsFlag) {
            this.statisticsFlag = statisticsFlag;
            return this;
        }

        public Builder addFile(File file) {
            this.files.add(file);
            return this;
        }

        public Params build() {
            return new Params(resultDirectoryPath, resultFilePrefix, appendToExistingFiles, statisticsFlag, files);
        }
    }
}

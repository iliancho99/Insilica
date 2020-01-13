package com.insilicokdd.data;

public class Result {
    private final Double outcome;
    private final String path1;
    private final Double diagnosis;
    private final String path2;

    private static class Builder {
        private Double outcome;
        private String path1;
        private Double diagnosis;
        private String path2;

        public Builder setOutcome(Double val) {
            outcome = val;
            return this;
        }

        public Builder setDiagnosis(Double val) {
            diagnosis = val;
            return this;
        }

        public Builder setPath1(String val) {
            path1 = val;
            return this;
        }

        public Builder setPath2(String val) {
            path2 = val;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }

    private Result(Builder builder) {
        outcome = builder.outcome;
        diagnosis = builder.diagnosis;
        path1 = builder.path1;
        path2 = builder.path2;
    }
}

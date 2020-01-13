package com.insilicokdd.data;

public class ModelBluePrint {
    private String name;
    private String path0;
    private String path1;
    private String mode;

    ModelBluePrint(String path0, String path1, String mode, String name) {
        this.path0 = path0;
        this.path1 = path1;
        this.mode = mode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath0() {
        return path0;
    }

    public void setPath0(String path0) {
        this.path0 = path0;
    }

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return name;
    }
}
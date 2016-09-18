package com.bradf83.springbootgenerator.controllers;

/**
 * Created by bradf on 2016-09-16.
 */
public class ModelField {
    private String name;
    private String type;
    private String column;

    public ModelField() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}

package com.bradf83.springbootgenerator.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bradf on 2016-09-16.
 */
public class GeneratorForm {
    private String packageName;

    private String modelName;

    private String tableName;

    private String extensionClass;

    private List<ModelField> modelFields;

    private Integer modelFieldToRemove;

    public GeneratorForm() {
        this.modelFields = new ArrayList<>();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getModelFieldToRemove() {
        return modelFieldToRemove;
    }

    public void setModelFieldToRemove(Integer modelFieldToRemove) {
        this.modelFieldToRemove = modelFieldToRemove;
    }

    public String getExtensionClass() {
        return extensionClass;
    }

    public void setExtensionClass(String extensionClass) {
        this.extensionClass = extensionClass;
    }

    public List<ModelField> getModelFields() {
        return modelFields;
    }

    public void setModelFields(List<ModelField> modelFields) {
        this.modelFields = modelFields;
    }

    public void addModelField(){
        this.modelFields.add(new ModelField());
    }

    public void removeModelField(Integer index){
        if(index != null && index < this.modelFields.size()){
            this.modelFields.remove(index.intValue());
        }
    }
}

package com.shitikov.project.model.entity.application;

public enum ApplicationType {
    CARGO("cargo"),
    PASSENGER("passenger");

    private String typeName;

    ApplicationType(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return typeName;
    }
}

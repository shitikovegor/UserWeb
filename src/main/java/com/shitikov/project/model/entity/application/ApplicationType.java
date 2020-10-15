package com.shitikov.project.model.entity.application;

public enum ApplicationType {
    CARGO("cargo"),
    PEOPLE("people");

    private String applicationName;

    ApplicationType(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }
}

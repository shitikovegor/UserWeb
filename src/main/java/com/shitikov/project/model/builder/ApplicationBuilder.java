package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.application.ApplicationType;

public abstract class ApplicationBuilder<T extends Application> {
    private long applicationId;
    private String title;
    private ApplicationType applicationType;
    private long date;
    private AddressTimeData addressTimeData;
    private String description;

    public long getApplicationId() {
        return applicationId;
    }

    public ApplicationBuilder<T> buildApplicationId(long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ApplicationBuilder<T> buildTitle(String title) {
        this.title = title;
        return this;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public ApplicationBuilder<T> buildApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public long getDate() {
        return date;
    }

    public ApplicationBuilder<T> buildDate(long date) {
        this.date = date;
        return this;
    }

    public AddressTimeData getAddressTimeData() {
        return addressTimeData;
    }

    public ApplicationBuilder<T> buildAddressTimeData(AddressTimeData addressTimeData) {
        this.addressTimeData = addressTimeData;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApplicationBuilder<T> buildDescription(String description) {
        this.description = description;
        return this;
    }

    public abstract T buildApplication();
}

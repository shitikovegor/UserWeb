package com.shitikov.project.model.builder;

import com.shitikov.project.model.entity.application.Application;
import com.shitikov.project.model.entity.application.AddressTimeData;
import com.shitikov.project.model.entity.type.ApplicationType;

/**
 * The type Application builder.
 *
 * @param <T> the type parameter
 * @author Shitikov Egor
 * @version 1.0
 */
public abstract class ApplicationBuilder<T extends Application> {
    private long applicationId;
    private String title;
    private ApplicationType applicationType;
    private long date;
    private AddressTimeData addressTimeData;
    private String description;

    /**
     * Gets application id.
     *
     * @return the application id
     */
    public long getApplicationId() {
        return applicationId;
    }

    /**
     * Build application id application builder.
     *
     * @param applicationId the application id
     * @return the application builder
     */
    public ApplicationBuilder<T> buildApplicationId(long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Build title application builder.
     *
     * @param title the title
     * @return the application builder
     */
    public ApplicationBuilder<T> buildTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Gets application type.
     *
     * @return the application type
     */
    public ApplicationType getApplicationType() {
        return applicationType;
    }

    /**
     * Build application type application builder.
     *
     * @param applicationType the application type
     * @return the application builder
     */
    public ApplicationBuilder<T> buildApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * Build date application builder.
     *
     * @param date the date
     * @return the application builder
     */
    public ApplicationBuilder<T> buildDate(long date) {
        this.date = date;
        return this;
    }

    /**
     * Gets address time data.
     *
     * @return the address time data
     */
    public AddressTimeData getAddressTimeData() {
        return addressTimeData;
    }

    /**
     * Build address time data application builder.
     *
     * @param addressTimeData the address time data
     * @return the application builder
     */
    public ApplicationBuilder<T> buildAddressTimeData(AddressTimeData addressTimeData) {
        this.addressTimeData = addressTimeData;
        return this;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Build description application builder.
     *
     * @param description the description
     * @return the application builder
     */
    public ApplicationBuilder<T> buildDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Build application t.
     *
     * @return the t
     */
    public abstract T buildApplication();
}

package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.ApplicationBuilder;
import com.shitikov.project.model.entity.Entity;

public abstract class Application extends Entity {
    private String title;
    private ApplicationType applicationType;
    private long date;
    private AddressTimeData addressTimeData;
    private String description;

    public Application(String title, ApplicationType applicationType
            , long date, AddressTimeData addressTimeData, String description) {
        this.title = title;
        this.applicationType = applicationType;
        this.date = date;
        this.addressTimeData = addressTimeData;
        this.description = description;
    }

    protected Application(ApplicationBuilder builder) {
        this.title = builder.getTitle();
        this.applicationType = builder.getApplicationType();
        this.date = builder.getDate();
        this.addressTimeData = builder.getAddressTimeData();
        this.description = builder.getDescription();
    }

    protected Application() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public AddressTimeData getAddressTimeData() {
        return addressTimeData;
    }

    public void setAddressTimeData(AddressTimeData addressTimeData) {
        this.addressTimeData = addressTimeData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Application other = (Application) obj;

        if (date != other.date) {
            return false;
        }
        if (title != null ? !title.equals(other.title) : other.title != null) {
            return false;
        }
        if (applicationType != other.applicationType) {
            return false;
        }
        if (addressTimeData != null ? !addressTimeData.equals(other.addressTimeData) : other.addressTimeData != null) {
            return false;
        }
        return description != null ? description.equals(other.description) : other.description == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (applicationType != null ? applicationType.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (addressTimeData != null ? addressTimeData.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("title='").append(title).append('\'');
        sb.append(", applicationType=").append(applicationType);
        sb.append(", date=").append(date);
        sb.append(", applicationAddress=").append(addressTimeData);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

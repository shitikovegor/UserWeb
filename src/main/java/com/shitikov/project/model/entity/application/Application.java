package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.ApplicationBuilder;
import com.shitikov.project.model.entity.Entity;

import java.util.Comparator;

public abstract class Application extends Entity {
    private long applicationId;
    private String title;
    private ApplicationType applicationType;
    private long date;
    private AddressTimeData addressTimeData;
    private String description;

    public Application(long applicationId, String title, ApplicationType applicationType
            , long date, AddressTimeData addressTimeData, String description) {
        this.applicationId = applicationId;
        this.title = title;
        this.applicationType = applicationType;
        this.date = date;
        this.addressTimeData = addressTimeData;
        this.description = description;
    }

    protected Application(ApplicationBuilder builder) {
        this.applicationId = builder.getApplicationId();
        this.title = builder.getTitle();
        this.applicationType = builder.getApplicationType();
        this.date = builder.getDate();
        this.addressTimeData = builder.getAddressTimeData();
        this.description = builder.getDescription();
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
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

        if (applicationId != other.applicationId) {
            return false;
        }

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
        int result = (int) (applicationId ^ (applicationId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (applicationType != null ? applicationType.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (addressTimeData != null ? addressTimeData.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Application{");
        sb.append("applicationId=").append(applicationId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", applicationType=").append(applicationType);
        sb.append(", date=").append(date);
        sb.append(", addressTimeData=").append(addressTimeData);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class IdComparator implements Comparator<Application> {

        @Override
        public int compare(Application o1, Application o2) {
            long id1 = o1.applicationId;
            long id2 = o2.applicationId;
            if (id1 == id2) {
                return 0;
            }
            return id1 > id2 ? 1 : -1;
        }
    }

    public static class DepartureDateComparator implements Comparator<Application> {

        @Override
        public int compare(Application o1, Application o2) {
            long id1 = o1.addressTimeData.getDepartureDate();
            long id2 = o2.addressTimeData.getDepartureDate();
            if (id1 == id2) {
                return 0;
            }
            return id1 > id2 ? 1 : -1;
        }
    }
}

package com.shitikov.project.model.entity.application;

import com.shitikov.project.model.builder.ApplicationBuilder;
import com.shitikov.project.model.entity.Entity;
import com.shitikov.project.model.entity.type.ApplicationType;

import java.util.Comparator;

/**
 * The type Application.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public abstract class Application extends Entity {
    private long applicationId;
    private String title;
    private ApplicationType applicationType;
    private long date;
    private AddressTimeData addressTimeData;
    private String description;

    /**
     * Instantiates a new Application.
     *
     * @param applicationId   the application id
     * @param title           the title
     * @param applicationType the application type
     * @param date            the date
     * @param addressTimeData the address time data
     * @param description     the description
     */
    public Application(long applicationId, String title, ApplicationType applicationType
            , long date, AddressTimeData addressTimeData, String description) {
        this.applicationId = applicationId;
        this.title = title;
        this.applicationType = applicationType;
        this.date = date;
        this.addressTimeData = addressTimeData;
        this.description = description;
    }

    /**
     * Instantiates a new Application.
     *
     * @param builder the builder
     */
    protected Application(ApplicationBuilder builder) {
        this.applicationId = builder.getApplicationId();
        this.title = builder.getTitle();
        this.applicationType = builder.getApplicationType();
        this.date = builder.getDate();
        this.addressTimeData = builder.getAddressTimeData();
        this.description = builder.getDescription();
    }

    /**
     * Gets application id.
     *
     * @return the application id
     */
    public long getApplicationId() {
        return applicationId;
    }

    /**
     * Sets application id.
     *
     * @param applicationId the application id
     */
    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
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
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Sets application type.
     *
     * @param applicationType the application type
     */
    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
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
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(long date) {
        this.date = date;
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
     * Sets address time data.
     *
     * @param addressTimeData the address time data
     */
    public void setAddressTimeData(AddressTimeData addressTimeData) {
        this.addressTimeData = addressTimeData;
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
     * Sets description.
     *
     * @param description the description
     */
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

    /**
     * The type Id comparator.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
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

    /**
     * The type Departure date comparator.
     *
     * @author Shitikov Egor
     * @version 1.0
     */
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

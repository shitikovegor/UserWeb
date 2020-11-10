package com.shitikov.project.model.entity.type;

/**
 * The enum Subject type.
 *
 * @author Shitikov Egor
 * @version 1.0
 */
public enum SubjectType {
    /**
     * Individual subject type.
     */
    INDIVIDUAL("individual"),
    /**
     * Organization subject type.
     */
    ORGANIZATION("organization");

    private final String subjectName;

    SubjectType(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Gets subject name.
     *
     * @return the subject name
     */
    public String getSubjectName() {
        return subjectName;
    }
}

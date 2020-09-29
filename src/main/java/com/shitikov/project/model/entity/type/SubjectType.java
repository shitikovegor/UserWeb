package com.shitikov.project.model.entity.type;

public enum SubjectType {
    INDIVIDUAL("individual"),
    ORGANIZATION("organization");

    private String subjectName;

    SubjectType(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}

package com.jipmer.dto;

public class PatientDTO {
    private long id;
    private String name;
    private String severity;
    private String lastVisit;
    private String admissionId;

    // Constructors
    public PatientDTO() {
    }

    public PatientDTO(String name, String severity, String lastVisit, String admissionId) {
        this.name = name;
        this.severity = severity;
        this.lastVisit = lastVisit;
        this.admissionId = admissionId;
    }

    // Getters and Setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }



}

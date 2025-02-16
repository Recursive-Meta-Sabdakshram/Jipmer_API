package com.jipmer.entity;



import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Admission")
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false, insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorid", nullable = false, insertable = false, updatable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "depid", nullable = false, insertable = false, updatable = false)
    private Department department;

    @Column(name = "pid", nullable = false)
    private Integer pid;

    @Column(name = "doctorid", nullable = false)
    private Integer doctorid;

    @Column(name = "depid", nullable = false)
    private Integer depid;

    @Column(name = "createddate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "updaeddate")
    @Temporal(TemporalType.DATE)
    private Date updaedDate;

    @Column(name = "severity", nullable = false)
    private String severity;

    @OneToMany(mappedBy = "admission", cascade = CascadeType.ALL)
    private List<Message> messages;

    // Constructors, Getters, and Setters

    public Admission() {
    }

    public Admission(Integer pid, Integer doctorid, Integer depid, Date createdDate, Date updaedDate) {
        this.pid = pid;
        this.doctorid = doctorid;
        this.depid = depid;
        this.createdDate = createdDate;
        this.updaedDate = updaedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Integer doctorid) {
        this.doctorid = doctorid;
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdaedDate() {
        return updaedDate;
    }

    public void setUpdaedDate(Date updaedDate) {
        this.updaedDate = updaedDate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }


    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {

        this.messages = messages;
    }

}

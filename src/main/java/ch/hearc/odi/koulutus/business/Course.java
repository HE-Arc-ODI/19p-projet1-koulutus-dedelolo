package ch.hearc.odi.koulutus.business;

import java.util.ArrayList;

public class Course {

    private Integer id;
    private Integer quarter;
    private Integer year;
    private Integer maxNumberOfParticipants;
    private String status;
    private ArrayList<Session> sessions;

    public Course(Integer id, Integer quarter, Integer year, Integer maxNumberOfParticipants, String status) {
        this.id = id;
        this.quarter = quarter;
        this.year = year;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMaxNumberOfParticipants() {
        return maxNumberOfParticipants;
    }

    public void setMaxNumberOfParticipants(Integer maxNumberOfParticipants) {
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }
}

package ch.hearc.odi.koulutus.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Course")
public class Course{

    private Integer id;
    private Integer quarter;
    private Integer year;
    private Integer maxNumberOfParticipants;
    private String status;
    private List<Session> sessions;

    public Course() {
        sessions = new ArrayList<>();
    }

    public Course(Integer id, Integer quarter, Integer year, Integer maxNumberOfParticipants, String status) {
        this();
        this.id = id;
        this.quarter = quarter;
        this.year = year;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.status = status;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    @OneToMany(targetEntity = Session.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "sessions")
    @OrderColumn(name = "order_sessions")
    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}

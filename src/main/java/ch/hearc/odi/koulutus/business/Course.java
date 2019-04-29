package ch.hearc.odi.koulutus.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.glassfish.jersey.message.internal.Statuses;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Course")
public class Course{

    public enum QuarterEnum {
        NUMBER_1(1),

        NUMBER_2(2),

        NUMBER_3(3),

        NUMBER_4(4);
        private Integer value;

        QuarterEnum(Integer value) {
            this.value = value;
        }
        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

    public enum StatusEnum {
        OPEN("OPEN"),

        CONFIRMED("CONFIRMED"),

        CANCELLED("CANCELLED");
        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

    private Long id;
    private QuarterEnum quarter;
    private Integer year;
    private Integer maxNumberOfParticipants;
    private StatusEnum status;
    private List<Session> sessions;
    private Participant participant;

    public Course() {
        sessions = new ArrayList<>();
    }

    public Course(Long id, QuarterEnum quarter, Integer year, Integer maxNumberOfParticipants, StatusEnum status) {
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuarterEnum getQuarter() {
        return quarter;
    }

    public void setQuarter(QuarterEnum quarter) {
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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
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

    @ManyToOne
    @JsonBackReference
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void addSession(Session s){
        sessions.add(s);
    }
}

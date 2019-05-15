package ch.hearc.odi.koulutus.business;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

@Entity
@Table
public class Course  implements Serializable {

  private Long id;
  private int year;
  private int maxNumberOfParticipants;
  private List<Participant> participants;

  private Program program;

  public Course() {
    participants = new ArrayList<>();
    status = CourseStatus.OPEN;
  }

  public enum CourseStatus {
    OPEN,
    CONFIRMED,
    CANCELED
  }

  private CourseStatus status;

  @Min(1)
  @Max(4)
  private Integer quarter;
  private List<Session> sessions;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMaxNumberOfParticipants() {
    return maxNumberOfParticipants;
  }

  public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
    this.maxNumberOfParticipants = maxNumberOfParticipants;
  }

  public CourseStatus getStatus() {
    return status;
  }

  public void setStatus(CourseStatus status) {
    this.status = status;
  }

  @OneToMany(targetEntity = Session.class, fetch = FetchType.EAGER)
  public List<Session> getSessions() {
    return sessions;
  }

  public void setSessions(List<Session> sessions) {
    this.sessions = sessions;
  }

  public Integer getQuarter() {
    return quarter;
  }

  public void setQuarter(Integer quarter) {
    this.quarter = quarter;
  }

  @ManyToMany(targetEntity = Participant.class, fetch = FetchType.EAGER)
  @JoinTable(name = "course_participant",
      joinColumns = @JoinColumn(name = "course_ID"),
      inverseJoinColumns = @JoinColumn(name = "participant_ID"))
  @OrderColumn(name = "order_course_participant")
  public List<Participant> getParticipants() {
    return participants;
  }

  public void setParticipants(List<Participant> participants) {
    this.participants = participants;
  }

  @ManyToOne
  @IndexColumn(name="Programs")
  public Program getProgram() {
    return program;
  }

  public void setProgram(Program program) {
    this.program = program;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o){
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Course course = (Course) o;
    return id == course.getId() &&
        quarter == course.getQuarter() &&
        year == course.getYear() &&
        maxNumberOfParticipants == course.getMaxNumberOfParticipants();
  }
}

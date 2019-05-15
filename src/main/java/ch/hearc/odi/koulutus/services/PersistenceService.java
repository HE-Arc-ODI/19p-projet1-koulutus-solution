/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.services;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.exception.CourseException;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.exception.SessionException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PersistenceService {
  private static Logger logger = LogManager.getLogger(PersistenceService.class);
  private EntityManagerFactory entityManagerFactory;

  public PersistenceService() {
    //  an EntityManagerFactory is set up once for an application
    //  IMPORTANT: the name here matches the name of persistence-unit in persistence.xml
    entityManagerFactory = Persistence.createEntityManagerFactory("ch.hearc.odi.koulutus.jpa");
  }

  @Override
  public void finalize() throws Throwable {
    entityManagerFactory.close();
    super.finalize();
  }

  public ArrayList<Participant> getParticipants() throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Participant> participants = entityManager
        .createQuery("from Participant", Participant.class)
        .getResultList();
    if (participants == null) {
      logger.error(" no participants available");
      throw new ParticipantException("the list is empty");
    }
    entityManager.getTransaction().commit();
    entityManager.close();
    return (ArrayList<Participant>) participants;
  }

  public Participant getParticipantById(Long participantId) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantId);
    if (participant == null) {
      logger.error(" Participant with id " +participantId +" not found");
      throw new ParticipantException("Participant " + participantId + " was not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();

    return participant;
  }

  public Participant createAndPersistParticipant(Participant participant)
      throws ParticipantException {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(participant);
      entityManager.getTransaction().commit();
      entityManager.close();
      logger.info("Participant " + participant.getFirstName() + " " + participant.getLastName()
          + " created");
      return participant;
  }

  public void deleteParticipantById(Long participantId) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Participant participant = entityManager.find(Participant.class, participantId);
    if (participant == null) {
      logger.error(" Participant with id " +participantId +" not found");
      throw new ParticipantException("Participant " + participantId + " was not found");
    }
    entityManager.getTransaction().begin();
    List<Course> courses = participant.getCourses();
    for (Course course : courses) {
      course.getParticipants().remove(participant);
    }
    courses.clear();
    entityManager.remove(participant);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  public Participant updateParticipantById(Long participantId, Participant newParticipant)
      throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantId);
    if( participant == null){
      logger.error("Participant not found");
      throw new ParticipantException("Participant " + participantId + " was not found");
    }
    participant.setFirstName(newParticipant.getFirstName());
    participant.setLastName(newParticipant.getLastName());
    participant.setBirthdate(newParticipant.getBirthdate());
    entityManager.getTransaction().commit();
    logger.info("Participant "+participant.getFirstName() + " " + participant.getLastName() + " updated");
    return participant;
  }

  public List<Course> getCoursesByParticipantId(Long participantId) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantId);
    if (participant == null) {
      logger.error("Participant not found");
      throw new ParticipantException("Participant " + participantId + " was not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();

    return participant.getCourses();
  }

  public Program createAndPersistProgram(Program program) throws ProgramException {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(program);
      entityManager.getTransaction().commit();
      entityManager.close();
      return program;
  }

  public void deleteProgramById(Long programId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Program program = entityManager.find(Program.class, programId);
    if (program == null) {
      logger.error(" programs with id "+ programId+ " not found");
    }
    entityManager.getTransaction().begin();
    entityManager.remove(program);
    entityManager.getTransaction().commit();
    logger.info(" Program " + program.getName() +" deleted");
    entityManager.close();
  }

  public ArrayList<Program> getPrograms() throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Program> program = entityManager
        .createQuery("from Program", Program.class)
        .getResultList();
    entityManager.getTransaction().commit();
    if (program == null) {
      logger.error(" programs not found");
      throw new ProgramException(" Program not found");
    }
    entityManager.close();
    return (ArrayList<Program>) program;
  }

  public Program updateProgramById(Long programId, Program newProgram) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);
    program.setName(newProgram.getName());
    program.setField(newProgram.getField());
    program.setPrice(newProgram.getPrice());
    program.setRichDescription(newProgram.getRichDescription());
    entityManager.getTransaction().commit();
    logger.info(" program " + program.getName() + " updated");
    return program;
  }

  public Program getProgramById(Long programId) throws ProgramException {

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);
    if (program == null) {
      logger.error(" program with id " + program.getId() + " not found");
      throw new ProgramException(" Program not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();
    return program;
  }

  public List<Course> getCoursesByProgramId(Long programId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Course> query = entityManager
        .createQuery("SELECT c from Course c where c.program.id = :programId", Course.class);
    List<Course> courses = query.setParameter("programId", programId).getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
    return (ArrayList<Course>) courses;
  }

  public Course createAndPersistCourse(Long programId, Course course) throws ProgramException {
    Program program = this.getProgramById(programId);
    program.setId(programId);
    course.setProgram(program);
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(course);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("course with id" + course.getId() + " created" );
    return course;
  }

  public Course getCourseById(Long programId, Long courseId) throws CourseException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Course> query = entityManager.createQuery(
        "SELECT c from Course c where c.program.id = :programId and c.id = :courseId",
        Course.class);
    Course course = query.setParameter("programId", programId)
        .setParameter("courseId", courseId)
        .getSingleResult();
    entityManager.getTransaction().commit();
    if(course == null){
      logger.error(" no courses found");
      throw new CourseException(" course not found");
    }

    entityManager.close();

    return course;
  }

  public void deleteCourse(Long programId, Long courseId) throws CourseException, ProgramException {
    Course course = this.getCourseById(programId, courseId);
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.remove(entityManager.contains(course) ? course : entityManager.merge(course));
    entityManager.getTransaction().commit();
    logger.info("course deleted");
    entityManager.close();
  }

  public Course updateCourse(Long programId, Long courseId, Course newCourse)
      throws CourseException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = getCourseById(programId, courseId);
    if (course == null) {
      logger.error( "course with id " + courseId +" not found");
      throw new CourseException(" course not found");
    }
    course.setQuarter(newCourse.getQuarter());
    course.setYear(newCourse.getYear());
    course.setMaxNumberOfParticipants(newCourse.getMaxNumberOfParticipants());
    entityManager.getTransaction().commit();
    logger.info(" course" + course.getId() + " upodated");
    return course;
  }

  public ArrayList<Session> getSessions(Long programId, Long courseId) throws SessionException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Session> query = entityManager.createQuery(
        "SELECT s from Session s where s.course.program.id = :programId and s.course.id = :courseId",
        Session.class);
    List<Session> sessions = query.setParameter("programId", programId)
        .setParameter("courseId", courseId).getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
    if( sessions == null){
      logger.error( "sessions not found");
      throw new SessionException(" session not found");
    }
    return (ArrayList<Session>) sessions;
  }

  public Session getSessionById(Long programId, Long courseId, Long sessionId)
      throws SessionException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Session> query = entityManager.createQuery(
        "SELECT s from Session s where s.id = :sessionId and s.course.program.id = :programId and s.course.id = :courseId",
        Session.class);
    Session session = query.setParameter("sessionId", sessionId)
        .setParameter("programId", programId)
        .setParameter("courseId", courseId)
        .getSingleResult();
    entityManager.getTransaction().commit();
    entityManager.close();
    if(session == null){
      logger.error( "sessions not found");
      throw new SessionException(" session not found");
    }
    return session;
  }

  public void deleteSessionById(Long programId, Long courseId, Long sessionId)
      throws SessionException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    //TODO: duplicated code
    TypedQuery<Session> query = entityManager.createQuery(
        "SELECT s from Session s where s.id = :sessionId and s.course.program.id = :programId and s.course.id = :courseId",
        Session.class);

    Session session = query.setParameter("sessionId", sessionId)
        .setParameter("programId", programId)
        .setParameter("courseId", courseId)
        .getSingleResult();
    if (session == null) {
     logger.error(" session not found ");
      throw new SessionException(" session not found");
    }
    entityManager.remove(session);
    entityManager.getTransaction().commit();
    logger.info( " session deleted");
    entityManager.close();
  }

  public Session updateSession(Long programId, Long courseId, Long sessionId, Session newSession)
      throws SessionException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    //TODO: duplicated code
    TypedQuery<Session> query = entityManager.createQuery(
        "SELECT s from Session s where s.id = :sessionId and s.course.program.id = :programId and s.course.id = :courseId",
        Session.class);

    Session session = query.setParameter("sessionId", sessionId)
        .setParameter("programId", programId)
        .setParameter("courseId", courseId)
        .getSingleResult();
    if (session == null) {
      logger.error( " session not found");
      throw new SessionException(" session not found");
    }
    session.setStartDateTime(newSession.getStartDateTime());
    session.setEndDateTime(newSession.getEndDateTime());
    session.setPrice(newSession.getPrice());
    session.setRoom(newSession.getRoom());
    entityManager.getTransaction().commit();
    logger.info("session updated");
    return session;
  }

  public List<Session> addSessions(Long programId, Long courseId, List<Session> sessions)   throws SessionException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    for (Session session : sessions) {
      Program program = new Program();
      program.setId(programId);
      Course course = new Course();
      course.setId(courseId);
      course.setProgram(program);
      session.setCourse(course);
      entityManager.persist(session);
      entityManager.flush();
      entityManager.clear();
    }
    entityManager.getTransaction().commit();
    logger.info("session added");
    return sessions;
  }

  public List<Participant> getParticipantByCourseId(Long programId, Long courseId)
      throws CourseException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = entityManager.find(Course.class, courseId);
    if (course == null) {
      logger.error(" course not found");
      throw new CourseException("Course not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();
    return course.getParticipants();
  }

  public void registerParticipantToCourse(Long programId, Long courseId, Long participantId)
      throws ProgramException, CourseException, ParticipantException {
    Course course = this.getCourseById(programId, courseId);
    Participant participant = this.getParticipantById(participantId);

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    course.getParticipants().add(participant);
    participant.getCourses().add(course);
    entityManager.merge(course);
    entityManager.merge(participant);
    entityManager.getTransaction().commit();
    entityManager.close();
  }
}






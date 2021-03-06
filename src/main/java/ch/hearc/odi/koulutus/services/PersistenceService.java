/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.services;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Course.QuarterEnum;
import ch.hearc.odi.koulutus.business.Course.StatusEnum;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Pojo;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistenceService {

  private EntityManagerFactory entityManagerFactory;

  private static final Logger logger = LogManager.getLogger(PersistenceService.class);

  public PersistenceService() {
    //  an EntityManagerFactory is set up once for an application
    //  IMPORTANT: the name here matches the name of persistence-unit in persistence.xml
    entityManagerFactory = Persistence.createEntityManagerFactory("ch.hearc.odi.koulutus.jpa");
  }

  /**
   * Return all existing programs
   *
   * @return a list
   */
  public ArrayList<Program> getPrograms() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Program> programs = entityManager.createQuery("from Program", Program.class)
        .getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Retrieve list of programs. Total :"+programs.size());
    return (ArrayList<Program>) programs;
  }

  /**
   * Create a new Program and persist
   *
   * @return the Program object created
   */
  public Program createAndPersistProgram(String name, String richDescription, String field, BigDecimal price) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = new Program(name,richDescription,field,price);
    entityManager.persist(program);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Create and persist program. Name : "+name);
    return program;
  }

  /**
   * Return program by ID
   *
   * @return a program
   */
  public Program getProgramById(Long programId) throws ProgramException {
    Program program = getProgramByID(programId);
    return program;
  }

  /**
   * Return course by ID
   *
   * @return a course
   */
  private Course getCourseById(Long courseId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = entityManager.find(Course.class, courseId);

    if (course == null) {
      logger.warn("Course " + courseId + " was not found");
      throw new ProgramException("Course " + courseId + " was not found");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Course " + courseId + " was found");
    return course;
  }

  /**
   * Return session by ID
   *
   * @return a session
   */
  private Session getSessionById(Long sessionId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Session session = entityManager.find(Session.class, sessionId);

    if (session == null) {
      logger.warn("Session " + sessionId + " was not found");
      throw new ProgramException("Session " + sessionId + " was not found");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Session " + sessionId + " was found");
    return session;
  }

  /**
   * Delete a program
   *
   * @return void
   */
  public void deleteProgram(Long programId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);
    entityManager.remove(program);
    if (program == null) {
      logger.warn("Program " + programId + " was not found for deletion");
      throw new ProgramException("Program " + programId + " was not found for deletion");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Program " + programId + " was deleted");
  }

  /**
   * Update a program
   *
   * @return program
   */
  public Program putProgram(Long programid, String name, String richDescription, String field, BigDecimal price) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programid);
    if (program == null) {
      logger.warn("Program " + programid + " was not found for update");
      throw new ProgramException("Program " + programid + " was not found for update");
    }
    Program programUpdated = new Program(programid,name,richDescription,field,price);
    entityManager.merge(programUpdated);
    entityManager.getTransaction().commit();
    entityManager.close();

    logger.info("Program " + programid + " was updated");
    return programUpdated;
  }

  /**
   * Return list of course for a given program
   *
   * @return list of course
   */
  public List<Course> getProgramAllCourse(Long programId) throws ProgramException {
    Program program = getProgramByID(programId);

    return program.getCourses();
  }

  private Program getProgramByID(Long programId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = entityManager.find(Program.class, programId);

    if (program == null) {
      logger.warn("Program " + programId + " was not found");
      throw new ProgramException("Program " + programId + " was not found");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Program " + programId + " was found");
    return program;
  }

  /**
   * Add a new course to an existing program
   *
   * @return a course
   */
  public Course addNewCourseToExistingProgram(Long programId,Long courseId, QuarterEnum quarter, Integer year, Integer maxNumberOfParticipants) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Course course = new Course(courseId, quarter, year, maxNumberOfParticipants, StatusEnum.OPEN);
    Program program = getProgramById(programId);

    if (program != null) {
      program.addCourse(course);
      entityManager.persist(course);
      entityManager.persist(program);//on devrait persister program cependant il y a une erreur lors de la persistencee par la suite.
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      throw new ProgramException("Program " + programId + " was not found");
    }

    return course;
  }

  /**
   * Return all sessions for a given course and program
   *
   * @return sessions
   */
  public List<Session> getSessionsForGivenCourseAndProgram(Long programId, Long courseId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Session> query = entityManager
        .createQuery("SELECT s.id from Session s JOIN Course c JOIN Program p where p.id = :programId AND c.id = :courseId", Session.class);
    List<Session> sessions = query.setParameter("programId",programId).setParameter("courseId",courseId).getResultList();

    if(sessions == null){
      logger.warn("Program or course was not found");
      throw new ProgramException("Program or course was not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info(sessions.size()+" sessions were returned");
    return sessions;
  }

  /**
   * Create sessions for a given course and program
   *
   * @return list of sessions
   */
  public List<Session> registerSessionsForCourseAndProgram(Long programId, Long courseId, List<Session> sessions) throws ProgramException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programId);
    Course course = getCourseById(courseId);

    if(course == null || program == null){
      logger.warn("Program or course was not found");
      throw new ProgramException("Program or course was not found");
    }
    for (Session s:sessions){
      course.addSession(s);
      logger.info("Session "+s.getId()+" added to course "+course.getId());
    }
    entityManager.persist(course);
    entityManager.getTransaction().commit();
    entityManager.close();
    return course.getSessions();
  }

  /**
   * Return details of a session for a given course and program
   *
   * @return a session
   */
  public Session getDetailsForGivenCourseAndProgram(Long programid, Long courseid, Long sessionid) throws ProgramException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programid);
    Course course = getCourseById(courseid);
    Session session = getSessionById(sessionid);

    if(course == null || program == null || session == null){
      logger.warn("Program or course or session was not found");
      throw new ProgramException("Program or course or session was not found");
    }
    entityManager.close();
    return session;
  }


  /**
   * delete a session by id for a given course and program
   *
   * @return void
   */
  public void deleteSessionFromProgramCourse(Long programid, Long courseid, Long sessionid) throws ProgramException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programid);
    Course course = getCourseById(courseid);
    Session session = getSessionById(sessionid);

    if(course == null || program == null || session == null){
      logger.warn("Program or course or session was not found");
      throw new ProgramException("Program or course or session was not found");
    }
    entityManager.remove(session);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Session " + sessionid + " was deleted");
  }

  /**
   * update a session by id for a given course and program
   *
   * @return session
   */
  public Session updateSessionFromProgramCourse(Long programid, Long courseid, Long sessionid, Date startDateTime, Date endDateTime, Double price, String room) throws ProgramException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programid);
    Course course = getCourseById(courseid);
    Session session = getSessionById(sessionid);

    if(course == null || program == null || session == null){
      logger.warn("Program or course or session was not found");
      throw new ProgramException("Program or course or session was not found");
    }
    Session sessionUpdated = new Session(sessionid,startDateTime,endDateTime,price,room);
    entityManager.merge(sessionUpdated);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Session " + sessionid + " was updated");
    return sessionUpdated;
  }

  /**
   * Return all existing participant
   *
   * @return a list
   */
  public ArrayList<Participant> getParticipant() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    List<Participant> participant = entityManager.createQuery("from Participant", Participant.class)
        .getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Retrieve list of participant. Total :"+participant.size());
    return (ArrayList<Participant>) participant;
  }

  /**
   * Create a new Participant and persist
   *
   * @return the Participant object created
   */
  public Participant createAndPersistParticipant(Long id, String firstName, String lastName, Date birthdate) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = new Participant(id, firstName, lastName, birthdate);
    entityManager.persist(participant);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Create and persist participant. Name : "+firstName+" "+lastName);
    return participant;
  }

  /**
   * Return participant by ID
   *
   * @return a participant
   */
  public Participant getParticipantById(Long participantId) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantId);

    if (participant == null) {
      logger.warn("Participant " + participantId + " was not found");
      throw new ParticipantException("Participant " + participantId + " was not found");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Participant " + participantId + " was found");
    return participant;
  }

  /**
   * Delete a participant
   *
   * @return void
   */
  public void deleteParticipant(Long participantId) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantId);
    entityManager.remove(participant);
    if (participant == null) {
      logger.warn("Participant " + participantId + " was not found for deletion");
      throw new ParticipantException("Participant " + participantId + " was not found for deletion");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Participant " + participantId + " was deleted");
  }

  /**
   * Update a participant
   *
   * @return participant
   */
  public Participant putParticipant(Long participantId, String firstname, String lastname, Date birthdate) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantId);
    if (participant == null) {
      logger.warn("Participant " + participantId + " was not found for update");
      throw new ParticipantException("Participant " + participantId + " was not found for update");
    }
    Participant participantUpdated = new Participant(participantId,firstname,lastname,birthdate);
    entityManager.merge(participantUpdated);
    entityManager.getTransaction().commit();
    entityManager.close();

    logger.info("Participant " + participantId + " was updated");
    return participantUpdated;
  }

  /**
   * Return all existing course for a participant
   *
   * @return a list
   */
  public ArrayList<Course> getParticipantCourseSummary(Long participantid) throws ParticipantException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Course> query = entityManager
        .createQuery("SELECT c from Course c where c.participant.id = :participantid", Course.class);

    List<Course> courses = query.setParameter("participantid", participantid).getResultList();

    if (courses == null) {
      throw new ParticipantException("Participant " + participantid + " was not found");
    }
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Retrieve list of course for a participant. Total :"+courses.size());
    return (ArrayList<Course>) courses;
  }

  /**
   * Register a participant to a course
   *
   * @return void
   */
  public void registerParticipantToCourse(Long programid, Long courseid, Long participantid) throws ParticipantException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Participant participant = entityManager.find(Participant.class, participantid);
    Course course = entityManager.find(Course.class, courseid);
    Program program = entityManager.find(Program.class, programid);
    entityManager.remove(participant);
    if (participant == null || course == null || program == null) {
      logger.warn("Participant or course or program not found");
      throw new ParticipantException("Participant or course or program not found");
    }
    participant.addCourse(course);
    entityManager.merge(participant);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Participant " + participantid + " was registered to course " + courseid);//todo fonction a tester 27.04.2019
  }

  /**
   * Get course by ID
   *
   * @return course
   */
  public Course getProgramCourseById(Long programId, Long courseId) throws ProgramException{
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programId);
    Course course = getCourseById(courseId);

    if (program == null || course == null) {
      logger.warn("Program or course was not found");
      throw new ProgramException("Program or course was not found");
    }

    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Course " + course.getId() + " was found");
    return course;
  }

  /**
   * Delete a course by id
   *
   * @return void
   */
  public void deleteCourseByProgramId(Long programId, Long courseId) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programId);
    Course course = getCourseById(courseId);

    if (program == null || course == null) {
      logger.warn("Program or course was not found");
      throw new ProgramException("Program or course was not found");
    }
    entityManager.remove(course);
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Course " + courseId + " was deleted");
  }


  /**
   * Update a course from program
   *
   * @return course
   */
  public Course updateCourseFromProgram(Long programid, Long courseid, QuarterEnum quarter, Integer year, Integer maxNumberOfParticipants) throws ProgramException {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Program program = getProgramById(programid);
    Course course = getCourseById(courseid);
    if (program == null || course == null) {
      logger.warn("Program or course was not found");
      throw new ProgramException("Program or course was not found");
    }
    Course CourseUpdated = new Course(courseid,quarter,year,maxNumberOfParticipants,course.getStatus());
    entityManager.merge(CourseUpdated);
    entityManager.getTransaction().commit();
    entityManager.close();

    logger.info("Course " + courseid + " was updated");
    return CourseUpdated;
  }

  /**
   * Return all existing participant from a course and program
   *
   * @return a list
   */
  public List<Participant> getParticipantsFromProgramCourse(Long programid, Long courseid) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    TypedQuery<Participant> query = entityManager
        .createQuery("SELECT pa from Participant pa JOIN Course c JOIN Program p where pa.id = :programId AND c.id = :courseId", Participant.class);
    List<Participant> participants = query.setParameter("programId",programid).setParameter("courseId",courseid).getResultList();
    entityManager.getTransaction().commit();
    entityManager.close();
    logger.info("Retrieve list of participant. Total :"+participants.size());
    return participants;
  }

  @Override
  public void finalize() throws Throwable {
    entityManagerFactory.close();
    super.finalize();
  }

  public Pojo createAndPersistAPojo(String myProperty){
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Pojo pojo = new Pojo();
    pojo.setSomeProperty(myProperty);
    entityManager.persist(pojo);
    entityManager.getTransaction().commit();
    entityManager.close();
    return pojo;
  }

}






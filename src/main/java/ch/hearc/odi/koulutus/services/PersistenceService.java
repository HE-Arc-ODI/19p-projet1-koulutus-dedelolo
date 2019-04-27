/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.services;


import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Pojo;
import ch.hearc.odi.koulutus.business.Program;
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






package ch.hearc.odi.koulutus.services;

import static org.junit.Assert.*;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Pojo;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersistenceServiceTest {

  private PersistenceService persistenceService;
  private EntityManagerFactory entityManagerFactory;

  @Before
  public void setUp() throws Exception {
    persistenceService = new PersistenceService();
    entityManagerFactory = Persistence.createEntityManagerFactory("ch.hearc.odi.koulutus.jpa");
  }

  @Test
  public void createAndPersistAPojo() {
    String testString = "hello world!";
    Pojo pojo = persistenceService.createAndPersistAPojo("hello world!");

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedPojoID = pojo.getId();
    Pojo actualPojo = entityManager.find(Pojo.class, expectedPojoID);

    assertEquals(pojo, actualPojo);
  }

  @Test
  public void createAndPersistProgram() {
    String testName = "nomDeProgramme";
    String testDescr = "description du fameux programme";
    String testField = "informatique";
    BigDecimal testPrice = new BigDecimal(145);
    Program program = persistenceService.createAndPersistProgram("nomDeProgramme","description du fameux programme","informatique", new BigDecimal(145));

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedProgramID = program.getId();
    Program actualProgram = entityManager.find(Program.class, expectedProgramID);

    assertEquals(program, actualProgram);
  }

  @Test
  public void createAndPersistParticipant() {
    Long testID = Long.valueOf(12345678910L);
    String testFirst = "John";
    String testLast = "Doe";
    Date testBDay = new Date( "2009-12-31" );
    Participant participant = persistenceService.createAndPersistParticipant(Long.valueOf(12345678910L), "John", "Doe", new Date( "2009-12-31" ) );

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedParticipantID = participant.getId();
    Participant actualParticipant = entityManager.find(Participant.class, expectedParticipantID);

    assertEquals(participant, actualParticipant);
  }

  /*
  @Test
  public void List<Session> registerSessionsForCourseAndProgram() {
    Long testProgramID = Long.valueOf(123123L);
    Long testCourseID = Long.valueOf((long) 456456D);
    List<Session> testSessions;
    Session session = persistenceService.registerSessionsForCourseAndProgram(Long.valueOf(123123L), Long.valueOf(456456D, List<Session> testSessions);

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedCourseID = testCourseID;
    long expectedProgramID = testProgramID;
    //Participant actualParticipant = entityManager.find(Participant.class, expectedParticipantID);

    //assertEquals(participant, actualParticipant);
  }
  */

/*
  @Test
  public void deleteProgram(Long programId) {
    int testProgramID = "1010";
    Program program = persistenceService.deleteProgram("1010");

    Response expectedResponse = Response.status(Response.Status.OK);
      }
*/



}
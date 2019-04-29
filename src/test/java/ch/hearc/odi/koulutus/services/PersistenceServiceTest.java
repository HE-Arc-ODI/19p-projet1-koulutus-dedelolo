package ch.hearc.odi.koulutus.services;

import static org.junit.Assert.*;

import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Course.QuarterEnum;
import ch.hearc.odi.koulutus.business.Pojo;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ProgramException;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
  public void createAndPersistProgram(){

    Program program2 = persistenceService.createAndPersistProgram("test","test","math", new BigDecimal(30));
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedProgramID = program2.getId();

    Program actualProgram = entityManager.find(Program.class, expectedProgramID);

    assertEquals(program2.getId(),actualProgram.getId());
  }

  @Test(expected=NullPointerException.class)
  public void deleteProgramById() throws ProgramException {
    Program program2 = persistenceService.createAndPersistProgram("test","test","math", new BigDecimal(30));
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedProgramID = program2.getId();

    persistenceService.deleteProgram(expectedProgramID);

    Program actualProgram = entityManager.find(Program.class, expectedProgramID);

    assertEquals(program2.getId(),actualProgram.getId());
  }

  @Test
  public void putProgramById() throws ProgramException{
    Program program2 = persistenceService.createAndPersistProgram("test","test","math", new BigDecimal(30));
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    long expectedProgramID = program2.getId();
    String expectedName = "testUpdated";

    Program programUpdated = persistenceService.putProgram(expectedProgramID,"testUpdated","test","math",new BigDecimal(30));
    String actualName = programUpdated.getName();

    assertEquals(expectedName,actualName);
  }
}
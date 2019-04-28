package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("program")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProgramResource {
  @Inject
  private PersistenceService persistenceService;

  @GET
  public List<Program> programGet(){
    return persistenceService.getPrograms();
  }

  @POST
  public Program programPost(Program program ){
    return persistenceService.createAndPersistProgram(program.getName(),program.getRichDescription(),program.getField(),program.getPrice());
  }

  @GET
  @Path("{programId}")
  public Program programIdGet(@PathParam("programId") Long programid){
    try{
      return persistenceService.getProgramById(programid);
    }catch(ProgramException e){
      throw new NotFoundException("the program does not exist");
    }
  }

  @DELETE
  @Path("{programId}")
  public Response programDelete(@PathParam("programId") Long programid){
    try{
      persistenceService.deleteProgram(programid);
      return Response.status(Response.Status.OK).build();
    }catch (Exception e){
      e.printStackTrace();
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @PUT
  @Path("{programId}")
  public Program programPut(@PathParam("programId") Long programid, Program program){
    try{
      return persistenceService.putProgram(programid, program.getName(),program.getRichDescription(),program.getField(),program.getPrice());
    }catch (ProgramException e){
      throw new NotFoundException("the program does not exist");
    }
  }

  @GET
  @Path("{programId}/course/{courseId}/session")
  public List<Session> programCourseSessionGet(@PathParam("programId") Long programid, @PathParam("courseId") Long courseid){
    try {
      return persistenceService.getSessionsForGivenCourseAndProgram(programid, courseid);
    }catch(Exception e){
      e.printStackTrace();
      throw new NotFoundException("the program or course does not exist");
    }//todo fonction a tester lorsque course sera fait 27.04.2019
  }

  @POST
  @Path("{programId}/course/{courseId}/session")
  public List<Session> programCreateNewSessionsForCourseAndProgram(@PathParam("programId") Long programid, @PathParam("courseId") Long courseid, List<Session> sessions){
    try {
      return persistenceService.registerSessionsForCourseAndProgram(programid, courseid, sessions);
    }catch(ProgramException e){
      throw new NotFoundException("the program does not exist");
    }//todo fonction a tester lorsque course sera fait 28.04.2019
  }

  @GET
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public Session programCourseSessionGetDetails(@PathParam("programId") Long programid, @PathParam("courseId") Long courseid, @PathParam("sessionId") Long sessionid){
    try {
      return persistenceService.getDetailsForGivenCourseAndProgram(programid, courseid, sessionid);
    }catch(Exception e){
      e.printStackTrace();
      throw new NotFoundException("the program or course does not exist");
    }//todo fonction a tester lorsque course sera fait 28.04.2019
  }

  @DELETE
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public Response programDeleteSessionFromProgramCourse(@PathParam("programId") Long programid, @PathParam("courseId") Long courseid, @PathParam("sessionId") Long sessionid){
    try{
      persistenceService.deleteSessionFromProgramCourse(programid,courseid,sessionid);
      return Response.status(Response.Status.OK).build();
    }catch (Exception e){
      e.printStackTrace();
      return Response.status(Status.NOT_FOUND).build();
    }//todo fonction a tester lorsque course sera fait 28.04.2019
  }

  @PUT
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public Session programPutSessionFromProgramCourse(@PathParam("programId") Long programid, @PathParam("courseId") Long courseid, @PathParam("sessionId") Long sessionid, Session session){
    try{
      return persistenceService.updateSessionFromProgramCourse(programid,courseid,sessionid,session.getStartDateTime(),session.getEndDateTime(),session.getPrice(),session.getRoom());
    }catch (ProgramException e){
      throw new NotFoundException("the program does not exist");
    }
  }


    @POST
  @Path("{programId}/course/{courseId}/participant/{participantId}")
  public Response programRegisterParticipantToCourse(@PathParam("programId") Long programid, @PathParam("courseId") Long courseid, @PathParam("participantId") Long participantid){
    try{
      persistenceService.registerParticipantToCourse(programid,courseid,participantid);
      return Response.status(Response.Status.OK).build();
    }catch (Exception e){
      e.printStackTrace();
      return Response.status(Status.NOT_FOUND).build();
    }
  }
}

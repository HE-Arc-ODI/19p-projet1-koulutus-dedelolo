package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
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

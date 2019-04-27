package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("participant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParticipantResource {

  @Inject
  private PersistenceService persistenceService;

  @GET
  public List<Participant> participantGet(){
    return persistenceService.getParticipant();
  }

  @POST
  public Participant participantPost(Participant participant){
    return persistenceService.createAndPersistParticipant(participant.getId(),participant.getFirstName(),participant.getLastName(),participant.getBirthdate());
  }

  @GET
  @Path("{participantId}")
  public Participant participantIdGet(@PathParam("participantId") Long participantid){
    try{
      return persistenceService.getParticipantById(participantid);
    }catch(ParticipantException e){
      throw new NotFoundException("the program does not exist");
    }
  }
}

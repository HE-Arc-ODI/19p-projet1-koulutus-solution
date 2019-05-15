package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.services.PersistenceService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Path("participant")
@Produces(MediaType.APPLICATION_JSON)

public class ParticipantResource {

  @Inject
  private PersistenceService persistenceService;

  @GET
  public List<Participant> participantGet() {
    try {
      return persistenceService.getParticipants();
    } catch (ParticipantException e) {
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{participantId}")
  public Participant getParticipantById(@PathParam("participantId") Long participantId) {
    try {
      return persistenceService.getParticipantById(participantId);
    } catch (ParticipantException e) {
      throw new WebApplicationException(e.getMessage());
    }
  }

  @DELETE
  @Path("{participantId}")
  public void deleteParticipant(@PathParam("participantId") Long participantId) {
    try {
      persistenceService.deleteParticipantById(participantId);
    } catch (ParticipantException e) {
      throw new WebApplicationException(e.getMessage());
    }
  }

  @PUT
  @Path("{participantId}")
  public Participant updateParticipant(@PathParam("participantId") Long participantId,
      Participant participant) {
    try {
      return persistenceService.updateParticipantById(participantId, participant);
    } catch (ParticipantException e) {
      throw new WebApplicationException(e.getMessage());
    }
  }

  @POST
  public Participant addParticipant(Participant participant) {
    try{
    return persistenceService.createAndPersistParticipant(participant);
    } catch (ParticipantException e) {
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{participantId}/summary")
  public List<Course> getCoursesByParticipantId(@PathParam("participantId") Long participantId) {
    try {
      return persistenceService.getCoursesByParticipantId(participantId);
    } catch (ParticipantException e) {
      throw new WebApplicationException(e.getMessage());
    }
  }
}
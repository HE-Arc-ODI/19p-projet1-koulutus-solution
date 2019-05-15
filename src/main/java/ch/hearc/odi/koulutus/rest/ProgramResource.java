package ch.hearc.odi.koulutus.rest;

import ch.hearc.odi.koulutus.business.Course;
import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Program;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.exception.CourseException;
import ch.hearc.odi.koulutus.exception.ParticipantException;
import ch.hearc.odi.koulutus.exception.ProgramException;
import ch.hearc.odi.koulutus.exception.SessionException;
import ch.hearc.odi.koulutus.services.PersistenceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("program")
@Produces(MediaType.APPLICATION_JSON)

public class ProgramResource {

  @Inject
  private PersistenceService persistenceService;

  @GET
  public List<Program> ProgramGet() {
    try{
      return persistenceService.getPrograms();
    } catch ( ProgramException e ){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{programId}")
  public Program getProgram(@PathParam("programId") Long programId) {
 try {
   return persistenceService.getProgramById(programId);
      } catch ( ProgramException e ){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @DELETE
  @Path("{programId}")
  public void deleteProgram(@PathParam("programId") Long programId) {
    try{
    persistenceService.deleteProgramById(programId);
    } catch ( ProgramException e ){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @POST
  public Program addProgram(Program program) {
    try{
    return persistenceService.createAndPersistProgram(program);
    } catch ( ProgramException e ){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @PUT
  @Path("{programId}")

  public Program updateProgram(@PathParam("programId") Long programId, Program program) {
    try{
    return persistenceService.updateProgramById(programId, program);
    } catch ( ProgramException e ){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{programId}/course")
  public List<Course> getCoursesByProgramId(@PathParam("programId") Long programId) {
    return persistenceService.getCoursesByProgramId(programId);
  }

  @POST
  @Path("{programId}/course")
  public Course addCourse(@PathParam("programId") Long programId, Course course) throws ProgramException {
      return persistenceService.createAndPersistCourse(programId, course);
  }

  @GET
  @Path("{programId}/course/{courseId}")
  public Course getCourseById(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId) {
    try {
      return persistenceService.getCourseById(programId, courseId);
    } catch ( CourseException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @DELETE
  @Path("{programId}/course/{courseId}")
  public void deleteCourse(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId) {
    try{
    persistenceService.deleteCourse(programId, courseId);
  } catch ( CourseException e){
        throw new WebApplicationException(e.getMessage());
  } catch (ProgramException e) {
        throw new WebApplicationException(e.getMessage());
    }
  }

  @PUT
  @Path("{programId}/course/{courseId}")
  public Course updateCourse(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId, Course course) {
    try{
    return persistenceService.updateCourse(programId, courseId, course);
    } catch ( CourseException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{programId}/course/{courseId}/participant")
  public List<Participant> getParticipantByCourseId(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId) {
    try{
    return persistenceService.getParticipantByCourseId(programId, courseId);
    } catch ( CourseException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{programId}/course/{courseId}/session")
  public List<Session> GetSessions(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId) {
    try{
    return persistenceService.getSessions(programId, courseId);
    } catch ( SessionException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @POST
  @Path("{programId}/course/{courseId}/session")
  public List<Session> addSessions(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId, List<Session> sessions) {
    try{
    return persistenceService.addSessions(programId, courseId, sessions);
    } catch ( SessionException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @GET
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public Session getSessionById(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId, @PathParam("sessionId") Long sessionId) {
    try{
    return persistenceService.getSessionById(programId, courseId, sessionId);
    } catch ( SessionException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @DELETE
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public void deleteSessionById(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId, @PathParam("sessionId") Long sessionId) {
    try{
    persistenceService.deleteSessionById(programId, courseId, sessionId);
    } catch ( SessionException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @PUT
  @Path("{programId}/course/{courseId}/session/{sessionId}")
  public Session updateSession(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId, @PathParam("sessionId") Long sessionId,
      Session session) {
    try{
    return persistenceService.updateSession(programId, courseId, sessionId, session);
    } catch ( SessionException e){
      throw new WebApplicationException(e.getMessage());
    }
  }

  @POST
  @Path("/{programId}/course/{courseId}/participant/{participantId}")
  public void registerParticipantToCourse(@PathParam("programId") Long programId,
      @PathParam("courseId") Long courseId, @PathParam("participantId") Long participantId) {
    try{
    persistenceService.registerParticipantToCourse(programId, courseId, participantId);
    }
    catch ( ProgramException e){
      throw new WebApplicationException(e.getMessage());
    }
    catch ( ParticipantException e){
      throw new WebApplicationException(e.getMessage());
    }
    catch ( CourseException e){
      throw new WebApplicationException(e.getMessage());
    }
  }
}

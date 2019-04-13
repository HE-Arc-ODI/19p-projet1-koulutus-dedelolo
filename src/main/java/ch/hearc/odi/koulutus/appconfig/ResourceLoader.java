/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.appconfig;

import ch.hearc.odi.koulutus.business.Participant;
import ch.hearc.odi.koulutus.business.Session;
import ch.hearc.odi.koulutus.injection.ServiceBinder;
import ch.hearc.odi.koulutus.injection.ServiceFeature;
import ch.hearc.odi.koulutus.rest.CourseResource;
import ch.hearc.odi.koulutus.rest.ParticipantResource;
import ch.hearc.odi.koulutus.rest.ProgramResource;
import ch.hearc.odi.koulutus.rest.SessionResource;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Registers all resources with Jersey
 */
public class ResourceLoader extends ResourceConfig {

  public ResourceLoader() {
    //  TODO: register resources
    register(ServiceFeature.class);
    register(CourseResource.class);
    register(ParticipantResource.class);
    register(ProgramResource.class);
    register(SessionResource.class);
    registerInstances(new ServiceBinder());
  }

}
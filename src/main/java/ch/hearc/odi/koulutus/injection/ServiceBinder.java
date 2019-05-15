/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.injection;

import ch.hearc.odi.koulutus.services.PersistenceService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Performs the singleton binding for the RestService mockup persistence object.
 */
public class ServiceBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(new PersistenceService()).to(PersistenceService.class);
  }
}

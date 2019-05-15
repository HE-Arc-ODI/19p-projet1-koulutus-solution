/*
 * Copyright (c) 2019. Cours outils de développement intégré, HEG-Arc
 */

package ch.hearc.odi.koulutus.injection;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * Configures the runtime context to load an instance of the service binder. Required for correct
 * injection of the RestService.
 */
@Provider
public class ServiceFeature implements Feature {

  @Override
  public boolean configure(final FeatureContext context) {
    context.register(new ServiceBinder());
    return true;
  }
}
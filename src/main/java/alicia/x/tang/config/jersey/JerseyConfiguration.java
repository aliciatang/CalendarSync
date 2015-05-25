package alicia.x.tang.config.jersey;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Injector;

public class JerseyConfiguration extends ResourceConfig {
	private final Logger log = getLogger(getClass().getName());

  @Inject
  public JerseyConfiguration(ServiceLocator serviceLocator, ServletContext servletContext) {
    log.info("Creating JerseyConfiguration");
    packages("alicia.x.tang.resources");

    GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
    GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
    guiceBridge.bridgeGuiceInjector((Injector) servletContext.getAttribute(Injector.class.getName()));
  }

}

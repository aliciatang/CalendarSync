package alicia.x.tang.guice;
import alicia.x.tang.services.ServiceModule;
import alicia.x.tang.servlets.ServletModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceServletContextListener extends com.google.inject.servlet.GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(
	    new ServiceModule(),
		new ServletModule());
  }
}

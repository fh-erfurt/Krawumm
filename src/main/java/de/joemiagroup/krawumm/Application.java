package de.joemiagroup.krawumm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import java.util.Arrays;

/**
 * This class is the base of the whole project and starts the application/website
 * <br>
 *
 * @author Jessica Eckhardtsberg
 *
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ServletRegistrationBean<?> jsfServletRegistration(ServletContext servletContext) {
        //spring boot only works if this is set
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

        //registration
        final ServletRegistrationBean<FacesServlet> srb = new ServletRegistrationBean();
        srb.setServlet(new FacesServlet());
        srb.setUrlMappings(Arrays.asList("*.xhtml"));
        srb.setLoadOnStartup(1);
        return srb;
    }
}

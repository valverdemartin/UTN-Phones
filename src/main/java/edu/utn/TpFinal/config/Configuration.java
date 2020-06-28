package edu.utn.TpFinal.config;


import edu.utn.TpFinal.session.BackOfficeSessionFilter;
import edu.utn.TpFinal.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@org.springframework.context.annotation.Configuration
@PropertySource("application.yml")
@EnableScheduling
@EnableCaching
public class  Configuration {

    @Autowired
    SessionFilter sessionFilter;
    @Autowired
    BackOfficeSessionFilter backOfficeSessionFilter;

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean backofficeFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(backOfficeSessionFilter);
        registration.addUrlPatterns("/backoffice/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean userFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/userController/*");
        return registration;
    }

    public static class UriGenerator {
        public static URI getLocation(Integer id){
            return ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(id)
                    .toUri();
        }
    }
}



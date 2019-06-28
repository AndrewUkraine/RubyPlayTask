package com.NightClubsAndTheirVisitors.project.application;

import com.NightClubsAndTheirVisitors.project.RubyPlayTaskApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RubyPlayTaskApplication.class);
    }

}


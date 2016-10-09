package com.wetrack.config;

import com.wetrack.service.AuthenService;
import com.wetrack.service.UserService;
import org.glassfish.jersey.server.ResourceConfig;

public class WeTrackApplication extends ResourceConfig {

    public WeTrackApplication() {
        register(UserService.class);
        register(AuthenService.class);
    }

}
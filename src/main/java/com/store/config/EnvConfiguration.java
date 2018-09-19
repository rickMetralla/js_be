package com.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//this can be replaced by just a static property into a config class
//[static property, with non-static private setter method and public getter]
@Component
@PropertySource("classpath:application-${app.environment}.properties")
@ConfigurationProperties(prefix = "env")
public class EnvConfiguration {

    private boolean dev;

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }
}

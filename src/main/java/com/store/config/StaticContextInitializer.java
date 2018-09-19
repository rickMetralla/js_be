package com.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StaticContextInitializer {

    @Autowired
    private EnvConfiguration myConfig;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        StaticUtils.setMyConfig(myConfig);
    }
}

package com.jedi.isolationlevel;

import org.springframework.test.context.ActiveProfilesResolver;

public class CustomActiveProfilesResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> testClass) {
        String profile = System.getProperty("spring.profiles.active");
        return new String[]{profile};
    }
}

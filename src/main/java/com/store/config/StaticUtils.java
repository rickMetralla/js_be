package com.store.config;

public final class StaticUtils {

    private static EnvConfiguration myConfig;

    public static void setMyConfig(EnvConfiguration myConfig) {
        StaticUtils.myConfig = myConfig;
    }

    public static boolean getProperty(){
        boolean res = myConfig.isDev();
        return res;
    }
}
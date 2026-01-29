package com.store;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerWrapper {
    public static final Logger logger = Logger.getLogger("com.store");

    private static LoggerWrapper instance = null;

    public static LoggerWrapper getInstance() {
        if(instance == null) {
            prepareLogger();
            instance = new LoggerWrapper();
        }
        return instance;
    }

    private static void prepareLogger() {
        FileHandler fileHandler = null;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);

        File logDir = new File("./out/logs");
        if( !(logDir.exists()) )
            logDir.mkdir();

        String fileLogName = String.format("out/logs/fileLog-%s.log", reportDate);

        try{
            fileHandler = new FileHandler(fileLogName, false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        if (fileHandler != null){
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        }
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.FINEST);
    }
}

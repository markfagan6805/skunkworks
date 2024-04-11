package com.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class WhiteListContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(WhiteListContextListener.class.getName());

    private String[] hostWhiteList = {};

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing the whitelist context.");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            logger.info("Updating the whitelist.");
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            hostWhiteList = new String[]{formattedTime + ".1.example.com", formattedTime + ".2.example.com", formattedTime + ".3.example.com"};
            sce.getServletContext().setAttribute("hostWhiteList", hostWhiteList);
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Destroying the whitelist context.");
    }

}

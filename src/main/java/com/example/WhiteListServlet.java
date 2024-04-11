package com.example;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class WhiteListServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(WhiteListServlet.class.getName());

    private String[] hostWhiteList = {};

    private Timer timer;

    @Override
    public void init() throws ServletException {
        super.init();
        timer = new Timer();
        long delay = 0; // Initial delay before the first execution (in milliseconds)
        long period = 30 * 1000; // Repeat every 24 hours (in milliseconds)
        timer.scheduleAtFixedRate(new WhiteListFetcher(), delay, period);
    }

    private class WhiteListFetcher extends TimerTask {
        @Override
        public void run() {
            logger.info("Updating the whitelist.");
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            hostWhiteList = new String[]{formattedTime + ".1.example.com", formattedTime + ".2.example.com", formattedTime + ".3.example.com"};
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("A request was made.");
        request.setAttribute("hosts", hostWhiteList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("whitelist.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        timer.cancel();
    }

}
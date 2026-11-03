package com.lab.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class OnlineUserListener implements HttpSessionListener {

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int count = onlineCount.incrementAndGet();
        se.getSession().getServletContext().setAttribute("onlineCount", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int count = onlineCount.decrementAndGet();
        if (count < 0) count = 0;
        se.getSession().getServletContext().setAttribute("onlineCount", count);
    }
}

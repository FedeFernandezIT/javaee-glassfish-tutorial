/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.mood;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 7 de mayo de 2017 19:51:38 ART
 */
@WebFilter(filterName = "TimeOfDayFilter", urlPatterns = {"/*"},
        initParams = {
            @WebInitParam(name = "mood", value = "awake")})
public class TimeOfDayFilter implements Filter {
    
    private String mood = null;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mood = filterConfig.getInitParameter("mood");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        Calendar cal = GregorianCalendar.getInstance();
        switch (cal.get(GregorianCalendar.HOUR_OF_DAY)) {
            case 23:
            case 24:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                mood = "sleepy";
                break;
            case 7: 
            case 13: 
            case 18:
                mood = "hungry";
                break;
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 16:
            case 17:
                mood = "alert";
                break;
            case 11:
            case 15:
                mood = "in need of cofee";
                break;
            case 19:
            case 20:
            case 21:
                mood = "thoughtful";
                break;
            case 22:
                mood = "lethargic";
                break;
        }
        request.setAttribute("mood", mood);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {        
    }

}

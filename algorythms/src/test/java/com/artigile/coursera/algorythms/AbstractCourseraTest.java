package com.artigile.coursera.algorythms;

/**
 * @author IoaN, 2/17/13 8:53 PM
 */
public abstract class AbstractCourseraTest {

    protected boolean displayLogs=true;

    protected void log(String log){
        if(displayLogs){
            System.out.println(log);
        }
    }
}

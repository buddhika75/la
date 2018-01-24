/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
@Named
@SessionScoped
public class CommonController implements Serializable {

    /**
     * Creates a new instance of LanguageController
     */
    public CommonController() {
    }
    
    public Date firstDayOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.getActualMinimum(Calendar.MONTH));
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        c.set(Calendar.HOUR, c.getActualMinimum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }
    
    public Date lastDayOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.getActualMaximum(Calendar.MONTH));
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        c.set(Calendar.HOUR, c.getActualMaximum(Calendar.HOUR));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
        return c.getTime();
    }
    
}

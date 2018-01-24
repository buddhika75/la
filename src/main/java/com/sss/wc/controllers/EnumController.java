/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.controllers;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import com.sss.wc.enums.Gender;
import com.sss.wc.enums.ItemType;

/**
 *
 * @author User
 */
@Named
@ApplicationScoped
public class EnumController {

    /**
     * Creates a new instance of EnumController
     */
    public EnumController() {
    }

    public Gender[] getGenders() {
        return Gender.values();
    }

    public ItemType[] getItemTypes(){
        return ItemType.values();
    }

}

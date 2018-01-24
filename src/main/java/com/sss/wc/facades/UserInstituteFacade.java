/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.facades;

import com.sss.wc.entity.UserInstitute;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author buddh
 */
@Stateless
public class UserInstituteFacade extends AbstractFacade<UserInstitute> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserInstituteFacade() {
        super(UserInstitute.class);
    }
    
}

package com.sss.wc.controllers;

import com.sss.wc.entity.UserPrivilege;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.WebUser;
import com.sss.wc.enums.Privilege;
import com.sss.wc.facades.UserPrivilegeFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("userPrivilegeController")
@SessionScoped
public class UserPrivilegeController implements Serializable {

    @EJB
    private com.sss.wc.facades.UserPrivilegeFacade ejbFacade;
    private List<UserPrivilege> items = null;
    private UserPrivilege selected;
    Privilege privilege;
    
    WebUser webUser;
    private List<UserPrivilege> WebUserPrivileges = null;

    public UserPrivilegeController() {
    }

    
     public List<UserPrivilege> getWebUserPrivileges() {
        if (webUser == null) {
            return null;
        }
        if (WebUserPrivileges == null) {
            String j = "select up from UserPrivilege up where up.webUser=:u";
            Map m = new HashMap();
            m.put("u", webUser);
            WebUserPrivileges = getFacade().findBySQL(j, m);
        }
        return WebUserPrivileges;
    }

    public void addWebUserPrivilege() {
        if (webUser == null) {
            JsfUtil.addErrorMessage("Select User");
            return;
        }
        if (privilege == null) {
            JsfUtil.addErrorMessage("Select Privilege");
        }
        UserPrivilege up;
        String j = "select up from UserPrivilege up where up.webUser=:u and up.privilege=:p";
        Map m = new HashMap();
        m.put("u", webUser);
        m.put("p", privilege);
        up = getFacade().findFirstBySQL(j, m);
        if (up == null) {
            up = new UserPrivilege();
            up.setWebUser(webUser);
            up.setPrivilege(privilege);
            getFacade().create(up);
            WebUserPrivileges = null;
            getWebUserPrivileges();
            JsfUtil.addSuccessMessage("Privilege added");
        } else {
            JsfUtil.addErrorMessage("Already added");
        }

    }

    public void removeUserPrivilege() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to remove");
            return;
        }
        getFacade().remove(selected);
        WebUserPrivileges = null;
        getWebUserPrivileges();
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    
    
    public WebUser getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
        WebUserPrivileges = null;
    }

    public UserPrivilege getSelected() {
        return selected;
    }

    public void setSelected(UserPrivilege selected) {
        this.selected = selected;
    }
    
    

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserPrivilegeFacade getFacade() {
        return ejbFacade;
    }

    public UserPrivilege prepareCreate() {
        selected = new UserPrivilege();
        initializeEmbeddableKey();
        return selected;
    }

    public void createOrUpdate(UserPrivilege up) {
        try {
            if (up.getId() == null) {
                getFacade().create(up);
                JsfUtil.addSuccessMessage(up + " created.");
            } else {
                getFacade().edit(up);
                JsfUtil.addSuccessMessage(up + " updated.");
            }
            items = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleUser1").getString("UserPrivilegeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleUser1").getString("UserPrivilegeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleUser1").getString("UserPrivilegeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UserPrivilege> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<UserPrivilege> getItems(WebUser wu) {
        String jpql = "select upr "
                + " from UserPrivilege upr "
                + " where upr.webUser=:wu "
                + " ";
        Map m = new HashMap();
        m.put("wu", wu);
        return getFacade().findBySQL(jpql, m);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleUser1").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleUser1").getString("PersistenceErrorOccured"));
            }
        }
    }

    public UserPrivilege getUserPrivilege(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<UserPrivilege> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UserPrivilege> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UserPrivilege.class)
    public static class UserPrivilegeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserPrivilegeController controller = (UserPrivilegeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userPrivilegeController");
            return controller.getUserPrivilege(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserPrivilege) {
                UserPrivilege o = (UserPrivilege) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UserPrivilege.class.getName()});
                return null;
            }
        }

    }

}

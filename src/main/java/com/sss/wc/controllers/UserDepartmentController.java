package com.sss.wc.controllers;

import com.sss.wc.entity.UserDepartment;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.facades.UserDepartmentFacade;

import java.io.Serializable;
import java.util.List;
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

@Named("userDepartmentController")
@SessionScoped
public class UserDepartmentController implements Serializable {

    @EJB
    private com.sss.wc.facades.UserDepartmentFacade ejbFacade;
    private List<UserDepartment> items = null;
    private UserDepartment selected;

    public UserDepartmentController() {
    }

    public UserDepartment getSelected() {
        return selected;
    }

    public void setSelected(UserDepartment selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserDepartmentFacade getFacade() {
        return ejbFacade;
    }

    public UserDepartment prepareCreate() {
        selected = new UserDepartment();
        initializeEmbeddableKey();
        return selected;
    }

     public void createOrUpdate(UserDepartment ud) {
        try {
            if (ud.getId() == null) {
                getFacade().create(ud);
                JsfUtil.addSuccessMessage(ud + " created.");
            } else {
                getFacade().edit(ud);
                JsfUtil.addSuccessMessage(ud + " updated.");
            }
            items = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleUser1").getString("UserDepartmentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleUser1").getString("UserDepartmentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleUser1").getString("UserDepartmentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UserDepartment> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public UserDepartment getUserDepartment(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<UserDepartment> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UserDepartment> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UserDepartment.class)
    public static class UserDepartmentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserDepartmentController controller = (UserDepartmentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userDepartmentController");
            return controller.getUserDepartment(getKey(value));
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
            if (object instanceof UserDepartment) {
                UserDepartment o = (UserDepartment) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UserDepartment.class.getName()});
                return null;
            }
        }

    }

}

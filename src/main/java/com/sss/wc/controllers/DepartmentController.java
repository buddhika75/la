package com.sss.wc.controllers;

import com.sss.wc.entity.Department;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.Institute;
import com.sss.wc.facades.DepartmentFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@SessionScoped
public class DepartmentController implements Serializable {

    @EJB
    private com.sss.wc.facades.DepartmentFacade ejbFacade;
    private List<Department> items = null;
    private Department selected;

    public DepartmentController() {
    }

    public Department getSelected() {
        return selected;
    }

    public void setSelected(Department selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DepartmentFacade getFacade() {
        return ejbFacade;
    }

    public Department prepareCreate() {
        selected = new Department();
        initializeEmbeddableKey();
        return selected;
    }

    public void createOrUpdate(Department dep) {
        try {
            if (dep.getId() == null) {
                getFacade().create(dep);
                JsfUtil.addSuccessMessage(dep + " created.");
            } else {
                getFacade().edit(dep);
                JsfUtil.addSuccessMessage(dep + " updated.");
            }
            items = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DepartmentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DepartmentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DepartmentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Department> getInstituteDepartments(Institute ins) {
        List<Department> deps = new ArrayList<Department>();
        if (ins != null) {
            String j = "Select d from Department d where d.institute= :ins order by d.name";
            Map m = new HashMap();
            m.put("ins", ins);
            deps = getFacade().findBySQL(j, m);
        }
        return deps;
    }

    public List<Department> getItems(String jpql) {
        return getFacade().findBySQL(jpql);
    }

    public List<Department> getItems(String jpql, Map m) {
        return getFacade().findBySQL(jpql, m);
    }

    public List<Department> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Department> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Department> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Department.class)
    public static class DepartmentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartmentController controller = (DepartmentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departmentController");
            return controller.getFacade().find(getKey(value));
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
            if (object instanceof Department) {
                Department o = (Department) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Department.class.getName()});
                return null;
            }
        }

    }

}

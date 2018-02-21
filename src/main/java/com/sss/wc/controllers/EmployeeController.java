package com.sss.wc.controllers;

import com.sss.wc.entity.Employee;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.facades.EmployeeFacade;

import java.io.Serializable;
import java.util.Date;
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
import javax.inject.Inject;

@Named
@SessionScoped
public class EmployeeController implements Serializable {

    @Inject
    WebUserController webUserController;

    @EJB
    private com.sss.wc.facades.EmployeeFacade ejbFacade;
    private List<Employee> items = null;
    private Employee selected;

  
    
    
    
    public EmployeeController() {
    }

    public WebUserController getWebUserController() {
        return webUserController;
    }

    
    public List<Employee> completeEmployee(String qry){
        String j;
        Map m = new HashMap();
        j="select e from Employee e "
                + " where lower(e.nameOfEmployee) like :q "
                + " order by e.nameOfEmployee";
        m.put("q", "%" + qry.toLowerCase() + "%");
        return getFacade().findBySQL(j, m);
    }
    
  
    
    public String toAddNewEmployee() {
        selected = new Employee();
        selected.setDateOfTransferToCurrentStation(new Date());
        System.out.println("webUserController.loggedUser = " + getWebUserController().getLoggedUser());
        if (getWebUserController().getLoggedUser() != null) {
            System.out.println("getWebUserController().getLoggedUser().getInstitute() = " + getWebUserController().getLoggedUser().getInstitute());
            selected.setRegisteredInstitute(getWebUserController().getLoggedUser().getInstitute());
            selected.setRegisteredDepartment(getWebUserController().getLoggedUser().getDepartment());
        }
        System.out.println("selected.getRegisteredInstitute()"
                + "= " + selected.getRegisteredInstitute());
        return "/employee/employee";
    }

    public String saveEmployee() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Nothing to save");
            return "";
        }
        if (selected.getId() == null) {
            getFacade().create(selected);
            JsfUtil.addSuccessMessage("Created");
        } else {
            getFacade().edit(selected);
            JsfUtil.addSuccessMessage("Updated");
        }
        selected = null;
        return "/employee/List";
    }

    public Employee getSelected() {
        return selected;
    }

    public void setSelected(Employee selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeFacade getFacade() {
        return ejbFacade;
    }

    public Employee prepareCreate() {
        selected = new Employee();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundlePt").getString("PatientCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundlePt").getString("PatientUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundlePt").getString("PatientDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Employee> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePt").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundlePt").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Employee getPatient(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Employee> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Employee> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Employee.class)
    public static class PatientControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeController controller = (EmployeeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeController");
            return controller.getPatient(getKey(value));
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
            if (object instanceof Employee) {
                Employee o = (Employee) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Employee.class.getName()});
                return null;
            }
        }

    }

}

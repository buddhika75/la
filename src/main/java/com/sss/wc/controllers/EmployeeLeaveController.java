package com.sss.wc.controllers;

import com.sss.wc.entity.EmployeeLeave;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.controllers.util.JsfUtil.PersistAction;
import com.sss.wc.entity.Employee;
import com.sss.wc.enums.LeaveSummery;
import com.sss.wc.facades.EmployeeLeaveFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named("employeeLeaveController")
@SessionScoped
public class EmployeeLeaveController implements Serializable {

    @EJB
    private com.sss.wc.facades.EmployeeLeaveFacade ejbFacade;
    private List<EmployeeLeave> items = null;
    private List<EmployeeLeave> selectedItems = null;
    private EmployeeLeave selected;
    List<LeaveSummery> leaveSummerries;
    private LeaveSummery leaveSummeryTotal;
    Employee employee;
    private Date fromDate;
    private Date toDate;

    private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private BarChartModel barModel;

    private BarChartModel initBarModel(List<LeaveSummery> lss) {
        BarChartModel model = new BarChartModel();

        ChartSeries annual = new ChartSeries();
        ChartSeries casual = new ChartSeries();
        ChartSeries sick = new ChartSeries();
        ChartSeries maternity = new ChartSeries();
        ChartSeries foreign = new ChartSeries();
        ChartSeries duty = new ChartSeries();
        ChartSeries other = new ChartSeries();

        annual.setLabel("Annual");
        casual.setLabel("Casual");
        sick.setLabel("Sick");
        maternity.setLabel("Maternity");
        foreign.setLabel("Foreign");
        duty.setLabel("Duty");
        other.setLabel("Other");

        for (LeaveSummery s : lss) {
            annual.set(s.getEmployee().getNameOfEmployee(), s.getAnnualDays());
            casual.set(s.getEmployee().getNameOfEmployee(), s.getCasualDays());
            sick.set(s.getEmployee().getNameOfEmployee(), s.getSickDays());
            maternity.set(s.getEmployee().getNameOfEmployee(), s.getMaternityDays());
            duty.set(s.getEmployee().getNameOfEmployee(), s.getDutyDays());
            other.set(s.getEmployee().getNameOfEmployee(), s.getOtherDays());
        }

        model.addSeries(annual);
        model.addSeries(casual);
        model.addSeries(sick);
        model.addSeries(maternity);
        model.addSeries(duty);
        model.addSeries(other);

        return model;
    }


    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);

        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);

        model.addSeries(series1);
        model.addSeries(series2);

        return model;
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);

        model.addSeries(boys);
        model.addSeries(girls);

        return model;
    }

    public void listSelectedEmployeeLeaveData() {
        String j;
        Map m = new HashMap();
        j = "select l "
                + " from EmployeeLeave l "
                + " where l.employee=:emp "
                + " and l.leaveFrom between :fd and :td "
                + " order by l.leaveFrom";
        m.put("emp", employee);
        m.put("fd", fromDate);
        m.put("td", toDate);
        selectedItems = getFacade().findBySQL(j, m);

    }

    public String toSearchEmployeeLeave() {
        selectedItems = new ArrayList<EmployeeLeave>();
        return "/employeeLeave/search_leave";
    }

    public String toViewEmployeeLeave() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Select a leave");
            return "";
        }
        return "/employeeLeave/leave";
    }

    public String deleteEmployeeLeave() {
        if (selected == null) {
            JsfUtil.addErrorMessage("Select a leave");
            return "";
        }
        getFacade().remove(selected);
        JsfUtil.addSuccessMessage("Leave Deleted");
        return toSearchEmployeeLeave();
    }

    public void listSelectedEmployeeLeaveSummery() {
        String j;
        Map m = new HashMap();
        j = "select new com.sss.wc.enums.LeaveSummery(l.employee, sum(l.leaveDays), l.leaveType) "
                + " from EmployeeLeave l "
                + " where l.employee=:emp "
                + " and l.leaveFrom between :fd and :td "
                + " group by l.leaveType, l.employee";
        m.put("emp", employee);
        m.put("fd", fromDate);
        m.put("td", toDate);
        List<Object> temLst;
        temLst = getFacade().findGroupingBySql(j, m);
        leaveSummerries = new ArrayList<LeaveSummery>();
        leaveSummeryTotal = new LeaveSummery();
        leaveSummeryTotal.setLeaveDays(0.0);
        for (Object o : temLst) {
            if (o instanceof LeaveSummery) {
                LeaveSummery ls = (LeaveSummery) o;
                leaveSummerries.add(ls);
                leaveSummeryTotal.setLeaveDays(leaveSummeryTotal.getLeaveDays() + ls.getLeaveDays());
            }
        }

    }

    public void listAllEmployeeLeaveColumnChart() {
        String j;
        Map m = new HashMap();
        j = "select new com.sss.wc.enums.LeaveSummery(l.employee, sum(l.leaveDays), l.leaveType) "
                + " from EmployeeLeave l "
                + " where l.leaveFrom between :fd and :td "
                + " group by l.leaveType, l.employee";
        List<Object> temLst;
        m.put("fd", fromDate);
        m.put("td", toDate);

        temLst = getFacade().findGroupingBySql(j, m);
        List<LeaveSummery> temLeaveSummerries = new ArrayList<LeaveSummery>();
        leaveSummerries = new ArrayList<LeaveSummery>();

        for (Object o : temLst) {
            if (o instanceof LeaveSummery) {
                LeaveSummery ls = (LeaveSummery) o;
                temLeaveSummerries.add(ls);
            }
        }
        for (LeaveSummery tls : temLeaveSummerries) {
            boolean summeryForEmployeeExists = false;
            for (LeaveSummery ls : leaveSummerries) {
                if (ls.getEmployee().equals(tls.getEmployee())) {
                    summeryForEmployeeExists = true;
                }
            }
            if (!summeryForEmployeeExists) {
                LeaveSummery nls = new LeaveSummery();
                nls.setEmployee(tls.getEmployee());
                nls.setAnnualDays(0.0);
                nls.setCasualDays(0.0);
                nls.setDutyDays(0.0);
                nls.setForeignDays(0.0);
                nls.setMaternityDays(0.0);
                nls.setOtherDays(0.0);
                nls.setSickDays(0.0);
                leaveSummerries.add(nls);
            }
        }
        for (LeaveSummery tls : temLeaveSummerries) {
            for (LeaveSummery ls : leaveSummerries) {
                if (ls.getEmployee().equals(tls.getEmployee())) {
                    switch (tls.getLeaveType()) {
                        case Annual_Leave:
                            ls.setAnnualDays(ls.getAnnualDays() + tls.getLeaveDays());
                            break;
                        case Casual_Leave:
                            ls.setCasualDays(ls.getCasualDays() + tls.getLeaveDays());
                            break;
                        case Duty_Leave:
                            ls.setDutyDays(ls.getDutyDays() + tls.getLeaveDays());
                            break;
                        case Foreign_Leave:
                            ls.setForeignDays(ls.getForeignDays() + tls.getLeaveDays());
                            break;
                        case Maternity_Leave:
                            ls.setMaternityDays(ls.getMaternityDays() + tls.getLeaveDays());
                            break;
                        case Sick_Leave:
                            ls.setSickDays(ls.getSickDays() + tls.getLeaveDays());
                            break;
                        case Other_Leave:
                            ls.setOtherDays(ls.getOtherDays() + tls.getLeaveDays());
                            break;
                    }
                }
            }
        }
        
        
        barModel = initBarModel(temLeaveSummerries);

        barModel.setTitle("Leave Summery Chart");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Leave Type");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Employee");
        
        yAxis.setMin(0);
        yAxis.setMax(50);
        

    }

    public void listAllEmployeeLeaveSummery() {
        String j;
        Map m = new HashMap();
        j = "select new com.sss.wc.enums.LeaveSummery(l.employee, sum(l.leaveDays), l.leaveType) "
                + " from EmployeeLeave l "
                + " where l.leaveFrom between :fd and :td "
                + " group by l.leaveType, l.employee";
        List<Object> temLst;
        m.put("fd", fromDate);
        m.put("td", toDate);

        temLst = getFacade().findGroupingBySql(j, m);
        List<LeaveSummery> temLeaveSummerries = new ArrayList<LeaveSummery>();
        leaveSummerries = new ArrayList<LeaveSummery>();

        for (Object o : temLst) {
            if (o instanceof LeaveSummery) {
                LeaveSummery ls = (LeaveSummery) o;
                temLeaveSummerries.add(ls);
            }
        }
        for (LeaveSummery tls : temLeaveSummerries) {
            boolean summeryForEmployeeExists = false;
            for (LeaveSummery ls : leaveSummerries) {
                if (ls.getEmployee().equals(tls.getEmployee())) {
                    summeryForEmployeeExists = true;
                }
            }
            if (!summeryForEmployeeExists) {
                LeaveSummery nls = new LeaveSummery();
                nls.setEmployee(tls.getEmployee());
                nls.setAnnualDays(0.0);
                nls.setCasualDays(0.0);
                nls.setDutyDays(0.0);
                nls.setForeignDays(0.0);
                nls.setMaternityDays(0.0);
                nls.setOtherDays(0.0);
                nls.setSickDays(0.0);
                leaveSummerries.add(nls);
            }
        }
        for (LeaveSummery tls : temLeaveSummerries) {
            for (LeaveSummery ls : leaveSummerries) {
                if (ls.getEmployee().equals(tls.getEmployee())) {
                    switch (tls.getLeaveType()) {
                        case Annual_Leave:
                            ls.setAnnualDays(ls.getAnnualDays() + tls.getLeaveDays());
                            break;
                        case Casual_Leave:
                            ls.setCasualDays(ls.getCasualDays() + tls.getLeaveDays());
                            break;
                        case Duty_Leave:
                            ls.setDutyDays(ls.getDutyDays() + tls.getLeaveDays());
                            break;
                        case Foreign_Leave:
                            ls.setForeignDays(ls.getForeignDays() + tls.getLeaveDays());
                            break;
                        case Maternity_Leave:
                            ls.setMaternityDays(ls.getMaternityDays() + tls.getLeaveDays());
                            break;
                        case Sick_Leave:
                            ls.setSickDays(ls.getSickDays() + tls.getLeaveDays());
                            break;
                        case Other_Leave:
                            ls.setOtherDays(ls.getOtherDays() + tls.getLeaveDays());
                            break;
                    }
                }
            }
        }

    }

    public String toAddNewEmployeeLeave() {
        selected = new EmployeeLeave();
        return "/employeeLeave/leave";
    }

    public void leaveDatesChanged() {
        if (selected.getLeaveFrom() == null) {
            return;
        }
        if (selected.getLeaveTo() == null) {
            return;
        }
        Long days;
        days = (selected.getLeaveTo().getTime() - selected.getLeaveFrom().getTime()) / (1000 * 60 * 60 * 24);
        days += 1;
        selected.setLeaveDays(days.doubleValue());
    }

    public String saveLeave() {
        if (selected.getEmployee() == null) {
            JsfUtil.addErrorMessage("Select an Employee");
            return "";
        }
        if (selected.getLeaveFrom() == null) {
            JsfUtil.addErrorMessage("Select date");
            return "";
        }
        if (selected.getLeaveTo() == null) {
            selected.setLeaveTo(selected.getLeaveFrom());
        }
        if (selected.getLeaveType() == null) {
            JsfUtil.addErrorMessage("Select Leave Type");
            return "";
        }
        if (selected.getId() == null) {
            getFacade().create(selected);
            JsfUtil.addSuccessMessage("Leave Added");
        } else {
            getFacade().edit(selected);
            JsfUtil.addSuccessMessage("Leave Updated");
        }

        return toAddNewEmployeeLeave();
    }

    public EmployeeLeaveController() {
    }

    public EmployeeLeave getSelected() {
        return selected;
    }

    public void setSelected(EmployeeLeave selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeLeaveFacade getFacade() {
        return ejbFacade;
    }

    public EmployeeLeave prepareCreate() {
        selected = new EmployeeLeave();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleLeave").getString("EmployeeLeaveCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleLeave").getString("EmployeeLeaveUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleLeave").getString("EmployeeLeaveDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EmployeeLeave> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleLeave").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleLeave").getString("PersistenceErrorOccured"));
            }
        }
    }

    public EmployeeLeave getEmployeeLeave(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<EmployeeLeave> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EmployeeLeave> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<LeaveSummery> getLeaveSummerries() {
        return leaveSummerries;
    }

    public void setLeaveSummerries(List<LeaveSummery> leaveSummerries) {
        this.leaveSummerries = leaveSummerries;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveSummery getLeaveSummeryTotal() {
        return leaveSummeryTotal;
    }

    public void setLeaveSummeryTotal(LeaveSummery leaveSummeryTotal) {
        this.leaveSummeryTotal = leaveSummeryTotal;
    }

    public Date getFromDate() {
        if (fromDate == null) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MILLISECOND, 1);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.DAY_OF_YEAR, 1);
            fromDate = c.getTime();
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MILLISECOND, 1);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.DAY_OF_YEAR, 1);
            c.add(Calendar.YEAR, 1);
            c.add(Calendar.MILLISECOND, -2);
            toDate = c.getTime();
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<EmployeeLeave> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<EmployeeLeave> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    public void setLineModel2(LineChartModel lineModel2) {
        this.lineModel2 = lineModel2;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    @FacesConverter(forClass = EmployeeLeave.class)
    public static class EmployeeLeaveControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmployeeLeaveController controller = (EmployeeLeaveController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "employeeLeaveController");
            return controller.getEmployeeLeave(getKey(value));
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
            if (object instanceof EmployeeLeave) {
                EmployeeLeave o = (EmployeeLeave) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EmployeeLeave.class.getName()});
                return null;
            }
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.enums;

import com.sss.wc.entity.Employee;

/**
 *
 * @author Buddhika
 */
public class LeaveSummery {

    Employee employee;
    Double leaveDays;
    LeaveType leaveType;
    private Double casualDays;
    private Double dutyDays;
    private Double sickDays;
    private Double foreignDays;
    private Double maternityDays;
    private Double annualDays;
    private Double otherDays;
    
    

    public LeaveSummery() {
    }

    public LeaveSummery(Employee employee, Double leaveDays, LeaveType leaveType) {
        this.employee = employee;
        this.leaveDays = leaveDays;
        this.leaveType = leaveType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Double getCasualDays() {
        return casualDays;
    }

    public void setCasualDays(Double casualDays) {
        this.casualDays = casualDays;
    }

    public Double getDutyDays() {
        return dutyDays;
    }

    public void setDutyDays(Double dutyDays) {
        this.dutyDays = dutyDays;
    }

    public Double getSickDays() {
        return sickDays;
    }

    public void setSickDays(Double sickDays) {
        this.sickDays = sickDays;
    }

    public Double getForeignDays() {
        return foreignDays;
    }

    public void setForeignDays(Double foreignDays) {
        this.foreignDays = foreignDays;
    }

    public Double getMaternityDays() {
        return maternityDays;
    }

    public void setMaternityDays(Double maternityDays) {
        this.maternityDays = maternityDays;
    }

    public Double getAnnualDays() {
        return annualDays;
    }

    public void setAnnualDays(Double annualDays) {
        this.annualDays = annualDays;
    }

    public Double getOtherDays() {
        return otherDays;
    }

    public void setOtherDays(Double otherDays) {
        this.otherDays = otherDays;
    }

}

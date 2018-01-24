/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.Gender;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author User
 */
@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfTransferToCurrentStation;
    String referenceNumber;
    String nameOfEmployee;
    
    @ManyToOne
    Institute registeredInstitute;
    @ManyToOne
    Department registeredDepartment;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfBirth;
    Gender gender;
    String Age;
    @Lob
    String address;
    String contactNumber;  
    private String nic;
    private Long salaryCode;
    
    private Boolean active;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date activeFrom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date activeTo;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    

    public Date getDateOfTransferToCurrentStation() {
        return dateOfTransferToCurrentStation;
    }

    public void setDateOfTransferToCurrentStation(Date dateOfTransferToCurrentStation) {
        this.dateOfTransferToCurrentStation = dateOfTransferToCurrentStation;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getNameOfEmployee() {
        return nameOfEmployee;
    }

    public void setNameOfEmployee(String nameOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
    }

    public Institute getRegisteredInstitute() {
        return registeredInstitute;
    }

    public void setRegisteredInstitute(Institute registeredInstitute) {
        this.registeredInstitute = registeredInstitute;
    }

    public Department getRegisteredDepartment() {
        return registeredDepartment;
    }

    public void setRegisteredDepartment(Department registeredDepartment) {
        this.registeredDepartment = registeredDepartment;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sss.wc.entity.Patient[ id=" + id + " ]";
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Long getSalaryCode() {
        return salaryCode;
    }

    public void setSalaryCode(Long salaryCode) {
        this.salaryCode = salaryCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Date getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(Date activeTo) {
        this.activeTo = activeTo;
    }

}

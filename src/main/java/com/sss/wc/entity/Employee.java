/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.entity;

import com.sss.wc.enums.CivilStatus;
import com.sss.wc.enums.EmployeeType;
import com.sss.wc.enums.Gender;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Lob
    String fullName;

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
    Long empNumber;
    @ManyToOne
    Item designation;

    private Boolean active;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date activeFrom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date activeTo;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfFirstAppointment;
    private String letterOfFirstAppointment;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date letterDateOfFirstAppointment;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfPermanentLetter;
    String numberOfPermanentLetter;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfCurrentDesignation;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateForGradeTwo;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date deteOfGradeOne;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfSpecialGrade;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfSuperGrade;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfCurrentStation;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateOfTransferFromCurrentStation;
    @Enumerated(EnumType.STRING)
    CivilStatus civilstatus;
    Integer noChi;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild1;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild2;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild3;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild4;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild5;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild6;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild7;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild8;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild9;
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dobOfChild10;

    @Lob
    String other;
    @Enumerated(EnumType.STRING)
    EmployeeType employeeType;

    @ManyToOne
    private Department department;
    @ManyToOne
    private Institute institute;

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

    public Item getDesignation() {
        return designation;
    }

    public void setDesignation(Item designation) {
        this.designation = designation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfFirstAppointment() {
        return dateOfFirstAppointment;
    }

    public void setDateOfFirstAppointment(Date dateOfFirstAppointment) {
        this.dateOfFirstAppointment = dateOfFirstAppointment;
    }

    public Date getDateOfPermanentLetter() {
        return dateOfPermanentLetter;
    }

    public void setDateOfPermanentLetter(Date dateOfPermanentLetter) {
        this.dateOfPermanentLetter = dateOfPermanentLetter;
    }

    public String getNumberOfPermanentLetter() {
        return numberOfPermanentLetter;
    }

    public void setNumberOfPermanentLetter(String numberOfPermanentLetter) {
        this.numberOfPermanentLetter = numberOfPermanentLetter;
    }

    public Date getDateOfCurrentDesignation() {
        return dateOfCurrentDesignation;
    }

    public void setDateOfCurrentDesignation(Date dateOfCurrentDesignation) {
        this.dateOfCurrentDesignation = dateOfCurrentDesignation;
    }

    public Date getDateForGradeTwo() {
        return dateForGradeTwo;
    }

    public void setDateForGradeTwo(Date dateForGradeTwo) {
        this.dateForGradeTwo = dateForGradeTwo;
    }

    public Date getDeteOfGradeOne() {
        return deteOfGradeOne;
    }

    public void setDeteOfGradeOne(Date deteOfGradeOne) {
        this.deteOfGradeOne = deteOfGradeOne;
    }

    public Date getDateOfSpecialGrade() {
        return dateOfSpecialGrade;
    }

    public void setDateOfSpecialGrade(Date dateOfSpecialGrade) {
        this.dateOfSpecialGrade = dateOfSpecialGrade;
    }

    public Date getDateOfSuperGrade() {
        return dateOfSuperGrade;
    }

    public void setDateOfSuperGrade(Date dateOfSuperGrade) {
        this.dateOfSuperGrade = dateOfSuperGrade;
    }

    public Date getDateOfCurrentStation() {
        return dateOfCurrentStation;
    }

    public void setDateOfCurrentStation(Date dateOfCurrentStation) {
        this.dateOfCurrentStation = dateOfCurrentStation;
    }

    public Date getDateOfTransferFromCurrentStation() {
        return dateOfTransferFromCurrentStation;
    }

    public void setDateOfTransferFromCurrentStation(Date dateOfTransferFromCurrentStation) {
        this.dateOfTransferFromCurrentStation = dateOfTransferFromCurrentStation;
    }

    public CivilStatus getCivilstatus() {
        return civilstatus;
    }

    public void setCivilstatus(CivilStatus civilstatus) {
        this.civilstatus = civilstatus;
    }

    public Integer getNoChi() {
        return noChi;
    }

    public void setNoChi(Integer noChi) {
        this.noChi = noChi;
    }

    public Date getDobOfChild1() {
        return dobOfChild1;
    }

    public void setDobOfChild1(Date dobOfChild1) {
        this.dobOfChild1 = dobOfChild1;
    }

    public Date getDobOfChild2() {
        return dobOfChild2;
    }

    public void setDobOfChild2(Date dobOfChild2) {
        this.dobOfChild2 = dobOfChild2;
    }

    public Date getDobOfChild3() {
        return dobOfChild3;
    }

    public void setDobOfChild3(Date dobOfChild3) {
        this.dobOfChild3 = dobOfChild3;
    }

    public Date getDobOfChild4() {
        return dobOfChild4;
    }

    public void setDobOfChild4(Date dobOfChild4) {
        this.dobOfChild4 = dobOfChild4;
    }

    public Date getDobOfChild5() {
        return dobOfChild5;
    }

    public void setDobOfChild5(Date dobOfChild5) {
        this.dobOfChild5 = dobOfChild5;
    }

    public Date getDobOfChild6() {
        return dobOfChild6;
    }

    public void setDobOfChild6(Date dobOfChild6) {
        this.dobOfChild6 = dobOfChild6;
    }

    public Date getDobOfChild7() {
        return dobOfChild7;
    }

    public void setDobOfChild7(Date dobOfChild7) {
        this.dobOfChild7 = dobOfChild7;
    }

    public Date getDobOfChild8() {
        return dobOfChild8;
    }

    public void setDobOfChild8(Date dobOfChild8) {
        this.dobOfChild8 = dobOfChild8;
    }

    public Date getDobOfChild9() {
        return dobOfChild9;
    }

    public void setDobOfChild9(Date dobOfChild9) {
        this.dobOfChild9 = dobOfChild9;
    }

    public Date getDobOfChild10() {
        return dobOfChild10;
    }

    public void setDobOfChild10(Date dobOfChild10) {
        this.dobOfChild10 = dobOfChild10;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
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
        String name = "";
        String sc = "";
        if (this.nameOfEmployee != null) {
            name = this.nameOfEmployee;
        }
        if (this.salaryCode != null) {
            sc = this.salaryCode.toString();
        }
        if ((name + sc).equals("")) {
            return "com.sss.wc.entity.Patient[ id=" + id + " ]";
        } else {
            return name + " " + salaryCode;
        }
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

    public Long getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(Long empNumber) {
        this.empNumber = empNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public String getLetterOfFirstAppointment() {
        return letterOfFirstAppointment;
    }

    public void setLetterOfFirstAppointment(String letterOfFirstAppointment) {
        this.letterOfFirstAppointment = letterOfFirstAppointment;
    }

    public Date getLetterDateOfFirstAppointment() {
        return letterDateOfFirstAppointment;
    }

    public void setLetterDateOfFirstAppointment(Date letterDateOfFirstAppointment) {
        this.letterDateOfFirstAppointment = letterDateOfFirstAppointment;
    }

}

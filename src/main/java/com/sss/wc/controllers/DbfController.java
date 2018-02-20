/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sss.wc.controllers;

import com.linuxense.javadbf.DBFException;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FileUploadEvent;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.sss.wc.controllers.util.JsfUtil;
import com.sss.wc.entity.Employee;
import com.sss.wc.enums.ItemType;
import com.sss.wc.facades.EmployeeFacade;
import com.sss.wc.facades.ItemFacade;

import org.primefaces.model.UploadedFile;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;

import javax.faces.context.FacesContext;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author buddhika
 */
@Named
@SessionScoped
public class DbfController implements Serializable {

    private UploadedFile file;

    @EJB
    EmployeeFacade employeeFacade;

    @EJB
    ItemFacade desFacade;

    @Inject
    InstituteController institutionController;
    @Inject
    ItemController itemController;

    public InstituteController getInstitutionController() {
        return institutionController;
    }

    public void setInstitutionController(InstituteController institutionController) {
        this.institutionController = institutionController;
    }

    public static int intValue(long value) {
        int valueInt = (int) value;
        if (valueInt != value) {
            throw new IllegalArgumentException(
                    "The long value " + value + " is not within range of the int type");
        }
        return valueInt;
    }

    /**
     * Creates a new instance of ImageController
     */
    public DbfController() {
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    private Boolean isCorrectDbfFile(DBFReader reader) {
        System.out.println("isCorrectDbfFile");
        Boolean correct = true;
        try {
            //System.out.println("field count is " + reader.getFieldCount());
            for (int i = 0; i < reader.getFieldCount(); i++) {
                //System.out.println("field " + i + " is " + reader.getField(i).getName());

            }
        } catch (DBFException ex) {
            System.out.println("ex at 97 = " + ex);
        }

        try {
            if (!reader.getField(0).getName().equalsIgnoreCase("F1_EMPNO")) {
                correct = false;
            }
            if (!reader.getField(2).getName().equalsIgnoreCase("F1_SURNAME")) {
                correct = false;
            }
            if (!reader.getField(7).getName().equalsIgnoreCase("F1_DOB")) {
                correct = false;
            }
            if (!reader.getField(48).getName().equalsIgnoreCase("F1_NICNO")) {
                correct = false;
            }
        } catch (DBFException e) {
            System.out.println("e at 114= " + e);
        }
        System.out.println("correct = " + correct);
        return correct;
    }

    public String extractData() {
        System.out.println("extractData");
        InputStream in;
        if (file == null) {
            JsfUtil.addErrorMessage("Please select the dbf file to upload");
            return "";
        }
        System.out.println("file = " + file);
        try {
            in = file.getInputstream();
            System.out.println("in = " + in);
            DBFReader reader = new DBFReader(in);
            System.out.println("reader = " + reader);
            if (!isCorrectDbfFile(reader)) {
                JsfUtil.addErrorMessage("But the file you selected is not the correct file. Please make sure you selected the correct file named PYREMPMA.DBF. If you are sure that you selected the correct file, you may be using an old version.");
                return "";
            }

            Object[] rowObjects;
            System.out.println("0");
            while ((rowObjects = reader.nextRecord()) != null) {

                
                String empNo;
                System.out.println("1");
                try {
                    empNo = rowObjects[0].toString();
                } catch (Exception e) {
                    empNo = "999999.0";
                    System.out.println("e at 149 " + e);
                }
                System.out.println("2");
                if (!"999999.0".equals(empNo)) {

                    Employee p = new Employee();
                    Double sc;
                    System.out.println("4");
                    try {
                        sc = Double.parseDouble(empNo);
                    } catch (NumberFormatException e) {

                        System.out.println("e = " + e);
                        sc = 0.0;
                    }
                    System.out.println("5");

                    p.setSalaryCode(sc.longValue());

                    String title = (rowObjects[1].toString());
                    String initials = (rowObjects[3].toString());
                    String surname = (rowObjects[2].toString());

                    p.setNameOfEmployee(title + " " + initials + " " + surname);

//                    System.out.println("6");
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//                    System.out.println("7");
//                    System.out.println("rowObjects[7].toString() = " + rowObjects[7].toString() );
//                    System.out.println("8");
//                    String tdate = rowObjects[7].toString();
//                    System.out.println("9");
//                    try {
//                        if (tdate != null && !"".equals(tdate.trim())) {
//                            p.setDateOfBirth(dateFormat.parse(tdate));
//                        }
//                    } catch (Exception e) {
//                        System.out.println("e at 177= " + e);
//                    }

                    System.out.println("6");
                    try {
                        p.setNic(rowObjects[48].toString());
                    } catch (Exception e) {
                        System.out.println("e at 184= " + e);
                    }

                    String address1 = (rowObjects[18].toString());
                    String address2 = (rowObjects[19].toString());
                    String address3 = (rowObjects[20].toString());
                    String offAddress1 = (rowObjects[21].toString());
                    String offAddress2 = (rowObjects[22].toString());
                    String offAddress3 = (rowObjects[23].toString());

                    p.setAddress(address1 + " "
                            + address2 + " "
                            + address3 + "\n"
                            + offAddress1 + " "
                            + offAddress2 + " "
                            + offAddress3);

                    System.out.println("7");
                    p.setDesignation(itemController.findItem(rowObjects[8].toString(), ItemType.Designation));
                    System.out.println("8 = " + 8);
                    try {
                        p.setActive((Boolean) rowObjects[40]);
                    } catch (Exception e) {
                        p.setActive(true);
                        System.out.println("e at 207 " + e);
                    }

                    employeeFacade.create(p);
                }

            }

            JsfUtil.addSuccessMessage("Data Captured. But NOT Recorded to the database. Please click Save to confirm.");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return "/employee/List";
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        InputStream in;
        String temNic;
        Boolean newEntries = false;

        if (event.getFile() == null) {
            JsfUtil.addErrorMessage("Please select the dbf file to upload");
            return;
        }

        try {
            in = event.getFile().getInputstream();
            DBFReader reader = new DBFReader(in);
            if (!isCorrectDbfFile(reader)) {
                JsfUtil.addErrorMessage("But the file you selected is not the correct file. Please make sure you selected the correct file named PYREMPMA.DBF. If you are sure that you selected the correct file, you may be using an old version.");
                return;
            }

            int numberOfFields = reader.getFieldCount();

            //System.out.println("Number of fields is " + numberOfFields);
            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                //System.out.println("Data Field " + i + " is " + field.getName());
            }

            Object[] rowObjects;

            while ((rowObjects = reader.nextRecord()) != null) {

                System.out.println("rowObjects[0].toString() = " + rowObjects[0].toString());

                String empNo = "";

                try {

                    empNo = rowObjects[0].toString();
                    System.out.println("empNo = " + empNo);
                } catch (Exception e) {
                    empNo = "999999.0";
                }

                if (!"999999.0".equals(empNo)) {

                    Employee p = new Employee();
                    Double sc;

                    try {
                        sc = Double.parseDouble(empNo);
                        System.out.println("sc = Double.parseDouble(empNo); = " + Double.parseDouble(empNo));
                        p.setSalaryCode(sc.longValue());
                    } catch (NumberFormatException e) {
                        System.out.println("e = " + e);
                    }

                    String title = (rowObjects[1].toString());
                    String initials = (rowObjects[3].toString());
                    String surname = (rowObjects[2].toString());

                    p.setNameOfEmployee(title + " " + initials + " " + surname);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    try {
                        p.setDateOfBirth(dateFormat.parse(rowObjects[7].toString()));
                    } catch (ParseException e) {
                        System.out.println("e = " + e);
                    }

                    try {
                        p.setNic(rowObjects[48].toString());
                    } catch (Exception e) {
                        System.out.println("e = " + e);
                    }

                    String address1 = (rowObjects[18].toString());
                    String address2 = (rowObjects[19].toString());
                    String address3 = (rowObjects[20].toString());
                    String offAddress1 = (rowObjects[21].toString());
                    String offAddress2 = (rowObjects[22].toString());
                    String offAddress3 = (rowObjects[23].toString());

                    p.setAddress(address1 + " "
                            + address2 + " "
                            + address3 + "\n"
                            + offAddress1 + " "
                            + offAddress2 + " "
                            + offAddress3);

                    p.setDesignation(itemController.findItem(rowObjects[8].toString(), ItemType.Designation));

                    try {
                        p.setActive((Boolean) rowObjects[40]);
                    } catch (Exception e) {
                        p.setActive(true);
                    }

                    employeeFacade.create(p);
                }
            }

        } catch (IOException e) {
            //System.out.println("Error " + e.getMessage());
        }
    }

}

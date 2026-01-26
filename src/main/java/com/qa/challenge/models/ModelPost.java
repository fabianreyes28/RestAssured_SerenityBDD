package com.qa.challenge.models;

import com.qa.challenge.exceptions.PropertiesDoesNotLoadException;
import com.qa.challenge.readexcel.LectorExcel;
import io.cucumber.datatable.DataTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

import static com.qa.challenge.utils.Constans.PATH_DATA_POST_EXCEL;
import static com.qa.challenge.utils.Constans.SHEET_NAME_EXCEL_POST;

@Generated("jsonschema2pojo")
public class ModelPost {

    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private LocalDate bookingdates;
    private String additionalneeds;

    static List<Map<String, String>> excelData = new LectorExcel().getData(PATH_DATA_POST_EXCEL,SHEET_NAME_EXCEL_POST);


    public ModelPost(DataTable dataTable) {
        this.excelData = dataTable.asMaps(String.class, String.class);
    }

    public String getFirstname() {

        return excelData.get(0).get("firstname");
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }


    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

 /*   public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
*/
}
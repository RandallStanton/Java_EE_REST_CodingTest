package com.codetest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 100)
@NamedQueries({ @NamedQuery(name = "Employee.countId", query = "SELECT COUNT(e) FROM Employee e WHERE e.id = :id"),
                @NamedQuery(name = "Employee.list", query = "Select e FROM Employee e")
    })
public class Employee {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Integer id = null;

    private String name;
    private String email;
    private String telephone;
    private String street;
    private String city;
    private String state;
    private String zip;

    public Employee() {
	super();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getTelephone() {
	return telephone;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public Integer getId() {
	return id;
    }

    @SuppressWarnings("unused")
    private void setId(int id) {
	this.id = id;
    }


}

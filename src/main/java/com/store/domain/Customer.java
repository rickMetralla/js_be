package com.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {

    @Column(name = "name")
    @NotNull
    @Size(min=3, max = 255, message="Name should have at least 3 characters and 255 at most.")
    private String name;

    @Id
    @Column(name = "dni")
    @NotNull
    @Digits(integer=8, fraction=0, message = "Dni should have 8 digits at most.")
    private int dni;

    @Column(name = "address")
    @NotNull
    @Size(min=3, max = 255, message="Name should have at least 3 characters and 255 at most.")
    private String address;

    @Column(name = "phone")
    @NotNull
    @Digits(integer=9, fraction=0, message = "Phone should have 9 digits at most.")
    private int phone;

    @Column(name = "email")
    @NotNull
    @Pattern(message = "Invalid email field.", regexp = "[A-Za-z0-9._+-]+@[a-z0-9.-]+.[a-z]{2,4}")
    private String email;

    public Customer(){
    }

    public Customer(String name, int dni, String address, int phone, String email){
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "name: " + name + ", dni: " + dni;
    }

    public boolean validate() {
        if(this.name == "" || !this.name.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;

    }
}

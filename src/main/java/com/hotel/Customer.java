package com.hotel;

import java.util.UUID;


public class Customer {
    public final String customerId;
    public final String name;
    public final String email;
    public final String phone;

    public Customer(String customerId, String name, String email, String phone){
        this.customerId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }

    public  String getCustomerId(){
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", name='" + name + '\'' +
                ", email=" + email +
                ", phone=" + phone +
                '}';
    }

//    @Override
//    public String toString() {
//        return super.toString();
//    }
}

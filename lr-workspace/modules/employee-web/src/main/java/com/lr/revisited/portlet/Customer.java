package com.lr.revisited.portlet;

public class Customer {
    private String name;
    private int age;
    private String phoneNo;
    private String address;

    // Constructor sin argumentos necesario para instanciar la clase
    public Customer() {
    }

    // Constructor con argumentos para inicializar la clase
    public Customer(String name, int age, String phoneNo, String address) {
        this.name = name;
        this.age = age;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    // Métodos getter y setter para acceder a los atributos
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

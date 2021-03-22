package com.alevel.homework24.model;

import java.time.LocalDate;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate birthday;
    private String email;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User:" +
                " {id=" + id +
                ", firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                ", address='" + address + "'" +
                ", birthday='" + birthday + "'" +
                ", email='" + email + "'}";
    }
}

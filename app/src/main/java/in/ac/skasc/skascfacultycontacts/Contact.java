package in.ac.skasc.skascfacultycontacts;


import android.support.annotation.NonNull;

import java.io.Serializable;

class Contact implements Comparable<Contact>, Serializable {

    private String id, CUGNumber, firstName, lastName, mobileNumber_1, mobileNumber_2, role, title, department;

    Contact() {
    }

    String getId() {
        return id != null ? id : "";
    }

    void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    String getCUGNumber() {
        return CUGNumber != null ? CUGNumber : "";
    }

    void setCUGNumber(String CUGNumber) {
        this.CUGNumber = CUGNumber;
    }

    String getFirstName() {
        return firstName != null ? firstName : "";
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName != null ? lastName : "";
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getMobileNumber_1() {
        return mobileNumber_1 != null ? mobileNumber_1 : "";
    }

    void setMobileNumber_1(String mobileNumber_1) {
        this.mobileNumber_1 = mobileNumber_1;
    }

    String getMobileNumber_2() {
        return mobileNumber_2 != null ? mobileNumber_2 : "";
    }

    void setMobileNumber_2(String mobileNumber_2) {
        this.mobileNumber_2 = mobileNumber_2;
    }

    String getRole() {
        return role != null ? role : "";
    }

    void setRole(String role) {
        this.role = role;
    }

    String getTitle() {
        return title != null ? title : "";
    }

    void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(@NonNull Contact o) {
        return this.getFirstName().compareTo(o.getFirstName());
    }

}

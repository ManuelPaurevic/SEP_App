package com.example.sep_socialful;

public class UserAccount {

    String nickName, gender, phoneNumber, email;
    int age;

    public UserAccount() {

    }

    public UserAccount(String nickName, String email, String gender, String phoneNumber, int age) {
        this.nickName = nickName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

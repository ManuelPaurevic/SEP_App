package com.example.sep_socialful;

public class UserAccount {

    String nickName, gender;
    int phoneNumber, age;

    public UserAccount() {

    }

    public UserAccount(String nickName, String gender, int phoneNumber, int age) {
        this.nickName = nickName;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

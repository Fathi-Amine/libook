package com.libook.models;

import java.util.List;

public class Member {
    private String name;
    private int serialNumber;

    public Member(String name, int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void createMember() {

    }

    public static Member getMemberBySerialNumber(int serialNumber) {

    }

    public void updateMember() {

    }

    public void deleteMember() {

    }

    public static List<Member> getAllMembers() {}
}


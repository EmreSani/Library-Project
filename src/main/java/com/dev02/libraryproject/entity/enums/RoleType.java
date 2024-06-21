package com.dev02.libraryproject.entity.enums;

public enum RoleType {

    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    MEMBER("Member");

    public final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

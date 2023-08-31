package me.zenv.test;

import java.util.UUID;

public class SubClass {
    public SubClass() {
        System.out.println("SubClass 实例化");
    }

    public String selectUserName(long id) {
        return "user id : " + id + ", name : " + UUID.randomUUID().toString();
    }

    public int selectAge() {
        return 29;
    }

    public static void staticMethod() {
        System.out.println("static method");
    }
}

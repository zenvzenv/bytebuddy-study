package me.zenv.test;

import java.util.UUID;

public class SubClass {
    public String selectUserName(long id) {
        return "user id : " + id + ", name : " + UUID.randomUUID().toString();
    }

    public int selectAge() {
        return 29;
    }
}

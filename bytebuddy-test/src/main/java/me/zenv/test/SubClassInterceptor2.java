package me.zenv.test;

import java.util.UUID;

public class SubClassInterceptor2 {
    public String selectUserName(long id) {
        return "SubClassInterceptor2 user id : " + id + ", name : " + UUID.randomUUID().toString();
    }
}

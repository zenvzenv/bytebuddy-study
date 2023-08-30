package me.zenv.test;

import java.util.UUID;

public class SubClassInterceptor1 {
    public static String selectUserName(long id) {
        return "SubClassInterceptor1 user id : " + id + ", name : " + UUID.randomUUID().toString();
    }
}

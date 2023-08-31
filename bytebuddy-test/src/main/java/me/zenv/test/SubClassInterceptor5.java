package me.zenv.test;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class SubClassInterceptor5 {
    @RuntimeType
    public void aaa(@This Object targetObj) {
        System.out.println(targetObj + "实例化了");
    }
}

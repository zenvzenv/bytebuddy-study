package me.zenv.test;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class SubClassInterceptor6 {
    @RuntimeType
    public void interceptor(
            @Origin Class<?> clazz,
            @Origin Method targetMethod,
            @AllArguments Object[] targetArgs,
            @SuperCall Callable<?> zuper
    ) {
        System.out.println("clazz = " + clazz);
        System.out.println("targetMethod = " + targetMethod);
        System.out.println("targetArgs = " + targetArgs);
        System.out.println("zuper = " + zuper);
        try {
            zuper.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

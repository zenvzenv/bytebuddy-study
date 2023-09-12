package me.zenv.intercept;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * 拦截方法并统计耗时
 */
public class ByteBuddyIntercept {
    public Object intercept(
            @This Object targetObj,
            @Origin Method targetMethod,
            @AllArguments Object[] targetMethodArgs,
            @SuperCall Callable<?> zuper
    ) throws Exception {
        System.out.println("start to exec controller " + targetMethod.getName() + " " + Arrays.toString(targetMethodArgs));
        long start = System.currentTimeMillis();
        Object call = zuper.call();
        System.out.println("after controller exec, result is " + call);
        long end = System.currentTimeMillis();
        System.out.println("controller exec " + (end - start) + "ms");
        return call;
    }
}

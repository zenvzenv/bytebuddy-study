package me.zenv.test;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class SubClassInterceptor4 {
    @RuntimeType
    public Object aaa(
            // 目标方法的参数
            @AllArguments Object[] targetMethodArgs,
            // 可变入参调用
            @Morph MyCallable zuper
    ) {
        try {
            if (null != targetMethodArgs && targetMethodArgs.length > 0) {
                if (targetMethodArgs[0] instanceof Long) {
                    targetMethodArgs[0] = Long.parseLong(targetMethodArgs[0].toString()) + 1;
                }
            }
            // 调用目标方法
            return zuper.call(targetMethodArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package me.zenv.test;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class SubClassInterceptor3 {
    /**
     * 被 @RuntimeType 标注的方法就是拦截方法，此时方法签名或返回值可以与被拦截方法不一致
     * <p>
     * byte buddy 会在运行期间会给被注解修饰的方法参数进行赋值
     */
    @RuntimeType
    public Object aaa(
            // 表示被拦截的目标对象，只有拦截实例方法时可用
            @This Object targetObj,
            // 表示被拦截的方法，实例方法或静态方法时可用
            @Origin Method targetMethod,
            // 目标方法的参数
            @AllArguments Object[] targetMethodArgs,
            // 表示被拦截的目标对象，只有拦截实例方法时可用
            @Super Object targetObj2,
            // 如果确定父类，也可用父类直接接受
//            @Super SubClass SubClass,
            // 用于调用目标方法
            @SuperCall Callable<?> zuper
    ) {
        // targetObj = a.b.SubObj@44821a96
        System.out.println("targetObj = " + targetObj);
        // targetMethod = public java.lang.String me.zenv.test.SubClass.selectUserName(long)
        System.out.println("targetMethod = " + targetMethod);
        // targetMethodArgs = [100]
        System.out.println("targetMethodArgs = " + Arrays.toString(targetMethodArgs));
        // targetObj2 = a.b.SubObj@44821a96
        System.out.println("targetObj2 = " + targetObj2);
        // zuper = a.b.SubObj$auxiliary$9gOAqAbe@120f102b
        System.out.println("zuper = " + zuper);
        try {
            // 调用目标方法
            return zuper.call();
            // 不能这么用，原方法的调用会被拦截，然后被拦截，导致递归调用
//            return targetMethod.invoke(targetObj, targetMethodArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

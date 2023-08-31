package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;

/**
 * 对构造方法进行插桩
 */
public class InstrumentConstructorMethod {
    private static final String PATH = InstrumentConstructorMethod.class.getClassLoader().getResource("").getPath();

    private static void instrumentConstructorMethod() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        .with(TypeValidation.DISABLED)
                        .subclass(SubClass.class)
                        .name("a.b.SubObj")
                        // 拦截任意构造方法
                        .constructor(ElementMatchers.any())
                        .intercept(
                                // 在构造方法执行完成之后再委托给拦截器
                                SuperMethodCall.INSTANCE.andThen(
                                        MethodDelegation.to(new SubClassInterceptor5())
                                )
                        )
                        .make()
        ) {
            unloaded.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        instrumentConstructorMethod();
    }
}

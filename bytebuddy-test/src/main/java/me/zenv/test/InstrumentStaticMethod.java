package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;

/**
 * 对静态方法进行插桩
 */
public class InstrumentStaticMethod {
    private static final String PATH = InstrumentStaticMethod.class.getClassLoader().getResource("").getPath();

    private static void instrumentStaticMethod() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        .with(TypeValidation.DISABLED)
                        // 静态方法拦截不能用 redefine，因为 redefine 不会保留原始方法在拦截器中就无法通过 zuper 来调用
                        // 静态方法不能被继承，也就不能使用 subclass
                        .rebase(SubClass.class)
                        .name("a.b.SubObj")
                        .method(ElementMatchers.named("staticMethod").and(ElementMatchers.isStatic()))
                        .intercept(MethodDelegation.to(new SubClassInterceptor6()))
                        .make()
        ) {
            unloaded.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        instrumentStaticMethod();
    }
}

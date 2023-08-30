package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.File;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 方法委托
 * <p>
 * 自定义拦截逻辑
 */
public class MyMethodDelegation {
    private static final String PATH = MyMethodDelegation.class.getClassLoader().getResource("").getPath();

    private static void methodDelegation() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        .subclass(SubClass.class)
                        .name("a.b.SubObj")
                        .method(named("selectUserName"))
                        // 委托给 SubClassInterceptor1 中同签名的静态方法
//                        .intercept(MethodDelegation.to(SubClassInterceptor1.class))
                        // 委托给 SubClassInterceptor2 中同签名的实例方法
//                        .intercept(MethodDelegation.to(new SubClassInterceptor2()))
                        // 通过byte buddy 的注解来指定委托的方法
                        .intercept(MethodDelegation.to(new SubClassInterceptor3()))
                        .make()
        ) {
//            unloaded.saveIn(new File(PATH));
            DynamicType.Loaded<SubClass> loaded = unloaded.load(MyMethodDelegation.class.getClassLoader());
            Class<? extends SubClass> loadedClass = loaded.getLoaded();
            SubClass subClass = loadedClass.getDeclaredConstructor().newInstance();
            String result = subClass.selectUserName(100L);
            System.out.println("result = " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        methodDelegation();
    }
}

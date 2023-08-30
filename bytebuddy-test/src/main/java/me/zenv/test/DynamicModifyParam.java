package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 动态修改入参
 * <p>
 * 1. 自定义 MyCallable
 * 2. 在拦截器处使用 @Morph 代替之前的 @SuperCall
 * 3. 指定拦截器前需要先调用 withBinder
 */
public class DynamicModifyParam {
    private static final String PATH = MyMethodDelegation.class.getClassLoader().getResource("").getPath();

    private static void dynamicModifyParam() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        .subclass(SubClass.class)
                        .name("a.b.SubObj")
                        .method(named("selectUserName"))
                        .intercept(MethodDelegation
                                .withDefaultConfiguration()
                                .withBinders(
                                        // 在 SubClassInterceptor4 中使用 MyCallable 之前需要告诉 bytebuddy 参数的类型是 MyCallable
                                        Morph.Binder.install(MyCallable.class)
                                )
                                .to(new SubClassInterceptor4())
                        )
                        .make()
        ) {
//            unloaded.saveIn(new File(PATH));
            DynamicType.Loaded<SubClass> loaded = unloaded.load(DynamicModifyParam.class.getClassLoader());
            Class<? extends SubClass> loadedClass = loaded.getLoaded();
            SubClass subClass = loadedClass.getDeclaredConstructor().newInstance();
            String result = subClass.selectUserName(100L);
            System.out.println("result = " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        dynamicModifyParam();
    }
}

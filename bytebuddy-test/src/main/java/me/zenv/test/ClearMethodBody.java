package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.StubMethod;

import java.io.File;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

/**
 * 情况方法体
 */
public class ClearMethodBody {
    private static final String PATH = ClearMethodBody.class.getClassLoader().getResource("").getPath();

    private static void clearMethodBody(){
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        // 是否进行字节码合法性校验，不校验生成错误的字节码无法被 jvm 加载
                        .with(TypeValidation.DISABLED)
                        .subclass(SubClass.class)
                        // 匹配任意方法(除了父类方法)
                        .method(any().and(isDeclaredBy(SubClass.class)))
                        // 清空方法体
                        .intercept(StubMethod.INSTANCE)
                        .make()
        ) {
            unloaded.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        clearMethodBody();
    }
}

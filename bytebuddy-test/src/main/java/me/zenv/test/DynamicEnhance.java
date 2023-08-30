package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;

import java.io.File;

/**
 * 动态增强
 */
public class DynamicEnhance {
    private static final String PATH = GenClass.class.getClassLoader().getResource("").getPath();

    public static void dynamicEnhance() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        // 是否进行字节码合法性校验，不校验生成错误的字节码无法被 jvm 加载
                        .with(TypeValidation.DISABLED)
                        .subclass(SubClass.class)
                        .name("a.b.SubObj")
                        .make()
        ) {
            unloaded.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

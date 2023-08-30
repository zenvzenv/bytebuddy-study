package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.lang.reflect.Modifier;

/**
 * 插入新属性
 */
public class InsertNewProperties {
    private static final String PATH = GenClass.class.getClassLoader().getResource("").getPath();

    public static void insertNewProperties() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        .with(TypeValidation.DISABLED)
                        .redefine(SubClass.class)
                        .name("a.b.SubObj")
                        //新增属性
                        .defineField("age", int.class, Modifier.PRIVATE)
                        // 指定 age 的 getter 和 setter 所在的接口
                        .implement(SubClassInterface.class)
                        // 实现 getter 和 setter
                        .intercept(FieldAccessor.ofField("age"))
                        .make()
        ) {
            unloaded.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}

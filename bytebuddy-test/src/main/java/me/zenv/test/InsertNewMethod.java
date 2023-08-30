package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.lang.reflect.Modifier;

/**
 * 插入新方法
 */
public class InsertNewMethod {
    private static final String PATH = GenClass.class.getClassLoader().getResource("").getPath();

    public static void insertNewMethod() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        .with(TypeValidation.DISABLED)
                        .subclass(SubClass.class)
                        .name("a.b.SubObj")
                        // 定义方法的名字、返回值和修饰符
                        .defineMethod("selectUserName2", String.class, Modifier.PUBLIC)
                        //指定方法的参数，参数类型和参数名
                        .withParameter(String[].class, "args")
                        // 指定新方法
                        .intercept(FixedValue.value("byte buddy 生成的新方法"))
                        .make()
        ) {
            unloaded.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        insertNewMethod();
    }
}

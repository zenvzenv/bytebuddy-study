package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;

import java.io.File;
import java.io.IOException;

/**
 * 生成一个 class
 */
public class GenClass {
    private static final String PATH = GenClass.class.getClassLoader().getResource("").getPath();

    /**
     * 生成 class
     */
    public static void genClass() throws IOException {
        NamingStrategy.SuffixingRandom suffixingRandom = new NamingStrategy.SuffixingRandom("zenv");
        // Unloaded 表示字节码还未加载到 jvm
        try(DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                // 是否进行字节码合法性校验，不校验生成错误的字节码无法被 jvm 加载
                .with(TypeValidation.DISABLED)
                // 指定生成类的命名策略
                .with(suffixingRandom)
                .subclass(SubClass.class)
                // 直接指定包名和类名，指定完毕之后 suffixingRandom 失效
                .name("a.b.SubObj")
                /*
                 * 生成的类的命名规则：
                 * 在不指定命名策略的情况下
                 * 1. 对于父类是 jdk 自带的类情况：net.bytebuddy.renamed.java.lang.Object$ByteBuddy$UDRCT7TY
                 * 2. 对于父类非 jdk 自带的类情况：me.zenv.test.SubClass1$ByteBuddy$Wk5kFypH
                 *
                 * 在指定命名策略的情况下
                 * 1. 对于父类是 jdk 自带的类情况：net.bytebuddy.renamed.java.lang.Object$zenv$UDRCT7TY
                 * 2. 对于父类非 jdk 自带的类情况：me.zenv.test.SubClass1$zenv$QrpmbkZ7
                 */
                .make()) {
            // 获取生成的字节码
            byte[] bytes = unloaded.getBytes();
            // 将字节码保存到文件中
            unloaded.saveIn(new File(PATH));
            // 将字节码注入到 jar 包中
            unloaded.inject(new File(""));
        }
    }

    public static void main(String[] args) throws IOException {
        genClass();
    }
}

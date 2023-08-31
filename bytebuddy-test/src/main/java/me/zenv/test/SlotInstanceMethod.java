package me.zenv.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

/**
 * 对实例方法进行插桩
 * <p>
 * 1. subclass
 * 2. rebase:变基，保留原方法，并重命名为 xx%original$9U6HI()，xx 为拦截后的逻辑，命令策略是：默认是和目标类全类名保持一致
 * 3. redefine:原方法不再保留，命令策略是：默认是和目标类全类名保持一致
 */
public class SlotInstanceMethod {
    private static final String PATH = GenClass.class.getClassLoader().getResource("").getPath();

    public static void slot() {
        try (
                DynamicType.Unloaded<SubClass> unloaded = new ByteBuddy()
                        // 是否进行字节码合法性校验，不校验生成错误的字节码无法被 jvm 加载
                        .with(TypeValidation.DISABLED)
                        // subclass
                        // rebase
                        // redefine
                        .subclass(SubClass.class)
                        // 直接指定包名和类名，指定完毕之后 suffixingRandom 失效
                        .name("a.b.SubObj")
                        // named 通过名字指定要拦截的方法
                        .method(
                                named("selectUserName")
                                        .and(
                                                returns(TypeDescription.ForLoadedType.of(Class.class))
                                                        .or(returns(TypeDescription.ForLoadedType.of(Object.class)))
                                                        .or(returns(TypeDescription.ForLoadedType.of(String.class)))
                                        )


                        )
                        // 指定拦截到方法后如何处理，方法内容被替换为了 hello world
                        .intercept(FixedValue.nullValue())
                        .make()
        ) {
            // loaded 表示生成的字节码已被加载到 jvm 中
            DynamicType.Loaded<SubClass> load = unloaded.load(SlotInstanceMethod.class.getClassLoader());
            // 获取到 class 对象
            Class<? extends SubClass> loaded = load.getLoaded();
            SubClass subClass1 = loaded.getDeclaredConstructor().newInstance();
            String toString = subClass1.toString();
            // net.bytebuddy.dynamic.loading.ByteArrayClassLoader
            System.out.println("loaded.getClassLoader() = " + loaded.getClassLoader());
            System.out.println("toString = " + toString);
            load.saveIn(new File(PATH));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        slot();
    }
}

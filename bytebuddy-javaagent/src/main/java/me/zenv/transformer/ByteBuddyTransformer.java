package me.zenv.transformer;

import me.zenv.intercept.ByteBuddyIntercept;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;

import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class ByteBuddyTransformer implements AgentBuilder.Transformer {
    private static final String MAPPING_PKG_PRE = "org.springframework.web.bind.annotation.";

    private static final String MAPPING_SUFFIX = "Mapping";

    /**
     * 被 type(ElementMatcher<? super TypeDescription> var1) 被第一次加载时会走此方法
     *
     * @param builder
     * @param typeDescription  被加载的类的信息
     * @param classLoader
     * @param javaModule
     * @param protectionDomain
     * @return
     */
    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                            TypeDescription typeDescription,
                                            ClassLoader classLoader,
                                            JavaModule javaModule,
                                            ProtectionDomain protectionDomain) {
        System.out.printf("actual name transformer: %s", typeDescription.getActualName());
        // 不能返回 builder，因为 bytebuddy 库中的类基本都不可变，需要返回修改之后的实例防止修改丢失
        return builder
//                .constructor(any())
                .method(
                        not(isStatic())
                                .and(
                                        isAnnotatedWith(
                                                nameStartsWith(MAPPING_PKG_PRE).and(nameEndsWith(MAPPING_SUFFIX))
                                        )
                                )
                )
                .intercept(MethodDelegation.to(new ByteBuddyIntercept()));
    }
}

package me.zenv.javaagent;

import me.zenv.listener.ByteBuddyListener;
import me.zenv.transformer.ByteBuddyTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class ByteBuddyDemo {
    private static final String REST_CONTROLLER_NAME = "org.springframework.web.bind.annotation.RestController";
    private static final String CONTROLLER_NAME = "org.springframework.stereotype.Controller";

    public static void premain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                // 配置忽略拦截的包
                .ignore(nameStartsWith("net.bytebuddy").or(nameStartsWith("org.apache")))
                // 配置拦截哪些类
//                .type(isAnnotatedWith(named(REST_CONTROLLER_NAME).or(named(CONTROLLER_NAME))))
                .type(buildMatcher())
                // 哪些方法需要进行拦截
                .transform(new ByteBuddyTransformer())
                .with(new ByteBuddyListener())
                .installOn(instrumentation);
    }

    /**
     * 构建需要拦截的类
     */
    private static ElementMatcher<? super TypeDescription> buildMatcher() {
//        return named(REST_CONTROLLER_NAME);
        return new ElementMatcher.Junction.AbstractBase<TypeDescription>() {
            @Override
            public boolean matches(TypeDescription target) {
                return target.getActualName().equals(REST_CONTROLLER_NAME);
            }
        };
    }
}

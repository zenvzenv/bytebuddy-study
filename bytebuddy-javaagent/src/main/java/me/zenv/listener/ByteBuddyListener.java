package me.zenv.listener;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

public class ByteBuddyListener implements AgentBuilder.Listener {
    /**
     * 当某个类将要被加载时，就会回调此方法
     */
    @Override
    public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

    }

    /**
     * 对某个类完成 transform 后会回调
     */
    @Override
    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

    }

    /**
     * 当某个类将要被加载并配置为 bytebuddy 忽略(ignore 和不匹配的)的类时会回调
     */
    @Override
    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

    }

    /**
     * 当 transform 过程中发生异常时会回调
     */
    @Override
    public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

    }

    /**
     * 当某个类处理完(transform,ignore,error)时 完成时会回调
     */
    @Override
    public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

    }
}

package me.zenv.app;

@org.springframework.stereotype.Service
public class AppService {
    public String say(String name) {
        return "hello " + name;
    }
}

package me.zenv.app;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @Resource
    private AppService appService;

    @GetMapping("/say")
    public String say(String name) {
        return appService.say(name);
    }
}

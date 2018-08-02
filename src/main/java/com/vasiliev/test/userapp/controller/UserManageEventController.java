package com.vasiliev.test.userapp.controller;

import com.vasiliev.test.userapp.model.UserManageEvent;
import com.vasiliev.test.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/sse/user/event")
public class UserManageEventController {

    private final List<SseEmitter> sses = new CopyOnWriteArrayList<>();

    @Autowired
    UserService userService;

    @GetMapping
    public SseEmitter userEventsEmitter(){
        SseEmitter emitter = new SseEmitter();
        this.sses.add(emitter);

        emitter.onCompletion(() -> this.sses.remove(emitter));
        emitter.onTimeout(() -> this.sses.remove(emitter));

        return emitter;
    }

    @EventListener
    public void onMemoryInfo(UserManageEvent memoryInfo) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.sses.forEach(emitter -> {
            try {
                emitter.send(memoryInfo);
            }
            catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });

        this.sses.removeAll(deadEmitters);
    }
}

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

/**
 * The type User manage event controller.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@RestController
@RequestMapping("/sse/user/event")
public class UserManageEventController {

    private final List<SseEmitter> sses = new CopyOnWriteArrayList<>();

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * User events emitter sse emitter.
     *
     * @return the sse emitter
     */
    @GetMapping
    public SseEmitter userEventsEmitter() {
        SseEmitter emitter = new SseEmitter();
        this.sses.add(emitter);

        emitter.onCompletion(() -> this.sses.remove(emitter));
        emitter.onTimeout(() -> this.sses.remove(emitter));

        return emitter;
    }

    /**
     * On memory info.
     *
     * @param memoryInfo the memory info
     */
    @EventListener
    public void onMemoryInfo(UserManageEvent memoryInfo) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.sses.forEach(emitter -> {
            try {
                emitter.send(memoryInfo);
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });

        this.sses.removeAll(deadEmitters);
    }
}

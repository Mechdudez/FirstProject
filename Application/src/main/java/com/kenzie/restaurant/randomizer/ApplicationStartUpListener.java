package com.kenzie.restaurant.randomizer;


import com.kenzie.restaurant.randomizer.service.ReviewService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}

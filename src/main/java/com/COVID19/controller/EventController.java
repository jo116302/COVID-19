package com.COVID19.controller;

import com.COVID19.dto.EventResponse;
import com.querydsl.core.types.Predicate;
import com.COVID19.domain.Event;
import com.COVID19.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ModelAndView events(
            @QuerydslPredicate(root = Event.class) Predicate predicate
    ) {
        Map<String, Object> map = new HashMap<>();
        List<EventResponse> events = eventService.getEvents(predicate)
                .stream()
                .map(EventResponse::from)
                .toList();

        map.put("events", events);

        return new ModelAndView("event/index", map);
    }

    @GetMapping("/{eventId}")
    public String eventDetail(@PathVariable Long eventId) {
        return "event/detail";
    }
}

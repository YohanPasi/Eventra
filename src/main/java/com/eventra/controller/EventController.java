package com.eventra.controller;

import com.eventra.model.Event;
import com.eventra.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {

    private EventService eventService = new EventService();

    @GetMapping("/events")
    public String viewEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events";
    }

    @GetMapping("/add-event")
    public String showAddEventPage() {
        return "add-event";
    }

    @PostMapping("/add-event")
    public String addEvent(@RequestParam String name,
                           @RequestParam String date,
                           @RequestParam double price,
                           @RequestParam int availableSeats) {

        Event event = new Event();
        event.setName(name);
        event.setDate(date);
        event.setPrice(price);
        event.setAvailableSeats(availableSeats);

        eventService.createEvent(event);

        return "redirect:/events";
    }
}

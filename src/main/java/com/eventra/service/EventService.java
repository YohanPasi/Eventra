package com.eventra.service;

import com.eventra.model.Event;
import com.eventra.repository.EventRepository;

import java.util.List;
import java.util.UUID;

public class EventService {

    private EventRepository eventRepository = new EventRepository();

    public void createEvent(Event event) {
        event.setEventId("E" + UUID.randomUUID().toString().substring(0,5));
        eventRepository.saveEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }
}

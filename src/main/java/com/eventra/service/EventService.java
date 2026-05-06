package com.eventra.service;

import com.eventra.model.Event;
import com.eventra.model.TicketPackage;
import com.eventra.repository.EventRepository;
import com.eventra.repository.TicketPackageRepository;

import java.util.List;
import java.util.UUID;

public class EventService {

    private EventRepository eventRepository = new EventRepository();
    private TicketPackageRepository ticketPackageRepository = new TicketPackageRepository();

    public void createEvent(Event event, List<TicketPackage> packages) {
        String eventId = "E" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        event.setEventId(eventId);
        eventRepository.saveEvent(event);

        // Save each ticket package linked to this event
        for (TicketPackage pkg : packages) {
            pkg.setPackageId("P" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
            pkg.setEventId(eventId);
            ticketPackageRepository.savePackage(pkg);
        }
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public Event getEventById(String eventId) {
        return eventRepository.findById(eventId);
    }

    public List<TicketPackage> getPackagesForEvent(String eventId) {
        return ticketPackageRepository.getPackagesByEventId(eventId);
    }

    public void updateEvent(Event event, List<TicketPackage> packages) {
        // 1. Update event header
        eventRepository.updateEvent(event);
        // 2. Delete all old packages for this event and re-save new ones
        ticketPackageRepository.deleteByEventId(event.getEventId());
        for (TicketPackage pkg : packages) {
            pkg.setPackageId("P" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
            pkg.setEventId(event.getEventId());
            ticketPackageRepository.savePackage(pkg);
        }
    }

    public void deleteEvent(String eventId) {
        eventRepository.deleteEvent(eventId);
        ticketPackageRepository.deleteByEventId(eventId);
    }
}

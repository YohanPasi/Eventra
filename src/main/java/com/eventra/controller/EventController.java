package com.eventra.controller;

import com.eventra.model.Event;
import com.eventra.model.TicketPackage;
import com.eventra.service.CloudinaryService;
import com.eventra.service.EventService;
import com.eventra.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    private EventService eventService = new EventService();

    private String getRole(HttpServletRequest request) {
        String role = CookieUtil.getRoleFromRequest(request);
        return role != null ? role : "GUEST";
    }

    private String getUsername(HttpServletRequest request) {
        String username = CookieUtil.getUsernameFromRequest(request);
        return username != null ? username : "Guest";
    }

    // ── View all events ──────────────────────────────────────────────────────
    @GetMapping("/events")
    public String viewEvents(Model model, HttpServletRequest request) {
        String role = getRole(request);
        if ("GUEST".equals(role)) return "redirect:/login";

        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("role", role);
        model.addAttribute("username", getUsername(request));

        // Attach ticket packages for each event directly to the Event object
        events.forEach(e -> e.setPackages(eventService.getPackagesForEvent(e.getEventId())));

        return "events";
    }

    // ── Show Add Event form (ADMIN only) ─────────────────────────────────────
    @GetMapping("/add-event")
    public String showAddEventPage(HttpServletRequest request) {
        if (!"ADMIN".equals(getRole(request))) return "redirect:/events";
        return "add-event";
    }

    @GetMapping("/edit-event/{id}")
    public String showEditEventPage(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        if (!"ADMIN".equals(getRole(request))) return "redirect:/events";
        Event event = eventService.getEventById(id);
        if (event != null) {
            event.setPackages(eventService.getPackagesForEvent(id));
            model.addAttribute("eventToEdit", event);
        }
        return "add-event";
    }

    @PostMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable("id") String id, HttpServletRequest request) {
        if (!"ADMIN".equals(getRole(request))) return "redirect:/events";
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    // ── Handle Add/Edit Event form submission ─────────────────────────────────────
    @PostMapping("/add-event")
    public String addEvent(
            @RequestParam(required = false) String eventId,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String date,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile eventImage,
            // Ticket package arrays — one entry per row in the dynamic table
            @RequestParam List<String> tierName,
            @RequestParam List<Double> price,
            @RequestParam List<Integer> maxCount,
            @RequestParam(required = false) List<String> benefits,
            HttpServletRequest request) {

        if (!"ADMIN".equals(getRole(request))) return "redirect:/events";

        Event event = (eventId != null && !eventId.isEmpty()) ? eventService.getEventById(eventId) : new Event();
        
        // 1. Upload image to Cloudinary if provided
        if (eventImage != null && !eventImage.isEmpty()) {
            try {
                String imageUrl = CloudinaryService.uploadImage(eventImage);
                event.setImageUrl(imageUrl);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // 2. Build Event header
        event.setName(name);
        event.setLocation(location);
        event.setDate(date);
        event.setDescription(description);

        // 3. Build TicketPackage list
        List<TicketPackage> packages = new ArrayList<>();
        for (int i = 0; i < tierName.size(); i++) {
            if (tierName.get(i) == null || tierName.get(i).isBlank()) continue;
            TicketPackage pkg = new TicketPackage();
            pkg.setTierName(tierName.get(i));
            pkg.setPrice(price != null && i < price.size() ? price.get(i) : 0.0);
            pkg.setMaxCount(maxCount != null && i < maxCount.size() ? maxCount.get(i) : 0);
            pkg.setBenefits(benefits != null && i < benefits.size() ? benefits.get(i) : "");
            packages.add(pkg);
        }

        // 4. Persist
        if (eventId != null && !eventId.isEmpty()) {
            eventService.updateEvent(event, packages);
        } else {
            eventService.createEvent(event, packages);
        }
        return "redirect:/events";
    }
}

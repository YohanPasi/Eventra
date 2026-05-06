package com.eventra.model;

public class Event {

    private String eventId;
    private String name;
    private String location;
    private String date;
    private String description;
    private String imageUrl;
    private java.util.List<TicketPackage> packages = new java.util.ArrayList<>();

    public Event() {}

    public Event(String eventId, String name, String location, String date, String description, String imageUrl) {
        this.eventId = eventId;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public java.util.List<TicketPackage> getPackages() { return packages; }
    public void setPackages(java.util.List<TicketPackage> packages) { this.packages = packages; }

    // Helper methods for the UI
    public Double getStartingPrice() {
        if (packages == null || packages.isEmpty()) return 0.0;
        return packages.stream().mapToDouble(TicketPackage::getPrice).min().orElse(0.0);
    }

    public Integer getTotalSeats() {
        if (packages == null || packages.isEmpty()) return 0;
        return packages.stream().mapToInt(TicketPackage::getMaxCount).sum();
    }
}

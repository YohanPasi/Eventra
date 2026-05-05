package com.eventra.model;

public class Event {

    private String eventId;
    private String name;
    private String date;
    private double price;
    private int availableSeats;

    public Event() {}

    public Event(String eventId, String name, String date, double price, int availableSeats) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    // Getters & Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}

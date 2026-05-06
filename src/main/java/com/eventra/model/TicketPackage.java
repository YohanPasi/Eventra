package com.eventra.model;

public class TicketPackage {

    private String packageId;
    private String eventId;
    private String tierName;       // e.g. VIP, General, Student
    private double price;
    private int maxCount;          // max tickets issuable for this tier
    private String benefits;       // e.g. "Front row seating, Free buffet"

    public TicketPackage() {}

    public TicketPackage(String packageId, String eventId, String tierName,
                         double price, int maxCount, String benefits) {
        this.packageId = packageId;
        this.eventId = eventId;
        this.tierName = tierName;
        this.price = price;
        this.maxCount = maxCount;
        this.benefits = benefits;
    }

    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTierName() { return tierName; }
    public void setTierName(String tierName) { this.tierName = tierName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getMaxCount() { return maxCount; }
    public void setMaxCount(int maxCount) { this.maxCount = maxCount; }

    public String getBenefits() { return benefits; }
    public void setBenefits(String benefits) { this.benefits = benefits; }
}

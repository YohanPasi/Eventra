package com.eventra.repository;

import com.eventra.model.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Format (pipe-delimited to allow commas in description):
// eventId|name|location|date|description|imageUrl

public class EventRepository {

    private static final String FILE_PATH = "src/main/resources/data/events.txt";
    private static final String DELIMITER = "\\|";
    private static final String WRITE_DELIM = "|";

    public EventRepository() {
        try {
            File file = new File(FILE_PATH);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void saveEvent(Event event) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(String.join(WRITE_DELIM,
                    event.getEventId(),
                    event.getName(),
                    event.getLocation(),
                    event.getDate(),
                    event.getDescription(),
                    event.getImageUrl() != null ? event.getImageUrl() : ""));
            bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] data = line.split(DELIMITER, -1);
                if (data.length >= 6) {
                    events.add(new Event(data[0], data[1], data[2], data[3], data[4], data[5]));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return events;
    }

    public Event findById(String eventId) {
        return getAllEvents().stream()
                .filter(e -> e.getEventId().equals(eventId))
                .findFirst().orElse(null);
    }

    public void updateEvent(Event updated) {
        List<Event> all = getAllEvents();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Event e : all) {
                Event toWrite = e.getEventId().equals(updated.getEventId()) ? updated : e;
                bw.write(String.join(WRITE_DELIM,
                        toWrite.getEventId(),
                        toWrite.getName(),
                        toWrite.getLocation(),
                        toWrite.getDate(),
                        toWrite.getDescription(),
                        toWrite.getImageUrl() != null ? toWrite.getImageUrl() : ""));
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void deleteEvent(String eventId) {
        List<Event> all = getAllEvents();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Event e : all) {
                if (!e.getEventId().equals(eventId)) {
                    bw.write(String.join(WRITE_DELIM,
                            e.getEventId(), e.getName(), e.getLocation(),
                            e.getDate(), e.getDescription(),
                            e.getImageUrl() != null ? e.getImageUrl() : ""));
                    bw.newLine();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}

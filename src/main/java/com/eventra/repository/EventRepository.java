package com.eventra.repository;

import com.eventra.model.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private static final String FILE_PATH = "src/main/resources/data/events.txt";

    public EventRepository() {
        try {
            File file = new File(FILE_PATH);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEvent(Event event) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(event.getEventId() + "," +
                     event.getName() + "," +
                     event.getDate() + "," +
                     event.getPrice() + "," +
                     event.getAvailableSeats());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                Event event = new Event(
                        data[0],
                        data[1],
                        data[2],
                        Double.parseDouble(data[3]),
                        Integer.parseInt(data[4])
                );

                events.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }
}

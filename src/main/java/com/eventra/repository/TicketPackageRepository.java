package com.eventra.repository;

import com.eventra.model.TicketPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Format: packageId|eventId|tierName|price|maxCount|benefits

public class TicketPackageRepository {

    private static final String FILE_PATH = "src/main/resources/data/ticket_packages.txt";
    private static final String DELIMITER = "\\|";
    private static final String WRITE_DELIM = "|";

    public TicketPackageRepository() {
        try {
            File file = new File(FILE_PATH);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void savePackage(TicketPackage pkg) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(String.join(WRITE_DELIM,
                    pkg.getPackageId(),
                    pkg.getEventId(),
                    pkg.getTierName(),
                    String.valueOf(pkg.getPrice()),
                    String.valueOf(pkg.getMaxCount()),
                    pkg.getBenefits() != null ? pkg.getBenefits() : ""));
            bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public List<TicketPackage> getAllPackages() {
        List<TicketPackage> packages = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] data = line.split(DELIMITER, -1);
                if (data.length >= 6) {
                    packages.add(new TicketPackage(
                            data[0], data[1], data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            data[5]));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return packages;
    }

    public List<TicketPackage> getPackagesByEventId(String eventId) {
        return getAllPackages().stream()
                .filter(p -> p.getEventId().equals(eventId))
                .collect(Collectors.toList());
    }

    public void deleteByEventId(String eventId) {
        List<TicketPackage> all = getAllPackages();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (TicketPackage p : all) {
                if (!p.getEventId().equals(eventId)) {
                    bw.write(String.join(WRITE_DELIM,
                            p.getPackageId(), p.getEventId(), p.getTierName(),
                            String.valueOf(p.getPrice()),
                            String.valueOf(p.getMaxCount()),
                            p.getBenefits() != null ? p.getBenefits() : ""));
                    bw.newLine();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}

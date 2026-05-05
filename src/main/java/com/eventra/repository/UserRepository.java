package com.eventra.repository;

import com.eventra.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String FILE_PATH = "src/main/resources/data/users.txt";

    public UserRepository() {
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

    // SAVE USER
    public void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(user.getId() + "," +
                     user.getName() + "," +
                     user.getEmail() + "," +
                     user.getPassword() + "," +
                     user.getRole());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET ALL USERS (for later use)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                User user = new User(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}

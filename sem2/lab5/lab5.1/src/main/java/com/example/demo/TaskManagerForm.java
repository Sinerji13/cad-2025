package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaskManagerForm extends JFrame {

    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;
    private String authHeader;

    public TaskManagerForm(String authHeader) {
        this.authHeader = authHeader;

        setTitle("Task Manager");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JButton refresh = new JButton("Обновить задачи");
        refresh.addActionListener(e -> loadTasks());

        JPanel panel = new JPanel();
        panel.add(refresh);
        add(panel, BorderLayout.SOUTH);

        loadTasks();
    }

    private void loadTasks() {
        try {
            URL url = new URL("http://localhost:8080/api");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Basic " + authHeader);
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Response Code: " + code);

            if (code == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                String body = response.toString().trim();
                System.out.println("Response: " + body);

                if (!body.startsWith("[")) {
                    JOptionPane.showMessageDialog(this,
                            "Сервер вернул не JSON:\n" + body.substring(0, Math.min(200, body.length())));
                    return;
                }

                parseJson(body);
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки: HTTP " + code);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка подключения к серверу");
        }
    }

    private void parseJson(String json) {
        taskListModel.clear();

        JSONArray arr = new JSONArray(json);

        int i = 1;
        for (Object o : arr) {
            JSONObject obj = (JSONObject) o;

            String desc = obj.optString("description", "(без описания)");
            boolean completed = obj.optBoolean("completed", false);

            String text = "Задача " + i++ + ": " + desc;
            if (completed) text += " (✅выполнена)";

            taskListModel.addElement(text);
            
        }
    }

    private static String encodeBasicAuth(String login, String password) {
        return Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
    }

    public static void main(String[] args) {
        String auth = encodeBasicAuth("user", "password");
        System.out.println("Encoded Auth: " + auth);
        SwingUtilities.invokeLater(() -> new TaskManagerForm(auth).setVisible(true));
    }
}

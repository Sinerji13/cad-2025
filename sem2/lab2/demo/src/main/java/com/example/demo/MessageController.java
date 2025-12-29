package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private List<String> userMessages = new ArrayList<>();

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/messages")
    public List<String> getAllMessages() {
        return new ArrayList<>(userMessages);
    }

    @PostMapping("/messages")
    public ResponseEntity<String> publishMessage(@RequestBody String message) {
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Ошибка: сообщение не может быть пустым или содержать только пробелы.");
        }
        userMessages.add(message);
        return ResponseEntity.ok("Message published successfully!");
    }

    @PutMapping("/messages/{index}")
    public ResponseEntity<String> updateMessage(@PathVariable int index, @RequestBody String message) {
        if (index >= 0 && index < userMessages.size()) {
            userMessages.set(index, message);
            return ResponseEntity.ok("Message updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Message not found at index " + index);
    }

    @DeleteMapping("/messages/{index}")
    public ResponseEntity<String> deleteMessage(@PathVariable int index) {
        if (index >= 0 && index < userMessages.size()) {
            userMessages.remove(index);
            return ResponseEntity.ok("Message deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Message not found at index " + index);
    }

    @DeleteMapping("/messages")
    public ResponseEntity<String> clearAllMessages() {
        userMessages.clear();
        return ResponseEntity.ok("All messages cleared successfully!");
    }
}

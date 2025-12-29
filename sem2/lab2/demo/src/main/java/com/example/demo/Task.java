package com.example.demo;

public class Task {
    private static Long nextId = 1L; // Статическая переменная для генерации уникальных идентификаторов задач
    private Long id; // Уникальный идентификатор задачи
    private String title; // Название задачи
    private String description; // Описание задачи
    private boolean completed; // Статус задачи (выполнена или нет)

    // Конструкторы
    public Task() {
        this.id = generateId();
    }

    public Task(String title, String description) {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.completed = false; // По умолчанию задача не выполнена
    }

    // Генерация уникального идентификатора задачи
    private synchronized Long generateId() {
        return nextId++;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
public String toString() {
    return "Task{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", completed=" + completed +
            '}';
}
}
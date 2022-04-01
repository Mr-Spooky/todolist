package com.example.todolist;

public class Todo {
    public String id;
    public String content;
    public boolean done;

    public Todo(String id, String content, boolean done) {
        this.id = id;
        this.content = content;
        this.done = done;
    }
}

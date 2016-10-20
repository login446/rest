package com.alex.rest;

/**
 * Created by admin on 19.10.2016.
 */
public class User {
    private int id;
    private String name;
    private int score;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    public User(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

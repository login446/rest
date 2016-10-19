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

}

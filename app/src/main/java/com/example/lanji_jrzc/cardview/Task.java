package com.example.lanji_jrzc.cardview;

/**
 *This class is to demonstrate the Object task
 *
 * @author Lan Jing Li
 * @version May 29, 2018
 */

public class Task {
    private String taskName;
    private String time;
    private String thumbnailLetter;
    private int color;

    //Parameterized constructor method for the task class
    public Task(String taskName, String time, String thumbnailLetter, int color) {
        this.taskName = taskName;
        this.time = time;
        this.thumbnailLetter = thumbnailLetter;
        this.color = color;
    }

    // Accessor for taskName instance variable
    public String getTaskName() {
        return taskName;
    }

    // Mutator for taskName instance variable
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // Accessor for time instance variable
    public String getTime() {
        return time;
    }

    // Mutator for time instance variable
    public void setTime(String time) {
        this.time = time;
    }

    // Accessor for thumbNailLetter instance variable
    public String getThumbnailLetter() {
        return thumbnailLetter;
    }

    // Mutator for thumbNailLetter instance variable
    public void setThumbnailLetter(String thumbnailLetter) {
        this.thumbnailLetter = thumbnailLetter;
    }

    // Accessor for color instance variable
    public int getColor() {
        return color;
    }

    // Mutator for color instance variable
    public void setColor(int color) {
        this.color = color;
    }
}

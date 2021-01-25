package de.joemiagroup.krawumm;

import java.time.LocalDateTime;

public class Comment {



    // attributes
    private String text;
    private String[] pictures;
    private RegisteredUser commentator;
    private LocalDateTime date;

    // constructor
    public Comment(String text, String[] pictures, RegisteredUser registeredUser) {
        this.text = text;
        this.pictures = pictures;
        this.commentator=registeredUser;
        this.date = LocalDateTime.now();
    }

    // constructor
    public Comment(String text, RegisteredUser registeredUser) {
        this.text = text;
        this.pictures = null;
        this.commentator=registeredUser;
        this.date = LocalDateTime.now();
    }


    // functions

    // getter + setter text
    public String getText() {
        return text;
    }

    // sets text if its not longer than 1000 characters(maximum)
    public void setText(String text) {
        if (text.length() <= 1000) {
            this.text = text;
        }
        else{
            System.out.println("Die Eingabe ist zu lang. Kommentare dürfen maximal aus 1000 Zeichen bestehen.");
        }
    }

    // getter + setter pictures
    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    // getter + setter commentator
    public RegisteredUser getCommentator() {
        return commentator;
    }

    public void setCommentator(RegisteredUser commentator) {
        this.commentator = commentator;
    }

    // getter + setter date
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

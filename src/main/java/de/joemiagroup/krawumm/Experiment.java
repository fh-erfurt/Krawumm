package de.joemiagroup.krawumm;

import java.util.ArrayList;

public class Experiment extends ExperimentData{

    // attributes
    protected float finalRating;
    protected ArrayList<Integer> rating;
    protected ArrayList<Comment> comments;

    // Constructor sets variables to values from the form
    public Experiment(Form form){
        this.name           = form.name;
        this.material       = form.material;
        this.description    = form.description;
        this.pictures       = form.pictures;
        this.indoorOutdoor  = form.indoorOutdoor;
        this.age            = form.age;
        this.duration       = form.duration;
        this.difficulty     = form.difficulty;
        this.video          = form.video;
        this.instruction    = form.instruction;
        this.creator        = form.creator;
        this.rating         = new ArrayList<Integer>();
        this.comments       = new ArrayList<Comment>();
    }

    // Setter and Getter

    // sets finalRating to average of all ratings
    public void setFinalRating() {
        float totalValue = 0;
        for (int i = 0 ; i < rating.size() ; i++){
            totalValue += rating.get(i);
        }
        this.finalRating = totalValue/rating.size();
    }

    public float getFinalRating() {
        return finalRating;
    }

    public ArrayList<Integer> getRating() {
        return rating;
    }

    // adds a rating to rating list
    public void addRating(int rating) {
        this.rating.add(rating);
    }

    public ArrayList<Comment> getComments() { return comments; }

    // returns a single comment by their position
    public Comment getSingleComment(int position) {
        return this.comments.get(position);
    }

    // adds a comment to comment list
    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    // removes a comment from comment list
    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }

}

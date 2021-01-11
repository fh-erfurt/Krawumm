package de.joemiagroup.krawumm;

import java.util.ArrayList;

public class Experiment extends ExperimentData{

    private float finalRating;
    private ArrayList<Integer> rating;
    private ArrayList<Comment> comments;


    //TODO:Constructor


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

    public void addRating(int rating) {
        this.rating.add(rating);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Comment getSingleComment(int position) {
        return this.comments.get(position);
    }

    public void addComment(Comment _comment){
        this.comments.add(_comment);
    }

    public void removeComment(int numberOfComment){
        this.comments.remove(numberOfComment);
    }

}

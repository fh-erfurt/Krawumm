package de.joemiagroup.krawumm;

import java.util.ArrayList;

public class Experiment extends ExperimentData{

    private float finalRating;
    private ArrayList<Integer> rating;
    private ArrayList<Comment> comments;


    public void setFinalRating(float finalRating) {
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

    public void setRating(ArrayList<Integer> rating) {
        this.rating = rating;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}

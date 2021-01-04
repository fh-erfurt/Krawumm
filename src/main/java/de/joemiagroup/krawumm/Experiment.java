package de.joemiagroup.krawumm;

public class Experiment extends ExperimentData{

    private float rating;
    private Comment[] comments;


    public void setRating(float rating) {
        this.rating = rating;
        // rework
    }

    public float getRating() {
        return rating;
    }



    public void setComments(Comment[] comments) {
        this.comments = comments;
        //rework
    }

    public Comment[] getComments() {
        return comments;
    }

}

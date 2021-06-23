package de.joemiagroup.krawumm;

import java.util.ArrayList;

/**
 * This class is a Subclass od ExperimentData gets attributes(+setter and getter)(same as used in Form)
 * adds attributes finalRating ,rating and comments which only needed for a released Experiment(not for a Form)
 * <br>
 *
 * @author Johannes Otto
 *
 * @param name is the name of the experiment
 * @param material is an array which contains the Materials needed for the experiments
 * @param description contains a short description text for the experiment (max 500 characters)
 * @param pictures contains pictures of the experiment
 * @param indoorOutdoor is 1 for indoor and 2 for outdoor
 * @param age holds the minimum age the child should be to do the experiment
 * @param duration holds the estimated time it takes to finish the experiment
 * @param difficulty holds a difficulty value between 1 and 5 (5 is hardest)
 * @param video contains vidoes of the experiment
 * @param instruction contains a instruction of the steps to do the experiment
 * @param creator holds a reference to the RegisteredUsser who uploaded this experiment
 * @param finalRating holds the calculated average of all ratings combined
 * @param rating contains all ratings of this experiment
 * @param comments contains all comments of this experiment
 *
 * @see de.joemiagroup.krawumm.ExperimentData
 */

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

    /**
     *This method calculates the average rating out of a array of ratings and sets the final rating to this value.
     *
     * no params
     *
     * no return value
     */
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

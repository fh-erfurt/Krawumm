package de.joemiagroup.krawumm.trash;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class describes the registered user with all of their attributes (with setter and getter) and user specific functions:
 * sendForm, rate, comment, searchComment, deleteComment, logOut, deleteAccount, changePassword, addFromBookmarks, deleteFromBookmarks
 * <br>
 *
 * @author Jessica Eckardtsberg
 *
 * @param username is the username of the user, has to be unique
 * @param email is the email of the user, has to be unique
 * @param password is the password of the user
 * @param isCreator is true if the user uploaded an experiment in the past
 * @param uploadedExperiments contains the experiments which the user uploaded, is empty if he never uploaded an experiment
 * @param bookmarks contains the bookmarks of the user (Experiments)
 * @param loggedIn is true if the user is logged in
 */

public class RegisteredUser {

    // attributes
    protected String      username;
    protected String      email;
    protected String      password;
    protected boolean     isCreator;
    protected ArrayList<Experiment> uploadedExperiments;
    protected ArrayList<Experiment>    bookmarks;
    protected boolean loggedIn;


    // constructor
    public RegisteredUser(String username, String email, String password) {
        this.username           = username;
        this.email              = email;
        this.password           = password;
        this.isCreator          = false;
        this.uploadedExperiments= new ArrayList<>();
        this.bookmarks          = new ArrayList<>();
        this.loggedIn           = true;
    }

    // Setter and Getter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsCreator() {
        return isCreator;
    }

    public void setIsCreator(boolean isCreator) {
        this.isCreator = isCreator;
    }

    public ArrayList<Experiment> getUploadedExperiments() {
        return uploadedExperiments;
    }

    public void setUploadedExperiments(ArrayList<Experiment> uploadedExperiments) {
        this.uploadedExperiments = uploadedExperiments;
    }

    public ArrayList<Experiment> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(ArrayList<Experiment> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    /**
     *This method puts a form into the main.formsList which means, that a user created a new experiment
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
     * @param creator holds a reference to the RegisteredUser who uploaded this experiment
     *
     * no return value
     */
    public void sendForm(String name,String[] material,String description,String[] pictures,int indoorOutdoor,int age,float duration,int difficulty,String video,String[] instruction,RegisteredUser creator) {
        Form newRelease = new Form(name,material,description,pictures,indoorOutdoor,age,duration,difficulty,video,instruction,creator);
        main.addForm(newRelease);
    }

    /**
     * This method lets a user rate an experiment
     *
     * @param experiment is experiment the user rated
     * @param rating is the rating the user gave the experiment
     *
     * @see de.joemiagroup.krawumm.Experiment
     */
    public void rate(Experiment experiment, int rating) {
        experiment.addRating(rating);
        experiment.setFinalRating();
    }

    /**
     * This method lets the user write a comment to an experiment
     * If pictures array is empty it uses the constructor without pictures and creates a comment without pictures
     *
     * @param experiment is the experiment the user commented
     * @param text is the content of the comment
     * @param pictures is an array of pictures for the comment
     */
    public void comment(Experiment experiment,String text, String[] pictures) {
        if(pictures.length == 0){
            Comment comment = new Comment(text, this);
            experiment.addComment(comment);
        } else{
            Comment comment = new Comment(text,pictures, this);
            experiment.addComment(comment);
        }
    }

    /**
     * This function searches a comment from a user, over the timestamp where it was uploaded, of a specific experiment
     * needed so it can be deleted by the user
     *
     * @param experiment is the experiment where the comment should be searched
     * @param date is the time the comment was created
     *
     * @return commentResult returns the found comment
     */
    public Comment searchComment(Experiment experiment, LocalDateTime date){
        Comment commentResult = null;
        for (int i=0; i < experiment.getComments().size(); i++){
            Comment comment = experiment.getSingleComment(i);
            if(comment.getCommentator() == this){
                if(comment.getDate() == date){
                    commentResult = comment;
                    break;
                }
            }
        }
        return commentResult;
    }

    /**
     * This method is used to delete a comment by the user, who wrote it on a specific experiment, over the timestamp it was uploaded
     *
     * @param experiment is the experiment where the comment should be deleted
     * @param date is the time the comment was created
     *
     */
    public void deleteComment(Experiment experiment, LocalDateTime date) {
        if(searchComment(experiment,date) != null){
            experiment.removeComment(searchComment(experiment,date));
        }
        else{
            System.out.println("Du kannst den Kommentar nicht löschen.");
        }
    }

    // allows user to log themself out
    public void logOut() {
        this.loggedIn = false;
    }

    // allows user to delete their Account
    public void deleteAcc() {
        main.getUserList().remove(this);
    }


    /**
     * This method sets the password of the user to the new input
     *
     * @param newPassword is the new password
     *
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * This method adds an experiment to the users bookmarksList so they can find easier
     *
     * @param experiment is the experiment, that should be added
     *
     */
    public void addToBookmarks(Experiment experiment) { this.bookmarks.add(experiment); }

    /**
     * This method removes an experiment from the users bookmarkslist
     *
     * @param experiment is the experiment, which should get removed from the users bookmarksList
     *
     */
    public void removeFromBookmarks (Experiment experiment) {
        this.bookmarks.remove(experiment);
    }
}

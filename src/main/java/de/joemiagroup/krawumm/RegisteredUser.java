package de.joemiagroup.krawumm;

import java.time.LocalDateTime;
import java.util.ArrayList;

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



    // allows user to send a form which creates an Experiment after the administrator confirmed it
    public void sendForm(String name,String[] material,String description,String[] pictures,int indoorOutdoor,int age,float duration,int difficulty,String video,String[] instruction,RegisteredUser creator) {
        Form newRelease = new Form(name,material,description,pictures,indoorOutdoor,age,duration,difficulty,video,instruction,creator);
    }

    // allows user to rate an Experiment
    public void rate(Experiment experiment, int rating) {
        experiment.addRating(rating);
        experiment.setFinalRating();
    }

    // allows user to comment on an Experiment
    public void comment(Experiment experiment,String text, String[] pictures) {
        Comment comment = new Comment(text, pictures, this);
        experiment.addComment(comment);
    }

    // needed to find the correct comment so it can be deleted by the user
    public Integer searchCommentPosition(Experiment experiment, LocalDateTime date){
        int position = 0;
        for (int i=0; i < experiment.getComments().size(); i++){
            Comment comment = experiment.getSingleComment(i);
            if(comment.getCommentator() == this){
                if(comment.getDate() == date){
                    position = i;
                    break;
                }
            }
            else position = -1;
        }
        return position;
    }

    // allows user to delete e specific comment which is written by themself
    public void deleteComment(Experiment experiment, LocalDateTime date) {
        if(searchCommentPosition(experiment,date) != -1){
            experiment.removeComment(searchCommentPosition(experiment,date));
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
        // Apparently gets deleted by garbage collector.
        this.username = null;
        this.email = null;
        this.bookmarks = null;
        this.password = null;
        this.isCreator = false;
        this.uploadedExperiments = null;
    }

    // allows user to change their password
    public void changePassword(String code) {
        this.password = code;
    }

    // allows user to add an Experiment to their bookmarks
    public void addToBookmarks(Experiment experiment) {
        this.bookmarks.add(experiment);
    }

    // allows user to remove an Experiment from their bookmarks
    public void removeFromBookmarks (Experiment experiment) {
        this.bookmarks.remove(experiment);
    }
}

package de.joemiagroup.krawumm;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RegisteredUser {

    protected String      username;
    protected String      email;
    protected String      password;
    protected boolean     iscreator;
    protected ArrayList<Experiment> uploadedExperiments;
    protected ArrayList<Experiment>    bookmarks;

    public RegisteredUser(String username, String email, String password) {
        this.username           = username;
        this.email              = email;
        this.password           = password;
        this.iscreator          = false;
        this.uploadedExperiments= new ArrayList<>();
        this.bookmarks          = new ArrayList<>();
    }

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

    public boolean isIscreator() {
        return iscreator;
    }

    public void setIscreator(boolean iscreator) {
        this.iscreator = iscreator;
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

    public void sendForm() {
        Form newRelease = new Form();
    }

    public void rate(Experiment _experiment, int rating) {
        _experiment.addRating(rating);
        _experiment.setFinalRating();
    }

    public void comment(Experiment _experiment,String text, String[] pictures) {
        Comment comment = new Comment(text, pictures, this);
        _experiment.addComment(comment);
    }

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

    public void deleteComment(Experiment experiment, LocalDateTime date) {
        if(searchCommentPosition(experiment,date) != -1){
            experiment.removeComment(searchCommentPosition(experiment,date));
        }
        else{
            // TODO:exception
        }
    }

    public void logOut() {}

    public void deleteAcc() {
        // Destructor???
    }

    public void changePassword() {
        //TODO: Input new password from the user.
        //TODO: Set the new password.
    }

    public void addToBookmarks(Experiment _experiment) {
        this.bookmarks.add(_experiment);
    }

    public void removeFromBookmarks (Experiment _experiment) {
        this.bookmarks.remove(_experiment);
    }
}

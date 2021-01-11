package de.joemiagroup.krawumm;

public class RegisteredUser {

    protected String      username;
    protected String      email;
    protected String      password;
    protected boolean     iscreator;
    protected String[]    uploadedmaterial;
    protected String[]    bookmarks;

    public RegisteredUser(String username, String email, String password) {
        this.username           = username;
        this.email              = email;
        this.password           = password;
        this.iscreator          = false;
        this.uploadedmaterial   = new String[]{};
        this.bookmarks          = new String[]{};
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

    public String[] getUploadedmaterial() {
        return uploadedmaterial;
    }

    public void setUploadedmaterial(String[] uploadedmaterial) {
        this.uploadedmaterial = uploadedmaterial;
    }

    public String[] getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(String[] bookmarks) {
        this.bookmarks = bookmarks;
    }

    public void sendForm() {
        Form newRelease = new Form();
    }

    public void rate(Experiment _experiment) {
        float currentRating = _experiment.getRating();
        //TODO: Get input from the user.
        //TODO: Calculate the new rating.
        float newRating = 0;
        _experiment.setRating(newRating);
    }

    public void comment(Experiment _experimet) {
        Comment myComment = new Comment();
        //TODO: Push the Comment in the Comment-Array from Experiments. Need a method.
    }

    public void deleteComment() {
        // ???
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
        //TODO: Push an Experiment to the Bookmark-Array.
    }

    public void removeFromBookmarks (Experiment _experiment) {
        //TODO: Pop Experiment from the Bookmark-Array.
    }
}

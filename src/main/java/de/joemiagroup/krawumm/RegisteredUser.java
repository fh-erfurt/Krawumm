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

    public void rate() {}

    public void comment() {}

    public void deleteComment() {}

    public void logOut() {}

    public void deleteAcc() {}

    public void changePassword() {}

    public void addToBookmarks() {}

    public void removeFromBookmarks () {}
}

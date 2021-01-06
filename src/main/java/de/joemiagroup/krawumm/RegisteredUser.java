package de.joemiagroup.krawumm;

public class RegisteredUser {

    private String      username;
    private String      email;
    private String      password;
    private boolean     iscreator = false;
    private String[]    uploadedmaterial;
    private String[]    bookmarks;

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

    public void sendForm() {}
    public void rate() {}
    public void comment() {}
    public void deleteComment() {}
    public void logOut() {}
    public void deleteAcc() {}
    public void changePassword() {}
    public void addToBookmarks() {}
    public void removeFromBookmarks () {}
}

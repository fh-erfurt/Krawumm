package de.joemiagroup.krawumm;

public class Admin extends RegisteredUser {

    public Admin(String _name, String _email, String _password) {
        this.username           = _name;
        this.email              = _email;
        this.password           = _password;
        this.iscreator          = false;
        this.uploadedmaterial   = new String[]{}; //Don't know about the new String[]. intelliJ asked for it.
        this.bookmarks          = new String[]{};
    }

    public void deleteUser(RegisteredUser _user) {
        _user = null; //apparently gets delete by garbage collector.
    }

    public void releaseExperiment(String _name, Form _data) {
        /*
        Constructor needed. Also this doesn't work anyway.
        Experiment _name = new Experiment(_data);
        */

        deleteForm(_data);
    }

    public void deleteForm(Form _form) {
        _form = null; //apparently gets delete by garbage collector.
    }
}

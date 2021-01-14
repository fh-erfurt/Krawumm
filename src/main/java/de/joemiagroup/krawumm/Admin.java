package de.joemiagroup.krawumm;

public class Admin extends RegisteredUser {

    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    public void deleteUser(RegisteredUser _user) {
        _user = null; //apparently gets delete by garbage collector.
    }

    public void releaseExperiment(Form _data, RegisteredUser _uploader) {
        Experiment newExperiment = new Experiment(_data);
        if (!_uploader.isIscreator()) {
            _uploader.setIscreator(true);
        } else {
            // Nothing left to do.
        }
        deleteForm(_data);
    }

    public void deleteForm(Form _form) {
        _form = null; //apparently gets delete by garbage collector.
    }
}

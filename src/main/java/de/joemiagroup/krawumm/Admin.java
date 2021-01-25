package de.joemiagroup.krawumm;


public class Admin extends RegisteredUser {

    // Constructor
    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    // allows admin to delete a user account
    public void deleteUser(RegisteredUser user) {
        main.getUserList().remove(user);
    }

    // method to delete a form used in releaseExperiment
    public void deleteForm(Form form) {
        main.getFormsList().remove(form);
    }

    // allows admin to confirm a form submitted by a user and turns it into a final Experiment and release it
    public void releaseExperiment(Form data, RegisteredUser uploader) {
        Experiment newExperiment = new Experiment(data);
        main.addExperiment(newExperiment);
        if (!uploader.isIsCreator()) {
            uploader.setIsCreator(true);
        }
        // deletes form which is not needed anymore
        deleteForm(data);
    }
}

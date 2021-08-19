package de.joemiagroup.krawumm.trash;

/**
 * This class is asubclass of RegisterdUser
 * Has the same attributes like RegisterdUser and gained extra functions: deleteUser, deleteForm, releaseExperiment
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

public class Admin extends RegisteredUser {

    // Constructor
    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    /**
     *This method deletes a RegisteredUser from the main.userList
     *
     * @param user RegisteredUser which should be deleted
     *
     * no return value
     */
    public void deleteUser(RegisteredUser user) {
        main.getUserList().remove(user);
    }

    /**
     *This method deletes a Form from the main.formsList
     *
     * @param form Form which should be deleted
     *
     * no return value
     */
    public void deleteForm(Form form) {
        main.getFormsList().remove(form);
    }

    /**
     *This method converts a form into an experiment and "releases it"-> put it into main.experimentList
     * afterwards the form gets deleted from the main.formsList
     *
     * @param data Form which should be converted into an experiment and gets deleted afterwards
     * @param uploader is the uploader of the experiment which gets turned into a creator if their not already
     *
     * no return value
     */
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

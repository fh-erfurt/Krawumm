package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.bookmarks.BookmarkRepository;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import de.joemiagroup.krawumm.repositories.experimenthasmaterials.ExperimentHasMaterialRepository;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import de.joemiagroup.krawumm.repositories.materials.MaterialRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import de.joemiagroup.krawumm.web.BaseView;


import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * This class is the view that shows the experiments
 * <br>
 *
 * @author Johannes Otto
 *
 */
@ManagedBean("personView")
@ViewScoped
public class RegisteredUserView extends BaseView<RegisteredUser> {

    private static final long serialVersionUID = 4093615052840371924L;
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", Pattern.CASE_INSENSITIVE);

    @Autowired
    public RegisteredUserView(final RegisteredUserRepository repository, final RatingRepository ratingRepository,
                              final PicturesRepository picturesRepository, final InstructionRepository instructionRepository,
                              final CommentRepository commentRepository, final BookmarkRepository bookmarkRepository,
                              final ExperimentRepository experimentRepository, final ExperimentHasMaterialRepository experimentHasMaterialRepository,
                              final MaterialRepository materialRepository) {
        this.lazyDataModel = LazyRegisteredUserDataModel.of(repository, commentRepository, bookmarkRepository, ratingRepository,
                experimentRepository, picturesRepository, instructionRepository,experimentHasMaterialRepository, materialRepository);
        this.lazyDataModel.setSelected(new RegisteredUser());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initUsers() {
        this.lazyDataModel.initUserData();
    }

    @Getter
    private LazyRegisteredUserDataModel lazyDataModel;

    /**
     * This function deletes a RegisteredUSer with all relating Data(own Comments, own Ratings, own Bookamrks,
     * own Experiments and all to those Experiments regarding comments, ratings, pictures, bookmarks, instruction, experimentHasMaterials)
     *
     * no return value
     */
    @Transactional
    public void onClickDeleteEntry() {
        if (Objects.isNull(this.lazyDataModel.getLoggedInUser())) {
            return;
        }
        this.lazyDataModel.deleteRelatedData(this.lazyDataModel.getLoggedInUser());
        this.lazyDataModel.delete(this.lazyDataModel.getLoggedInUser());
        this.renderMessage(FacesMessage.SEVERITY_ERROR, "Tschüss.");
        this.lazyDataModel.setLoggedInUser(new RegisteredUser());
        this.lazyDataModel.setLoggedIn(false);
    }

    /**
     * This function checks if the email follows a email format
     *
     * no return value
     */
    public static boolean emailValidate(String email) {
        Matcher matcher= EMAIL_REGEX.matcher(email);
        return matcher.find();
    }

    /**
     * This function calls the function, which changes the password in the Database
     *
     * no return value
     */
    @Transactional
    public void onClickChangePassword() {
        this.editMode.set(true);
        String changeMessage = this.lazyDataModel.changePassword();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "" + changeMessage);
    }

    /**
     * This function calls the function, which checks the login and sets the loggedInUser
     *
     * no return value
     */
    @Transactional
    public void onClickLogin() {
        this.editMode.set(false);
        String loginMessage = this.lazyDataModel.checkLogin();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "" + loginMessage);
        if(loginMessage == "Login erfolgreich"){
            this.lazyDataModel.setLoggedInUser(new RegisteredUser());
            this.lazyDataModel.handleLoginData();
            this.lazyDataModel.setLoggedIn(true);
            this.lazyDataModel.setSelected(new RegisteredUser());
        }
    }

    /**
     * This function logs out the user and resets the loggedInUser
     *
     * no return value
     */
    @Transactional
    public void onClickLogout() {
        this.lazyDataModel.setLoggedIn(false);
        this.lazyDataModel.setLoggedInUser(new RegisteredUser());
    }

    /**
     * This function calls the function, which writes a new User into the database if the inputed data is correct
     *
     * no return value
     */
    @Transactional
    public void onClickSaveEntry() {
        this.editMode.set(false);

        if(!emailValidate(this.lazyDataModel.getSelected().getEmail())) {
            this.renderMessage(FacesMessage.SEVERITY_INFO, "Email hat falsches Format");
            this.lazyDataModel.setSelected(new RegisteredUser());
            return;
        }

        String message = this.lazyDataModel.save();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "" + message);
        if (message == "Willkommen bei Krawumm!"){
            this.lazyDataModel.setSelected(new RegisteredUser());
            this.lazyDataModel.setRedirectSignup(true);
        }
    }
}

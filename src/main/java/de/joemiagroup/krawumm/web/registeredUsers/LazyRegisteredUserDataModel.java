package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.Instruction;
import de.joemiagroup.krawumm.domains.Pictures;
import de.joemiagroup.krawumm.domains.ExperimentHasMaterial;
import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Bookmark;
import de.joemiagroup.krawumm.domains.Comment;
import de.joemiagroup.krawumm.domains.Rating;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.domains.TrueFalse;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import de.joemiagroup.krawumm.repositories.bookmarks.BookmarkRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import de.joemiagroup.krawumm.repositories.experimenthasmaterials.ExperimentHasMaterialRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 * This class communicates with the repositories to secure the database
 * <br>
 *
 * @author Johannes Otto
 *
 */
@RequiredArgsConstructor(staticName = "of")
public class LazyRegisteredUserDataModel extends LazyDataModel<RegisteredUser> {
    private static final long serialVersionUID = 3843316090759353348L;

    private final RegisteredUserRepository registeredUserRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RatingRepository ratingRepository;
    private final ExperimentRepository experimentRepository;
    private final PicturesRepository picturesRepository;
    private final InstructionRepository instructionRepository;
    private final ExperimentHasMaterialRepository experimentHasMaterialRepository;

    private final List<RegisteredUser> cache = new ArrayList<>();

    @Getter
    @Setter
    private RegisteredUser selected;
    @Getter
    @Setter
    private RegisteredUser loggedInUser;
    @Getter
    @Setter
    private boolean loggedIn = false;
    @Getter
    @Setter
    private boolean redirectSignup = false;
    @Getter
    @Setter
    private String oldPassword;
    @Getter
    @Setter
    private String newPassword;


    @Override
    public List<RegisteredUser> load(int page, int size, Map<String, SortMeta> sorts, Map<String, FilterMeta> filters) {

        cache.clear();

        cache.addAll(registeredUserRepository.findByParameters(page, size, filters, sorts));

        setRowCount((int) registeredUserRepository.countByParameters(filters));

        return cache;
    }

    @Override
    public RegisteredUser getRowData(String rowKey) {
        return cache.stream().filter(entry -> Objects.equals(String.valueOf(entry.getId()), rowKey)).findFirst().orElse(null);
    }

    @Override
    public String getRowKey(RegisteredUser object) {
        return String.valueOf(object.getId());
    }


    public String changePassword(){
        RegisteredUser user;
        user = registeredUserRepository.findUserDataByName(this.getLoggedInUser().getUserName());
        if (this.getOldPassword() != null) {
            if (BCrypt.checkpw(this.getOldPassword(), user.getPassword())){
                this.getLoggedInUser().setPassword(hashPassword(this.getNewPassword()));
                this.registeredUserRepository.save(this.getLoggedInUser());
                return "Passwort wurde geändert!";
            }
            else return "Passwort falsch";
        }
        else return "Passwort muss angegeben werden3!";
    }

    public String checkLogin() {
        if (Objects.isNull(this.getSelected())) {
            return "Benutzername oder Passwort falsch";
        }
        RegisteredUser user = null;
        if (Objects.nonNull(this.getSelected().getUserName())) {
            boolean userExists = registeredUserRepository.findUserByName(this.getSelected().getUserName());
            if(userExists) user = registeredUserRepository.findUserDataByName(this.getSelected().getUserName());
            else return "Benutzername oder Passwort falsch";
        }
        if (Objects.nonNull(this.getSelected().getPassword()) && user != null) {
            if (BCrypt.checkpw(this.getSelected().getPassword(), user.getPassword())) return "Login erfolgreich";
            else return "Benutzername oder Passwort falsch";
        }
        return "Benutzername oder Passwort falsch";
    }

    public void handleLoginData() {
        RegisteredUser user = null;
        if (Objects.nonNull(this.getSelected().getUserName())) {
            boolean userExists = registeredUserRepository.findUserByName(this.getSelected().getUserName());
            if (userExists) user = registeredUserRepository.findUserDataByName(this.getSelected().getUserName());
            this.getLoggedInUser().setId(user.getId());
            this.getLoggedInUser().setUserName(user.getUserName());
            this.getLoggedInUser().setPassword(user.getPassword());
            this.getLoggedInUser().setEmail(user.getEmail());
            this.getLoggedInUser().setIsAdmin(user.getIsAdmin());
            this.getLoggedInUser().setIsCreator(user.getIsCreator());
        }
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public String save() {
        if (Objects.isNull(this.getSelected())) {
            return "Bitte gib deine Daten ein";
        }

        if (Objects.nonNull(this.getSelected().getUserName())) {
            boolean loaded = registeredUserRepository.findUserByName(this.getSelected().getUserName());
            if(loaded){
                return "Benutzername schon vergeben";
            }
        }
        if (Objects.nonNull(this.getSelected().getEmail())) {
            boolean loadedUser = registeredUserRepository.findUserByEmail(this.getSelected().getEmail());
            if(loadedUser){
                return "Zu dieser Email-Adresse existiert schon ein Account!";
            }
        }
        if (Objects.nonNull(this.getSelected().getPassword())) {
            this.getSelected().setPassword(hashPassword(this.getSelected().getPassword()));
        }
        this.getSelected().setIsAdmin(TrueFalse.F);
        this.getSelected().setIsCreator(TrueFalse.F);
        this.registeredUserRepository.save(this.getSelected());
        return "Willkommen bei Krawumm!";
    }

    public List<Bookmark> showBookmarksOfUser(){
        List<Bookmark> bookmarks = this.registeredUserRepository.findBookmarksOfUser(this.loggedInUser);
        return bookmarks;
    }

    public void deleteRelatedData(RegisteredUser user){
        //delete Own Comments
        List<Comment> ownComments = this.registeredUserRepository.findCommentsOfUser(user);
        for(Comment i : ownComments){
            this.commentRepository.delete(i);
        }
        //delete Own Bookmarks
        List<Bookmark> ownBookmarks = this.registeredUserRepository.findBookmarksOfUser(user);
        for(Bookmark i : ownBookmarks){
            this.bookmarkRepository.delete(i);
        }
        //delete Own Ratings
        List<Rating> ownRatings = this.registeredUserRepository.findRatingsOfUser(user);
        for(Rating i : ownRatings){
            this.ratingRepository.delete(i);
        }

        List<Experiment> ownExperiments = this.registeredUserRepository.findExperimentsOfUser(user);

        //delete Comments of experiments
        for(Experiment e : ownExperiments){
            List<Comment> comments = this.experimentRepository.getCommentsForExperiment(e);
            for(Comment c : comments){
                this.commentRepository.delete(c);
            }
        }
        //delete Ratings of experiments
        for(Experiment e : ownExperiments){
            List<Rating> ratings = this.experimentRepository.getRatingsForExperiment(e);
            for(Rating r : ratings){
                this.ratingRepository.delete(r);
            }
        }
        //delete Instructions of Experiments
        for(Experiment e : ownExperiments){
            List<Instruction> instructions = this.experimentRepository.getInstructionsForExperiment(e);
            for(Instruction i : instructions){
                this.instructionRepository.delete(i);
            }
        }
        //delete Pictures of Experiments
        for(Experiment e : ownExperiments){
            List<Pictures> pictures = this.experimentRepository.getPicturesForExperiment(e);
            for(Pictures p : pictures){
                this.picturesRepository.delete(p);
            }
        }
        //delete Experiment has Material of experiments
        for(Experiment e : ownExperiments){
            List<ExperimentHasMaterial> experimentHasMaterials = this.experimentRepository.getExperimentHasMaterialsForExperiment(e);
            for(ExperimentHasMaterial ehs : experimentHasMaterials){
                this.experimentHasMaterialRepository.delete(ehs);
            }
        }
        //delete Bookmarks of experiments
        for(Experiment e : ownExperiments){
            List<Bookmark> bookmarks = this.experimentRepository.getBookmarksForExperiment(e);
            for(Bookmark b : bookmarks){
                this.bookmarkRepository.delete(b);
            }
        }
        //delete Own experiments
        for(Experiment e : ownExperiments){
            this.experimentRepository.delete(e);
        }

    }

    public void delete(RegisteredUser registeredUser) {
        this.registeredUserRepository.deleteRegisteredUserById(registeredUser.getId());
    }
}

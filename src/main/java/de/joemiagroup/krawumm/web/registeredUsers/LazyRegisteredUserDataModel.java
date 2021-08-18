package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.Bookmark;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.domains.TrueFalse;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepositoryCustom;
import jdk.jfr.Registered;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.faces.application.FacesMessage;

@RequiredArgsConstructor(staticName = "of")
public class LazyRegisteredUserDataModel extends LazyDataModel<RegisteredUser> {
    private static final long serialVersionUID = 3843316090759353348L;

    private final RegisteredUserRepository registeredUserRepository;

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
        List<Bookmark> bookmarks = registeredUserRepository.findBookmarksOfUser(this.loggedInUser);
        return bookmarks;
    }

    public void delete(RegisteredUser registeredUser) {
        this.registeredUserRepository.deleteRegisteredUserById(registeredUser.getId());
    }
}

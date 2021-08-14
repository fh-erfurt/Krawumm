package de.joemiagroup.krawumm.web.registeredUsers;

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
/*      this.registeredUserRepository.save(this.getSelected());*/
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public void save() {
        System.out.println(this.selected);
        if (Objects.isNull(this.getSelected())) {
            return;
        }

        if (Objects.nonNull(this.getSelected().getUserName())) {
            boolean loaded = registeredUserRepository.findUserByName(this.getSelected().getUserName());
            if(loaded){
                return;
            }
        }
        if (Objects.nonNull(this.getSelected().getPassword())) {
            this.getSelected().setPassword(hashPassword(this.getSelected().getPassword()));
        }
        this.getSelected().setIsAdmin(TrueFalse.F);
        this.getSelected().setIsCreator(TrueFalse.F);
        this.registeredUserRepository.save(this.getSelected());
    }


    public void delete(RegisteredUser registeredUser) {
        this.registeredUserRepository.deleteRegisteredUserById(registeredUser.getId());
    }
}

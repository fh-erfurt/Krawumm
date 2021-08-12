package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import de.joemiagroup.krawumm.web.BaseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;

import de.joemiagroup.krawumm.web.BaseView;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean("personView")
@ViewScoped
public class RegisteredUserPrimefacesView extends BaseView<RegisteredUser> {

    private static final long serialVersionUID = 4093615052840371924L;
    List<RegisteredUser> user = new ArrayList<>();


    @Autowired
    public RegisteredUserPrimefacesView(final RegisteredUserRepository repository) {
        this.lazyDataModel = LazyRegisteredUserDataModel.of(repository);
    }

    @Getter
    private final LazyRegisteredUserDataModel lazyDataModel;

    /*@Transactional
    public void onClickCreateNewEntry() {
        this.editMode.set(false);
        this.lazyDataModel.setSelected(new RegisteredUser());
    }*/

    @Transactional
    public void onClickDeleteEntry(RegisteredUser entry) {
        if (Objects.isNull(entry)) {
            return;
        }

        this.lazyDataModel.delete(entry);
        this.renderMessage(FacesMessage.SEVERITY_ERROR, "User deleted");
    }

    @Transactional
    public void onClickCreateNewEntry() {
        user.add(new RegisteredUser());
/*        this.lazyDataModel.save();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "User created");*/
    }
}

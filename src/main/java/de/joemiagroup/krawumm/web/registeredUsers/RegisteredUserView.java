package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import de.joemiagroup.krawumm.web.BaseView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;

import de.joemiagroup.krawumm.web.BaseView;
import de.joemiagroup.krawumm.web.IndexView;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean("personView")
@ViewScoped
public class RegisteredUserView extends BaseView<RegisteredUser> {

    private static final long serialVersionUID = 4093615052840371924L;

    private final AtomicBoolean loggedIn = new AtomicBoolean(false);

    public boolean isLoggedIn() {return this.loggedIn.get();}

    @Autowired
    public RegisteredUserView(final RegisteredUserRepository repository) {
        this.lazyDataModel = LazyRegisteredUserDataModel.of(repository);
        this.lazyDataModel.setSelected(new RegisteredUser());
    }

    @Getter
    private final LazyRegisteredUserDataModel lazyDataModel;

    @Transactional
    public void onClickCreateNewEntry() {
        this.editMode.set(false);

    }

    @Transactional
    public void onClickDeleteEntry(RegisteredUser entry) {
        if (Objects.isNull(entry)) {
            return;
        }

        this.lazyDataModel.delete(entry);
        this.renderMessage(FacesMessage.SEVERITY_ERROR, "Tschüss.");
    }

    @Transactional
    public void onClickSaveEntry() {
        this.lazyDataModel.save();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "Willkommen bei Krawumm!");
        this.loggedIn.set(true);
    }
}

package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import de.joemiagroup.krawumm.web.BaseView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", Pattern.CASE_INSENSITIVE);

    @Autowired
    public RegisteredUserView(final RegisteredUserRepository repository) {
        this.lazyDataModel = LazyRegisteredUserDataModel.of(repository);
        this.lazyDataModel.setSelected(new RegisteredUser());
    }

    @Getter
    private LazyRegisteredUserDataModel lazyDataModel;

    @Transactional
    public void onClickDeleteEntry(RegisteredUser entry) {
        if (Objects.isNull(entry)) {
            return;
        }

        this.lazyDataModel.delete(entry);
        this.renderMessage(FacesMessage.SEVERITY_ERROR, "Tschüss.");
    }

    public static boolean emailValidate(String email) {
        Matcher matcher= EMAIL_REGEX.matcher(email);
        return matcher.find();
    }

    @Transactional
    public void onClickLogin() {
        this.editMode.set(false);
        String loginMessage = this.lazyDataModel.checkLogin();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "" + loginMessage);
        this.lazyDataModel.setSelected(new RegisteredUser());
    }

    @Transactional
    public void onClickSaveEntry() {
        this.editMode.set(false);

        if(!emailValidate(this.lazyDataModel.getSelected().getEmail())) {
            this.renderMessage(FacesMessage.SEVERITY_ERROR, "Email hat falsches Format");
            this.lazyDataModel.setSelected(new RegisteredUser());
            return;
        }

        this.lazyDataModel.save();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "Willkommen bei Krawumm!");
        this.lazyDataModel.setSelected(new RegisteredUser());
    }
}

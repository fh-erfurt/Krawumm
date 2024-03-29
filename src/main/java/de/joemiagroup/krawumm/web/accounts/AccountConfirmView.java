package de.joemiagroup.krawumm.web.accounts;

import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * This class is the view to delete an account
 * <br>
 *
 * @author Michel Rost
 *
 */

@ManagedBean("confirmAccount")
@RequestScope
public class AccountConfirmView {
    public void delete() {
        addMessage("Bestätigt", "Konto wurde gelöscht.");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}

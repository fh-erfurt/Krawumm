package de.joemiagroup.krawumm.web;

import de.joemiagroup.krawumm.core.UserSessionContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Optional;

/**
 * This class is the index view that includes the different views
 * <br>
 *
 * @author Michel Rost
 *
 */

@ManagedBean("indexView")
@ViewScoped
public class IndexView extends BaseView{
    private static final long serialVersionUID = -3584769291129542280L;

    private final UserSessionContext context;

    private static final String CACHE_KEY_CURRENT_CONTENT = "currentContent";

    private ContentTypes currentContent;
    @Getter
    private boolean showFilter = false;

    @Autowired
    public IndexView(final UserSessionContext context) {
        this.context = context;
        final Optional<ContentTypes> cachedCurrentContent = context.getCached(CACHE_KEY_CURRENT_CONTENT, ContentTypes.class);
        this.currentContent = cachedCurrentContent.orElse(ContentTypes.HOME);
    }

    public enum ContentTypes {
        HOME,
        ACCOUNT,
        LOGIN,
        SIGNUP,
        VIEW,
        CREATE
    }

    public void load(String contentType) {
        this.currentContent = ContentTypes.valueOf(contentType);
        context.cache(CACHE_KEY_CURRENT_CONTENT, this.currentContent);
    }

    public String getCurrentContent() {
        return this.currentContent.name();
    }

    public void makeFiltersAppear() {
        this.showFilter = true;
    }

    public void makeFiltersDisappear() {
        this.showFilter = false;
    }
}

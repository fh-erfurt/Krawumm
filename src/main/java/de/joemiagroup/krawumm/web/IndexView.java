package de.joemiagroup.krawumm.web;

import de.joemiagroup.krawumm.core.UserSessionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Optional;

@ManagedBean("indexView")
@ViewScoped
public class IndexView extends BaseView{
    private static final long serialVersionUID = -3584769291129542280L;

    private final UserSessionContext context;

    private static final String CACHE_KEY_CURRENT_CONTENT = "currentContent";

    private ContentTypes currentContent;

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
        VIEW
    }

    public void load(String contentType) {
        this.currentContent = ContentTypes.valueOf(contentType);
        context.cache(CACHE_KEY_CURRENT_CONTENT, this.currentContent);
    }

    public String getCurrentContent() {
        return this.currentContent.name();
    }
}

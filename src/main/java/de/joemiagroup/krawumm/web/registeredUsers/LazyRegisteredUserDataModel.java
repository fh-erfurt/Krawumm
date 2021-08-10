package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

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

    public void save() {
        if (Objects.isNull(this.getSelected())) {
            return;
        }

/*        if (Objects.nonNull(this.getSelected().getAddress()) && Objects.nonNull(this.getSelected().getAddress().getId())) {
            Address loaded = addressRepository.findById(this.getSelected().getAddress().getId()).orElse(null);
            this.getSelected().setAddress(loaded);
        }*/

        this.registeredUserRepository.save(this.getSelected());
    }

    public void delete(RegisteredUser registeredUser) {
        this.registeredUserRepository.deleteRegisteredUserById(registeredUser.getId());
    }
}

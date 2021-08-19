package de.joemiagroup.krawumm.web.registeredUsers;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.repositories.materials.MaterialRepository;
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

<<<<<<< Updated upstream
/**
 * This class communicates with the repositories to secure the database
 * <br>
 *
 * @author Johannes Otto
 *
 */
=======
>>>>>>> Stashed changes
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
    private final MaterialRepository materialRepository;

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

<<<<<<< Updated upstream
=======
    //Own methods
    public void initUserData() {
        RegisteredUser pyro59 = new RegisteredUser(this.hashPassword("Pyro1234"), "pyro95@mail.com", "Pyro95", TrueFalse.T, TrueFalse.F);
        RegisteredUser supermum = new RegisteredUser(this.hashPassword("Mum1234"), "supermom89@mail.com", "Supermom89", TrueFalse.T, TrueFalse.F);
        RegisteredUser erdbeere = new RegisteredUser(this.hashPassword("Erdbärchen33"), "strawberry@mail.com", "Erdbeere", TrueFalse.F, TrueFalse.F);
        RegisteredUser admin = new RegisteredUser(this.hashPassword("a9!?Kr$43"), "admin@krawumm.de", "Admin", TrueFalse.F, TrueFalse.T);

        if (!this.registeredUserRepository.findUserByName("Pyro95")) {
            this.registeredUserRepository.save(pyro59);
            this.registeredUserRepository.save(supermum);
            this.registeredUserRepository.save(erdbeere);
            this.registeredUserRepository.save(admin);
        }

        Experiment exp1 = new Experiment("Mentos-Cola-Explosion",
                "Explosion durch chemische Reaktion bei Kontakt von Cola und Mentos",
                IndoorOutdoor.O,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                1,
                "https://www.youtube.com/watch?v=tSbZLSo93zM",
                5,
                5,
                TrueFalse.T);
        Experiment exp2 = new Experiment("Teebeutel-Rakete",
                "Teebeutel wird angezündet und fliegt in die Luft",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                2,
                null,
                10,
                5,
                TrueFalse.T);
        Experiment exp3 = new Experiment("Oberflächenspannung",
                "Zeigt die Wirkungsweise der Oberflächenspannung von Wasser",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                3,
                null,
                7,
                10,
                TrueFalse.T);
        Experiment exp4 = new Experiment("Kristallwachstum",
                "Kristallisierung von Salz über eine längere Zeitspanne",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                4,
                null,
                12,
                10,
                TrueFalse.T);
        Experiment exp5 = new Experiment("Feuer ersticken",
                "Zeigt, dass Feuer Sauerstoff benötigt",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                2,
                null,
                6,
                5,
                TrueFalse.T);
        Experiment exp6 = new Experiment("Teebeutel-Rakete",
                "Teebeutel wird angezündet und fliegt in die Luft",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                2,
                null,
                10,
                5,
                TrueFalse.T);
        Experiment exp7 = new Experiment("Leuchtende Straßenmalkreide",
                "Anleitung für die Herstellung von leuchtender Straßenmalkreide",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                4,
                null,
                8,
                20,
                TrueFalse.T);
        Experiment exp8 = new Experiment("Backpulver-Vulkan",
                "Zeigt die Reaktion von Essig und Backpulver",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                4,
                "https://www.youtube.com/watch?v=LmR8gwPXQP8",
                6,
                20,
                TrueFalse.T);
        Experiment exp9 = new Experiment("Pflanzen färben",
                "Pflanzen verfärben sich dank buntem Wasser",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                1,
                null,
                8,
                10,
                TrueFalse.T);
        Experiment exp10 = new Experiment("Gewürztrenner",
                "Zeigt, wie man Pfeffer von Salz mit Hilfe von Elektrostatik trennen kann",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Supermom89"),
                2,
                null,
                8,
                5,
                TrueFalse.T);
        Experiment exp11 = new Experiment("Raketenauto",
                "Raketenauto selbst bauen",
                IndoorOutdoor.O,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                5,
                null,
                12,
                30,
                TrueFalse.F);
        Experiment exp12 = new Experiment("Sanduhr",
                "Eine Sanduhr selbst bauen",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Pyro95"),
                23,
                null,
                8,
                15,
                TrueFalse.F);
        Experiment exp13 = new Experiment("Geheimschrift",
                "Zitronensaft als Tinte lässt die Schrift verschwinden",
                IndoorOutdoor.I,
                this.registeredUserRepository.findUserDataByName("Supermom89"),
                2,
                null,
                6,
                10,
                TrueFalse.F);

        if(this.experimentRepository.getAllExperiments(TrueFalse.T).isEmpty()) {
            this.experimentRepository.save(exp1);
            this.experimentRepository.save(exp2);
            this.experimentRepository.save(exp3);
            this.experimentRepository.save(exp4);
            this.experimentRepository.save(exp5);
            this.experimentRepository.save(exp6);
            this.experimentRepository.save(exp7);
            this.experimentRepository.save(exp8);
            this.experimentRepository.save(exp9);
            this.experimentRepository.save(exp10);
            this.experimentRepository.save(exp11);
            this.experimentRepository.save(exp12);
            this.experimentRepository.save(exp13);
        }

        if(this.materialRepository.findMaterialByName("Cola").isEmpty()) {
            this.materialRepository.save(new Material("Cola"));
            this.materialRepository.save(new Material("Mentos"));
            this.materialRepository.save(new Material("Teebeutel"));
            this.materialRepository.save(new Material("Schere"));
            this.materialRepository.save(new Material("Feuerzeug"));
            this.materialRepository.save(new Material("Glas"));
            this.materialRepository.save(new Material("Wasser"));
            this.materialRepository.save(new Material("Spülmittel"));
            this.materialRepository.save(new Material("Büroklammer"));
            this.materialRepository.save(new Material("Salz"));
            this.materialRepository.save(new Material("Löffel"));
            this.materialRepository.save(new Material("Lebensmittelfarbe"));
            this.materialRepository.save(new Material("Teelicht"));
            this.materialRepository.save(new Material("Mehl"));
            this.materialRepository.save(new Material("nachtleuchtende Farbe"));
            this.materialRepository.save(new Material("Schneebesen"));
            this.materialRepository.save(new Material("Flüssigseife"));
            this.materialRepository.save(new Material("Pinsel"));
            this.materialRepository.save(new Material("Zitrone"));
            this.materialRepository.save(new Material("Nagel"));
            this.materialRepository.save(new Material("Kupferdraht"));
            this.materialRepository.save(new Material("Kabel"));
            this.materialRepository.save(new Material("Kopfhörer"));
            this.materialRepository.save(new Material("Teller"));
            this.materialRepository.save(new Material("Alufolie"));
            this.materialRepository.save(new Material("Klebeband"));
            this.materialRepository.save(new Material("Backpulver"));
            this.materialRepository.save(new Material("Essig"));
            this.materialRepository.save(new Material("wasserdichte Unterlage"));
            this.materialRepository.save(new Material("Staudensellerie"));
            this.materialRepository.save(new Material("Plastiklöffel"));
            this.materialRepository.save(new Material("Wollpullover"));
            this.materialRepository.save(new Material("Pfeffer"));
            this.materialRepository.save(new Material("Plastikflasche"));
            this.materialRepository.save(new Material("Bierdeckel"));
            this.materialRepository.save(new Material("Trinkhalm"));
            this.materialRepository.save(new Material("Holzspieß"));
            this.materialRepository.save(new Material("Klarsichthülle"));
            this.materialRepository.save(new Material("Korken"));
            this.materialRepository.save(new Material("Schrauböse"));
            this.materialRepository.save(new Material("Bindfaden"));
            this.materialRepository.save(new Material("Heißkleber"));
            this.materialRepository.save(new Material("Messer"));
            this.materialRepository.save(new Material("Kerze"));
            this.materialRepository.save(new Material("Stift"));
            this.materialRepository.save(new Material("Lineal"));
            this.materialRepository.save(new Material("Sand"));
            this.materialRepository.save(new Material("Hammer"));
            this.materialRepository.save(new Material("Sieb"));
            this.materialRepository.save(new Material("Pappe"));
            this.materialRepository.save(new Material("Stoppuhr"));
            this.materialRepository.save(new Material("Papier"));
            this.materialRepository.save(new Material("Zitronensaft"));
        }

        if(true) {
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(18), this.experimentRepository.getExperimentById(5)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(19), this.experimentRepository.getExperimentById(5)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(20), this.experimentRepository.getExperimentById(6)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(21), this.experimentRepository.getExperimentById(6)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(22), this.experimentRepository.getExperimentById(6)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(23), this.experimentRepository.getExperimentById(7)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(24), this.experimentRepository.getExperimentById(7)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(25), this.experimentRepository.getExperimentById(7)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(26), this.experimentRepository.getExperimentById(7)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(23), this.experimentRepository.getExperimentById(8)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(24), this.experimentRepository.getExperimentById(8)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(27), this.experimentRepository.getExperimentById(8)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(28), this.experimentRepository.getExperimentById(8)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(29), this.experimentRepository.getExperimentById(8)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(22), this.experimentRepository.getExperimentById(9)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(23), this.experimentRepository.getExperimentById(9)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(30), this.experimentRepository.getExperimentById(9)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(31), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(24), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(32), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(33), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(34), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(29), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(35), this.experimentRepository.getExperimentById(10)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(36), this.experimentRepository.getExperimentById(11)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(37), this.experimentRepository.getExperimentById(11)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(38), this.experimentRepository.getExperimentById(11)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(39), this.experimentRepository.getExperimentById(11)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(40), this.experimentRepository.getExperimentById(11)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(41), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(42), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(43), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(44), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(45), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(46), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(21), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(23), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(29), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(47), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(48), this.experimentRepository.getExperimentById(12)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(49), this.experimentRepository.getExperimentById(13)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(50), this.experimentRepository.getExperimentById(13)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(27), this.experimentRepository.getExperimentById(13)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(41), this.experimentRepository.getExperimentById(13)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(51), this.experimentRepository.getExperimentById(14)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(52), this.experimentRepository.getExperimentById(14)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(53), this.experimentRepository.getExperimentById(14)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(54), this.experimentRepository.getExperimentById(14)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(14)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(45), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(51), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(59), this.experimentRepository.getExperimentById(15)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(64), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(37), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(65), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(37), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(65), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(43), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(67), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(68), this.experimentRepository.getExperimentById(16)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(69), this.experimentRepository.getExperimentById(17)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(70), this.experimentRepository.getExperimentById(17)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(35), this.experimentRepository.getExperimentById(17)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(22), this.experimentRepository.getExperimentById(17)));
            this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getOne(61), this.experimentRepository.getExperimentById(17)));
        }
    }
>>>>>>> Stashed changes

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

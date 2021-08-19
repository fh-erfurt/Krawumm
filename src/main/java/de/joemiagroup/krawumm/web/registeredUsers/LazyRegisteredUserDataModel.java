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
import org.aspectj.apache.bcel.classfile.Code;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;


/**
 * This class communicates with the repositories to secure the database
 * <br>
 *
 * @author Johannes Otto
 *
 */
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


    /**
     * This function initializes the database with example data
     *
     * no return value
     */
        public void initUserData() {
            RegisteredUser pyro59 = new RegisteredUser(this.hashPassword("Pyro1234"), "pyro95@mail.com", "Pyro95", TrueFalse.T, TrueFalse.F);
            RegisteredUser supermum = new RegisteredUser(this.hashPassword("Mum1234"), "supermom89@mail.com", "Supermom89", TrueFalse.T, TrueFalse.F);
            RegisteredUser erdbeere = new RegisteredUser(this.hashPassword("Erdbärchen33"), "strawberry@mail.com", "Erdbeere", TrueFalse.F, TrueFalse.F);
            RegisteredUser admin = new RegisteredUser(this.hashPassword("a9!?Kr$43"), "admin@krawumm.de", "Admin", TrueFalse.F, TrueFalse.T);

            if (this.registeredUserRepository.getAllRegisteredUsers().isEmpty()) {
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

            if(this.materialRepository.getAllMaterials().isEmpty()) {
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

            if(this.experimentHasMaterialRepository.getAllExperimentHasMaterials().isEmpty()) {
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(18), this.experimentRepository.getExperimentById(5)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(19), this.experimentRepository.getExperimentById(5)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(20), this.experimentRepository.getExperimentById(6)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(21), this.experimentRepository.getExperimentById(6)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(22), this.experimentRepository.getExperimentById(6)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(23), this.experimentRepository.getExperimentById(7)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(24), this.experimentRepository.getExperimentById(7)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(25), this.experimentRepository.getExperimentById(7)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(26), this.experimentRepository.getExperimentById(7)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(23), this.experimentRepository.getExperimentById(8)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(24), this.experimentRepository.getExperimentById(8)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(27), this.experimentRepository.getExperimentById(8)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(28), this.experimentRepository.getExperimentById(8)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(29), this.experimentRepository.getExperimentById(8)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(22), this.experimentRepository.getExperimentById(9)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(23), this.experimentRepository.getExperimentById(9)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(30), this.experimentRepository.getExperimentById(9)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(31), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(24), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(32), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(33), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(34), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(29), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(35), this.experimentRepository.getExperimentById(10)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(36), this.experimentRepository.getExperimentById(11)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(37), this.experimentRepository.getExperimentById(11)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(38), this.experimentRepository.getExperimentById(11)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(39), this.experimentRepository.getExperimentById(11)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(40), this.experimentRepository.getExperimentById(11)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(41), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(42), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(43), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(44), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(45), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(46), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(21), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(23), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(29), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(24), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(25), this.experimentRepository.getExperimentById(12)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(23), this.experimentRepository.getExperimentById(13)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(24), this.experimentRepository.getExperimentById(13)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(29), this.experimentRepository.getExperimentById(13)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(47), this.experimentRepository.getExperimentById(13)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(48), this.experimentRepository.getExperimentById(14)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(49), this.experimentRepository.getExperimentById(14)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(50), this.experimentRepository.getExperimentById(14)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(27), this.experimentRepository.getExperimentById(14)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(41), this.experimentRepository.getExperimentById(14)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(51), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(52), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(53), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(54), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(55), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(56), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(57), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(58), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(59), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(60), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(61), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(62), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(63), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(44), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(45), this.experimentRepository.getExperimentById(15)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(51), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(59), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(64), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(38), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(65), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(43), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(67), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(68), this.experimentRepository.getExperimentById(16)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(69), this.experimentRepository.getExperimentById(17)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(70), this.experimentRepository.getExperimentById(17)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(35), this.experimentRepository.getExperimentById(17)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(22), this.experimentRepository.getExperimentById(17)));
                this.experimentHasMaterialRepository.save(new ExperimentHasMaterial(this.materialRepository.getMaterialById(61), this.experimentRepository.getExperimentById(17)));
            }

            if(this.ratingRepository.getAllRatings().isEmpty()) {
                this.ratingRepository.save(new Rating(5, this.experimentRepository.getExperimentById(5), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(4, this.experimentRepository.getExperimentById(5), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(4, this.experimentRepository.getExperimentById(7), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(5, this.experimentRepository.getExperimentById(7), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(2, this.experimentRepository.getExperimentById(6), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(5, this.experimentRepository.getExperimentById(6), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(5, this.experimentRepository.getExperimentById(13), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(4, this.experimentRepository.getExperimentById(12), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(3, this.experimentRepository.getExperimentById(12), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(3, this.experimentRepository.getExperimentById(14), this.registeredUserRepository.findUserDataByName("Pyro1234")));
                this.ratingRepository.save(new Rating(4, this.experimentRepository.getExperimentById(8), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(3, this.experimentRepository.getExperimentById(9), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(4, this.experimentRepository.getExperimentById(10), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(4, this.experimentRepository.getExperimentById(11), this.registeredUserRepository.findUserDataByName("Erdbeere")));
                this.ratingRepository.save(new Rating(3, this.experimentRepository.getExperimentById(11), this.registeredUserRepository.findUserDataByName("Supermom89")));
                this.ratingRepository.save(new Rating(5, this.experimentRepository.getExperimentById(10), this.registeredUserRepository.findUserDataByName("Erdbeere")));
            }

            if(this.picturesRepository.getAllPictures().isEmpty()) {
                this.picturesRepository.save(new Pictures("https://www.chemie-azubi.de/fileadmin/user_upload/Rheinland-Pfalz/2017/Some_things_money_just_can_t_buy_scott_robibson_flickr_cc_ba_2.0.jpg", null, this.experimentRepository.getExperimentById(5)));
                this.picturesRepository.save(new Pictures("https://kindergarten-ideen.b-cdn.net/wp-content/uploads/2013/03/teebeutelrakete-5.jpg", null, this.experimentRepository.getExperimentById(6)));
                this.picturesRepository.save(new Pictures("https://www.simplyscience.ch/fileadmin/_processed_/6/a/csm_Wasserglas_Oberflaechenspannung_23ea79e04a.jpg", null, this.experimentRepository.getExperimentById(7)));
                this.picturesRepository.save(new Pictures("https://www.talu.de/wp-content/uploads/2017/02/zuckerkristalle-zuechten-02.jpg", null, this.experimentRepository.getExperimentById(8)));
                this.picturesRepository.save(new Pictures("https://image.jimcdn.com/app/cms/image/transf/dimension=1920x400:format=jpg/path/s76ecc4c668aa39e6/image/ia013eb426a9fd02c/version/1430385995/image.jpg", null, this.experimentRepository.getExperimentById(9)));
                this.picturesRepository.save(new Pictures("https://www.trendcreativ.de/images/product_images/original_images/23147-2.jpg", null, this.experimentRepository.getExperimentById(10)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30070038/t/H6/v4/w960/r0/-/bild-3-gross-jpg--32695-.jpg", null, this.experimentRepository.getExperimentById(11)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30118956/t/88/v3/w960/r0/-/backpulver-vulkan-zuschnitt-jpg--66830-.jpg", null, this.experimentRepository.getExperimentById(12)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30123200/t/gv/v3/w960/r0/-/bunt-gefaerbte-pflanzen-in-vasen-experiment-fuer-kinder-jpg--69634-.jpg", null, this.experimentRepository.getExperimentById(13)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30090570/t/Df/v3/w480/r0/-/03-jpg--144-.jpg", null, this.experimentRepository.getExperimentById(14)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30144008/t/t1/v4/w960/r0/-/raketenauto-1-geolino-png--81984-.jpg", null, this.experimentRepository.getExperimentById(15)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30146452/t/ZP/v3/w960/r0/-/sanduhr-bauen-teaser-jpg--83414-.jpg", null, this.experimentRepository.getExperimentById(16)));
                this.picturesRepository.save(new Pictures("https://image.geo.de/30045450/t/3H/v3/w960/r0/-/geheimtinte-03-gross-jpg--19160-.jpg", null, this.experimentRepository.getExperimentById(17)));
            }

            if(this.instructionRepository.getAllInstructions().isEmpty()) {
                this.instructionRepository.save(new Instruction("Zuerst muss man die Colaflasche öffnen.", this.experimentRepository.getExperimentById(5)));
                this.instructionRepository.save(new Instruction("Danach nimmt man ein Mentos und lässt es in die Flaschenöffnung fallen.", this.experimentRepository.getExperimentById(5)));
                this.instructionRepository.save(new Instruction("Zuerst schneidet man den Teebeutel mit der Schere auf und leert ihn.", this.experimentRepository.getExperimentById(6)));
                this.instructionRepository.save(new Instruction("Danach faltet man den Teebeutel so auf, dass er ein oben und unten offener Zylinder ist und stellt ihn auf.", this.experimentRepository.getExperimentById(6)));
                this.instructionRepository.save(new Instruction("Im Anschluss zündet man den Teebeutel oben an.", this.experimentRepository.getExperimentById(6)));
                this.instructionRepository.save(new Instruction("Zuerst wird das Glas bis zum Rand mit Wasser gefüllt.", this.experimentRepository.getExperimentById(7)));
                this.instructionRepository.save(new Instruction("Danach legt man die Büroklammer vorsichtig auf die Wasseroberfläche.", this.experimentRepository.getExperimentById(7)));
                this.instructionRepository.save(new Instruction("Zum Schluss gibt man das Spülmittel hinzu.", this.experimentRepository.getExperimentById(7)));
                this.instructionRepository.save(new Instruction("Zuerst füllt man das Glas 1cm hoch mit Tafelsalz.", this.experimentRepository.getExperimentById(8)));
                this.instructionRepository.save(new Instruction("Wasser hinzufügen bis das Glas zu 3-4cm gefüllt ist.", this.experimentRepository.getExperimentById(8)));
                this.instructionRepository.save(new Instruction("Im Anschluss Lebensmittelfarbe hinzufügen und umrühren.", this.experimentRepository.getExperimentById(8)));
                this.instructionRepository.save(new Instruction("Nun muss man warten und das Glas etwa zwei Wochen warm stehen lassen, damit das Wasser verdunstet.", this.experimentRepository.getExperimentById(8)));
                this.instructionRepository.save(new Instruction("Zuerst wird das Teelicht angezündet.", this.experimentRepository.getExperimentById(9)));
                this.instructionRepository.save(new Instruction("Im Anschluss muss man das Glas über das Teelicht stülpen.", this.experimentRepository.getExperimentById(9)));
                this.instructionRepository.save(new Instruction("Zuerst Mehl, Wasser und die nachtleuchtende Farbe gut vermischen und noch einige Tropfen Flüssigseife hinzugeben.", this.experimentRepository.getExperimentById(10)));
                this.instructionRepository.save(new Instruction("Für bunte Kreide einfach ein bisschen Lebensmittelfarbe untermischen.", this.experimentRepository.getExperimentById(10)));
                this.instructionRepository.save(new Instruction("Jetzt kann die flüssige Straßenmalkreide mit dem Pinsel aufgetragen werden.", this.experimentRepository.getExperimentById(10)));
                this.instructionRepository.save(new Instruction("Zuerst steckt man die Eisennägel und das Kupfer mit einem Abstand von etwa 5cm in die Zitrone.", this.experimentRepository.getExperimentById(11)));
                this.instructionRepository.save(new Instruction("Anschließend werden die Kabel jeweils an einem der Metalle befestigt, indem der Draht der Kabel darum gewickelt wird.", this.experimentRepository.getExperimentById(11)));
                this.instructionRepository.save(new Instruction("Zum Schluss wird ein Kabel um den unteren Teil des Kopfhörersteckers gewickelt und das andere Kabel um den oberen Teil. Nun kann man es über die Kopfhörer knistern hören.", this.experimentRepository.getExperimentById(11)));
                this.instructionRepository.save(new Instruction("Zuerst klebt man ein Glas mit einem Röllchen aus Klebeband mittig auf den Teller.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Danach legt man zwei Bahnen Alufolie darüber, sodass Glas und Teller bedeckt sind. Die Folie mit Klebeband unten am Teller befestigen.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Nun mit der Schere ein Loch in der Mitte der Glasöffnung in die Folie schneiden. Davon ausgehend ein Kreuz bis zum Innenrand des Glases scheiden.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Die daraus entstandenen Ecken im Glas festkleben.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Jetzt das Backpulver in das Glas geben.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Ein weiteres Glas zur Hälfte mit Essig füllen und die andere Hälfte mit Wasser. Rote Lebensmittelfarbe und einen Spritzer Spülmittel hinzugeben und umrühren.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Den \"Vulkan\" auf eine wasserfeste Unterlage stellen und das Gemisch in den \"Krater\" gießen.", this.experimentRepository.getExperimentById(12)));
                this.instructionRepository.save(new Instruction("Zuerst Gläser mit Wasser füllen und verschiedene Lebensmittelfarben untermischen.", this.experimentRepository.getExperimentById(13)));
                this.instructionRepository.save(new Instruction("Danach stellt man in jedes Glas einen Selleriestengel.", this.experimentRepository.getExperimentById(13)));
                this.instructionRepository.save(new Instruction("Die Gläser sollten dann für zwei bis drei Tage an einem sonnigen Ort stehen.", this.experimentRepository.getExperimentById(13)));
                this.instructionRepository.save(new Instruction("Zuerst ein bisschen Salz und Pfeffer auf den Teller schütten und vermischen.", this.experimentRepository.getExperimentById(14)));
                this.instructionRepository.save(new Instruction("Danach den Plastiklöffel ordentlich am Wollpullover reiben und knapp über Salz und Pfeffer halten.", this.experimentRepository.getExperimentById(14)));
                this.instructionRepository.save(new Instruction("Von drei Korken wird derjenige zur Seite gelegt, der am besten als Verschluss für eine 1 Liter Plastikflasche passt.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Die anderen beiden Korken werden je in vier gleich große Scheiben geschnitten.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Die Flasche zu einem Viertel füllen und auf eine gerade Fläche legen.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Vom Flaschenboden aus mit 5cm Abstand auf beiden Seiten auf Höhe des Wassers eine Punkt setzen. Das gleiche mit 10cm Abstand zur Flaschenöffnung.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Die Flasche wieder entleeren und die Messerspitze über der Kerze erwärmen, um an den angezeichneten Stellen Löcher für die zwei Trinkhalme zu bohren.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Die Trinkhalbe durch die Löcher schieben und so abschneiden, dass sie auf beiden Seiten noch zu 3cm rausstehen. Danach die Löcher mit Heißkleber abdichten.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Auf vier runden Bierdeckeln jeweils die Mitte markieren und Korkscheibe, Bierdeckel und eine weitere Korkscheibe auf eine Holzspieß schieben.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Den Holzspieß durch einen der Trinkhalme schieben und erneut Korkscheibe, Bierdeckel und Korkscheibe drauf schieben. Gegebenenfalls den Holzspieß kürzen.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Die letzten beiden Schritte mit einem weiteren Holzspieß für den anderen Trinkhalm wiederholen.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("In den dritten Korken die Schrauböse drehen und eine Schnur daran knoten.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Aus der Klarsichthülle ein Stück schneiden, das 8cm lang und so breit ist, dass es um den Korken passt.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Nun die Folie an den Korken kleben, sodass die Folie zum Ende etwas schlanker wird.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Draußen eine ebene Teststrecke suchen und 100ml Essig in die Flasche geben. In die Folie am Korken 20g Backpulver füllen.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Jetzt den Korken in die Flasche schieben, sodass sich Essig und Backpulver NICHT vermischen.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Das Auto hinten anheben, sodass das Backpulver in den Essig rieseln, kräftig schütteln und in Startposition setzen.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Den Korken am Faden festhalten, damit er nicht wegfliegt.", this.experimentRepository.getExperimentById(15)));
                this.instructionRepository.save(new Instruction("Zuerst muss man von den zwei gleichen Flaschen die Deckel abschrauben und die Oberseiten der Deckel aneinander kleben.", this.experimentRepository.getExperimentById(16)));
                this.instructionRepository.save(new Instruction("Wenn der Kleber kalt ist, ein dickes Stück Pappe unterlegen und mit Nagel und Hammer mittig in die Deckel ein Loch schlagen.", this.experimentRepository.getExperimentById(16)));
                this.instructionRepository.save(new Instruction("Den Sand durch ein feines Sieb sieben, um sehr feine Sand zu erhalten und dann den Sand in eine der Flaschen füllen.", this.experimentRepository.getExperimentById(16)));
                this.instructionRepository.save(new Instruction("Die Flasche mit dem doppelten Deckel verschließen und die andere Flasche draufschrauben.", this.experimentRepository.getExperimentById(16)));
                this.instructionRepository.save(new Instruction("Nun kann man mit der Stoppuhr messen, wie lang der Sand von der einen in die andere Flasche braucht und gegebenenfalls die Sandmenge anpassen.", this.experimentRepository.getExperimentById(16)));
                this.instructionRepository.save(new Instruction("Zum Schluss noch die Deckel mit Klebeband umwickeln, damit die Sanduhr stabiler wird. Außerdem kann die Sanduhr noch mit Aufklebern verziert werden.", this.experimentRepository.getExperimentById(16)));
                this.instructionRepository.save(new Instruction("Den Pinsel in Zitronensaft tunken und damit auf Papier schreiben. Dabei den Pinsel immer wieder mit Zitronensaft tränken.", this.experimentRepository.getExperimentById(17)));
                this.instructionRepository.save(new Instruction("Um das Geschriebene sichtbar zu machen, das Papier mit Abstand über die Kerzenflamme halten.", this.experimentRepository.getExperimentById(17)));
            }

            if(this.commentRepository.getAllComments().isEmpty()) {
                this.commentRepository.save(new Comment("Das ist zwar nicht explosiv, aber trotzdem interessant. Mein Kind war jedenfalls begeistert.", this.registeredUserRepository.findUserDataByName("Pyro95"), this.experimentRepository.getExperimentById(14)));
                this.commentRepository.save(new Comment("Das ist doch viel zu gefährlich, wenn das brennend durch die Gegend fliegt!", this.registeredUserRepository.findUserDataByName("Supermom89"), this.experimentRepository.getExperimentById(6)));
                this.commentRepository.save(new Comment("Das ist bei mir total schön geworden! Diese Beschreibung hat mich daran erinnert, dass ich das so ähnlich schon mal in meiner Kindheit gemacht habe. Deswegen habe ich eine Faden reingehängt.", this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(8)));
                this.commentRepository.save(new Comment("Das ist doch mal ein sinnvolles Experiment, um Kinder an die Physik ranzuführen.", this.registeredUserRepository.findUserDataByName("Supermom89"), this.experimentRepository.getExperimentById(7)));
                this.commentRepository.save(new Comment("Für bunte Dinge sind meine Tochter und ich immer zu haben!", this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(13)));
                this.commentRepository.save(new Comment("Das war eine totale Sauerei, aber es hat sehr viel Spaß gemacht. Das werden wir bestimmt wiederholen.", this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(12)));
                this.commentRepository.save(new Comment("Meine Tochter war total begeistert, sodass ich es zwei Mal hintereinander vorführen musste. Ich glaube, bei uns gibt es in Zukunft häufiger Tee...", this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(6)));
            }

            if(this.bookmarkRepository.getAllBookmarks().isEmpty()) {
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Pyro95"), this.experimentRepository.getExperimentById(14)));
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Supermom89"), this.experimentRepository.getExperimentById(7)));
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Supermom89"), this.experimentRepository.getExperimentById(8)));
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(8)));
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(10)));
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(13)));
                this.bookmarkRepository.save(new Bookmark(this.registeredUserRepository.findUserDataByName("Erdbeere"), this.experimentRepository.getExperimentById(14)));
            }
        }


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



    /**
     * This function changes the password if old password correct
     *
     * @return String holds success Message
     */
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

    /**
     * This function checks the passed Data from the form with the Data of the database
     *
     * @return String holds success Message
     */
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

    /**
     * This function sets the loggedInUser data so it can be used in this session
     *
     * no return value
     */
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

    /**
     * This function hashes the plainTextPassword so it can be stored safely into the database
     *
     * @return String hashedPassword
     */
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * This function saves the RegisteredUser data into the Database
     *
     * @return String holds success Message
     */
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

    /**
     * This function finds the Bookmarks of a User
     *
     * @return List<Bookmark> holds Bookmarks of User
     */
    public List<Bookmark> showBookmarksOfUser(){
        List<Bookmark> bookmarks = this.registeredUserRepository.findBookmarksOfUser(this.loggedInUser);
        return bookmarks;
    }

    /**
     * This function deletes all relating Data of a Registered User from the database, so the User can be deleted(own Comments, own Ratings, own Bookmarks,
     * own Experiments and all to those Experiments regarding comments, ratings, pictures, bookmarks, instruction, experimentHasMaterials)
     *
     * no return value
     */
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

    /**
     * This function deletes a RegisteredUser in the database
     *
     * no return value
     */
    public void delete(RegisteredUser registeredUser) {
        this.registeredUserRepository.deleteRegisteredUserById(registeredUser.getId());
    }
}

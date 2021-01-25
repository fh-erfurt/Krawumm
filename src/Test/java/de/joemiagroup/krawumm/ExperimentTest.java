package de.joemiagroup.krawumm;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExperimentTest {

    RegisteredUser user1 = new RegisteredUser("user1", "user1@email.de", "12345678");
    RegisteredUser user2 = new RegisteredUser("user2", "user2@email.de", "passwort");
    RegisteredUser user3 = new RegisteredUser("user3", "user3@email.de", "Dörte");
    String picturesComment[] = {"Bild1","Bild2"};
    String material[] = {"Mentos","Cola"};
    String picturesForm[] = {"picture1","picture2"};
    String instruction[] = {"Colaflasche öffnen","Ein Mentos nehmen und in die Flaschenöffnung fallen lassen"};

    Form form1 = new Form(  "Mentos Cola Explosion",
                            material,
                            "Explosion durch chemische Reaktion bei Kontakt von Cola und Mentos!",
                            picturesForm,
                            2,
                            5,
                            5,
                            1,
                            "no video",
                            instruction,
                            user1);

    Experiment experiment = new Experiment(form1);

    Comment comment1 = new Comment("Tolles Experiment", user2 );
    Comment comment2 = new Comment("Habe es drinnen gemacht, mein Haus sieht aus wie Sau", user1);
    Comment comment3 = new Comment("Schlecht!!!!", picturesComment, user3 );

    @BeforeEach
    void setup(){
        experiment.addRating(5);
        experiment.addRating(4);
        experiment.addRating(3);
        experiment.addRating(5);
        experiment.addRating(1);
        experiment.addRating(4);
        experiment.addRating(4);
        experiment.addRating(2);
        experiment.addComment(comment1);
        experiment.addComment(comment2);
        experiment.addComment(comment3);
    }

    @Test
    void calculate_the_right_finalRating() {

        experiment.setFinalRating();
        assertEquals(3.5, experiment.getFinalRating(), "The final Rating should be 3.5");
    }

    @Test
    void check_if_method_getSingleComment_delivers_a_comment() {

        assertEquals(comment1, experiment.getSingleComment(0), "The delivered comment should be comment1(Tolles Experiment, user2");
    }

    @Test
    void check_if_method_removeComment_removes_a_comment() {

        experiment.removeComment(comment2);
        assertEquals(-1, experiment.getComments().indexOf(comment2), "The delivered value should be -1 because the comment is deleted and doesn't exists anymore in the list");
    }

}

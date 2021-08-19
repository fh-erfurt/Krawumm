package de.joemiagroup.krawumm;

import static org.junit.jupiter.api.Assertions.*;
import de.joemiagroup.krawumm.trash.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterdUserTest {
    RegisteredUser user1 = new RegisteredUser("user1", "user1@email.de", "12345678");
    RegisteredUser user2 = new RegisteredUser("user2", "user2@email.de", "passwort");
    RegisteredUser user3 = new RegisteredUser("user3", "user3@email.de", "Dörte");

    String picturesComment[] = {"Bild1","Bild2"};
    String material[] = {"Mentos","Cola"};
    String picturesForm[] = {"picture1","picture2"};
    String instruction[] = {"Colaflasche öffnen","Ein Mentos nehmen und in die Flaschenöffnung fallen lassen"};

    String material1[] = {"Teebeutel","Schere","Feuerzeug"};
    String picturesForm1[] = {"picture1","picture2"};
    String instruction1[] = {"Teebeutel öffnen, Tee entfernen, Teebeutel anzünden!!"};

    Form form1 = new Form("Teebeutel Rakete",
                                material1,
                                "Teebeutel wird angezündet und fliegt in die Luft",
                                picturesForm1,
                                1,
                                10,
                                5,
                                1,
                                "no Video",
                                instruction1,
                                user2);

    Experiment experiment = new Experiment(form1);
    Comment comment2 = new Comment("Habe es drinnen gemacht, mein Haus sieht aus wie Sau", user1);
    @BeforeEach
    void setup(){
        experiment.addComment(comment2);
        main.addUser(user3);
    }
    @Test
    void test_if_sendForm_creates_a_form_and_puts_it_into_the_arraylist() {
        user1.sendForm("Mentos Cola Explosion",
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
        Form form = main.getFormsList().get(0);

        assertEquals(false , main.getFormsList().isEmpty(), "The list shouldn't be empty because we added a form!");
        assertEquals( "Mentos Cola Explosion", form.getName(), "The name should be 'Mentos Cola Explosion' because this form was added!");
    }

    @Test
    void test_if_method_rate_works() {
        user1.rate(experiment, 5);

        assertEquals(5 , experiment.getFinalRating(), "Since only one rating was sent the final rating should be 5");
    }

    @Test
    void test_if_method_comment_works() {
        user1.comment(experiment, "Tolles Experiment! Hat Spass gemacht!",picturesComment);

        assertEquals( user1, experiment.getSingleComment(0).getCommentator(), "Since only one rating was sent the final rating should be 5");
    }

    @Test
    void test_if_method_deleteComment_works_includes_test_for_searchComment() {
        user1.deleteComment(experiment, comment2.getDate());

        assertEquals( -1, experiment.getComments().indexOf(comment2), "Comment2 is deleted and shouldn't exist in the comment list anymore!");
    }

    @Test
    void test_if_method_deleteAcc_works() {
        user3.deleteAcc();

        assertEquals( -1, main.getUserList().indexOf(user3), "User3 is deleted and shouldn't exist in the Users list anymore!");
    }

}


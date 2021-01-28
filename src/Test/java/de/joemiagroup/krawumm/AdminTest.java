package de.joemiagroup.krawumm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AdminTest {


    Admin admin = new Admin("achim","achim@email.com", "gdh$%H3123G");
    RegisteredUser badUser = new RegisteredUser("böserBube","böserBube@email.de","qwertz");

    RegisteredUser user1 = new RegisteredUser("user1", "user1@email.de", "12345678");
    String material[] = {"Mentos","Cola"};
    String picturesForm[] = {"picture1","picture2"};
    String instruction[] = {"Colaflasche öfnnen","Ein Mentos nehmen und in die Flaschenöffnung fallen lassen"};

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


    @BeforeEach

    @Test
    void check_if_deletUser_method_deletes_the_user_from_the_users_list() {

        admin.deleteUser(badUser);
        assertEquals(-1, main.getUserList().indexOf(badUser), "The delivered value should be -1 because the user is deleted and doesn't exists anymore in the list");
    }

    @Test
    void check_if_releaseExperiment_method_works_and_cheks_if_deleteForm_works() {

        admin.releaseExperiment(form1, user1);
        assertEquals(1, main.getExperimentList().size(), "The delivered value should be 1 because the admin released one experiment");
        assertEquals(true, user1.isIsCreator(), "The user has a confirmed experiment and is a creator. So isIsCreator should deliver true!");
        assertEquals(-1, main.getFormsList().indexOf(form1), "The form1 has been set null to get deleted by garbageCollection");
    }
}

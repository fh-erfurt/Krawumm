package de.joemiagroup.krawumm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class mainTest {

    RegisteredUser user1 = new RegisteredUser("user1", "user1@email.de", "12345678");


    String material1[] = {"Mentos","Cola"};
    String picturesForm1[] = {"picture1","picture2"};
    String instruction1[] = {"Colaflasche öffnen","Ein Mentos nehmen und in die Flaschenöffnung fallen lassen"};

    Form form1 = new Form(  "Mentos Cola Explosion",
            material1,
            "Explosion durch chemische Reaktion bei Kontakt von Cola und Mentos!",
            picturesForm1,
            2,
            5,
            5,
            1,
            "no video",
            instruction1,
            user1);
    Experiment experiment1 = new Experiment(form1);

    String material2[] = {"Teebeutel","Schere","Feuerzeug"};
    String picturesForm2[] = {"picture1","picture2"};
    String instruction2[] = {"Teebeutel öffnen", "Tee entfernen", "Teebeutel anzünden!!"};

    Form form2 = new Form("Teebeutel Rakete",
            material2,
            "Teebeutel wird angezündet und fliegt in die Luft",
            picturesForm2,
            1,
            10,
            5,
            1,
            "no Video",
            instruction2,
            user1);
    Experiment experiment2 = new Experiment(form2);

    String material3[] = {"Glas", "Wasser", "Spülmittel", "Büroklammer"};
    String picturesForm3[] = {"picture1","picture2"};
    String instruction3[] = {"Glas bis zum Rand mit Wasser füllen", "Büroklammer vorsichtig auf Wasseroberfläche ablegen", "Spülmittel zu Wasser hinzugeben"};

    Form form3 = new Form("Wasser Oberflächenspannung ",
            material3,
            "Zeigt die Wirkungsweise der Oberflächenspannung von Wasser",
            picturesForm3,
            1,
            7,
            10,
            5,
            "no Video",
            instruction3,
            user1);
    Experiment experiment3 = new Experiment(form3);

    String material4[] = {"Glas", "Wasser", "Tafelsalz", "Löffel", "Lebensmittelfarbe"};
    String picturesForm4[] = {"picture1","picture2"};
    String instruction4[] = {"Glas 1cm hoch mit Tafelsalz füllen", "So viel Wasser hinzugeben, dass das Glas 3-4 cm mit Wasser gefüllt ist", "Lebensmittelfarbe hinzugeben", "rühren", "2 Wochen warm stehen lassen", "Wasser verdunsten lassen"};

    Form form4 = new Form("Kristallwachstum",
            material4,
            "Kristallisaierung das Salzes ist beobachtbar",
            picturesForm4,
            1,
            12,
            15,
            4,
            "no Video",
            instruction4,
            user1);
    Experiment experiment4 = new Experiment(form4);

    String material5[] = {"Glas", "Teelicht", "Streichhölzer"};
    String picturesForm5[] = {"picture1","picture2"};
    String instruction5[] = {"Teelicht anzünden", "Glas darüber stülpen"};

    Form form5 = new Form("Sauerstoffverbrauch von Feuer",
            material5,
            "Zeigt, dass Feuer Sauerstoff benötigt",
            picturesForm5,
            1,
            4,
            2,
            1,
            "no Video",
            instruction5,
            user1);
    Experiment experiment5 = new Experiment(form5);

    @BeforeEach
    void setup(){
            main.addExperiment(experiment1);
            main.addExperiment(experiment2);
            main.addExperiment(experiment3);
            main.addExperiment(experiment4);
            main.addExperiment(experiment5);
    }

    @AfterEach
    void cleanup(){
        main.getExperimentList().remove(experiment1);
        main.getExperimentList().remove(experiment2);
        main.getExperimentList().remove(experiment3);
        main.getExperimentList().remove(experiment4);
        main.getExperimentList().remove(experiment5);
    }

    @Test
    void check_if_method_register_creates_a_new_user() {
        main.register("Angler", "angler@email.de", "12345678");
        assertEquals("Angler", main.getUserList().get(0).getUsername(), "The user on this position should be the user with the name: Angler.");
    }

    @Test
    void check_if_method_register_doesnt_create_a_new_account_if_email_already_exists() {
        main.register("Jäger", "jaeger@email.de", "12345678");
        main.register("Fischer", "jaeger@email.de", "12345678");
            assertNotEquals(3, main.getUserList().size(), "The user should not be added because the email already exists.");
    }

    @Test
    void check_if_method_login_logs_into_the_right_account() {
        main.register("Angler", "angler@email.de", "12345678");
        main.login("angler@email.de","12345678");
        assertEquals(true, main.getUserList().get(0).isLoggedIn(), "The user on this position should be logged in!");
    }

    @Test
    void check_if_method_search_works() {

        ArrayList<Experiment> result1 = main.search("Rakete");
        assertEquals(true, result1.contains(experiment2), "The experiment2(Teebeutel Rakete) should be included in the search results of the keyword: Rakete");
        assertEquals(false, result1.contains(experiment4), "The experiment4(Kristallwachstum) should not be included in the search results of the keyword: Rakete");
        assertEquals(1, result1.size(), "1 experiments fit the serch term: expected 1");
        ArrayList<Experiment> result2 = main.search("Rakete Feuer");
        assertEquals(true, result2.contains(experiment2), "The experiment2(Teebeutel Rakete) should be included in the search results of the keyword: Rakete Feuer");
        assertEquals(true, result2.contains(experiment5), "The experiment5(Sauerstoffverbrauch von Feuer) should be included in the search results of the keyword: Rakete Feuer");
        assertEquals(false, result2.contains(experiment4), "The experiment4(Kristallwachstum) should not be included in the search results of the keyword: Rakete Feuer");
        assertEquals(2, result2.size(), "2 experiments fit the serch term: expected 2");
    }


    @Test
    void check_if_method_compare_works() {

        ArrayList<Experiment> resultList = new ArrayList<Experiment>();
        ArrayList<Experiment> compareList = new ArrayList<Experiment>();
        resultList.add(experiment1);
        resultList.add(experiment2);
        compareList.add(experiment2);
        compareList.add(experiment3);
        ArrayList<Experiment> compare = main.compare(resultList,compareList);
        assertEquals(true, compare.contains(experiment2), "Experiment 2 should be included");
        assertEquals(1, compare.size(), "The size should be 1");

    }

   @Test
    void check_if_method_filter_works() {

        //if filter not set write default value "0"

        ArrayList<Experiment> filter1 = main.filter(7,0,0,0,0);
        assertEquals(true, (filter1.contains(experiment1) && filter1.contains(experiment3) && filter1.contains(experiment5)), "Expected Experiment 1,3,5 because they are for children aged 7 years or younger");

       ArrayList<Experiment> filter2 = main.filter(0,1,0,10,1);
        assertEquals(true, (filter2.contains(experiment2) && filter2.contains(experiment5)), "Expected Experiment 2,5 because they have difficulty one and duration 10 or below and indoor(1)");

        ArrayList<Experiment> filter3 = main.filter(0,0,0,0,0);
        assertEquals(true, (filter3.contains(experiment1) && filter3.contains(experiment2) && filter3.contains(experiment3) && filter3.contains(experiment4) && filter3.contains(experiment5)), "Expected all experiments because it is the default case with no filters which shows all experiments");

    }


}

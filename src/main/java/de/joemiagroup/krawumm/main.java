package de.joemiagroup.krawumm;

import java.util.ArrayList;

/**
 *This class is the class which holds the main functions which can be used by everybody (including none registered users).
 * Functions: register, login, search, compare, filter, setter and getter
 * <br>
 *
 * @author Michel Rost
 *
 * @param experimentList contains all released experiments
 * @param userList contains all registered users
 * @param formsList contains all unconfirmed forms
 */

public class main {

    // main List which holds all existing Experiments
    private static ArrayList<Experiment> experimentList = new ArrayList<Experiment>();

    //main List which holds all existing users
    private static ArrayList<RegisteredUser> userList = new ArrayList<RegisteredUser>();

    // main List which holds all existing Experiments
    private static ArrayList<Form> formsList = new ArrayList<Form>();

    // Setter and Getter
    public static ArrayList<Experiment> getExperimentList() {
        return experimentList;
    }

    public static void addExperiment(Experiment experiment){
        ArrayList<Experiment> experimentList = getExperimentList();
        experimentList.add(experiment);
    }

    public static ArrayList<RegisteredUser> getUserList() {
        return userList;
    }

    public static void addUser(RegisteredUser user){
        ArrayList<RegisteredUser> userList = getUserList();
        userList.add(user);
    }

    public static ArrayList<Form> getFormsList() {
        return formsList;
    }

    public static void addForm(Form form){
        ArrayList<Form> formsList = getFormsList();
        formsList.add(form);
    }

    /**
     *This method creates a new account if there's no other user with this username or email address
     *
     * @param username new username has to be unique
     * @param email new email address has to be unique
     * @param password new password
     *
     * no return value
     */
    // allows new user to create an account
    public static void register(String username, String email, String password){
        boolean error = false;
        for(int i = 0; i< getUserList().size();i++){
            if(getUserList().get(i).getUsername() == username){
                System.out.println("Der Benutzername ist nicht mehr verfügbar!");
                error = true;
                break;
            }
            if(getUserList().get(i).getEmail() == email){
                System.out.println("Die Email ist schon vergeben!");
                error = true;
                break;
            }
        }
        if(error == false){
            RegisteredUser user = new RegisteredUser(username, email,password);
            main.addUser(user);
        }

    }
    /**
     *This method searches the account which belongs to the email and checks if the password is correct.
     *If so it sets loggedIn to true
     *
     * @param email email address of the user who wants to login
     * @param password password of the user who wants to login
     *
     * no return value
     */
    public static void login(String  email, String password){
        RegisteredUser user = null;

        for(int i = 0; i< getUserList().size();i++) {
            if (getUserList().get(i).getEmail() == email) {
                user = getUserList().get(i);
                break;
            }
        }
        if(user.email == email  && user.password == password){
            user.loggedIn = true;
        }
        else {
            System.out.println("Nutzername oder Passwort falsch!");
        }
    }

    /**
     *This function searches for experiments which have one of the keywords in their names and puts them in a list (checks, that every experiment only gets put in once)
     *
     *Returns list of experiments which are included in both lists
     *
     * @param searchString String of keywords which should be searched
     *
     * @return searchResult List of all experiments which contain one searched keyword in their name
     */
    public static ArrayList<Experiment> search(String searchString){
        ArrayList<Experiment> searchResult = new ArrayList<Experiment>();
        String[] keywords = searchString.split(" ");
        for (String keyword : keywords) {
            for (Experiment experiment : main.experimentList) {
               if (experiment.getName().contains(keyword)){
                   boolean isInArray = false;
                   for(int i  = 0; i <searchResult.size(); i++){
                       if(searchResult.get(i) == experiment){
                           isInArray = true;
                           break;
                       }
                   }
                   if(!isInArray){
                       searchResult.add(experiment);
                   }
                }
            }
        }
        if(searchResult.size()==0){
            System.out.println("Keine Suchergebnisse vorhanden!");
        }
        return searchResult;
    }

    // method to shows a specific Experiment
    public void showExperiment(Experiment experiment){
        System.out.println(experiment);
    }

    /**
     *This function is used in the function filter to compare a list of experiments with filters already applied to a list of experiments of a filter which should be added
     *If a experiment of the list with the new filter doesn't exists in the one with the applied filters it doesn't get included into the result list because it doesn't fit the other filters
     *If a experiment of the list with the new filter does exists in the one with the applied filters it stays in the result list(list of applied filters)
     *If a experiment of the list with applied filters doesn't exist in the list with the new filter it gets removed from the result list (list of applied filters) because it doesn't fit the new filter
     *Returns list of experiments which are included in both lists
     *
     * @param resultList list of of experiments with one or mor filters already applied
     * @param compareList list of experiments with a new filter which should be added
     *
     * @return resultList is a list of Experiments which fit all filters(exists in both lists)
     */
    public static ArrayList<Experiment> compare(ArrayList<Experiment> resultList, ArrayList<Experiment> compareList ){
        ArrayList<Experiment> removeList = new ArrayList<Experiment>();
        for (Experiment experimentResult : resultList) {
            boolean existsInList = false;
            for (Experiment experimentCompare : compareList) {
                if (experimentResult == experimentCompare) {
                    existsInList = true;
                    break;
                }
            }
            if (!existsInList){
                //resultList.remove(experimentResult);
                removeList.add(experimentResult);
            }
        }
        for (Experiment removeExperiment : removeList) {
            resultList.remove(removeExperiment);
        }
        return resultList;
    }

    /**
     *This function can filter experiments by age, difficulty, finalRating, duration, indoorOutdoor (multiple filters possible)
     *If all values are 0 the function shows all experiments
     *
     * @param age filters for all experiments for this age or below
     * @param difficulty filters for all experiments with this difficulty or below
     * @param finalRating filters for all experiments with this rating or above
     * @param duration filters for all experiments with this duration or below
     * @param indoorOutdoor filters for all experiments with exactly that inddorOutddor value
     *
     * @return filterList is a list of Experiments which fit the filter configuration
     */
    public static ArrayList<Experiment> filter(int age, int difficulty, float finalRating, float duration, int indoorOutdoor){

        // list which gets filled with the result Experiments
        ArrayList<Experiment> filterList;
        // lists that will get filled with the results of each filter individually
        ArrayList<Experiment> ageList = new ArrayList<Experiment>();
        ArrayList<Experiment> difficultyList = new ArrayList<Experiment>();
        ArrayList<Experiment> finalRatingList = new ArrayList<Experiment>();
        ArrayList<Experiment> durationList = new ArrayList<Experiment>();
        ArrayList<Experiment> indoorOutdoorList = new ArrayList<Experiment>();
        // get list of all Experiments
        ArrayList<Experiment> experimentList = main.experimentList;

        // puts all Experiments, which match the filter if the filter is set, in their specific list
        if (age != 0) {
            for (Experiment experiment : experimentList) {
                if (age >= experiment.age) {
                    ageList.add(experiment);
                }
            }
        }

        if (difficulty != 0) {
            for (Experiment experiment : experimentList) {
                if (difficulty >= experiment.difficulty) {
                    difficultyList.add(experiment);
                }
            }
        }

        if (finalRating != 0) {
            for (Experiment experiment : experimentList) {
                if (finalRating <= experiment.finalRating) {
                    finalRatingList.add(experiment);
                }
            }
        }

        if (duration != 0) {
            for (Experiment experiment : experimentList) {
                if (duration >= experiment.duration) {
                    durationList.add(experiment);
                }
            }
        }

        if (indoorOutdoor != 0) {
            for (Experiment experiment : experimentList) {
                if (indoorOutdoor == experiment.indoorOutdoor) {
                    indoorOutdoorList.add(experiment);
                }
            }
        }


        // compares all existing filter lists and puts all Experiments, which match all set filters, in the return/filterList
        // if age filter was set check if other filters were set
        if (!ageList.isEmpty()){
            filterList = ageList;
            if (!difficultyList.isEmpty()){
                filterList = compare(filterList, difficultyList);
            }
            if (!finalRatingList.isEmpty()){
                filterList = compare(filterList, finalRatingList);
            }
            if (!durationList.isEmpty()){
                filterList = compare(filterList, durationList);
            }
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age filter not set check if difficulty filter was set and check if other filters were set
        else if (!difficultyList.isEmpty()){
            filterList = difficultyList;
            if (!finalRatingList.isEmpty()){
                filterList = compare(filterList, finalRatingList);
            }
            if (!durationList.isEmpty()){
                filterList = compare(filterList, durationList);
            }
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age and difficulty filter not set check if finalRating filter was set and check if other filters were set
        else if (!finalRatingList.isEmpty()){
            filterList = finalRatingList;
            if (!durationList.isEmpty()){
                filterList = compare(filterList, durationList);
            }
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age, difficulty and finalRating filter not set check if duration filter was set and check if other filters were set
        else if (!durationList.isEmpty()){
            filterList = durationList;
            if (!indoorOutdoorList.isEmpty()){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age, difficulty, finalRating and duration filter not set check if indoorOutdoor filter was set
        else if (!indoorOutdoorList.isEmpty()){
            filterList = indoorOutdoorList;
        }
        // no filters set show all Experiments
        else filterList = getExperimentList();
        return filterList;
    }
}


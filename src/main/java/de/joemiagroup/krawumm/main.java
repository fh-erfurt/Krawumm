package de.joemiagroup.krawumm;

import com.sun.tools.attach.AgentInitializationException;

import java.util.ArrayList;

public class main {

    // main List which holds all existing Experiments
    private static ArrayList<Experiment> experimentList = null;

    // Setter and Getter
    public static ArrayList<Experiment> getExperimentList() {
        return experimentList;
    }

    public static void addExperiment(Experiment experiment){
        ArrayList<Experiment> experimentList = getExperimentList();
        experimentList.add(experiment);
    }



    // allows new user to create an account
    public void register(String username, String email, String password){
        RegisteredUser user = new RegisteredUser(username, email,password);
    }

    // allows user to log in to their account
    public void login(RegisteredUser user, String  email, String password, String userName){
        if((user.email == email || user.username == userName) && user.password == password){
            user.loggedIn = true;
        }
        else {
            System.out.println("Nutzername oder Passwort falsch!");
        }
    }

    // function to search through the experimentList and find matching Experiments(name) to given keywords
    public ArrayList<Experiment> search(String searchString){
        ArrayList<Experiment> searchResult = null;
        String[] keywords = searchString.split(" ");
        for (String keyword :keywords) {
            for (Experiment experiment: this.experimentList) {
               if (experiment.name.contains(keyword)){
                   boolean isInArray = false;
                   for(int i  = 0; i <searchResult.size(); i++){
                       if(!(searchResult.get(i) == experiment)){
                       }
                       else{
                           isInArray = true;
                           break;
                       }
                   }
                   if(!isInArray){
                       searchResult.add(experiment);
                   }
                }
               else{
                   System.out.println("Keine Suchergebnisse vorhanden!");
               }
            }
        }
        return searchResult;
    }

    // method to show a specific Experiment
    public void showExperiment(Experiment experiment){
        System.out.println(experiment);
    }

    // function needed for filter function to compare two existing lists and only maintain the values existing in both of them
    public ArrayList<Experiment> compare(ArrayList<Experiment> resultList, ArrayList<Experiment> compareList ){
        for (Experiment experimentResult: resultList) {
            boolean existsInList = false;
            for (Experiment experimentCompare: compareList) {
                if (experimentResult == experimentCompare) {
                    existsInList = true;
                    break;
                }
            }
            if (!existsInList){
                resultList.remove(experimentResult);
            }
        }
        return resultList;
    }

    // creates a list which only contains the Experiments matching the filters
    public ArrayList<Experiment> filter(int age, int difficulty, float finalRating, float duration, int indoorOutdoor){

        // list which gets filled with the result Experiments
        ArrayList<Experiment> filterList = null;
        // lists that will get filled with the results of each filter individually
        ArrayList<Experiment> ageList = null;
        ArrayList<Experiment> difficultyList = null;
        ArrayList<Experiment> finalRatingList = null;
        ArrayList<Experiment> durationList = null;
        ArrayList<Experiment> indoorOutdoorList = null;
        // get list of all Experiments
        ArrayList<Experiment> experimentList = getExperimentList();

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
                if (finalRating >= experiment.finalRating) {
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
        if (ageList != null){
            filterList = ageList;
            if (difficultyList != null){
                filterList = compare(filterList, difficultyList);
            }
            if (finalRatingList != null){
                filterList = compare(filterList, finalRatingList);
            }
            if (durationList != null){
                filterList = compare(filterList, durationList);
            }
            if (indoorOutdoorList != null){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age filter not set check if difficulty filter was set and check if other filters were set
        else if (difficultyList != null){
            filterList = difficultyList;
            if (finalRatingList != null){
                filterList = compare(filterList, finalRatingList);
            }
            if (durationList != null){
                filterList = compare(filterList, durationList);
            }
            if (indoorOutdoorList != null){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age and difficulty filter not set check if finalRating filter was set and check if other filters were set
        else if (finalRatingList != null){
            filterList = finalRatingList;
            if (durationList != null){
                filterList = compare(filterList, durationList);
            }
            if (indoorOutdoorList != null){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age, difficulty and finalRating filter not set check if duration filter was set and check if other filters were set
        else if (durationList != null){
            filterList = durationList;
            if (indoorOutdoorList != null){
                filterList = compare(filterList, indoorOutdoorList);
            }
        }
        // age, difficulty, finalRating and duration filter not set check if indoorOutdoor filter was set
        else if (indoorOutdoorList != null){
            filterList = indoorOutdoorList;
        }
        // no filters set show all Experiments
        else filterList = getExperimentList();

        return filterList;
    }

}


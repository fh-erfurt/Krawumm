package de.joemiagroup.krawumm;

/**
 * this class is a subclass of ExperimentData gets attributes(+setter and getter)(same as used in Experiment)
 * instanze of this class used by RegisterdUser to create a Experiment. If confirmed by the admin get turned into an Experiment.
 * <br>
 *
 * @author Johannes Otto
 *
 * @param name is the name of the experiment
 * @param material is an array which contains the Materials needed for the experiments
 * @param description contains a short description text for the experiment (max 500 characters)
 * @param pictures contains pictures of the experiment
 * @param indoorOutdoor is 1 for indoor and 2 for outdoor
 * @param age holds the minimum age the child should be to do the experiment
 * @param duration holds the estimated time it takes to finish the experiment
 * @param difficulty holds a difficulty value between 1 and 5 (5 is hardest)
 * @param video contains vidoes of the experiment
 * @param instruction contains a instruction of the steps to do the experiment
 * @param creator holds a reference to the RegisteredUser who uploaded this experiment
 *
 * @see de.joemiagroup.krawumm.ExperimentData
 */

public class Form extends ExperimentData{

    // Constructor sets the variables to input data
    public Form(String name,String[] material,String description,String[] pictures,int indoorOutdoor,int age,float duration,int difficulty,String video,String[] instruction,RegisteredUser creator){
        this.name           = name;
        this.material       = material;
        this.description    = description;
        this.pictures       = pictures;
        this.indoorOutdoor  = indoorOutdoor;
        this.age            = age;
        this.duration       = duration;
        this.difficulty     = difficulty;
        this.video          = video;
        this.instruction    = instruction;
        this.creator        = creator;
    }
}

package de.joemiagroup.krawumm;

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

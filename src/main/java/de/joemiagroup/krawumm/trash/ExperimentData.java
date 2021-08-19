package de.joemiagroup.krawumm.trash;

/**
 * This class describes the attributes used in Form and Experiment and their setter and getter functions
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
 */

public class ExperimentData {

    // attributes
    protected String name;
    protected String[] material;
    protected String description;
    protected String[] pictures;
    // 1=indoor 2=outdoor
    protected int indoorOutdoor;
    protected int age;
    protected float duration;
    protected int difficulty;
    protected String video;
    protected String[] instruction;
    protected RegisteredUser creator;

    // Setter and Getter

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }

    public String[] getMaterial() {
            return material;
        }

    public void setMaterial(String[] material) {
            this.material = material;
        }

    public String getDescription() {
            return description;
        }

    public void setDescription(String description) {
        //max 500 letters

        if (description.length() <= 500) {
            this.description = description;
        } else {
            System.out.println("Die Eingabe ist zu lang. Zusammenfassungen dürfen maximal aus 500 Zeichen bestehen.");
        }
    }


    public int getIndoorOutdoor() {
        return indoorOutdoor;
    }

    public void setIndoorOutdoor(int indoorOutdoor) { this.indoorOutdoor = indoorOutdoor;
    }

    public String[] getPictures() {
        return pictures;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {

        if((difficulty <= 5) && (difficulty >= 1)){
            this.difficulty = difficulty;
        } else {System.out.println("Schwierigkeitswert außerhalb des gültigen Wertes (zwischen 1 und 5)!");}
    }


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String[] getInstruction() {
        return instruction;
    }

    public void setInstruction(String[] instruction) {
        this.instruction = instruction;
    }

    public RegisteredUser getCreator() {
        return creator;
    }

    public void setCreator(RegisteredUser creator) {
        this.creator = creator;
    }
}


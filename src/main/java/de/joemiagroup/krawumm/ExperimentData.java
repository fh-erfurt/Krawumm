package de.joemiagroup.krawumm;

// Class holds attributes needed in Form and Experiment
public class ExperimentData {

    // attributes
    protected String name;
    protected String[] material;
    protected String description;
    protected String[] pictures;
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

    public void setIndoorOutdoor(int indoorOutdoor) {
        this.indoorOutdoor = indoorOutdoor;
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
        this.difficulty = difficulty;
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


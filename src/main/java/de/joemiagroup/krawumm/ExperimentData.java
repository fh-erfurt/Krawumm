package de.joemiagroup.krawumm;

public class ExperimentData {


        private String name;
        private String[] material;
        private String description;
        private String[] pictures;
        private String[] categories;
        private int age;
        private float duration;
        private int difficulty;
        private String video;
        private String[] instruction;
        private RegisteredUser creator;

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
            this.description = description;
            //max 500 letters
        }


        public String[] getPictures() {
            return pictures;
        }

        public void setPictures(String[] pictures) {
            this.pictures = pictures;
        }


        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
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


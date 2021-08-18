package de.joemiagroup.krawumm.web.experiments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ExperimentDataView {
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommentDataView {
        public String userName;
        public Timestamp createdAt;
        public String text;
        public List<String> pictureComment;
    }

    long id;
    public String title;
    public String creator;
    public String description;
    public int age;
    public int difficulty;
    public float duration;
    public String location;
    public float rating1;
    public int rating2;
    public List<String> picture;
    public String video;
    public List<String> instructions;
    public List<String> materials;
    public List<CommentDataView> comments;
}

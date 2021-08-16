package de.joemiagroup.krawumm.web.experiments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExperimentDataView {
    public String title;
    public String creator;
    public String description;
    public int age;
    public int difficulty;
    public float duration;
    public String location;
    public float rating;
    public String picture;
}

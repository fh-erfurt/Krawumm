package de.joemiagroup.krawumm.web.experiments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterView {
    private int minRating;
    private int maxDifficulty;
    private String location;
    private int minAge;
    private float maxTime;
}

package de.joemiagroup.krawumm.web.experiments;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is a template for the filters
 * <br>
 *
 * @author Michel Rost
 *
 */


@Getter
@Setter
public class FilterView {
    private int minRating;
    private int maxDifficulty;
    private String location;
    private int minAge;
    private float maxTime;
}

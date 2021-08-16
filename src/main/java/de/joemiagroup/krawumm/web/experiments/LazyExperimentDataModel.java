package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.IndoorOutdoor;
import de.joemiagroup.krawumm.domains.TrueFalse;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(staticName = "of")
public class LazyExperimentDataModel extends LazyDataModel<Experiment> {
    private final ExperimentRepository experimentRepository;
    private final RatingRepository ratingRepository;
    private final PicturesRepository picturesRepository;

    private final List<Experiment> cache = new ArrayList<>();

    @Getter
    @Setter
    private ExperimentDataView selected;

    @Override
    public List<Experiment> load(int page, int size, Map<String, SortMeta> sorts, Map<String, FilterMeta> filters) {
        cache.clear();
        cache.addAll(experimentRepository.findByParameters(page, size, filters, sorts));
        setRowCount((int) experimentRepository.countByParameters(filters));

        return cache;
    }

    public List<Experiment> loadAllExperiments() {
        List<Experiment> experiments = experimentRepository.getAllExperiments(TrueFalse.T);

        return experiments;
    }

    public String translateIndoorOutdoor(IndoorOutdoor toTranslate) {
        if (toTranslate.toString().equals("O")) {
            return "draußen";
        }
        return "drinnen";
    }

    public List<ExperimentDataView> gatherData() {
        List<ExperimentDataView> results = new ArrayList<>();

        List<Experiment> experimentList = this.loadAllExperiments();

        for (Experiment e : experimentList) {
            String loc = this.translateIndoorOutdoor(e.getIndoorOutdoor());
            float rating = ratingRepository.getRatingForExperiment(e);
            List<String> picturesNameList = picturesRepository.getPicturesForExperiment(e);

            results.add(new ExperimentDataView(e.getId(),
                                               e.getExperimentName(),
                                               e.getRegisteredUser().getUserName(),
                                               e.getDescription(),
                                               e.getAge(),
                                               e.getDifficulty(),
                                               e.getDuration(),
                                               loc,
                                               rating,
                                               picturesNameList.get(0) ));
        }

        return results;
    }
}

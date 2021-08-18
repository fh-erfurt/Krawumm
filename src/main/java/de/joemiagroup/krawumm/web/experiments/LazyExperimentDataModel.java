package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
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
import java.lang.Math;

@RequiredArgsConstructor(staticName = "of")
public class LazyExperimentDataModel extends LazyDataModel<Experiment> {
    private final ExperimentRepository experimentRepository;
    private final RatingRepository ratingRepository;
    private final PicturesRepository picturesRepository;
    private final InstructionRepository instructionRepository;

    private final List<Experiment> cache = new ArrayList<>();

    @Override
    public List<Experiment> load(int page, int size, Map<String, SortMeta> sorts, Map<String, FilterMeta> filters) {
        cache.clear();
        cache.addAll(experimentRepository.findByParameters(page, size, filters, sorts));
        setRowCount((int) experimentRepository.countByParameters(filters));

        return cache;
    }

    @Getter
    @Setter
    private ExperimentDataView selected;
    @Getter
    @Setter
    private ExperimentDataView newData;

    //Own methods
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

        results = this.putDataForExperimentsTogether(experimentList);

        return results;
    }

    public List<ExperimentDataView.CommentDataView> gatherCommentDataForExperiment(Experiment experiment) {
        List<ExperimentDataView.CommentDataView> commentData = new ArrayList<>();

        List<Comment> commentList = experimentRepository.getCommentsForExperiment(experiment);
        for (Comment c : commentList) {
            List<String> picturesList = picturesRepository.getPicturesForComment(c);
            commentData.add(new ExperimentDataView.CommentDataView(c.getRegisteredUser().getUserName(),
                                                                   c.getCreatedAt(),
                                                                   c.getText(),
                                                                   picturesList));
        }

        return commentData;
    }

    public List<ExperimentDataView> usingSearch(String search) {
        List<ExperimentDataView> results = new ArrayList<>();

        List<Experiment> experimentList = experimentRepository.lookForStringInExperimentName(search);

        results = this.putDataForExperimentsTogether(experimentList);

        return results;
    }

    private List<ExperimentDataView> putDataForExperimentsTogether(List<Experiment> experimentList) {
        List<ExperimentDataView> dataList = new ArrayList<>();

        for (Experiment e : experimentList) {
            String loc = this.translateIndoorOutdoor(e.getIndoorOutdoor());
            float rating = ratingRepository.getRatingForExperiment(e);
            List<String> picturesNameList = picturesRepository.getPicturesForExperiment(e);
            List<String> instructions = instructionRepository.getInstructionsForExperiment(e);;
            dataList.add(new ExperimentDataView(e.getId(),
                    e.getExperimentName(),
                    e.getRegisteredUser().getUserName(),
                    e.getDescription(),
                    e.getAge(),
                    e.getDifficulty(),
                    e.getDuration(),
                    loc,
                    rating,
                    Math.round(rating),
                    picturesNameList,
                    e.getVideo(),
                    instructions,
                    this.gatherCommentDataForExperiment(e)));
        }

        return dataList;
    }
}

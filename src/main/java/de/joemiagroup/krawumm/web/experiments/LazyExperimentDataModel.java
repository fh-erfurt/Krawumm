package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.repositories.bookmarks.BookmarkRepository;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import de.joemiagroup.krawumm.web.registeredUsers.RegisteredUserView;
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
import java.util.Objects;

@RequiredArgsConstructor(staticName = "of")
public class LazyExperimentDataModel extends LazyDataModel<Experiment> {
    private final ExperimentRepository experimentRepository;
    private final RatingRepository ratingRepository;
    private final PicturesRepository picturesRepository;
    private final InstructionRepository instructionRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;

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
    private FilterView filter;

    @Getter
    @Setter
    private ExperimentDataView newData;
    @Getter
    @Setter
    private String commentText;

    @Getter
    @Setter
    private int rating;


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
            List<String> instructions = instructionRepository.getInstructionsForExperiment(e);
            List<String> materials = experimentRepository.getMaterialsForExperiment(e);
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
                    materials,
                    this.gatherCommentDataForExperiment(e)));
        }

        return dataList;
    }

    public void writeComment(long experimentId, RegisteredUser user) {
        if (Objects.isNull(commentText)) {
            return;
        }

        if (Objects.isNull(user)) {
            return;
        }

        Comment comment = new Comment();

        comment.setText(commentText);

        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        comment.setExperiment(experiment);

        comment.setRegisteredUser(user);

        commentRepository.save(comment);

        commentText = null;
        return;
    }

    public List<ExperimentDataView> useFiltersOnExperimentList () {
        List<Experiment> experimentList = experimentRepository.useFilterOnAllExperiments(this.filter);
        List<ExperimentDataView> dataList = this.putDataForExperimentsTogether(experimentList);

        return dataList;
    }

    public void createExperiment(RegisteredUser user){
        Experiment experiment = new Experiment();
        experiment.setExperimentName(getNewData().getTitle());
        experiment.setDescription(getNewData().getDescription());
        experiment.setRegisteredUser(user);
        experiment.setDifficulty(getNewData().getDifficulty());
        experiment.setVideo(getNewData().getVideo());
        experiment.setAge(getNewData().getAge());
        experiment.setDuration(getNewData().getDuration());
        experiment.setIsReleased(TrueFalse.F);
        if(getNewData().getLocation() == "indoor") experiment.setIndoorOutdoor(IndoorOutdoor.I);
        else experiment.setIndoorOutdoor(IndoorOutdoor.O);
        experimentRepository.save(experiment);
    }

    public Experiment getLastInsertedExperiment(){
        Experiment experiment = experimentRepository.getLastInsertedExperiment();
        return experiment;
    }

    public int getRatingOfExperiment(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        int rating = this.experimentRepository.getRatingOfExperimentForUser(user ,experiment);
        return rating;
    }

    public void rateExperiment(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        if(this.rating > 0){
            Rating rating = new Rating();
            rating.setRatingValue(this.rating);
            rating.setExperiment(experiment);
            rating.setRegisteredUser(user);
            this.ratingRepository.save(rating);
        }
    }

    public boolean getBookmarkOfExperiment(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        boolean bookmarkExists = this.experimentRepository.getBookmarkOfExperiment(user ,experiment);
        return bookmarkExists;
    }

    public void deleteBookmark(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        Bookmark bookmark = this.experimentRepository.getBookmarkDataOfExperiment(user ,experiment);
        this.bookmarkRepository.delete(bookmark);
    }

    public void createBookmark(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        Bookmark bookmark = new Bookmark();
        bookmark.setRegisteredUser(user);
        bookmark.setExperiment(experiment);
        this.bookmarkRepository.save(bookmark);
    }

}

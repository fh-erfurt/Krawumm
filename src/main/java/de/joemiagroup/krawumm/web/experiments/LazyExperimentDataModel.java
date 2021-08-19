package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.repositories.bookmarks.BookmarkRepository;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import de.joemiagroup.krawumm.repositories.experimenthasmaterials.ExperimentHasMaterialRepository;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import de.joemiagroup.krawumm.repositories.materials.MaterialRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class communicates with the repositories to secure the database
 * <br>
 *
 * @author Michel Rost
 *
 */

@RequiredArgsConstructor(staticName = "of")
public class LazyExperimentDataModel extends LazyDataModel<Experiment> {
    private final ExperimentRepository experimentRepository;
    private final RatingRepository ratingRepository;
    private final PicturesRepository picturesRepository;
    private final InstructionRepository instructionRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RegisteredUserRepository registeredUserRepository;
    private final ExperimentHasMaterialRepository experimentHasMaterialRepository;
    private final MaterialRepository materialRepository;

    private final List<Experiment> cache = new ArrayList<>();

    /**
     * This function loads experiments that match the parameters
     *
     * @param page
     * @param size
     * @param sorts
     * @param filters
     *
     * @return cache
     */
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
    private FilterView filter = new FilterView();

    @Getter
    @Setter
    private ExperimentDataView newData = new ExperimentDataView();
    @Getter
    @Setter
    private String commentText;

    @Getter
    @Setter
    private int rating;


    //Own methods
    /**
     * This function loads all experiments
     *
     *@return all experiments
     */
    public List<Experiment> loadAllExperiments() {
        List<Experiment> experiments = experimentRepository.getAllExperiments(TrueFalse.T);

        return experiments;
    }

    /**
     * This function "translates" the values for indoor and outdoor
     *
     * @param toTranslate value of indoor or outdoor that should be translated
     *
     * @return string value for indoor and outdoor
     */
    public String translateIndoorOutdoor(IndoorOutdoor toTranslate) {
        if (toTranslate.toString().equals("O")) {
            return "draußen";
        }
        return "drinnen";
    }

    /**
     * This function gathers data for the released experiments
     *
     * @return all experiments
     */
    public List<ExperimentDataView> gatherData() {
        List<ExperimentDataView> results = new ArrayList<>();

        List<Experiment> experimentList = this.loadAllExperiments();

        results = this.putDataForExperimentsTogether(experimentList);

        return results;
    }

    /**
     * This function gathers all data for the comments of a specific experiment
     *
     * @param experiment specific experiment
     *
     * @return all comments of an experiment
     */
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

    /**
     * This function creates a list with all experiments that match the search string
     *
     * @param search search string
     *
     * @return all data for the experiments that match the search string
     */
    public List<ExperimentDataView> usingSearch(String search) {
        List<ExperimentDataView> results = new ArrayList<>();

        List<Experiment> experimentList = experimentRepository.lookForStringInExperimentName(search);

        results = this.putDataForExperimentsTogether(experimentList);

        return results;
    }

    /**
     * This function creates a list with edited data of the experiments that should be shown on the website
     *
     * @param experimentList list of experiments
     *
     * @return data of the experiments
     */
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

    /**
     * This function creates a list with edited data of the bookmarks
     *
     * @param bookmarks list of bookmarks
     *
     * @return data of the bookmarks
     */
    public List<ExperimentDataView> putDataForBookmarksTogether(List<Bookmark> bookmarks) {
        List<Experiment> experimentList = new ArrayList<>();
        for (Bookmark b : bookmarks) {
            experimentList.add(b.getExperiment());
        }
        return this.putDataForExperimentsTogether(experimentList);
    }

    /**
     * This function is used to save a comment to the database
     *
     * @param experimentId primary key of an experiment
     * @param user specific user
     *
     * no return values
     */
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

    /**
     * This function creates a list with edited data of the experiments that match the filters
     *
     * @return data of experiments that match the filters
     */
    public List<ExperimentDataView> useFiltersOnExperimentList () {
        List<Experiment> experimentList = experimentRepository.useFilterOnExperiment(this.filter);
        List<ExperimentDataView> dataList = this.putDataForExperimentsTogether(experimentList);


        return dataList;
    }

    /**
     * This function is used to save a new experiment to the database that needs to be released
     *
     * @param user specific user
     *
     * no return value
     */
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
        if(getNewData().getLocation() == "Drinnen") experiment.setIndoorOutdoor(IndoorOutdoor.I);
        else experiment.setIndoorOutdoor(IndoorOutdoor.O);
        this.experimentRepository.save(experiment);
    }

    /**
     * This function is used to save an instruction to the database
     *
     * @param experiment specific experiment
     * @param text instruction text
     *
     * no return value
     */
    public void createInstructions(Experiment experiment, String text) {
        Instruction instruction = new Instruction();
        instruction.setExperiment(experiment);
        instruction.setText(text);
        System.out.println(instruction.getId());
        this.instructionRepository.save(instruction);
    }

    /**
     * This function is used to save a picture to the database
     *
     * @param experiment specific experiment
     * @param text picture url
     *
     * no return value
     */
    public void createPicture(Experiment experiment, String text) {
        Pictures pictures = new Pictures();
        pictures.setExperiment(experiment);
        pictures.setPictureName(text);
        this.picturesRepository.save(pictures);
    }

    /**
     * This function looks if a material is already in the database
     *
     * @param text instruction text
     *
     * @return material
     */
    public List<Material> findMaterial(String text) {
        return this.materialRepository.findMaterialByName(text);
    }

    /**
     * This function is used to save which material belongs to which experiment to the database
     *
     * @param experiment specific experiment
     * @param material material
     *
     * no return value
     */
    public void createExperimentHasMaterial(Experiment experiment, Material material) {
        ExperimentHasMaterial ehm = new ExperimentHasMaterial();
        ehm.setExperiment(experiment);
        ehm.setMaterial(material);
        this.experimentHasMaterialRepository.save(ehm);
    }

    /**
     * This function is used to save a material to the database
     *
     * @param text material
     *
     * no return value
     */
    public void createMaterial(String text) {
        Material material = new Material();
        material.setMaterialName(text);
        this.materialRepository.save(material);
    }

    /**
     * This function looks for the material that was inserted last
     *
     * @return material
     */
    public Material findLastInsertedMaterial() {
        return this.materialRepository.getLastInsertedMaterial();
    }

    /**
     * This function looks for the experiment that was inserted last
     *
     * @return experiment
     */
    public Experiment getLastInsertedExperiment(){
        Experiment experiment = experimentRepository.getLastInsertedExperiment();
        return experiment;
    }

    /**
     * This function looks for the rating for an experiment by a specific user
     *
     * @param user specific user
     * @param experimentId primary key of experiment
     *
     * @return rating
     */
    public int getRatingOfExperiment(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        int rating = this.experimentRepository.getRatingOfExperimentForUser(user ,experiment);
        return rating;
    }

    /**
     * This function is used to save a rating to the database
     *
     * @param user specific user
     * @param experimentId primary key of experiment
     *
     * no return value
     */
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

    /**
     * This function looks whether a user has a bookmark for a specific experiment
     *
     * @param user specific user
     * @param experimentId primary key of experiment
     *
     * @return boolean whether bookmark exists or not
     */
    public boolean getBookmarkOfExperiment(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        boolean bookmarkExists = this.experimentRepository.getBookmarkOfExperiment(user ,experiment);
        return bookmarkExists;
    }

    /**
     * This function deletes a bookmark by a user for an experiment
     *
     * @param user specific user
     * @param experimentId primary key of experiment
     *
     * no return value
     */
    public void deleteBookmark(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        Bookmark bookmark = this.experimentRepository.getBookmarkDataOfExperiment(user ,experiment);
        this.bookmarkRepository.delete(bookmark);
    }

    /**
     * This function is used to save a bookmark to the database
     *
     * @param user specific user
     * @param experimentId primary key of experiment
     *
     * no return value
     */
    public void createBookmark(RegisteredUser user, long experimentId){
        Experiment experiment = this.experimentRepository.getExperimentById(experimentId);
        Bookmark bookmark = new Bookmark();
        bookmark.setRegisteredUser(user);
        bookmark.setExperiment(experiment);
        this.bookmarkRepository.save(bookmark);
    }

    /**
     * This function looks for the unreleased experiments
     *
     * @return experiments
     */
    public List<ExperimentDataView> getUnreleasedExperiments(){
        List<Experiment> experimentList = this.experimentRepository.getAllExperiments(TrueFalse.F);
        List<ExperimentDataView> experiments = this.putDataForExperimentsTogether(experimentList);
        return experiments;
    }

    /**
     * This function looks for the experiments by a specific user
     *
     * @param user specific user
     *
     * @return experiments by a specific user
     */
    public List<ExperimentDataView> getExperimentsCreatedByUser(RegisteredUser user) {
        List<ExperimentDataView> results = new ArrayList<>();
        List<Experiment> experiments = this.experimentRepository.getExperimentsForUser(user);
        results = this.putDataForExperimentsTogether(experiments);

        return results;
    }

    /**
     * This function checks whether an experiment is released or not
     *
     * @param experimentId primary key of experiment
     *
     * @return boolean whether an experiment is released or not
     */
    public boolean experimentIsReleased(long experimentId){
        boolean result = this.experimentRepository.isExperimentReleased(experimentId);
        return result;
    }

    /**
     * This function changes the column is_released for a selected experiment released
     *
     * no return value
     */
    public void releaseSelectedExperiment(){
        RegisteredUser creator = this.registeredUserRepository.findUserDataByName(this.getSelected().getCreator());
        Experiment experiment = new Experiment();
        experiment.setId(this.getSelected().getId());
        experiment.setExperimentName(this.getSelected().getTitle());
        experiment.setDescription(this.getSelected().getDescription());
        experiment.setRegisteredUser(creator);
        if(this.getSelected().getLocation() == "draußen"){
            experiment.setIndoorOutdoor(IndoorOutdoor.O);
        } else experiment.setIndoorOutdoor(IndoorOutdoor.I);
        experiment.setAge(this.getSelected().getAge());
        experiment.setDifficulty(this.getSelected().getDifficulty());
        experiment.setDuration(this.getSelected().getDuration());
        experiment.setVideo(this.getSelected().getVideo());
        experiment.setIsReleased(TrueFalse.T);
        this.experimentRepository.save(experiment);
    }

    /**
     * This function is used to delete an experiment
     *
     * @param id primary key of experiment
     *
     * no return value
     */
    public void deleteExperimentData(long id){

        Experiment experiment = this.experimentRepository.getExperimentById(id);

        //delete Comments of experiment
        List<Comment> comments = this.experimentRepository.getCommentsForExperiment(experiment);
        for(Comment c : comments){
            this.commentRepository.delete(c);
        }
        //delete Ratings of experiment
        List<Rating> ratings = this.experimentRepository.getRatingsForExperiment(experiment);
        for(Rating r : ratings){
            this.ratingRepository.delete(r);
        }
        //delete Instructions of Experiment
        List<Instruction> instructions = this.experimentRepository.getInstructionsForExperiment(experiment);
        for(Instruction i : instructions){
            this.instructionRepository.delete(i);
        }
        //delete Pictures of Experiment
        List<Pictures> pictures = this.experimentRepository.getPicturesForExperiment(experiment);
        for(Pictures p : pictures){
            this.picturesRepository.delete(p);
        }
        //delete Experiment has Material of experiment
        List<ExperimentHasMaterial> experimentHasMaterials = this.experimentRepository.getExperimentHasMaterialsForExperiment(experiment);
        for(ExperimentHasMaterial ehs : experimentHasMaterials){
            this.experimentHasMaterialRepository.delete(ehs);
        }
        //delete Bookmarks of experiment
            List<Bookmark> bookmarks = this.experimentRepository.getBookmarksForExperiment(experiment);
            for(Bookmark b : bookmarks){
                this.bookmarkRepository.delete(b);
            }
        //delete experiment
        this.experimentRepository.delete(experiment);
    }

}

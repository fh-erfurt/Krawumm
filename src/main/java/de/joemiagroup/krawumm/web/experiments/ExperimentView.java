package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Material;
import de.joemiagroup.krawumm.domains.RegisteredUser;
import de.joemiagroup.krawumm.repositories.bookmarks.BookmarkRepository;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import de.joemiagroup.krawumm.repositories.experimenthasmaterials.ExperimentHasMaterialRepository;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import de.joemiagroup.krawumm.repositories.materials.MaterialRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import de.joemiagroup.krawumm.repositories.registeredUsers.RegisteredUserRepository;
import de.joemiagroup.krawumm.web.BaseView;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the view that shows the experiments
 * <br>
 *
 * @author Michel Rost
 *
 */

@ManagedBean("experimentView")
@ViewScoped
@Getter
@Setter
public class ExperimentView extends BaseView<Experiment> {
    @Autowired
    public ExperimentView(final ExperimentRepository repository, final RatingRepository ratingRepository,
                          final PicturesRepository picturesRepository, final InstructionRepository instructionRepository,
                          final CommentRepository commentRepository, final BookmarkRepository bookmarkRepository,
                          final RegisteredUserRepository registeredUserRepository, final ExperimentHasMaterialRepository experimentHasMaterialRepository,
                          final MaterialRepository materialRepository) {
        this.lazyExperimentDataModel = LazyExperimentDataModel.of(repository, ratingRepository, picturesRepository, instructionRepository,
                                                                  commentRepository, bookmarkRepository, registeredUserRepository, experimentHasMaterialRepository,
                                                                  materialRepository);
        this.data = lazyExperimentDataModel.gatherData();
    }


    private List<String> instructionText;
    private String instruction = "";

    private List<String> materialText;
    private String material = "";

    private List<String> pictureText;
    private String picture = "";


    private List<ExperimentDataView> data;
    private String search = "";
    private int numberInstruction = 1;
    private int numberMaterial = 1;
    private int numberPicture = 1;


    private final LazyExperimentDataModel lazyExperimentDataModel;

    /**
     * This function initializes array lists for instructions, material and pictures for the experiment creation with program start
     *
     * no return value
     */
    @PostConstruct
    public void init() {
        instructionText = new ArrayList<>();
        materialText = new ArrayList<>();
        pictureText = new ArrayList<>();
    }

    /**
     * This function adds an instruction step to the creation of an experiment
     *
     * no return value
     */
    public void incrementInstruction() {
        numberInstruction++;
        instructionText.add(this.instruction);
        this.instruction = "";
    }

    /**
     * This function adds an material to the creation of an experiment
     *
     * no return value
     */
    public void incrementMaterial() {
        numberMaterial++;
        materialText.add(this.material);
        this.material = "";
    }

    /**
     * This function adds an picture to the creation of an experiment
     *
     * no return value
     */
    public void incrementPicture() {
        numberPicture++;
        pictureText.add(this.picture);
        this.picture = "";
    }

    /**
     * This function filters the experiments
     *
     * no return value
     */
    public void useFilters() {
        this.data = this.lazyExperimentDataModel.useFiltersOnExperimentList();
    }

    public void resetEverything() {
        this.data = this.lazyExperimentDataModel.gatherData();
        this.lazyExperimentDataModel.setFilter(new FilterView());
    }

    public void onExperimentSelect(SelectEvent<ExperimentDataView> event) {
        this.lazyExperimentDataModel.setSelected(event.getObject());
    }

    public void searchForStringInName () {
        this.data = lazyExperimentDataModel.usingSearch(search);
        this.search = "";
    }

    public void writeComment (long experimentId, RegisteredUser user) {
        this.lazyExperimentDataModel.writeComment(experimentId, user);
        this.data = lazyExperimentDataModel.gatherData();
    }

    public void onClickCreateExperiment(RegisteredUser user){
        this.editMode.set(false);
        this.lazyExperimentDataModel.createExperiment(user);
        Experiment experiment = this.lazyExperimentDataModel.getLastInsertedExperiment();
        for (String t : this.instructionText) {
            this.lazyExperimentDataModel.createInstructions(experiment, t);
        }
        for (String t : this.pictureText) {
            if (t != "") this.lazyExperimentDataModel.createPicture(experiment, t);
        }
        for (String t : this.materialText) {
            List<Material> material = this.lazyExperimentDataModel.findMaterial(t);
            if (material.size() == 1) {
                this.lazyExperimentDataModel.createExperimentHasMaterial(experiment, material.get(0));
            } else {
                this.lazyExperimentDataModel.createMaterial(t);
                Material m = this.lazyExperimentDataModel.findLastInsertedMaterial();
                this.lazyExperimentDataModel.createExperimentHasMaterial(experiment, m);
            }
        }
        this.renderMessage(FacesMessage.SEVERITY_INFO, "Formular wurde eingereicht.");
    }

    public void releaseExperiment(){
        this.editMode.set(true);
        this.lazyExperimentDataModel.releaseSelectedExperiment();
        this.renderMessage(FacesMessage.SEVERITY_INFO, "Experiment wurde veröffentlicht");
        this.data = lazyExperimentDataModel.gatherData();
    }

    public void deleteExperiment(long id){
        this.editMode.set(false);
        this.lazyExperimentDataModel.deleteExperimentData(id);
        this.renderMessage(FacesMessage.SEVERITY_INFO, "Experiment wurde gelöscht");
        this.data = lazyExperimentDataModel.gatherData();
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
}

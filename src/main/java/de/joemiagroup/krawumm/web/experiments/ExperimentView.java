package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.*;
import de.joemiagroup.krawumm.repositories.bookmarks.BookmarkRepository;
import de.joemiagroup.krawumm.repositories.comments.CommentRepository;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.repositories.instructions.InstructionRepository;
import de.joemiagroup.krawumm.repositories.pictures.PicturesRepository;
import de.joemiagroup.krawumm.repositories.ratings.RatingRepository;
import de.joemiagroup.krawumm.web.BaseView;
import de.joemiagroup.krawumm.web.IndexView;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean("experimentView")
@ViewScoped
@Getter
@Setter
public class ExperimentView extends BaseView<Experiment> {
    @Autowired
    public ExperimentView(final ExperimentRepository repository, final RatingRepository ratingRepository,
                          final PicturesRepository picturesRepository, final InstructionRepository instructionRepository,
                          final CommentRepository commentRepository, final BookmarkRepository bookmarkRepository) {
        this.lazyExperimentDataModel = LazyExperimentDataModel.of(repository, ratingRepository, picturesRepository, instructionRepository,
                                                                  commentRepository, bookmarkRepository);
        this.data = lazyExperimentDataModel.gatherData();
    }

    //Creating an inner class for the instruction set-up
    @Getter
    @Setter
    public static class Layout {
        public int position;
        public String text;

        public Layout(int position, String text) {
            this.position = position;
            this.text = text;
        }
    }

    private List<Layout> instructions;
    private List<Layout> materials;
    private List<Layout> pictures;
    @Getter
    @Setter
    private List<String> instructionText;
    private List<String> materialText;
    private List<String> pictureText;


    private List<ExperimentDataView> data;
    private String search = "";
    private int numberInstruction = 1;
    private int numberMaterial = 1;
    private int numberPicture = 1;

    private final LazyExperimentDataModel lazyExperimentDataModel;

    @PostConstruct
    public void init() {
        instructions = new ArrayList<>();
        instructionText = new ArrayList<>();
        instructionText.add("");
        instructions.add(new Layout(1, instructionText.get(0)));
        materials = new ArrayList<>();
        materialText = new ArrayList<>();
        materialText.add("");
        materials.add(new Layout(1, materialText.get(0)));
        pictures = new ArrayList<>();
        pictureText = new ArrayList<>();
        pictureText.add("");
        pictures.add(new Layout(1, pictureText.get(0)));
    }

    public void incrementInstruction() {
        numberInstruction++;
        instructionText.add("");
        instructions.add(new Layout(numberInstruction, instructionText.get(numberInstruction -1)));
    }
    public void decreaseInstruction() {
        if(numberInstruction >1){
            instructions.remove((numberInstruction -1));
            instructionText.remove((numberInstruction -1));
            numberInstruction--;
        }
    }

    public void incrementMaterial() {
        numberMaterial++;
        materialText.add("");
        materials.add(new Layout(numberMaterial, materialText.get(numberMaterial-1)));
    }
    public void decreaseMaterial() {
        if(numberMaterial >1) {
            materials.remove((numberMaterial - 1));
            materialText.remove((numberMaterial -1));
            numberMaterial--;
        }
    }

    public void updateMaterial(SelectEvent<String> event, int position){
        Layout layout = new Layout (position, event.getObject());
        materials.set(position-1,layout);
    }

    public void incrementPicture() {
        numberPicture++;
        pictureText.add("");
        pictures.add(new Layout(numberPicture, pictureText.get(numberPicture-1)));
    }
    public void decreasePicture() {
            pictures.remove((numberPicture - 1));
            pictureText.remove((numberPicture-1));
            numberPicture--;
    }

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
        this.lazyExperimentDataModel.createExperiment(user);
        Experiment experiment = this.lazyExperimentDataModel.getLastInsertedExperiment();
        for(Layout instruction : this.instructions){

        }
    }

}

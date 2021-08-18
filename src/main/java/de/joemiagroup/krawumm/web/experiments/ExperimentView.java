package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.*;
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
                          final CommentRepository commentRepository) {
        this.lazyExperimentDataModel = LazyExperimentDataModel.of(repository, ratingRepository, picturesRepository, instructionRepository,
                                                                  commentRepository);
        this.data = lazyExperimentDataModel.gatherData();
    }

    //Creating an inner class for the instruction set-up
    public static class Layout {
        public int position;
        public String text;

        public Layout(int position, String text) {
            this.position = position;
            this.text = text;
        }
    }

    private List<Layout> instructions;
    private String newText;
    private Instruction instruction = new Instruction();

    private List<ExperimentDataView> data;
    @Getter
    @Setter
    private String search = "";

    private int number = 1;

    private final LazyExperimentDataModel lazyExperimentDataModel;

    @PostConstruct
    public void init() {
        instructions = new ArrayList<>();
        instructions.add(new Layout(1, newText));
    }

    public void increment() {
        number++;
        instructions.add(new Layout(number, newText));
    }

    public void onExperimentSelect(SelectEvent<ExperimentDataView> event) {
        this.lazyExperimentDataModel.setNewData(event.getObject());
    }

    public void searchForStringInName () {
        this.data = lazyExperimentDataModel.usingSearch(search);
        this.search = "";
    }

    public void writeComment (long experimentId, RegisteredUser user) {
        this.lazyExperimentDataModel.writeComment(experimentId, user);
        this.data = lazyExperimentDataModel.gatherData();
    }
}

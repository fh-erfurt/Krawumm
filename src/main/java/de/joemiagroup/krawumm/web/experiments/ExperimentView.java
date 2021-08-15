package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Instruction;
import de.joemiagroup.krawumm.repositories.experiments.ExperimentRepository;
import de.joemiagroup.krawumm.web.BaseView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean("experimentView")
@ViewScoped
public class ExperimentView extends BaseView<Experiment> {
    @Autowired
    public ExperimentView(final ExperimentRepository repository) {
        this.lazyExperimentDataModel = LazyExperimentDataModel.of(repository);
    }

    //Creating an inner class for the instruction set-up
    public class Layout {
        @Getter
        public int position;
        @Getter
        @Setter
        public String text;

        public Layout(int position, String text) {
            this.position = position;
            this.text = text;
        }
    }

    private List<Layout> instructions;
    private String newText;
    private Instruction instruction = new Instruction();

    private int number = 1;
    @Getter
    @Setter
    private int age;
    @Getter
    @Setter
    private int time;

    @Getter
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

    public List<Layout> getInstructions() {
        return instructions;
    }
}

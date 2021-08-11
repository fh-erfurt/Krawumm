package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Instruction;
import de.joemiagroup.krawumm.web.BaseView;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;

@ManagedBean("experimentView")
@ViewScoped
public class ExperimentView extends BaseView<Experiment> {
    //private List<String> instructions;
    private int number;

    public int getNumber() {
        return number;
    }

    public void create() {
        number++;
    }
}

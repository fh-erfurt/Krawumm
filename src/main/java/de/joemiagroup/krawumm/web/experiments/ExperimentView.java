package de.joemiagroup.krawumm.web.experiments;

import de.joemiagroup.krawumm.domains.Experiment;
import de.joemiagroup.krawumm.domains.Instruction;
import de.joemiagroup.krawumm.web.BaseView;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean("experimentView")
@ViewScoped
public class ExperimentView extends BaseView<Experiment> {
    //Creating an inner class for the instruction set-up
    public class Layout {
        public int position;
        public String text;

        public Layout(int position, String text) {
            this.position = position;
            this.text = text;
        }

        public int getPosition() {
            return position;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private List<Layout> instructions;
    private String newText;
    private Instruction instruction = new Instruction();

    private int number = 1;
    private int age;
    private int time;

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

    public int getNumber() {
        return number;
    }

    public int getAge() {
        return age;
    }

    public int getTime() {
        return time;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

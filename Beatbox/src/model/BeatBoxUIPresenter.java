package model;

import model.BeatBoxUIModel;
import view.BeatBoxView;

import javax.swing.*;
import java.util.ArrayList;

public class BeatBoxUIPresenter {
    private final BeatBoxUIModel model;
    private final BeatBoxView view;

    public BeatBoxUIPresenter() {
        this.model = new BeatBoxUIModel();
        this.view = new BeatBoxView();
    }

    public ArrayList<JCheckBox> fetchCheckBoxList() {
        return model.getCheckBoxList();
    }

    public void setCheckBoxList(ArrayList<JCheckBox> checkBoxList) {
        model.setCheckBoxList(checkBoxList);
    }

}

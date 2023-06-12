package model;

import view.BeatBoxView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BeatBoxUIPresenter {
    private final BeatBoxUIModel model;

    public BeatBoxUIPresenter() {
        this.model = new BeatBoxUIModel();
    }

    public String[] fetchInstrumentNames() {
        return model.getInstrumentNames();
    }

    public ArrayList<JCheckBox> fetchCheckBoxList() {
        return model.getCheckBoxList();
    }

    public void setCheckBoxList(ArrayList<JCheckBox> checkBoxList) {
        model.setCheckBoxList(checkBoxList);
    }
}

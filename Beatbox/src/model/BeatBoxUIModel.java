package model;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BeatBoxUIModel {
    private static BeatBoxUIModel instance = null;
    String[] instrumentNames = {"Bass Drum", "Closed Hi-hat", "Open Hi-hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "High Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open High Conga"};
    ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();

    private BeatBoxUIModel() {

    }

    public static BeatBoxUIModel getInstance() {
        if (instance == null) {
            instance = new BeatBoxUIModel();
        }
        return instance;
    }


    protected String[] getInstrumentNames() {
        return instrumentNames;
    }
    protected ArrayList<JCheckBox> getCheckBoxList() {
        return checkBoxList;
    }
    protected void setCheckBoxList(ArrayList<JCheckBox> checkBoxList) {
        this.checkBoxList = checkBoxList;
    }
}

package model;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BeatBoxUIModel {
    String[] instrumentNames = {"Bass Drum", "Closed Hi-hat", "Open Hi-hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "High Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open High Conga"};
    ArrayList<JCheckBox> checkBoxList = null;

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

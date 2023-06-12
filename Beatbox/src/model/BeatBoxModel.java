package model;

import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.swing.*;
import java.util.ArrayList;

public class BeatBoxModel {
    JPanel mainPanel;
    JFrame theFrame;
    String[] instrumentNames = {"Bass Drum", "Closed Hi-hat", "Open Hi-hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "High Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open High Conga"};
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
}

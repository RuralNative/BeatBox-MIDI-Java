import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class BeatBox {
    JPanel mainPanel;
    JFrame theFrame;
    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    String[] instrumentNames = {"Bass Drum", "Closed Hi-hat", "Open Hi-hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "High Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open High Conga"};
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

    public void buildGUI() {
        //Instantiates the JFrame of the Application with necessary properties
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Instantiates the main JPanel with BorderLayout and Border
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Instantiates CheckBox List
        checkBoxList = new ArrayList<JCheckBox>();

        //Instantiate Box to contain Buttons
        Box buttonBox = new Box(Y_AXIS);

        //Instantiates and adds Buttons to our Box (buttonBox)
        JButton start = new JButton("Start");
        buttonBox.add(start);
        JButton stop = new JButton("Stop");
        buttonBox.add(stop);
        JButton upTempo = new JButton("Tempo Up");
        buttonBox.add(upTempo);
        JButton downTempo = new JButton("Tempo Down");
        buttonBox.add(downTempo);

        //Instantiates Box to contain 16 labels created from a for loop and based from an array of String of instrument names
        Box nameBox = new Box(Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        //Add all containers - buttonBox and nameBox - to our JPanel (background)
        background.add(BorderLayout.EAST, buttonBox);

        background.add(BorderLayout.WEST, nameBox);
        //Add main JPanel to JFrame
        theFrame.getContentPane().add(background);

        //Create a GridLayout of 16x16 dimension as Layout for a JPanel (mainPanel) and adds it to the main JPanel
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        // Using a for loop, instantiates 256 unchecked JCheckboxes that will be added to an ArrayList of JCheckboxes (checkBoxList) and to the JPanel (mainPanel)
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        //Calls function setUpMIDI()
        setUpMIDI();

        //Set JFrame properties
        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMIDI() {
        //Sets value for Sequencer, Sequence, and Track with a set tempo with try/catch code block
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart() {
        //Create array of integers to hold values for an instrument across 16 beats, and will be played later
        int[] trackList = null;

        //Delete old track, and make a new one
        sequence.deleteTrack(track);
        track = sequence.createTrack();
    }
}
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        //Instantiates and adds Buttons to our Box (buttonBox), then attach listeners to each of them
        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
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

        //For loop to create a trackList of integer values for each instrument defined in the integer[] array;
        for (int i = 0; i < 16; i++) {
            //Set value of trackList as an integer array of size 16, which may hold only two possible value options: 0 or the equivalent integer of the instrument i.e. 35 for Bass drum/42 for Closed Hi-hat
            trackList = new int[16];

            //Set the value of an integer variable to the equivalent integer value from instrument array of position i; for example if the i is equal to 0, it shall return 35 of instrument Bass Drum
            int key = instruments[i];

            //For loop to set the 16 values of the trackList by checking whether the JCheckBox belonging to a particular instrument is checked or not; if checked then add the value of key to the tracklist, if not then it will be set to 0
            for (int j = 0; j < 16; j++) {
                //Get the position of the JCheckBox of the instrument i.e. Bass drum instrument own the 1st to 16th JCheckboxes (0-15), while the Open Hi-hat - the 3rd instrument - own the 33rd and 48th JCheckboxes (32-47)
                JCheckBox jc = (JCheckBox) checkBoxList.get(j + (16 * i));
                //If loop to check if JCheckbox is selected and adding value to the tracklist of the instrument (either the value of the instrument key or 0)
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            //Convert trackList into playable MIDI events
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }
        track.add(makeEvent(192, 9, 1, 0, 15));
        //Add Sequence and play the Sequencer
        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Adds events for an instrument for all 16 beats, with additional NOTE ON and NOTE OFF events based from the trackList made for the same instrument
    public void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];

            //If key is not equal to 0, then add the NOTE ON and NOTE OFF events in the track
            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    }
}
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class BeatBox {

    ArrayList<JCheckBox> checkBoxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        new BeatBox().buildGUI();
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

    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }
}